package com.qin.tao.share.model.cache;

/**
 * Created by Administrator on 2018/1/4.
 * 收藏缓存.数据类型
 */

public enum CacheEnum {
    JOKE_TEXT("joke_text"),
    JOKE_IMG("joke_img");

    private String type;

    CacheEnum(String value) {
        this.type = value;
    }

    public String getType() {
        return type;
    }

    public static CacheEnum getType(String value) {
        CacheEnum type = null;
        if (JOKE_TEXT.getType().equalsIgnoreCase(value)) {
            type = JOKE_TEXT;
        } else if (JOKE_IMG.getType().equalsIgnoreCase(value)) {
            type = JOKE_IMG;
        }
        return type;
    }
}
