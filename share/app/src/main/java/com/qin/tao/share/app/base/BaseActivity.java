package com.qin.tao.share.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qin.tao.share.app.log.Logger;

import ddd.eee.fff.nm.bn.BannerManager;
import ddd.eee.fff.nm.bn.BannerViewListener;


/**
 * @author qintao
 * @time 2017/9/1 11:34
 */
public abstract class BaseActivity extends FragmentActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        receiveParam();
        initViews();
        initData();
    }

    public void startEnterActivity(Class cla) {
        startActivity(new Intent(context, cla));
        animLeftToRight();
    }

    public void startEnterActivityForResult(Class cla, int requestCode) {
        startActivityForResult(new Intent(context, cla), requestCode);
        animLeftToRight();
    }

    public void startExitActivity(Class cla) {
        startActivity(new Intent(context, cla));
        animRightToLeft();
    }

    /**
     * 当前Activity进入时的动画
     */
    public void animLeftToRight() {
        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 当前Activity退出时的动画
     */
    public void animRightToLeft() {
        //overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void animBottomToTop() {
        //overridePendingTransition(R.anim.push_bottom_in, R.anim.push_top_out);
    }

    public void animTopToBottom() {
        //overridePendingTransition(R.anim.push_top_in, R.anim.push_bottom_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animRightToLeft();
    }


    /**
     * receive param form otherActivity
     */
    public abstract void receiveParam();

    /**
     * init all views
     */
    public abstract void initViews();

    /**
     * init current activity need data
     */
    public abstract void initData();


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean isCloseSoftKeyboardInTouch(MotionEvent ev) {
        return true;
    }
}
