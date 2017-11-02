package com.qin.tao.share.controller.activity.juhe;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseListView;
import com.qin.tao.share.app.config.FinalConfig;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.controller.activity.ad.LocalADManager;
import com.qin.tao.share.controller.activity.web.WebViewActivity;
import com.qin.tao.share.controller.adapter.juhe.WeChatAdapter;
import com.qin.tao.share.model.DataCallBack;
import com.qin.tao.share.model.juhe.JuHeData;
import com.qin.tao.share.model.juhe.WeChatInfo;
import com.qin.tao.share.tools.PullToRefresh.PullToRefreshBase;
import com.qin.tao.share.tools.PullToRefresh.PullToRefreshListView;
import com.qin.tao.share.widget.title.TitleView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ddd.eee.fff.nm.sp.SpotListener;
import ddd.eee.fff.nm.sp.SpotManager;

/**
 * @author qintao on 2017/9/12 16:38
 */

public class JokeWeChatActivity extends BaseActivity implements WeChatAdapter.IOnItemClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private WeChatAdapter weChatAdapter;
    private int page = 1;

    private Timer timer;
    private TimerTask timerTask;

    @Override
    public void receiveParam() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.acticity_wechat_layout);
        TitleView titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("精选").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {

            }
        });
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<BaseListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<BaseListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<BaseListView> refreshView) {
                page = weChatAdapter.getCount() / 20 + 1;
                if (page < 1)
                    page = 1;
                requestData(page, false);
            }
        });
        weChatAdapter = new WeChatAdapter(this);
        weChatAdapter.setListener(this);
        pullToRefreshListView.setAdapter(weChatAdapter);
    }

    @Override
    public void initData() {
        requestData(page, true);
    }

    private void requestData(int page, boolean loading) {
        JuHeData juHeData = new JuHeData(this);
        juHeData.requestWechat(page, loading, new DataCallBack<List<WeChatInfo>>() {
            @Override
            public void onSuccess(List<WeChatInfo> weChatInfoList) {
                if (CollectionUtils.isEmpty(weChatInfoList)) {
                    pullToRefreshListView.post(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                        }
                    });
                } else {
                    if (weChatAdapter.getCount() == 0)
                        weChatAdapter.setData(weChatInfoList);
                    else
                        weChatAdapter.addData(weChatInfoList);
                }
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onFail(String msg) {
                pullToRefreshListView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onItemClick(WeChatInfo weChatInfo) {
        if (weChatInfo == null)
            return;
        Intent intent = new Intent(JokeWeChatActivity.this, WebViewActivity.class);
        intent.putExtra(IntentKey.KEY_URL, weChatInfo.getUrl());
        intent.putExtra(IntentKey.KEY_TITLE, weChatInfo.getTitle());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LocalADManager.isShowAd)
            initTask();
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
    public void onBackPressed() {
        if (SpotManager.getInstance(this).isSpotShowing()) {
            SpotManager.getInstance(this).hideSpot();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelTask();
        SpotManager.getInstance(this).onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelTask();
        SpotManager.getInstance(this).onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTask();
        SpotManager.getInstance(this).onDestroy();
    }
}
