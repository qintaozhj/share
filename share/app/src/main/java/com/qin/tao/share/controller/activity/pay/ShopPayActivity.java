package com.qin.tao.share.controller.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.utils.ToastUtils;
import com.qin.tao.share.controller.pay.AlipayZeroSdk;
import com.qin.tao.share.model.pay.PayData;
import com.qin.tao.share.widget.title.TitleView;

/**
 * @author qintao
 * @time 2017/7/21 12:00
 * 打赏支付页面
 */
public class ShopPayActivity extends BaseActivity {
    private BaseTextView payMoney, select_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void receiveParam() {
    }

    @Override
    public void initViews() {
        setContentView(R.layout.shop_pay_activity);
        TitleView titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("打赏").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {

            }
        });
        payMoney = (BaseTextView) findViewById(R.id.pay_money_tx);
        select_text = (BaseTextView) findViewById(R.id.select_text);
        findViewById(R.id.alipay_layout).setOnClickListener(onBaseClickListener);
        findViewById(R.id.wechat_layout).setOnClickListener(onBaseClickListener);
        findViewById(R.id.alipay_scan_layout).setOnClickListener(onBaseClickListener);
    }

    @Override
    public void initData() {
        PayData.getInstance().initData();
        String info = PayData.getInstance().getRoundString();
        select_text.setText(info);
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            switch (v.getId()) {
                case R.id.alipay_layout:
                    if (AlipayZeroSdk.hasInstalledAlipayClient(ShopPayActivity.this))
                        AlipayZeroSdk.startAlipayClient(ShopPayActivity.this, "FKX08543PUUVPPESYCUP3C");
                    else
                        ToastUtils.showText(ShopPayActivity.this, "您没有安装支付宝噢");
                    break;
                case R.id.wechat_layout:
                    Intent intent = new Intent(ShopPayActivity.this, PayScanActivity.class);
                    intent.putExtra(IntentKey.PAY_TYPE, 1);
                    startActivity(intent);
                    break;
                case R.id.alipay_scan_layout:
                    Intent intent_alipay = new Intent(ShopPayActivity.this, PayScanActivity.class);
                    intent_alipay.putExtra(IntentKey.PAY_TYPE, 2);
                    startActivity(intent_alipay);
                    break;
            }
        }
    };


}
