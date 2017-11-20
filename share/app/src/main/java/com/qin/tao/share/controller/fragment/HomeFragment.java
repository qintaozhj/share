package com.qin.tao.share.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseFragment;
import com.qin.tao.share.controller.adapter.SharePagerAdapter;

/**
 * @author qintao on 2017/11/9 15:19
 */

public class HomeFragment extends BaseFragment {

    private View mParentView = null;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mParentView == null) {
            mParentView = inflater.inflate(R.layout.fragment_home, container, false);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) mParentView.getParent();
            if (parent != null) {
                parent.removeView(mParentView);
            }
        }
        return mParentView;
    }

    private void initView() {
        mActivity = getActivity();
        viewPager = (ViewPager) mParentView.findViewById(R.id.viewPager);
        tabs = (PagerSlidingTabStrip) mParentView.findViewById(R.id.tabs);
        SharePagerAdapter adapter = new SharePagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
