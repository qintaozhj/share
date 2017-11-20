package com.qin.tao.share.controller.activity.juhe;

import android.content.Intent;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseListView;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.controller.activity.web.WebViewActivity;
import com.qin.tao.share.controller.adapter.juhe.WeChatAdapter;
import com.qin.tao.share.model.DataCallBack;
import com.qin.tao.share.model.juhe.JuHeData;
import com.qin.tao.share.model.juhe.WeChatInfo;
import com.qin.tao.share.tools.PullToRefresh.PullToRefreshBase;
import com.qin.tao.share.tools.PullToRefresh.PullToRefreshListView;
import com.qin.tao.share.widget.title.TitleView;

import java.util.List;

import ddd.eee.fff.nm.sp.SpotManager;

/**
 * @author qintao on 2017/9/12 16:38
 */

public class JokeWeChatActivity extends BaseActivity implements WeChatAdapter.IOnItemClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private WeChatAdapter weChatAdapter;
    private int page = 1;

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
        initAdData();
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
