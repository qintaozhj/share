package com.qin.tao.share.controller.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.controller.fragment.NewsFragment;

/**
 * @author qintao on 2017/11/9 15:03
 *         destroyItem 方法注释掉，可保证viewPager在切换滑动的时候不会重新创建fragment
 */

public class SharePagerAdapter extends FragmentPagerAdapter {

    //	类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    private String[] title = {"推荐", "社会", "国内", "娱乐", "时尚", "体育", "军事", "科技", "财经", "国际"};

    public SharePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentKey.NEWS_TYPE, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
