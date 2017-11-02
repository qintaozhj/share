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
import com.qin.tao.share.app.utils.ToastUtils;
import com.qin.tao.share.controller.activity.ad.LocalADManager;
import com.qin.tao.share.controller.activity.web.WebViewActivity;
import com.qin.tao.share.controller.adapter.juhe.JokeTopLineAdapter;
import com.qin.tao.share.model.DataCallBack;
import com.qin.tao.share.model.juhe.JuHeData;
import com.qin.tao.share.model.juhe.TopLineInfo;
import com.qin.tao.share.widget.title.TitleView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ddd.eee.fff.nm.sp.SpotListener;
import ddd.eee.fff.nm.sp.SpotManager;

/**
 * @author qintao on 2017/9/12 16:38
 *         聚合今日头条
 */

public class JokeTopLineActivity extends BaseActivity implements JokeTopLineAdapter.IOnItemClickListener {

    private BaseListView listView;
    private JokeTopLineAdapter jokeTopLineAdapter;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    public void receiveParam() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.acticity_top_line_layout);
        TitleView titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("头条").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {

            }
        });
        listView = (BaseListView) findViewById(R.id.listView);
        jokeTopLineAdapter = new JokeTopLineAdapter(this);
        jokeTopLineAdapter.setListener(this);
        listView.setAdapter(jokeTopLineAdapter);
    }

    @Override
    public void initData() {
        requestData();
    }

    private void requestData() {
        JuHeData juHeData = new JuHeData(this);
        juHeData.requestTopLine(new DataCallBack<List<TopLineInfo>>() {
            @Override
            public void onSuccess(List<TopLineInfo> jokeInfoList) {
                if (CollectionUtils.isEmpty(jokeInfoList)) {
                    ToastUtils.showText(JokeTopLineActivity.this, "暂无数据");
                } else {
                    jokeTopLineAdapter.setData(jokeInfoList);
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtils.showText(JokeTopLineActivity.this, msg);
            }
        });
    }


    @Override
    public void onItemClick(TopLineInfo topLineInfo) {
        if (topLineInfo == null)
            return;
        Intent intent = new Intent(JokeTopLineActivity.this, WebViewActivity.class);
        intent.putExtra(IntentKey.KEY_URL, topLineInfo.getUrl());
        intent.putExtra(IntentKey.KEY_TITLE, topLineInfo.getTitle());
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
