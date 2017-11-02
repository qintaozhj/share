package com.qin.tao.share.app.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.qin.tao.share.app.base.BaseApplication;

import java.lang.reflect.Field;
import java.util.Locale;


/**
 * 设备信息配置:
 * 设备信息(像素,密度,...)
 */
public class DeviceConfig {
    /**
     * 应用显示.区域尺寸(设备屏幕尺寸-信号栏高度-虚拟按键高度)
     */
    private static int mDisplayWidthPixels = 0, mDisplayHeightPixels = 0;
    /**
     * 设备屏幕尺寸
     */
    private static int mDeviceWidthPixels = 0, mDeviceHeightPixels = 0;
    private static String mId, mModel, mVersionName;

    private static int sysVersionCode = 0;


    private static final int verSion = 14;//手机最低系统版本号
    private static final int widthPixels = 540, heightPixels = 960;//手机最低分辨率

    private static float screenDensity = 1.5f;// 屏幕密度

    private static int sizeOfSd = 50;//SD剩余剩余数


    //设备mcc mnc
    private static String deviceMCC = "";
    private static String deviceMNC = "";
    //设备 IMEI
    private static String deviceIMEI = "";

    /**
     * 初始化
     */
    @SuppressLint("HardwareIds")
    public static void init() {

        Context context = BaseApplication.getContext();
        if (context != null) {
            try {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                mDisplayWidthPixels = displayMetrics.widthPixels;
                mDisplayHeightPixels = displayMetrics.heightPixels;
                screenDensity = displayMetrics.density;
            } catch (Exception ex) {
                ex.printStackTrace();
                mDisplayWidthPixels = 0;
                mDisplayHeightPixels = 0;
            } finally {
                mDeviceWidthPixels = mDisplayWidthPixels;
                mDeviceHeightPixels = mDisplayHeightPixels;
            }

            WindowManager windowManager = null;
            try {
                windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display defaultDisplay = windowManager.getDefaultDisplay();

                Class<?> displayClass = Display.class;
                Field fieldDisplayInfo = displayClass.getDeclaredField("mDisplayInfo");
                fieldDisplayInfo.setAccessible(true);
                Object displayInfo = fieldDisplayInfo.get(defaultDisplay);
                if (displayInfo != null) {
                    Class<?> displayInfoClass = displayInfo.getClass();
                    Field fieldLogicalHeight = displayInfoClass.getDeclaredField("logicalHeight");
                    Field fieldLogicalWidth = displayInfoClass.getDeclaredField("logicalWidth");
                    fieldLogicalHeight.setAccessible(true);
                    fieldLogicalWidth.setAccessible(true);
                    Object logicalHeight = fieldLogicalHeight.get(displayInfo);
                    Object logicalWidth = fieldLogicalWidth.get(displayInfo);
                    if (logicalHeight != null && logicalWidth != null) {
                        mDeviceHeightPixels = Integer.parseInt(logicalHeight.toString());
                        mDeviceWidthPixels = Integer.parseInt(logicalWidth.toString());
                    }
                }
            } catch (Exception ex) {
                mDeviceWidthPixels = mDisplayWidthPixels;
                mDeviceHeightPixels = mDisplayHeightPixels;
            } finally {
                if (windowManager != null)
                    windowManager = null;
            }
            mId = getAndroidId(context);
            mModel = android.os.Build.MODEL;
            mVersionName = android.os.Build.VERSION.RELEASE;
            // NetworkUtils.init();
            Object service = context.getSystemService(Context.TELEPHONY_SERVICE);
            if (service instanceof TelephonyManager) {
                TelephonyManager telephonyManager = (TelephonyManager) service;
                String networkOperator = telephonyManager.getNetworkOperator();
                if (!TextUtils.isEmpty(networkOperator) && networkOperator.length() > 3) {
                    try {
                        deviceMCC = networkOperator.substring(0, 3);
                        deviceMNC = networkOperator.substring(3);
                    } catch (StringIndexOutOfBoundsException e) {
                        deviceMCC = "";
                        deviceMNC = "";
                    }
                }

                //常规方式获取设备.IMEI
                if (ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    deviceIMEI = telephonyManager.getDeviceId();
                } else {
                    //XXX 无法通过常规方式获取设备.IMEI
                }
            }
        }

    }


    /**
     * 获取屏幕显示 宽度
     */
    public static int displayWidthPixels() {
        return mDisplayWidthPixels;
    }

    /**
     * 获取屏幕显示 高度
     */
    public static int displayHeightPixels() {
        return mDisplayHeightPixels;
    }

    /**
     * 获取设备 宽度
     */
    public static int deviceWidthPixels() {
        return mDeviceWidthPixels;
    }

    /**
     * 获取设备 高度
     */
    public static int deviceHeightPixels() {
        return mDeviceHeightPixels;
    }

    /**
     * 获取屏幕密度
     */
    public static float getDensity() {
        return screenDensity;
    }

    /**
     * android 设备 唯一  Id
     */
    public static String getId() {
        return mId == null ? "" : mId;
    }

    /**
     * 设备分辨率
     */
    public static String getResolution() {
        return String.format(Locale.ENGLISH, "%d*%d", mDeviceWidthPixels, mDeviceHeightPixels);
    }

    /**
     * 设备型号
     */
    public static String getModel() {
        return mModel == null ? "" : mModel;
    }

    /**
     * 手机系统API版本号
     */
    public static int getSysVersionCode() {
        return sysVersionCode;
    }

    /**
     * 设备 android 版本
     */
    public static String getVersionName() {
        return mVersionName == null ? "" : mVersionName;
    }

    private static String getAndroidId(Context context) {
        String androidId = null;
        if (context != null) {
            androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            if (androidId == null) {
                // this happens on 1.6 and older
                androidId = android.provider.Settings.System
                        .getString(BaseApplication.getContext().getContentResolver(), android.provider.Settings.System.ANDROID_ID);
            }
            //Build.MODEL
        }
        return androidId == null ? "" : androidId;
    }

    public static String getDeviceMCC() {
        return deviceMCC;
    }

    public static String getDeviceMNC() {
        return deviceMNC;
    }

    public static String getDeviceIMEI() {
        return deviceIMEI == null ? "" : deviceIMEI;
    }


}
