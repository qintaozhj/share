package com.qin.tao.share.tools.http.request;

/**
 * @author qintao on 2017/9/6 10:50
 */
public enum HttpMethodEnum {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private String name;

    private HttpMethodEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
