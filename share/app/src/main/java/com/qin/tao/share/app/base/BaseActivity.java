package com.qin.tao.share.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.qin.tao.share.app.config.FinalConfig;
import com.qin.tao.share.app.log.Logger;
import com.qin.tao.share.controller.activity.ad.LocalADManager;

import java.util.Timer;
import java.util.TimerTask;

import ddd.eee.fff.nm.sp.SpotListener;
import ddd.eee.fff.nm.sp.SpotManager;


/**
 * @author qintao
 * @time 2017/9/1 11:34
 */
public abstract class BaseActivity extends FragmentActivity {

    public Context context;
    private Timer timer;
    private TimerTask timerTask;

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

    public void initAdData() {
        Logger.d("isShowAd:" + LocalADManager.isShowAd);
        if (LocalADManager.isShowAd)
            initTask();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setupSpotAd();
        }
    };

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

    private void setupSpotAd() {
        Logger.d("调用");
        SpotManager.getInstance(this).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);
        SpotManager.getInstance(this)
                .setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);
        SpotManager.getInstance(this).showSpot(this, new SpotListener() {

            @Override
            public void onShowSuccess() {
                Logger.d("调用:success");
            }

            @Override
            public void onShowFailed(int errorCode) {
                Logger.d("调用:" + errorCode);
            }

            @Override
            public void onSpotClosed() {
                Logger.d("调用:close");
            }

            @Override
            public void onSpotClicked(boolean isWebPage) {
                Logger.d("调用:isWebPage");
            }
        });
    }

    public void cancelTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animRightToLeft();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
