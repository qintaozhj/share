package com.qin.tao.share.app.utils;


import com.qin.tao.share.app.base.BaseApplication;

/**
 * @author qintao
 *         created at 2016/6/3 14:36
 */

public class HelperUtils {
    private static XMLSPHelper helper, helperOfApp;

    /**
     * 用户数据相关
     *
     * @return
     */
    public static XMLSPHelper getHelperInstance() {
        if (null == helper)
            helper = new XMLSPHelper(BaseApplication.appContext, "share_user");
        return helper;
    }

    /**
     * 应用数据相关
     *
     * @return
     */
    public static XMLSPHelper getHelperAppInstance() {
        if (null == helperOfApp)
            helperOfApp = new XMLSPHelper(BaseApplication.appContext, "share_app");
        return helperOfApp;
    }

}
