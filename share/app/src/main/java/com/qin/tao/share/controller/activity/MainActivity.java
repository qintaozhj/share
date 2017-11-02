package com.qin.tao.share.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.log.Logger;
import com.qin.tao.share.app.utils.ToastUtils;
import com.qin.tao.share.controller.activity.ad.LocalADManager;
import com.qin.tao.share.controller.activity.gank.WelFareActivity;
import com.qin.tao.share.controller.activity.juhe.JokeImageActivity;
import com.qin.tao.share.controller.activity.juhe.JokeTextActivity;
import com.qin.tao.share.controller.activity.juhe.JokeTopLineActivity;
import com.qin.tao.share.controller.activity.juhe.JokeWeChatActivity;
import com.qin.tao.share.controller.activity.more.AboutMeActivity;
import com.qin.tao.share.controller.activity.pay.ShopPayActivity;
import com.qin.tao.share.controller.activity.web.WebViewActivity;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushServiceV3;

import java.util.UUID;

import ddd.eee.fff.nm.cm.ErrorCode;
import ddd.eee.fff.nm.sp.SpotManager;
import ddd.eee.fff.nm.vdo.VideoAdListener;
import ddd.eee.fff.nm.vdo.VideoAdManager;
import ddd.eee.fff.nm.vdo.VideoAdSettings;

/**
 * 主activity
 */
public class MainActivity extends BaseActivity {

    private String url;
    private String title;
    private View rl_recommend, adLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XGPushConfig.enableDebug(this, false);
        //信鸽推送注册
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override

            public void onSuccess(Object data, int flag) {
                Logger.d("TPush", "注册成功，设备token为：" + data);
            }

            @Override

            public void onFail(Object data, int errCode, String msg) {
                Logger.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        //（2.36之前的版本）已知MIUI V6上会禁用所有静态广播，若出现有类似的情况，请添加以下代码兼容该系统。
        Context context = getApplicationContext();
        Intent service = new Intent(context, XGPushServiceV3.class);
        context.startService(service);
    }

    @Override
    public void receiveParam() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(IntentKey.KEY_FROM)) {
            String key = intent.getStringExtra(IntentKey.KEY_FROM);
            if (TextUtils.equals(key, "XG")) {
                this.url = intent.getStringExtra(IntentKey.KEY_URL);
                if (!TextUtils.isEmpty(this.url) && !URLUtil.isNetworkUrl(this.url))
                    this.url = "http://" + this.url;
                this.title = intent.getStringExtra(IntentKey.KEY_TITLE);
                toWebView();
            }
        }
    }

    private void toWebView() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(IntentKey.KEY_URL, url);
        intent.putExtra(IntentKey.KEY_TITLE, title);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.hasExtra(IntentKey.KEY_FROM)) {
            String key = intent.getStringExtra(IntentKey.KEY_FROM);
            if (TextUtils.equals(key, "XG")) {
                this.url = intent.getStringExtra(IntentKey.KEY_URL);
                if (!TextUtils.isEmpty(this.url) && !URLUtil.isNetworkUrl(this.url))
                    this.url = "http://" + this.url;
                this.title = intent.getStringExtra(IntentKey.KEY_TITLE);
                toWebView();
            }
        }
    }

    @Override
    public void initData() {
        if (LocalADManager.isShowAd) {
            rl_recommend.setVisibility(View.VISIBLE);
            adLine.setVisibility(View.VISIBLE);
            preloadData();
        }
        //百度更新检查
        BDAutoUpdateSDK.uiUpdateAction(MainActivity.this, new MyUICheckUpdateCallback());
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        findViewById(R.id.welfare).setOnClickListener(onBaseClickListener);
        findViewById(R.id.tv_joke_text).setOnClickListener(onBaseClickListener);
        findViewById(R.id.tv_joke_mig).setOnClickListener(onBaseClickListener);
        findViewById(R.id.reward).setOnClickListener(onBaseClickListener);
        findViewById(R.id.topLine).setOnClickListener(onBaseClickListener);
        findViewById(R.id.wechat).setOnClickListener(onBaseClickListener);
        findViewById(R.id.rl_about_me).setOnClickListener(onBaseClickListener);
        rl_recommend = findViewById(R.id.rl_recommend);
        adLine = findViewById(R.id.adLine);
        rl_recommend.setOnClickListener(onBaseClickListener);
        rl_recommend.setVisibility(View.GONE);
        adLine.setVisibility(View.GONE);
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            switch (v.getId()) {
                case R.id.welfare:
                    startActivity(new Intent(MainActivity.this, WelFareActivity.class));
                    break;
                case R.id.reward:
                    startActivity(new Intent(MainActivity.this, ShopPayActivity.class));
                    break;
                case R.id.topLine:
                    startActivity(new Intent(MainActivity.this, JokeTopLineActivity.class));
                    break;
                case R.id.wechat:
                    startActivity(new Intent(MainActivity.this, JokeWeChatActivity.class));
                    break;
                case R.id.tv_joke_text:
                    startActivity(new Intent(MainActivity.this, JokeTextActivity.class));
                    break;
                case R.id.tv_joke_mig:
                    startActivity(new Intent(MainActivity.this, JokeImageActivity.class));
                    break;
                case R.id.rl_about_me:
                    startActivity(new Intent(MainActivity.this, AboutMeActivity.class));
                    break;
                case R.id.rl_recommend:
                    setupVideoAd();
                    break;
            }

        }
    };


    /**
     * 预加载数据
     */
    private void preloadData() {
        String uuid = UUID.randomUUID().toString();
        VideoAdManager.getInstance(this).setUserId(uuid);
        VideoAdManager.getInstance(this).requestVideoAd(this);
    }

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
                        ToastUtils.showText(MainActivity.this, "播放视频被中断");
                    }

                    @Override
                    public void onPlayFailed(int errorCode) {
                        Logger.d("视频播放失败");
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                ToastUtils.showText(MainActivity.this, "网络异常");
                                break;
                            case ErrorCode.NON_AD:
                                ToastUtils.showText(MainActivity.this, "视频暂无内容");
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                ToastUtils.showText(MainActivity.this, "视频资源还没准备好");
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                ToastUtils.showText(MainActivity.this, "视频展示间隔限制");
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                ToastUtils.showText(MainActivity.this, "视频控件处在不可见状态");
                                break;
                            default:
                                ToastUtils.showText(MainActivity.this, "请稍后再试");
                                break;
                        }
                    }

                    @Override
                    public void onPlayCompleted() {
                        ToastUtils.showText(MainActivity.this, "视频播放成功");
                    }
                });
    }


    private class MyUICheckUpdateCallback implements UICheckUpdateCallback {

        @Override
        public void onNoUpdateFound() {
            Logger.d("没有新版本");
        }

        @Override
        public void onCheckComplete() {
            Logger.d("版本检查完毕");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(this).onAppExit();
        VideoAdManager.getInstance(this).onAppExit();
    }
}
