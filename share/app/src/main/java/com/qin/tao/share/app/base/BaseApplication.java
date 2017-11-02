package com.qin.tao.share.app.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.qin.tao.share.app.analysis.MTAManager;
import com.qin.tao.share.app.config.DeviceConfig;
import com.qin.tao.share.app.exception.CrashHandler;
import com.qin.tao.share.tools.http.AppRequestQueue;
import com.qin.tao.share.tools.http.OkHttpUtils;
import com.qin.tao.share.tools.image.LoadImageUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import ddd.eee.fff.AdManager;
import okhttp3.OkHttpClient;


/**
 * Date: 2016年5月10日
 * Time: 下午4:15:33
 * Des:程序初始化相关
 */
public class BaseApplication extends Application {
    public static Context appContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BaseApplication.appContext == null) {
            //防止多进程调用造成的App初始化多次
            String appName = this.getCurProcessName(this.getApplicationContext());
            appContext = this.getApplicationContext();
            if (!TextUtils.isEmpty(appName) && appName.equals(getApplicationContext().getPackageName())) {
                //初始化App相关配置
                initApplicationConfig();
                initHttp();
            }
        }
    }

    private void initHttp() {

        AppRequestQueue.instance().initRequestQueue(appContext);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 上下文
     */
    public static Context getContext() {
        return appContext;
    }

    /**
     * App相关配置初始化
     */
    private void initApplicationConfig() {
        DeviceConfig.init();
        //应用所需环境进行校验
        //添加异常捕获模块.
        CrashHandler.getInstance().init(appContext);
        CrashHandler.getInstance().collectCrashDeviceInfo(appContext);
        //初始化 ImageLoader
        LoadImageUtils.initImageLoader(appContext);
        new MTAManager().init(true);
        AdManager.getInstance(appContext).init("e1686254031911bb", "3f6d8f8eda02caa5", true);

    }


    /**
     * 获取当前进程名称
     *
     * @param context .
     * @return .
     */
    private String getCurProcessName(Context context) {
        String returnValue = null;
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid)
                returnValue = appProcess.processName;
        }
        return returnValue;
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
    }
}
