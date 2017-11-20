package com.qin.tao.share.controller.fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseFragment;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.log.Logger;
import com.qin.tao.share.app.utils.DipPxConversion;
import com.qin.tao.share.app.utils.ToastUtils;
import com.qin.tao.share.controller.activity.ad.LocalADManager;
import com.qin.tao.share.controller.activity.gank.WelFareActivity;
import com.qin.tao.share.controller.activity.juhe.JokeImageActivity;
import com.qin.tao.share.controller.activity.juhe.JokeTextActivity;
import com.qin.tao.share.controller.activity.juhe.JokeWeChatActivity;
import com.qin.tao.share.controller.activity.more.AboutMeActivity;
import com.qin.tao.share.controller.activity.pay.ShopPayActivity;

import ddd.eee.fff.nm.cm.ErrorCode;
import ddd.eee.fff.nm.vdo.VideoAdListener;
import ddd.eee.fff.nm.vdo.VideoAdManager;
import ddd.eee.fff.nm.vdo.VideoAdSettings;

/**
 * @author qintao on 2017/11/9 15:19
 */

public class UserFragment extends BaseFragment {
    private View mParentView = null;
    private Activity mActivity;
    private ValueAnimator animator;
    private BaseTextView tipView;
    private float currentAnimatedValue = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mParentView == null) {
            mParentView = inflater.inflate(R.layout.activity_user_layout, container, false);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) mParentView.getParent();
            if (parent != null) {
                parent.removeView(mParentView);
            }
        }
        requestData();
        return mParentView;
    }

    private void initView() {
        mActivity = getActivity();
        mParentView.findViewById(R.id.rl_welfare).setOnClickListener(onBaseClickListener);
        mParentView.findViewById(R.id.rl_joke_text).setOnClickListener(onBaseClickListener);
        mParentView.findViewById(R.id.rl_joke_img).setOnClickListener(onBaseClickListener);
        mParentView.findViewById(R.id.rl_weChat).setOnClickListener(onBaseClickListener);
        mParentView.findViewById(R.id.rl_recommend).setOnClickListener(onBaseClickListener);
        mParentView.findViewById(R.id.rl_reward).setOnClickListener(onBaseClickListener);
        mParentView.findViewById(R.id.rl_about_me).setOnClickListener(onBaseClickListener);
        tipView = (BaseTextView) mParentView.findViewById(R.id.tipView);
        startAnimator();
    }

    private void requestData() {
    }


    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            if (mActivity == null || mActivity.isFinishing())
                return;
            switch (v.getId()) {
                case R.id.rl_welfare:
                    startActivity(new Intent(mActivity, WelFareActivity.class));
                    break;
                case R.id.rl_reward:
                    startActivity(new Intent(mActivity, ShopPayActivity.class));
                    break;
                case R.id.rl_weChat:
                    startActivity(new Intent(mActivity, JokeWeChatActivity.class));
                    break;
                case R.id.rl_joke_text:
                    startActivity(new Intent(mActivity, JokeTextActivity.class));
                    break;
                case R.id.rl_joke_img:
                    startActivity(new Intent(mActivity, JokeImageActivity.class));
                    break;
                case R.id.rl_about_me:
                    startActivity(new Intent(mActivity, AboutMeActivity.class));
                    break;
                case R.id.rl_recommend:
                    setupVideoAd();
                    break;
            }

        }
    };

    /**
     * 设置视频s
     */
    private void setupVideoAd() {
        if (mActivity == null || mActivity.isFinishing())
            return;
        if (!LocalADManager.isShowAd) {
            ToastUtils.showText(mActivity, "暂无资源，请稍后在看吧");
            return;
        }
        final VideoAdSettings videoAdSettings = new VideoAdSettings();
        videoAdSettings.setInterruptTips("视频还没有播放完成" + "\n确定要退出吗？");
        VideoAdManager.getInstance(mActivity)
                .showVideoAd(mActivity, videoAdSettings, new VideoAdListener() {
                    @Override
                    public void onPlayStarted() {
                        Logger.d("开始播放视频");
                    }

                    @Override
                    public void onPlayInterrupted() {
                        ToastUtils.showText(mActivity, "播放视频被中断");
                    }

                    @Override
                    public void onPlayFailed(int errorCode) {
                        Logger.d("视频播放失败");
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                ToastUtils.showText(mActivity, "网络异常");
                                break;
                            case ErrorCode.NON_AD:
                                ToastUtils.showText(mActivity, "视频暂无内容");
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                ToastUtils.showText(mActivity, "视频资源还没准备好");
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                ToastUtils.showText(mActivity, "视频展示间隔限制");
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                ToastUtils.showText(mActivity, "视频控件处在不可见状态");
                                break;
                            default:
                                ToastUtils.showText(mActivity, "请稍后再试");
                                break;
                        }
                    }

                    @Override
                    public void onPlayCompleted() {
                        ToastUtils.showText(mActivity, "视频播放成功");
                    }
                });
    }

    /**
     * 设置开始动画
     */
    private void startAnimator() {
        final int viewHeight = DipPxConversion.dip2px(mActivity, 40);
        animator = ValueAnimator.ofFloat(0f, 1f).setDuration(800);
        animator.setStartDelay(350);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAnimatedValue = (float) animation.getAnimatedValue();
                if (currentAnimatedValue <= 1.0) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tipView.getLayoutParams();
                    params.setMargins(0, (int) (viewHeight * currentAnimatedValue) - viewHeight, 0, 0);
                    tipView.setLayoutParams(params);
                    tipView.requestLayout();
                    if (tipView.getVisibility() == View.GONE)
                        tipView.setVisibility(View.VISIBLE);
                }
            }
        });
        animator.start();
    }

}
