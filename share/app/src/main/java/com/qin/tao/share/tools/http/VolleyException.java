package com.qin.tao.share.tools.http;

import android.text.TextUtils;

/**
 * @author qintao on 2017/9/7 18:56
 * volley 返回错误类型整理
 */

public class VolleyException {

    /**
     * 网络不给力时提示字符串
     */
    public final static String netWorkErrorMsg = "网路不给力请检查后重试";
    /**
     * 数据异常，没获取到数据，数据解析错误时提示字符串
     */
    public final static String dataErrorMsg = "很抱歉没有您要找的信息";
    /**
     * 无权限访问
     */
    public final static String authErrorMsg = "无权限访问";

    /**
     * 默认出错信息
     */
    public final static String defaultErrorMsg = "很抱歉服务器出错了";


    /**
     * @param errorMsg
     * @return 异常提示信息 volleryException返回回来的异常，我们封装在 一个JSON格式中返回给界面，至于提示信息产品确定
     */
    public static String getVolleyErrorMsg(String errorMsg) {
        String result = defaultErrorMsg;
        if (TextUtils.isEmpty(errorMsg))
            return result;
        if (errorMsg.contains("com.android.volley.NoConnectionError")) {
            result = netWorkErrorMsg;
        } else if (errorMsg.contains("com.android.volley.TimeoutError")) {
            result = netWorkErrorMsg;
        } else if (errorMsg.contains("com.android.volley.NetworkError")) {
            result = netWorkErrorMsg;
        } else if (errorMsg.contains("com.android.volley.ParseError")) {
            result = dataErrorMsg;
        } else if (errorMsg.contains("com.android.volley.ServerError")) {
            result = dataErrorMsg;
        } else if (errorMsg.contains("com.android.volley.VolleyError")) {
            result = dataErrorMsg;
        } else if (errorMsg.contains("com.android.volley.AuthFailureError")) {
            result = authErrorMsg;
        }
        return result;

    }
}
