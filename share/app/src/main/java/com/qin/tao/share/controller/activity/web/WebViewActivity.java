package com.qin.tao.share.controller.activity.web;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.config.FinalConfig;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.log.Logger;
import com.qin.tao.share.app.utils.ToastUtils;
import com.qin.tao.share.controller.activity.ad.LocalADManager;
import com.qin.tao.share.widget.title.TitleView;

import java.util.Timer;
import java.util.TimerTask;

import ddd.eee.fff.nm.sp.SpotListener;
import ddd.eee.fff.nm.sp.SpotManager;

/**
 * Created by Administrator on 2017/9/24.
 */
public class WebViewActivity extends BaseActivity {

    private final static String URL_ABOUT = "about:blank";
    private FrameLayout mWebViewContainer;
    private WebView mWebView;
    private FrameLayout webActivity_fl_errorView;
    private ProgressBar mProgressBar;
    private String mUrl = null;
    private String mTitleOfWebPage = null;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void receiveParam() {
        Intent intent = getIntent();
        if (intent != null) {
            this.mUrl = intent.getStringExtra(IntentKey.KEY_URL);
            if (!TextUtils.isEmpty(this.mUrl) && !URLUtil.isNetworkUrl(this.mUrl))
                this.mUrl = "http://" + this.mUrl;
            this.mTitleOfWebPage = intent.getStringExtra(IntentKey.KEY_TITLE);
        }
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_web_view_layout);
        TitleView titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle(mTitleOfWebPage).setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {

            }
        });
        webActivity_fl_errorView = (FrameLayout) findViewById(R.id.webActivity_fl_errorView);
        this.mWebViewContainer = (FrameLayout) this.findViewById(R.id.webViewContainer);
        this.mWebView = (WebView) this.mWebViewContainer.findViewById(R.id.webView);
        this.mProgressBar = (ProgressBar) this.mWebViewContainer.findViewById(R.id.webViewProgressBar);
        this.mProgressBar.setVisibility(View.INVISIBLE);
        this.mProgressBar.setMax(100);

        webActivity_fl_errorView.setOnClickListener(new OnBaseClickListener() {
            @Override
            public void onBaseClick(View v) {
                webActivity_fl_errorView.setVisibility(View.GONE);
                mWebView.clearHistory();
                loadDefaultUrl();
            }
        });
    }

    @Override
    public void initData() {
        WebSettings settings = this.mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setSavePassword(false);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        this.onFixSecurity(this.mWebView);

        this.mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url) || URL_ABOUT.equalsIgnoreCase(url)) {
                    mUrl = url;
                    WebViewActivity.this.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String buildUrl = mUrl == null ? "" : mUrl.replaceAll("/", "");
                    String failedUrl = request.getUrl().toString().replaceAll("/", "");
                    if (TextUtils.equals(failedUrl, buildUrl)) {
                        mWebView.loadUrl(URL_ABOUT);
                        webActivity_fl_errorView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String buildUrl = mUrl == null ? "" : mUrl.replaceAll("/", "");
                String failedUrl = failingUrl.replaceAll("/", "");
                if (TextUtils.equals(failedUrl, buildUrl)) {
                    mWebView.loadUrl(URL_ABOUT);
                    webActivity_fl_errorView.setVisibility(View.VISIBLE);
                }
            }

        });

        this.mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (TextUtils.isEmpty(mTitleOfWebPage))
                    mTitleOfWebPage = title;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        loadDefaultUrl();
    }


    private void initTask() {
        if (null == timer)
            timer = new Timer();
        if (null == timerTask)
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            };
        timer.schedule(timerTask, FinalConfig.TIME_DELAY, FinalConfig.TIME_LOOP);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setupSpotAd();
        }
    };

    private void cancelTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }


    protected void loadDefaultUrl() {
        this.loadUrl(this.mUrl);
    }

    protected void loadUrl(String url) {
        if (!URLUtil.isNetworkUrl(url)) {
            ToastUtils.showText(WebViewActivity.this, "网址错误");
            return;
        }
        this.mProgressBar.setVisibility(View.VISIBLE);
        this.mWebView.loadUrl(url);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SpotManager.getInstance(this).isSpotShowing()) {
                SpotManager.getInstance(this).hideSpot();
                return true;
            } else {
                if (this.mWebView != null && this.mWebView.canGoBack()) {
                    this.mWebView.goBack();//返回上一页面
                    return true;
                } else {
                    finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     */
    private void setupSpotAd() {
        SpotManager.getInstance(this).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);
        SpotManager.getInstance(this)
                .setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);
        SpotManager.getInstance(this).showSpot(this, new SpotListener() {

            @Override
            public void onShowSuccess() {
            }

            @Override
            public void onShowFailed(int errorCode) {

            }

            @Override
            public void onSpotClosed() {
            }

            @Override
            public void onSpotClicked(boolean isWebPage) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (LocalADManager.isShowAd)
            initTask();

        try {
            Class.forName("android.webkit.WebView").getMethod("onResume", (Class[]) null).invoke(mWebView, (Object[]) null);
        } catch (Exception e) {
            Logger.d("WebView", e.toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            cancelTask();
            SpotManager.getInstance(this).onPause();
            Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(mWebView, (Object[]) null);
        } catch (Exception e) {
            Logger.d("WebView", e.toString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelTask();
        SpotManager.getInstance(this).onStop();
    }

    @Override
    public void onDestroy() {
        //XXX 销毁需要释放资源
        cancelTask();
        SpotManager.getInstance(this).onDestroy();
        if (this.mWebView != null) {
            this.mWebView.stopLoading();
            this.mWebView.loadUrl("");
            this.mWebView.reload();

            this.mWebView.clearCache(true);
            this.mWebView.clearFormData();
            //			clearWebViewCache();
            this.mWebViewContainer.removeView(this.mWebView);
            this.mWebView.removeAllViews();

            boolean isWebViewDestroyDone = false;
            try {
                Class.forName("android.webkit.WebView").getMethod("destroy", (Class[]) null).invoke(mWebView, (Object[]) null);
                isWebViewDestroyDone = true;
            } catch (Exception e) {
                Logger.d("WebView", e.toString());
                isWebViewDestroyDone = false;
            }
            try {
                if (!isWebViewDestroyDone)
                    this.mWebView.destroy();
            } catch (Exception e) {
                Logger.d("WebView", e.toString());
            }
            mWebView = null;
        }
        super.onDestroy();
    }


    /**
     * @param webView
     */
    private void onFixSecurity(WebView webView) {
        if (webView == null)
            return;
        try {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
        } catch (Exception ex) {
        }
        try {
            webView.removeJavascriptInterface("accessibility");
        } catch (Exception ex) {
        }
        try {
            webView.removeJavascriptInterface("accessibilityTraversal");
        } catch (Exception ex) {
        }
    }
}
