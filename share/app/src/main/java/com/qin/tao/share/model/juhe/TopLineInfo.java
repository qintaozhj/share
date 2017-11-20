package com.qin.tao.share.model.juhe;

import android.text.TextUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qintao on 2017/9/12 17:02
 *         <p>
 *         "uniquekey": "6c4caa0c3ba6e05e2a272892af43c00e",
 *         "title": "杨幂的发际线再也回不去了么？网友吐槽像半秃",
 *         "date": "2017-01-05 11:03",
 *         "category": "yule",
 *         "author_name": "腾讯娱乐",
 *         "url": "http://mini.eastday.com/mobile/170105110355287.html?qid=juheshuju",
 *         "thumbnail_pic_s": "http://03.imgmini.eastday.com/mobile/20170105/20170105110355_
 *         806f4ed3fe71d04fa452783d6736a02b_1_mwpm_03200403.jpeg",
 *         "thumbnail_pic_s02": "http://03.imgmini.eastday.com/mobile/20170105/20170105110355_
 *         806f4ed3fe71d04fa452783d6736a02b_2_mwpm_03200403.jpeg",
 *         "thumbnail_pic_s03": "http://03.imgmini.eastday.com/mobile/20170105/20170105110355_
 *         806f4ed3fe71d04fa452783d6736a02b_3_mwpm_03200403.jpeg"
 */

public class TopLineInfo {
    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private List<String> picList = new ArrayList<>();

    public TopLineInfo(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.uniquekey = jsonObject.optString("uniquekey");
            this.title = jsonObject.optString("title");
            this.date = jsonObject.optString("date");
            this.category = jsonObject.optString("category");
            this.author_name = jsonObject.optString("author_name");
            this.url = jsonObject.optString("url");
            String thumbnail_pic_s = jsonObject.optString("thumbnail_pic_s");
            String thumbnail_pic_s02 = jsonObject.optString("thumbnail_pic_s02");
            String thumbnail_pic_s03 = jsonObject.optString("thumbnail_pic_s03");
            if (!TextUtils.isEmpty(thumbnail_pic_s))
                picList.add(thumbnail_pic_s);
            if (!TextUtils.isEmpty(thumbnail_pic_s02))
                picList.add(thumbnail_pic_s02);
            if (!TextUtils.isEmpty(thumbnail_pic_s03))
                picList.add(thumbnail_pic_s03);
        }
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}
