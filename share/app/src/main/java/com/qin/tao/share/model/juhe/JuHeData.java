package com.qin.tao.share.model.juhe;

import android.content.Context;

import com.android.volley.Request;
import com.qin.tao.share.app.config.ApiConfig;
import com.qin.tao.share.model.DataCallBack;
import com.qin.tao.share.tools.http.request.HttpRequestBuilder;
import com.qin.tao.share.tools.http.request.HttpRequestLoadingEnum;
import com.qin.tao.share.tools.http.request.HttpRequestTask;
import com.qin.tao.share.tools.http.request.HttpServerManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author qintao on 2017/9/7 19:43
 */

public class JuHeData {
    public JuHeData(Context context) {
        this.context = context;
    }

    private Context context;

    public void requestJokeText(int page, boolean loading, final DataCallBack<List<JokeInfo>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("key", "7ee782336f816dc898da75169130c678");
        param.put("page", String.valueOf(page));
        param.put("pagesize", "15");
        HttpRequestTask httpRequestTask = new HttpRequestBuilder(context).interfaceName(ApiConfig.JUHE_JOKE_TEXT).param(param).httpMethod(Request.Method.GET)
                .httpRequestLoadingEnum(loading ? HttpRequestLoadingEnum.HTTP_LOADING_DEFAULT : HttpRequestLoadingEnum.HTTP_LOADING_NONE)
                .httpRequestStartListener(new HttpRequestBuilder.IHttpRequestListener() {

                    @Override
                    public void onResponseSuccess(JSONObject jsonObject) {
                        if (jsonObject != null && callBack != null) {
                            List<JokeInfo> list = new ArrayList<JokeInfo>();
                            JSONObject result = jsonObject.optJSONObject("result");
                            if (result != null) {
                                JSONArray data = result.optJSONArray("data");
                                if (data != null && data.length() > 0) {
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject jokeInfo = data.optJSONObject(i);
                                        list.add(new JokeInfo(jokeInfo));
                                    }
                                }
                                callBack.onSuccess(list);
                            }
                        }
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (callBack != null)
                            callBack.onFail(errMsg);
                    }
                }).build();
        httpRequestTask.execute(HttpServerManager.serverIpJuHe);
    }

    public void requestJokeImg(int page, boolean loading, final DataCallBack<List<JokeInfo>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("key", "7ee782336f816dc898da75169130c678");
        param.put("page", String.valueOf(page));
        param.put("pagesize", "15");
        HttpRequestTask httpRequestTask = new HttpRequestBuilder(context).interfaceName(ApiConfig.JUHE_JOKE_IMG).param(param).httpMethod(Request.Method.GET)
                .httpRequestLoadingEnum(loading ? HttpRequestLoadingEnum.HTTP_LOADING_DEFAULT : HttpRequestLoadingEnum.HTTP_LOADING_NONE)
                .httpRequestStartListener(new HttpRequestBuilder.IHttpRequestListener() {

                    @Override
                    public void onResponseSuccess(JSONObject jsonObject) {
                        if (jsonObject != null && callBack != null) {
                            List<JokeInfo> list = new ArrayList<JokeInfo>();
                            JSONObject result = jsonObject.optJSONObject("result");
                            if (result != null) {
                                JSONArray data = result.optJSONArray("data");
                                if (data != null && data.length() > 0) {
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject jokeInfo = data.optJSONObject(i);
                                        list.add(new JokeInfo(jokeInfo));
                                    }
                                }
                                callBack.onSuccess(list);
                            }
                        }
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (callBack != null)
                            callBack.onFail(errMsg);
                    }
                }).build();
        httpRequestTask.execute(HttpServerManager.serverIpJuHe);
    }

    public void requestTopLine(String type, final DataCallBack<List<TopLineInfo>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("key", "af6856dc88b9af367d1eb46707da4c4e");
        param.put("type", type);
        HttpRequestTask httpRequestTask = new HttpRequestBuilder(context).interfaceName(ApiConfig.JUHE_TOP_LINE).param(param).httpMethod(Request.Method.GET)
                .httpRequestLoadingEnum(HttpRequestLoadingEnum.HTTP_LOADING_DEFAULT)
                .httpRequestStartListener(new HttpRequestBuilder.IHttpRequestListener() {

                    @Override
                    public void onResponseSuccess(JSONObject jsonObject) {
                        if (jsonObject != null && callBack != null) {
                            List<TopLineInfo> list = new ArrayList<TopLineInfo>();
                            JSONObject result = jsonObject.optJSONObject("result");
                            if (result != null) {
                                JSONArray data = result.optJSONArray("data");
                                if (data != null && data.length() > 0) {
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject topLineInfo = data.optJSONObject(i);
                                        list.add(new TopLineInfo(topLineInfo));
                                    }
                                }
                                callBack.onSuccess(list);
                            } else {
                                callBack.onFail("");
                            }
                        }
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (callBack != null)
                            callBack.onFail(errMsg);
                    }
                }).build();
        httpRequestTask.execute(HttpServerManager.serverIpJuHeNew);
    }

    public void requestWechat(int page, boolean loading, final DataCallBack<List<WeChatInfo>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("key", "feb7c6290716278ccb598b1532b8bed1");
        param.put("pno", String.valueOf(page));
        param.put("ps", "20");
        HttpRequestTask httpRequestTask = new HttpRequestBuilder(context).interfaceName(ApiConfig.JUHE_WECHAT).param(param).httpMethod(Request.Method.GET)
                .httpRequestLoadingEnum(loading ? HttpRequestLoadingEnum.HTTP_LOADING_DEFAULT : HttpRequestLoadingEnum.HTTP_LOADING_NONE)
                .httpRequestStartListener(new HttpRequestBuilder.IHttpRequestListener() {

                    @Override
                    public void onResponseSuccess(JSONObject jsonObject) {
                        if (jsonObject != null && callBack != null) {
                            List<WeChatInfo> list = new ArrayList<WeChatInfo>();
                            JSONObject result = jsonObject.optJSONObject("result");
                            if (result != null) {
                                JSONArray data = result.optJSONArray("list");
                                if (data != null && data.length() > 0) {
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject weChatInfo = data.optJSONObject(i);
                                        list.add(new WeChatInfo(weChatInfo));
                                    }
                                }
                                callBack.onSuccess(list);
                            }
                        }
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (callBack != null)
                            callBack.onFail(errMsg);
                    }
                }).build();
        httpRequestTask.execute(HttpServerManager.serverIpJuHeNew);
    }
}
