package com.qin.tao.share.controller.activity.pay;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseEditText;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.model.pay.PayData;
import com.qin.tao.share.model.pay.PayInfo;
import com.qin.tao.share.widget.title.TitleView;

/**
 * 赞赏Activity
 *
 * @date 2017-9-17
 */
public class AppreciateActivity extends BaseActivity {

    private TitleView mTitleView;
    private ImageView iv_dice;
    private BaseTextView btv_comment, btv_update_account;
    private BaseTextView btv_money_amount;
    private BaseEditText bet_money_amount;
    private LinearLayout ll_dice, ll_show_amount;
    private Button submit_money;
    private PayInfo payInfo;
    private AnimationDrawable mDiceAnimDrawable;
    private boolean isEdit = false;
    private View maskView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void receiveParam() {


    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_reward_set_layout);
        // 标题初始化
        mTitleView = ((TitleView) findViewById(R.id.titleView));
        mTitleView.setTitle("赞赏").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {

            }
        });
        maskView = findViewById(R.id.maskView);
        maskView.setOnClickListener(onBaseClickListener);
        iv_dice = (ImageView) findViewById(R.id.iv_dice);
        ll_show_amount = (LinearLayout) findViewById(R.id.ll_show_amount);
        btv_comment = (BaseTextView) findViewById(R.id.btv_comment);
        btv_money_amount = (BaseTextView) findViewById(R.id.btv_money_amount);
        btv_money_amount.setEnabled(false);
        bet_money_amount = (BaseEditText) findViewById(R.id.bet_money_amount);
        bet_money_amount.addTextChangedListener(new InnerTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String source = s.toString().trim();
                int index = source.indexOf(".");
                if (index == -1 && source.length() > 4 || index > 4) {
                    // 整数部分最多4位
                    s.replace(startIndex, startIndex + changeSize, "");
                }
            }
        });
        btv_update_account = (BaseTextView) findViewById(R.id.btv_update_account);
        btv_update_account.setOnClickListener(onBaseClickListener);
        ll_dice = (LinearLayout) findViewById(R.id.ll_dice);
        ll_dice.setOnClickListener(onBaseClickListener);
        submit_money = (Button) findViewById(R.id.sbmit_money);
        submit_money.setOnClickListener(onBaseClickListener);
    }

    @Override
    public void initData() {
        PayData.getInstance().initPayInfo();
        payInfo = PayData.getInstance().getRoundPayInfo(null);
        btv_money_amount.setText(String.valueOf(payInfo.getAmountString()));
        btv_comment.setText(payInfo.getDescription());
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            switch (v.getId()) {
                case R.id.maskView:
                    break;
                case R.id.btv_update_account:
                    if (!isEdit) {
                        isEdit = true;
                        ll_show_amount.setVisibility(View.GONE);
                        bet_money_amount.setText("");
                        bet_money_amount.setVisibility(View.VISIBLE);
                        btv_update_account.setText("确定");
                    } else {
                        isEdit = false;
                        btv_update_account.setText(getString(R.string.modify_amount));
                        ll_show_amount.setVisibility(View.VISIBLE);
                        bet_money_amount.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(bet_money_amount.getText().toString())) {
                            String value = bet_money_amount.getText().toString();
                            payInfo.setAmount((int) (formatNumber(value) * 100));
                            btv_money_amount.setText(payInfo.getAmountString());
                        }
                    }
                    break;
                case R.id.ll_dice:
                    startAnimation();
                    break;
                case R.id.sbmit_money:
                    float amount = formatNumber(btv_money_amount.getText().toString());
                    if (amount * 100 > 0) {
                        Intent intent = new Intent(AppreciateActivity.this, ShopPayActivity.class);
                        intent.putExtra(IntentKey.AMOUNT, btv_money_amount.getText().toString());
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 开始金额动画
     *
     * @param startAmount
     * @param endAmount
     */
    private void startAmountAnimation(final float startAmount, final float endAmount, final PayInfo info) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startAmount, endAmount);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = formatNumber(valueAnimator.getAnimatedValue().toString());
                btv_money_amount.setText(payInfo.getAmountString(value));
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setMaskVisibility(true);
                payInfo.setAmount(info.getAmount());
                payInfo.setDescription(info.getDescription());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setMaskVisibility(false);
                // 停止股子动画与改变股子背景
                if (mDiceAnimDrawable != null) {
                    mDiceAnimDrawable.stop();
                }
                iv_dice.setImageResource(R.drawable.dice_13);
                btv_comment.setText(payInfo.getDescription());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                setMaskVisibility(false);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    /**
     * 开始金额动画
     */
    private void startAmountAnimation() {
        // 获取一个随机金额
        final PayInfo info = PayData.getInstance().getRoundPayInfo(payInfo);
        float startAmount = formatNumber(payInfo.getAmountString());
        float tarAmount = formatNumber(info.getAmountString());
        startAmountAnimation(startAmount, tarAmount, info);
    }

    /**
     * 开启骰子动画
     */
    private void startDiceAnimation() {
        if (iv_dice == null) {
            return;
        }

        iv_dice.setImageResource(R.drawable.dice_anim);
        mDiceAnimDrawable = (AnimationDrawable) iv_dice.getDrawable();

        if (mDiceAnimDrawable != null) {
            // 在动画start()之前要先stop()，不然在第一次动画之后会停在最后一帧，这样动画就只会触发一次。
            mDiceAnimDrawable.stop();
            mDiceAnimDrawable.start();
        }
    }

    /**
     * 骰子点击事件
     */
    private void startAnimation() {
        startDiceAnimation();
        startAmountAnimation();
    }

    /**
     * 格式化数字字符串(最多保留2位小数）
     *
     * @param numberStr
     * @return
     */
    private float formatNumber(String numberStr) {
        float amount = 0;
        if (!TextUtils.isEmpty(numberStr)) {
            try {
                // 最多保留2位小数
                int index = numberStr.indexOf(".");
                if (index != -1 && index + 3 < numberStr.length()) {
                    numberStr = numberStr.substring(0, index + 3);
                }
                amount = Float.parseFloat(numberStr);
            } catch (NumberFormatException e) {
            }
        }
        return amount;
    }

    public void setMaskVisibility(boolean maskVisibility) {
        maskView.setVisibility(maskVisibility ? View.VISIBLE : View.GONE);
    }

    private class InnerTextWatcher implements TextWatcher {
        protected int startIndex = 0;
        protected int changeSize = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            startIndex = start;
            changeSize = after - count;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
