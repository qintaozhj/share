package com.qin.tao.share.controller.activity.ad;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.log.Logger;
import com.qin.tao.share.app.utils.ToastUtils;

import ddd.eee.fff.nm.cm.ErrorCode;
import ddd.eee.fff.nm.vdo.VideoAdListener;
import ddd.eee.fff.nm.vdo.VideoAdManager;
import ddd.eee.fff.nm.vdo.VideoAdSettings;


/**
 * @author qintao on 2017/9/21 19:39
 */

public class RecommendActivity extends BaseActivity {

    @Override
    public void receiveParam() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_recomend);
    }

    @Override
    public void initData() {
        setupVideoAd();
    }

    /**
     * 设置视频s
     */
    private void setupVideoAd() {
        final VideoAdSettings videoAdSettings = new VideoAdSettings();
        videoAdSettings.setInterruptTips("视频还没有播放完成" + "\n确定要退出吗？");
        VideoAdManager.getInstance(this)
                .showVideoAd(this, videoAdSettings, new VideoAdListener() {
                    @Override
                    public void onPlayStarted() {
                        Logger.d("开始播放视频");
                    }

                    @Override
                    public void onPlayInterrupted() {
                        ToastUtils.showText(RecommendActivity.this, "播放视频被中断");
                    }

                    @Override
                    public void onPlayFailed(int errorCode) {
                        Logger.d("视频播放失败");
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                ToastUtils.showText(RecommendActivity.this, "网络异常");
                                break;
                            case ErrorCode.NON_AD:
                                ToastUtils.showText(RecommendActivity.this, "视频暂无内容");
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                ToastUtils.showText(RecommendActivity.this, "视频资源还没准备好");
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                ToastUtils.showText(RecommendActivity.this, "视频展示间隔限制");
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                ToastUtils.showText(RecommendActivity.this, "视频控件处在不可见状态");
                                break;
                            default:
                                ToastUtils.showText(RecommendActivity.this, "请稍后再试");
                                break;
                        }
                    }

                    @Override
                    public void onPlayCompleted() {
                        ToastUtils.showText(RecommendActivity.this, "视频播放成功");
                    }
                });
    }


}
