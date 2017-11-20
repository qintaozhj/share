package com.qin.tao.share.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.log.Logger;
import com.qin.tao.share.controller.activity.ad.LocalADManager;
import com.qin.tao.share.controller.activity.web.WebViewActivity;
import com.qin.tao.share.controller.fragment.HomeFragment;
import com.qin.tao.share.controller.fragment.UserFragment;
import com.qin.tao.share.widget.tabview.TabSwitcher;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushServiceV3;

import java.util.UUID;

import ddd.eee.fff.nm.sp.SpotManager;
import ddd.eee.fff.nm.vdo.VideoAdManager;

/**
 * 主activity
 */
public class MainActivity extends BaseActivity {

    private String url;
    private String title;
    private TabSwitcher tabSwitcher;
    private HomeFragment homeFragment;
    private UserFragment userFragment;

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
            preloadData();
        }
        //百度更新检查
        BDAutoUpdateSDK.uiUpdateAction(MainActivity.this, new MyUICheckUpdateCallback());
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main_layout);

        tabSwitcher = (TabSwitcher) findViewById(R.id.tabSwitcher);
        tabSwitcher.addTab("首页", getResources().getDrawable(R.drawable.home_default),
                getResources().getDrawable(R.drawable.home_select));
        tabSwitcher.addTab("我的", getResources().getDrawable(R.drawable.me_default),
                getResources().getDrawable(R.drawable.me_select));
        tabSwitcher.setOnTabChangedListener(new TabSwitcher.OnTabChangedListener() {
            @Override
            public void onTabChanged(int last, int current) {
                showFragment(current);
            }
        });
        showFragment(0);
    }


    private void showFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (index == 0) {
            if (homeFragment == null)
                homeFragment = new HomeFragment();
            transaction.replace(R.id.ll_container, homeFragment);
        } else {
            if (userFragment == null)
                userFragment = new UserFragment();
            transaction.replace(R.id.ll_container, userFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 预加载数据
     */
    private void preloadData() {
        String uuid = UUID.randomUUID().toString();
        VideoAdManager.getInstance(this).setUserId(uuid);
        VideoAdManager.getInstance(this).requestVideoAd(this);
    }

    private void toWebView() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(IntentKey.KEY_URL, url);
        intent.putExtra(IntentKey.KEY_TITLE, title);
        startActivity(intent);
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
        VideoAdManager.getInstance(this).onAppExit();
        SpotManager.getInstance(this).onAppExit();
    }
}
