package com.qin.tao.share.controller.activity.juhe;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseListView;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.controller.adapter.juhe.JokeTextAdapter;
import com.qin.tao.share.model.DataCallBack;
import com.qin.tao.share.model.juhe.JokeInfo;
import com.qin.tao.share.model.juhe.JuHeData;
import com.qin.tao.share.tools.PullToRefresh.PullToRefreshBase;
import com.qin.tao.share.tools.PullToRefresh.PullToRefreshListView;
import com.qin.tao.share.widget.title.TitleView;

import java.util.List;

import ddd.eee.fff.nm.sp.SpotManager;

/**
 * @author qintao on 2017/9/12 16:38
 */

public class JokeTextActivity extends BaseActivity {

    private PullToRefreshListView pullToRefreshListView;
    private JokeTextAdapter jokeTextAdapter;
    private int page = 1;

    @Override
    public void receiveParam() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.acticity_welfare_layout);
        TitleView titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("笑话").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
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
                page = jokeTextAdapter.getCount() / 15 + 1;
                if (page < 1)
                    page = 1;
                requestData(page, false);
            }
        });
        jokeTextAdapter = new JokeTextAdapter(this);
        pullToRefreshListView.setAdapter(jokeTextAdapter);
    }

    @Override
    public void initData() {
        requestData(page, true);
        initAdData();
    }

    private void requestData(int page, boolean loading) {
        JuHeData juHeData = new JuHeData(this);
        juHeData.requestJokeText(page, loading, new DataCallBack<List<JokeInfo>>() {
            @Override
            public void onSuccess(List<JokeInfo> jokeInfoList) {
                if (CollectionUtils.isEmpty(jokeInfoList)) {
                    pullToRefreshListView.post(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                        }
                    });
                } else {
                    if (jokeTextAdapter.getCount() == 0)
                        jokeTextAdapter.setData(jokeInfoList);
                    else
                        jokeTextAdapter.addData(jokeInfoList);
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
