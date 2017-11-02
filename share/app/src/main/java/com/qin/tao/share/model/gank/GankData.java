package com.qin.tao.share.model.gank;

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
import java.util.List;

/**
 * @author qintao on 2017/9/7 19:43
 */

public class GankData {
    public GankData(Context context) {
        this.context = context;
    }

    private Context context;

    /**
     * 请求干货福利
     */
    public void requestWelfare(int page, boolean loading, final DataCallBack<List<WelfareInfo>> callBack) {
        HttpRequestTask httpRequestTask = new HttpRequestBuilder(context).interfaceName(ApiConfig.GANK_WELFARE + String.valueOf(page)).httpMethod(Request.Method.GET)
                .httpRequestLoadingEnum(loading ? HttpRequestLoadingEnum.HTTP_LOADING_DEFAULT : HttpRequestLoadingEnum.HTTP_LOADING_NONE)
                .httpRequestStartListener(new HttpRequestBuilder.IHttpRequestListener() {

                    @Override
                    public void onResponseSuccess(JSONObject jsonObject) {
                        if (jsonObject != null && callBack != null) {
                            List<WelfareInfo> list = new ArrayList<WelfareInfo>();
                            JSONArray results = jsonObject.optJSONArray("results");
                            if (results != null && results.length() > 0) {
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject welfare = results.optJSONObject(i);
                                    list.add(new WelfareInfo(welfare));
                                }
                            }
                            callBack.onSuccess(list);
                        }
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (callBack != null) {
                            callBack.onFail(errMsg);
                        }
                    }
                }).build();
        httpRequestTask.execute(HttpServerManager.serverIpGank);
    }

}
