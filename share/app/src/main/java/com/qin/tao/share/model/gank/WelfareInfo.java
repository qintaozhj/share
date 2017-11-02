package com.qin.tao.share.model.gank;

import org.json.JSONObject;

/**
 * @author qintao on 2017/9/11 17:17
 */

public class WelfareInfo {
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public WelfareInfo(JSONObject object) {
        if (object != null) {
            this._id = object.optString("_id");
            this.createdAt = object.optString("createdAt");
            this.desc = object.optString("desc");
            this.publishedAt = object.optString("publishedAt");
            this.source = object.optString("source");
            this.type = object.optString("type");
            this.url = object.optString("url");
            this.used = object.optBoolean("used");
            this.who = object.optString("who");
        }
    }

    public String get_id() {
        return _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean getUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }
}
