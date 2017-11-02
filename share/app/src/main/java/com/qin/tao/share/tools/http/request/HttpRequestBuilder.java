package com.qin.tao.share.tools.http.request;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author qintao on 2017/9/6 10:47
 */

public class HttpRequestBuilder {

    protected Context context = null;

    /**
     * Api 接口地址
     */
    protected String interfaceName = "";

    /**
     * 请求方式
     */
    protected int httpMethod;//请求方法

    /**
     * 参数 http参数
     */
    protected HashMap<String, String> paramMap = null;//参数列表

    /**
     * loading 网络请求显示Loading动画类型
     */
    protected HttpRequestLoadingEnum httpRequestLoadingEnum = null;

    /**
     * 网络类型
     */
    protected HttpNetEnum httpNetEnum;

    /**
     * 网络请求监听
     */
    protected IHttpRequestListener httpRequestListener;


    public HttpRequestBuilder(Context context) {
        this.context = context;
        this.httpMethod = Request.Method.GET;
        this.httpRequestLoadingEnum = HttpRequestLoadingEnum.HTTP_LOADING_NONE;
        this.paramMap = new HashMap<String, String>();
    }

    public HttpRequestTask build() {
        return new HttpRequestTask(this);
    }


    public HttpRequestBuilder interfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public HttpRequestBuilder httpMethod(int httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public HttpRequestBuilder param(HashMap<String, String> paramMap) {
        this.paramMap = paramMap;
        return this;
    }

    public HttpRequestBuilder httpRequestLoadingEnum(HttpRequestLoadingEnum httpRequestLoadingEnum) {
        this.httpRequestLoadingEnum = httpRequestLoadingEnum;
        return this;
    }

    protected HttpRequestBuilder httpNetEnum(HttpNetEnum httpNetEnum) {
        this.httpNetEnum = httpNetEnum;
        return this;
    }


    public HttpRequestBuilder httpRequestStartListener(IHttpRequestListener httpRequestListener) {
        this.httpRequestListener = httpRequestListener;
        return this;
    }


    public interface IHttpRequestListener {

        //处理成功
        void onResponseSuccess(JSONObject jsonObject);

        //失败
        void onError(String errMsg);

    }
}
