package com.qin.tao.share.controller.activity;

import android.view.View;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseListView;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.database.CacheInfoDao;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.controller.adapter.juhe.CollectionAdapter;
import com.qin.tao.share.model.cache.CacheInfo;
import com.qin.tao.share.widget.title.TitleView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 * 收藏
 */

public class CollectionActivity extends BaseActivity {

    private BaseListView listView;
    private BaseTextView emptyData;
    private CollectionAdapter mAdapter;
    private List<CacheInfo> cacheInfoList;

    @Override
    public void receiveParam() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.acticity_collection_layout);
        TitleView titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("收藏").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {

            }
        });
        listView = (BaseListView) findViewById(R.id.listView);
        emptyData = (BaseTextView) findViewById(R.id.emptyData);
        mAdapter = new CollectionAdapter(this);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        cacheInfoList = CacheInfoDao.getDao().queryAll(CacheInfo.class);
        if (CollectionUtils.isEmpty(cacheInfoList)) {
            listView.setVisibility(View.GONE);
            emptyData.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyData.setVisibility(View.GONE);
            mAdapter.setData(cacheInfoList);
        }
    }
}
