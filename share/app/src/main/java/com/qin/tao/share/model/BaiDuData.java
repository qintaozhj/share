package com.qin.tao.share.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.qin.tao.share.app.config.ApiConfig;
import com.qin.tao.share.tools.http.request.HttpRequestBuilder;
import com.qin.tao.share.tools.http.request.HttpRequestLoadingEnum;
import com.qin.tao.share.tools.http.request.HttpRequestTask;
import com.qin.tao.share.tools.http.request.HttpServerManager;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author qintao on 2017/9/7 19:43
 */

public class BaiDuData {
    public BaiDuData(Context context) {
        this.context = context;
    }

    private Context context;

    public void request() {
        HashMap<String, String> param = new HashMap<>();
        param.put("type", "yuantong");
        param.put("postid", "11111111111");

        HttpRequestTask httpRequestTask = new HttpRequestBuilder(context).interfaceName(ApiConfig.GANK_WELFARE).param(param).httpMethod(Request.Method.POST)
                .httpRequestLoadingEnum(HttpRequestLoadingEnum.HTTP_LOADING_DEFAULT)
                .httpRequestStartListener(new HttpRequestBuilder.IHttpRequestListener() {

                    @Override
                    public void onResponseSuccess(JSONObject jsonObject) {
                        if (jsonObject != null)
                            Log.e("FF", jsonObject.toString());
                    }

                    @Override
                    public void onError(String errMsg) {

                    }
                }).build();
        httpRequestTask.execute(HttpServerManager.serverIpJuHe);
    }

    public void requestUpdate() {
        HttpRequestTask httpRequestTask = new HttpRequestBuilder(context).interfaceName(ApiConfig.GANK_WELFARE).param(null).httpMethod(Request.Method.GET)
                .httpRequestLoadingEnum(HttpRequestLoadingEnum.HTTP_LOADING_DEFAULT)
                .httpRequestStartListener(new HttpRequestBuilder.IHttpRequestListener() {

                    @Override
                    public void onResponseSuccess(JSONObject jsonObject) {
                        if (jsonObject != null)
                            Log.e("FF", jsonObject.toString());
                    }

                    @Override
                    public void onError(String errMsg) {

                    }
                }).build();
        //httpRequestTask.execute(HttpServerManager.test);
    }
}
