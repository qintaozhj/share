package com.qin.tao.share.tools.http;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.qin.tao.share.app.base.BaseApplication;

/**
 * @author qintao on 2017/9/7 16:55
 */

public class AppRequestQueue {
    private static final Object gLocker = new Object();
    /**
     * 网络请求超时时间
     */
    private static final int TIMEOUT = 10 * 1000;
    private static AppRequestQueue instance;
    private RequestQueue mQueue = null;
    private RetryPolicy retryPolicy = null;

    public static AppRequestQueue instance() {
        if (instance == null) {
            synchronized (gLocker) {
                if (instance == null)
                    instance = new AppRequestQueue();
            }
        }
        return instance;
    }


    /**
     * 确保mQueue 不会返回空的情况
     *
     * @return
     */
    public RequestQueue getQueue() {
        if (mQueue == null)
            initRequestQueue(BaseApplication.appContext);
        return mQueue;
    }

    /**
     * 初始化app  网络请求
     *
     * @param context
     */
    public void initRequestQueue(Context context) {
        if (context == null)
            return;
        if (mQueue == null)
            mQueue = Volley.newRequestQueue(context);
    }

    /**
     * 设置网络请求参数
     * @return
     */
    public RetryPolicy getRetryPolicy() {
        if (retryPolicy == null)
            retryPolicy = new DefaultRetryPolicy(TIMEOUT, 0, 0f);
        return retryPolicy;
    }

}
