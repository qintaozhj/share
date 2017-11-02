package com.qin.tao.share.tools.http.request;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qin.tao.share.R;
import com.qin.tao.share.tools.http.AppRequestQueue;
import com.qin.tao.share.tools.http.VolleyException;
import com.qin.tao.share.widget.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求
 * StringRequest 在传参数的时候可以用getParams()进行获取
 * JsonObjectRequest 则getParams()无效，需通过其他方法来获取或者自己组装参数到url
 */
public class HttpRequestTask {

    private HttpRequestBuilder builder;
    private StringRequest httpRequest;


    public HttpRequestTask(HttpRequestBuilder builder) {
        this.builder = builder;
    }

    /**
     * 执行请求
     */
    public void execute(String host) {
        if (builder == null || TextUtils.isEmpty(host))
            return;
        String url = host + builder.interfaceName;
        if (builder.httpMethod == Request.Method.GET)
            try {
                url += addHttpGetRequestParam(builder.paramMap, false);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        httpRequest = new StringRequest(builder.httpMethod, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LoadingDialog.closeDialog();
                try {
                    JSONObject object = new JSONObject(response);
                    if (builder.httpRequestListener != null)
                        builder.httpRequestListener.onResponseSuccess(object);
                } catch (JSONException e) {
                    if (builder.httpRequestListener != null)
                        builder.httpRequestListener.onError(builder.context.getString(R.string.data_error));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LoadingDialog.closeDialog();
                if (builder.httpRequestListener != null)
                    builder.httpRequestListener.onError(VolleyException.getVolleyErrorMsg(error.toString()));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return builder.paramMap;
            }
        };
        httpRequest.setRetryPolicy(AppRequestQueue.instance().getRetryPolicy());
        //执行请求开始
        if (builder.httpRequestLoadingEnum != HttpRequestLoadingEnum.HTTP_LOADING_NONE)
            new LoadingDialog(builder.context).show();
        AppRequestQueue.instance().getQueue().add(httpRequest);
    }

    /**
     * 增加HttpGet参数
     *
     * @param paramMap 参数列表
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private String addHttpGetRequestParam(HashMap<String, String> paramMap, boolean isUrlEncoder) throws IOException {
        String requestUrl = "";
        if (paramMap != null && paramMap.size() > 0) {
            requestUrl += "?";
            for (Map.Entry<String, String> stringStringEntry : paramMap.entrySet()) {
                Map.Entry entry = (Map.Entry) stringStringEntry;
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (TextUtils.isEmpty(key) || value == null) {
                    throw new IOException("请求参数有误", new Throwable("paramMap key or value is null"));
                }
                if (isUrlEncoder)
                    requestUrl += String.format("%s=%s&", key, URLEncoder.encode(value, "UTF-8"));
                else
                    requestUrl += String.format("%s=%s&", key, value);
            }
            if (!TextUtils.isEmpty(requestUrl))
                requestUrl = requestUrl.substring(0, requestUrl.length() - 1);
        }
        return requestUrl;
    }
}
