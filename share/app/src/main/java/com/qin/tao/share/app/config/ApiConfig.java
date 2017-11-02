package com.qin.tao.share.app.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author qintao on 2017/9/7 19:44
 */

public class ApiConfig {
    /**
     * 干货福利
     */
    private static String str = "福利";
    private static String welfare;

    static {
        try {
            welfare = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static final String GANK_WELFARE = "/api/data/" + welfare + "/12/";

    /***
     * 聚合笑话
     */
    public static final String JUHE_JOKE_TEXT = "/joke/content/text.from";

    /***
     * 聚合趣图
     */
    public static final String JUHE_JOKE_IMG = "/joke/img/text.from";

    /***
     * 聚合头条
     */
    public static final String JUHE_TOP_LINE = "/toutiao/index";

    /***
     * 聚合微信精选
     */
    public static final String JUHE_WECHAT = "/weixin/query";


}
