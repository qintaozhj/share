package com.qin.tao.share.app.analysis;

import android.content.Context;
import android.util.Log;

import com.qin.tao.share.BuildConfig;
import com.qin.tao.share.app.base.BaseApplication;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.tencent.stat.common.StatConstants;

/**
 * MTA用户数据分析
 */
public class MTAManager {
    private static boolean MTA_ENABLE = false;

    public void init() {
        init(true);
    }

    public void init(boolean isEnable) {
        MTA_ENABLE = isEnable;
        if (!MTA_ENABLE) return;

        StatConfig.setAppKey(BuildConfig.MTA);
        StatConfig.setInstallChannel("share");
        StatConfig.setSessionTimoutMillis(1000 * 60 * 3);//超时时间3分
        StatConfig.setEnableConcurrentProcess(true);//多进程支持
        StatConfig.setDebugEnable(false);
        StatConfig.setNativeCrashDebugEnable(true);
        StatConfig.setAutoExceptionCaught(true);
        StatService.setContext(BaseApplication.getContext());
        // 开启统计
        try {
            StatService.startStatService(BaseApplication.getContext(), BuildConfig.MTA, StatConstants.VERSION);
        } catch (MtaSDkException e) {
            Log.e("MTA", "startStatService error:" + e.toString());
        }
    }

    /**
     * MTA 统计恢复
     */
    public static void onResume(Context context) {
        if (context == null || !MTA_ENABLE)
            return;
        StatService.onResume(context);
    }

    /**
     * MTA 统计暂停
     */
    public static void onPause(Context context) {
        if (context == null || !MTA_ENABLE)
            return;
        StatService.onPause(context);
    }

}
