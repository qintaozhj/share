package com.qin.tao.share.tools.http.request;

import android.text.TextUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpRequestEx {

    private String requestUrl = "";

    public Request makeHttpRequest(HttpRequestBuilder builder) {
        //todo 1.检测网络是否连接
        //API地址
        if (TextUtils.isEmpty(builder.interfaceName)) {
            //请求接口为空
            return null;
        }
        HashMap<String, String> valuePair = new HashMap<String, String>();
        //构建正确的 URL 地址.
        requestUrl = "http://";//this.generateRequestUrl(builder.serverEnum, builder.interfaceName, builder.paramMap, builder.httpMethodEnum, valuePair);

        Request okHttpRequest = null;
        try {
            okHttpRequest = generateHttpUriRequest(builder, requestUrl, valuePair);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (okHttpRequest == null) {
            throw null;
        }
        return okHttpRequest;
    }

    private RequestBody getMethodBody(HttpMethodEnum httpMethodEnum) {
        switch (httpMethodEnum) {
            case GET:
                return null;
            case POST:
            case PUT:
            case DELETE:
            case PATCH:


                return null;
            default:
                return null;
        }
    }

    private String generateRequestUrl(String interfaceName, HashMap<String, String> paramMap, HttpMethodEnum httpMethodEnum, HashMap<String, String> valuePair) {
        if (TextUtils.isEmpty(interfaceName))
            return null;
        String url = null;


        url = HttpServerManager.serverIpJuHe + interfaceName;
        //参数可为空
        if (paramMap != null && paramMap.size() > 0) {

//            //生成数字签名
//            if (HttpConfig.isGenerateSing)
//            {
//                String currentTime = String.valueOf(TimeFormat.getServerTimeByLocal());
//                String sign = generateSign(paramMap, currentTime);
//                if (!TextUtils.isEmpty(sign))
//                {
//                    paramMap.put("_time", currentTime);
//                    paramMap.put("_sign", sign);
//                }
//            }
            //生成url地址
            try {
                if (httpMethodEnum == HttpMethodEnum.GET) {
                    url += addHttpGetRequestParam(paramMap, true);
                } else {
                    valuePair.putAll(paramMap);
                }
            } catch (Exception e) {
                //参数传递错误 清空url地址
                url = null;
            }
        }
        return url;
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
            Iterator<Map.Entry<String, String>> iter = paramMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (TextUtils.isEmpty(key) || value == null) {
                    throw new IOException("http error", new Throwable("paramMap key or value is null"));
                }
                if (isUrlEncoder)
                    requestUrl += String.format(Locale.ENGLISH, "%s=%s&", key, URLEncoder.encode(value, "UTF-8"));
                else
                    requestUrl += String.format(Locale.ENGLISH, "%s=%s&", key, value);
            }
            if (!TextUtils.isEmpty(requestUrl))
                requestUrl = requestUrl.substring(0, requestUrl.length() - 1);
        }
        return requestUrl;
    }


    /**
     * 构建统一 HttpUriRequest
     *
     * @param builder
     * @return Get 方式的HttpGet 或  HttpPost 都以 HttpUriRequest 对象 返回
     * @throws UnsupportedEncodingException
     */
    private Request generateHttpUriRequest(HttpRequestBuilder builder, String requestUrl, HashMap<String, String> postValuePair)
            throws UnsupportedEncodingException {
        Request returnValue = null;
        Request.Builder requestBuilder = new Request.Builder();
        if (!TextUtils.isEmpty("")) {
            //有hostName时直接使用,忽略掉interfaceName 重新创建url
//            String url = builder.hostName;
//            try
//            {
//                if (builder.httpMethodEnum == HttpMethodEnum.GET)
//                {
//                    url += addHttpGetRequestParam(builder.paramMap, true);
//                    returnValue = requestBuilder.url(url).get().headers(builder.attachedHeaders).build();
//                } else
//                {
//                    returnValue = requestBuilder.url(url).post(createRequestBody(builder.paramMap)).headers(builder.attachedHeaders).build();
//                }
//            } catch (IOException e)
//            {
//                return returnValue;
//            }
//            returnValue = requestBuilder.build();
            returnValue = null;
        } else {
            //UserToken
//            String authorizeToken;
//            if (TextUtils.isEmpty(builder.token))
//                authorizeToken = UserToken.get();
//            else
//                authorizeToken = builder.token;
//            if (!TextUtils.isEmpty(authorizeToken))
//            {
//                //base64 后一定要 trim !!!
//                //否则会导致 http 协议 post 重载为 put 时,header 在服务端解析失败.
//                try
//                {
//                    authorizeToken = Base64.encodeToString(authorizeToken.getBytes(Charset.forName("UTF-8")), Base64.DEFAULT).trim();
//                } catch (AssertionError uee)
//                {
//                    //兼容性.异常处理
//                    authorizeToken = null;
//                }
//            }
//            switch (builder.httpMethodEnum) {
//                case GET:
//                    requestBuilder = requestBuilder.url(requestUrl).get()//
//                            //  .headers(HttpRequestHeader.getInstance().getHeaders()) //
//                            .header("cli-wifi", NetworkUtils.getNetWorkState() == NetworkUtils.NetWorkEnum.NetWork_WIFI ? "1" : "0");
//                    /*if (!TextUtils.isEmpty(authorizeToken))
//                        requestBuilder = requestBuilder.header("authorize-token", authorizeToken);
//                    if (!TextUtils.isEmpty(cacheToken))
//                        requestBuilder = requestBuilder.header("Local-Cache-Token", cacheToken);*/
//                    returnValue = requestBuilder.build();
//                    break;
//                case POST:
//                case PUT:
//                case DELETE:
//                    requestBuilder = requestBuilder.post(createRequestBody(postValuePair)) //
//                            //  .headers(HttpRequestHeader.getInstance().getHeaders()) //
//                            .url(requestUrl).header("cli-wifi", NetworkUtils.getNetWorkState() == NetworkUtils.NetWorkEnum.NetWork_WIFI ? "1" : "0");
////                    if (!TextUtils.isEmpty(authorizeToken))
////                        requestBuilder = requestBuilder.addHeader("authorize-token", authorizeToken);
////                    if (!TextUtils.isEmpty(cacheToken))
////                        requestBuilder = requestBuilder.addHeader("Local-Cache-Token", cacheToken);
//                    if (builder.httpMethodEnum == HttpMethodEnum.PUT) {
//                        //X-HTTP-Method-Override
//                        requestBuilder = requestBuilder.addHeader("X-HTTP-Method-Override", "PUT");
//                    } else if (builder.httpMethodEnum == HttpMethodEnum.DELETE) {
//                        //X-HTTP-Method-Override
//                        requestBuilder = requestBuilder.addHeader("X-HTTP-Method-Override", "DELETE");
//                    }
//                    returnValue = requestBuilder.build();
//                    break;
//            }
        }
        return returnValue;
    }

    public FormBody createRequestBody(HashMap<String, String> postValuePair) {
        // FIXME: 2016/11/16 the encode format
        FormBody.Builder builder = new FormBody.Builder();
        if (postValuePair != null && postValuePair.size() > 0)
            for (String key : postValuePair.keySet()) {
                builder.addEncoded(key, postValuePair.get(key));
            }
        return builder.build();
    }
}
