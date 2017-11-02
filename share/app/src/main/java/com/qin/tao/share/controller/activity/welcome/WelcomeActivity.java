package com.qin.tao.share.controller.activity.welcome;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.log.Logger;
import com.qin.tao.share.controller.activity.MainActivity;
import com.qin.tao.share.controller.activity.ad.LocalADManager;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import java.util.Calendar;

import ddd.eee.fff.AdManager;
import ddd.eee.fff.onlineconfig.OnlineConfigCallBack;


/**
 * @author qintao
 *         created at 2016/5/31 19:01
 *         vod应用主入口
 */

public class WelcomeActivity extends BaseActivity {

    private PermissionHelper mPermissionHelper;
    private static final String MY_KEY = "isOpenYouMiAD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 移除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        //解决每次从通知栏打开都会重启APP的bug, Launcher是否加 android:launchMode = "singleTop"待验证
        //判断是否从推送通知栏打开的
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            //从推送通知栏打开,会重新执行Launcher流程
            //查看是不是全新打开的面板
            if (isTaskRoot()) {
                return;
            }
            //如果有面板存在则关闭当前的面板
            finish();
        }
    }

    @Override
    public void receiveParam() {
    }


    @Override
    public void initViews() {
        setContentView(R.layout.activity_welcome);
        BaseTextView appName = (BaseTextView) findViewById(R.id.appName);
        appName.setText("有趣的生活，从这里开始");
        BaseTextView version = (BaseTextView) findViewById(R.id.version);
        Calendar c = Calendar.getInstance();//
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        version.setText("Copyright©2017" + (mYear == 2017 ? "" : "-" + mYear));
    }

    @Override
    public void initData() {
        getYouMiOnLineParam();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null)
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPermissionHelper != null)
            mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void requestYouMiPermission() {
        // 当系统为6.0以上时，需要申请权限
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                LocalADManager.isShowAd = true;
                runApp();
            }
        });
        if (Build.VERSION.SDK_INT < 23) {
            // 如果系统版本低于23，直接跑应用的逻辑
            LocalADManager.isShowAd = true;
            runApp();
        } else {
            // 如果权限全部申请了，那就直接跑应用逻辑
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                LocalADManager.isShowAd = true;
                runApp();
            } else {
                // 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
                mPermissionHelper.applyPermissions();
            }
        }
    }

    /**
     * 获取在线配置参数
     */
    private void getYouMiOnLineParam() {
        try {
            AdManager.getInstance(this).asyncGetOnlineConfig(MY_KEY, new OnlineConfigCallBack() {
                @Override
                public void onGetOnlineConfigSuccessful(String key, String value) {
                    // 获取在线参数成功
                    Logger.d("获取在线配置参数成功" + key + "==>>" + value);
                    if (TextUtils.equals("open", value)) {
                        requestYouMiPermission();
                    } else {
                        LocalADManager.isShowAd = false;
                        runApp();
                    }
                }

                @Override
                public void onGetOnlineConfigFailed(String key) {
                    // 获取在线参数失败，可能原因有：键值未设置或为空、网络异常、服务器异常
                    Logger.d("获取在线配置参数失败" + key);
                    LocalADManager.isShowAd = false;
                    runApp();
                }
            });
        } catch (Exception e) {
            runApp();
        }
    }

    /**
     * 跑应用的逻辑
     */
    private void runApp() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivityOfMan();
            }
        }, 3000);
    }


    private void startActivityOfMan() {
        //TODO当没有登录的时候进入登录页，否则进入首页
        startEnterActivity(MainActivity.class);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //强制 GC 回收效果更好.
        System.gc();
    }
}
