package com.qin.tao.share.model.juhe;

import org.json.JSONObject;

/**
 * @author qintao on 2017/9/12 17:02
 */

public class JokeInfo {
    private String content;
    private String time;
    private String url;

    public JokeInfo(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.content = jsonObject.optString("content");
            this.time = jsonObject.optString("updatetime");
            this.url = jsonObject.optString("url");
        }

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
