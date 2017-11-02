package com.qin.tao.share.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.qin.tao.share.app.base.BaseApplication;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lxl
 *         2014-12-23-下午12:03:18
 *         <p/>
 *         网络信息配置:
 *         网络环境检测(wifi,2G/3G/4G,网络状态检查)
 * @author xjunjie@gmail.com  2015-1-19 下午3:04:38
 *         取消对公共变量的直接读写.添加 get 和 set 入口.以便支持异步锁 synchronized
 */
public class NetworkUtils {
    /*
     * 取消该变量的 public 访问修饰.目的是该变量多线程环境读写时,需要加锁控制
     */
    private static NetWorkEnum networkType = NetWorkEnum.NETWORK_UNAVAILABLE;

    public NetworkUtils() {
    }

    public NetWorkEnum getNetworkType() {
        NetWorkEnum returnValue = NetWorkEnum.NETWORK_UNAVAILABLE;
        if (BaseApplication.appContext != null) {
            Object systemService = BaseApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (systemService instanceof ConnectivityManager) {
                ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
                returnValue = this.getNetworkType(connectivityManager.getActiveNetworkInfo());
            }
        }
        return returnValue;
    }

    public NetWorkEnum getNetworkType(NetworkInfo info) {
        NetWorkEnum returnValue = NetWorkEnum.NETWORK_UNAVAILABLE;
        //无网络连接
        if (info != null) {
            if (info.isAvailable() && info.isConnectedOrConnecting()) {
                int type = info.getType();
                //判断网络类型
                if (type == ConnectivityManager.TYPE_WIFI) {
                    returnValue = NetWorkEnum.NetWork_WIFI;
                } else {
                    String netType = info.getExtraInfo();
                    if (!TextUtils.isEmpty(netType)) {
                        //区分 国内 三大主流运营商
                        if (netType.equals("cmwap") || netType.equals("cmnet"))
                            returnValue = NetWorkEnum.NetWork_CHINA_MOBILE;
                        else if (netType.equals("3gwap") || netType.equals("3gnet"))
                            returnValue = NetWorkEnum.NetWork_CHN_CUGSM;
                        else if (netType.equals("ctwap") || netType.equals("ctwap"))
                            returnValue = NetWorkEnum.NetWork_CHINA_TELECOM;
                        else
                            returnValue = NetWorkEnum.NETWORK_OTHER;
                    }
                }
            }
            //            else {
            //                Logger.i("NetworkUtils", "getNetworkType info is not Available or  is not Connected Or is not Connecting");
            //            }
        } else {
            //By xjunjie@gmail.com 2016.07.19
            //发现部分机型.通过 connectivityManager.getActiveNetworkInfo() 获取 信息对象为空.
            //针对此情况.返回网络状态为：WIFI
            //Logger.i("NetworkUtils", "获取网络状态:失败.默认 Wifi");
            returnValue = NetWorkEnum.NetWork_WIFI;
        }
        return returnValue;
    }

    public enum NetWorkEnum {
        /**
         * 移动网络-中国移动
         */
        NetWork_CHINA_MOBILE,

        /**
         * 移动网络-中国联通
         */
        NetWork_CHN_CUGSM,

        /**
         * 移动网络-中国电信
         */
        NetWork_CHINA_TELECOM,

        /**
         * 移动网络-其他
         */
        NETWORK_OTHER,

        /**
         * Wifi 无线网络
         */
        NetWork_WIFI,

        /**
         * 网络不可用
         */
        NETWORK_UNAVAILABLE
    }

    /**
     * 设置网络状态
     *
     * @param netWorkEnum
     */
    public static synchronized void setNetWorkState(NetWorkEnum netWorkEnum) {
        networkType = netWorkEnum;
    }

    /**
     * 获取网络状态.公共查询网络状态
     *
     * @return 网络状态枚举
     */
    public static synchronized NetWorkEnum getNetWorkState() {
        return networkType;
    }

    private static NetworkConnectBroadcastReceiver mNetworkConnectBroadcastReceiver = null;

    /**
     * 实时监控.系统网络状态变化
     * 如果.应用运行过程中,需要实时监听网络状态变化.则在应用启动过程中.调用一次.
     */
    public static void init() {
        if (BaseApplication.appContext != null) {
            if (mNetworkConnectBroadcastReceiver == null) {
                mNetworkConnectBroadcastReceiver = new NetworkConnectBroadcastReceiver();
                IntentFilter mFilter = new IntentFilter();
                mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                BaseApplication.appContext.registerReceiver(mNetworkConnectBroadcastReceiver, mFilter);
            }

            NetWorkEnum networkType = new NetworkUtils().getNetworkType();
            //			Logger.i("NetworkUtils", "初始化:" + networkType.toString());
            NetworkUtils.setNetWorkState(networkType);
        } else {
            throw new RuntimeException();
        }
    }

    private static class NetworkConnectBroadcastReceiver extends BroadcastReceiver {

        private final ReentrantLock mLocker = new ReentrantLock();

        @Override
        public void onReceive(Context context, Intent intent) {
            if (context != null && intent != null) {
                String action = intent.getAction();
                //				Logger.i("NetworkUtils", "action:" + action);
                if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    mLocker.lock();
                    Object systemService = context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (systemService instanceof ConnectivityManager) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
                        NetWorkEnum mNetWorkEnum = new NetworkUtils().getNetworkType(connectivityManager.getActiveNetworkInfo());
                        NetworkUtils.setNetWorkState(mNetWorkEnum);

                    }

                    mLocker.unlock();
                }
            }
        }
    }
}
