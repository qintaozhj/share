package com.qin.tao.share.controller.activity.gank;

import android.content.Intent;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseListView;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.controller.activity.photo.PhotoOriginalActivity;
import com.qin.tao.share.controller.adapter.gank.WelFareAdapter;
import com.qin.tao.share.model.DataCallBack;
import com.qin.tao.share.model.gank.GankData;
import com.qin.tao.share.model.gank.WelfareInfo;
import com.qin.tao.share.model.photo.PhotoInfo;
import com.qin.tao.share.tools.PullToRefresh.PullToRefreshBase;
import com.qin.tao.share.tools.PullToRefresh.PullToRefreshListView;
import com.qin.tao.share.widget.title.TitleView;

import java.util.ArrayList;
import java.util.List;

import ddd.eee.fff.nm.sp.SpotManager;

/**
 * @author qintao on 2017/9/11 16:14
 *         干货福利activity
 */

public class WelFareActivity extends BaseActivity implements WelFareAdapter.ILookPhotoListener {

    private PullToRefreshListView pullToRefreshListView;
    private WelFareAdapter welFareAdapter;
    private int page = 1;

    @Override
    public void receiveParam() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.acticity_welfare_layout);
        TitleView titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("福利").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
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
                page = welFareAdapter.getSize() / 10 + 1;
                if (page < 1)
                    page = 1;
                requestData(page, false);
            }
        });
        welFareAdapter = new WelFareAdapter(this);
        welFareAdapter.setLookPhotoListener(this);
        pullToRefreshListView.setAdapter(welFareAdapter);
    }

    @Override
    public void initData() {
        requestData(page, true);
        initAdData();
    }

    private void requestData(int page, boolean loading) {
        GankData gankData = new GankData(this);
        gankData.requestWelfare(page, loading, new DataCallBack<List<WelfareInfo>>() {
            @Override
            public void onSuccess(List<WelfareInfo> welfareInfoList) {
                if (CollectionUtils.isEmpty(welfareInfoList)) {
                    pullToRefreshListView.post(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                        }
                    });
                } else {
                    if (welFareAdapter.getCount() == 0)
                        welFareAdapter.setData(welfareInfoList);
                    else
                        welFareAdapter.addData(welfareInfoList);
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
    public void lookPhoto(int position) {
        ArrayList<PhotoInfo> mPhotoList = new ArrayList<PhotoInfo>();
        List<WelfareInfo> list = welFareAdapter.getData();
        if (CollectionUtils.isEmpty(list))
            return;
        for (int i = 0; i < list.size(); i++) {
            WelfareInfo info = list.get(i);
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPhotoId(info.get_id());
            photoInfo.setPhotoPath(info.getUrl());
            mPhotoList.add(photoInfo);
        }
        Intent intent = new Intent(this, PhotoOriginalActivity.class);
        intent.putExtra(IntentKey.POSITION, position);
        intent.putParcelableArrayListExtra(IntentKey.PHOTO_PICK_TARGET_PHOTO_LIST, mPhotoList);
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
