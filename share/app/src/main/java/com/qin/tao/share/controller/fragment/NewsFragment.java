package com.qin.tao.share.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseFragment;
import com.qin.tao.share.app.base.BaseListView;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.app.utils.ToastUtils;
import com.qin.tao.share.controller.activity.web.WebViewActivity;
import com.qin.tao.share.controller.adapter.juhe.JokeTopLineAdapter;
import com.qin.tao.share.model.DataCallBack;
import com.qin.tao.share.model.juhe.JuHeData;
import com.qin.tao.share.model.juhe.TopLineInfo;

import java.util.List;

/**
 * @author qintao on 2017/11/9 15:19
 *         新闻.头条
 */

public class NewsFragment extends BaseFragment implements JokeTopLineAdapter.IOnItemClickListener {
    private View mParentView = null;
    private BaseListView listView;
    private BaseTextView emptyData;
    private JokeTopLineAdapter jokeTopLineAdapter;
    private Activity mActivity;
    private String[] type = {"top", "shehui", "guonei", "yule", "shishang", "tiyu", "junshi", "keji", "caijing", "guoji"};
    private int mCurrentType = 0;
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mParentView == null) {
            mParentView = inflater.inflate(R.layout.acticity_top_line_layout, container, false);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) mParentView.getParent();
            if (parent != null) {
                parent.removeView(mParentView);
            }
        }
        isViewCreated = true;
        lazyLoad();
        return mParentView;
    }

    private void initView() {
        mActivity = getActivity();
        mCurrentType = getArguments().getInt(IntentKey.NEWS_TYPE);
        listView = (BaseListView) mParentView.findViewById(R.id.listView);
        emptyData = (BaseTextView) mParentView.findViewById(R.id.emptyData);
        jokeTopLineAdapter = new JokeTopLineAdapter(mActivity);
        jokeTopLineAdapter.setListener(this);
        listView.setAdapter(jokeTopLineAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }


    /**
     * 懒加载
     */
    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            requestData(type[mCurrentType]);
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }


    private void requestData(String type) {
        JuHeData juHeData = new JuHeData(mActivity);
        juHeData.requestTopLine(type, new DataCallBack<List<TopLineInfo>>() {
            @Override
            public void onSuccess(List<TopLineInfo> jokeInfoList) {
                if (CollectionUtils.isEmpty(jokeInfoList)) {
                    ToastUtils.showText(mActivity, R.string.string_empty_data);
                    showView(false);
                } else {
                    showView(true);
                    jokeTopLineAdapter.setData(jokeInfoList);
                }
            }

            @Override
            public void onFail(String msg) {
                showView(false);
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtils.showText(mActivity, msg);
                } else {
                    ToastUtils.showText(mActivity, R.string.string_empty_data);
                }
            }
        });
    }

    private void showView(boolean haveData) {
        emptyData.setVisibility(haveData ? View.GONE : View.VISIBLE);
        listView.setVisibility(haveData ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(TopLineInfo topLineInfo) {
        if (topLineInfo == null)
            return;
        Intent intent = new Intent(mActivity, WebViewActivity.class);
        intent.putExtra(IntentKey.KEY_URL, topLineInfo.getUrl());
        intent.putExtra(IntentKey.KEY_TITLE, topLineInfo.getTitle());
        startActivity(intent);
    }
}
