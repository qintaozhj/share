package com.qin.tao.share.model.juhe;

import org.json.JSONObject;

/**
 * @author qintao on 2017/9/25 14:27
 *         <p>
 *         "id": "wechat_20150401071583",
 *         "title": "孩子们喜欢怎样的房间 你不能装作不知道",
 *         "source": "尚品宅配",
 *         "firstImg": "http://zxpic.gtimg.com/infonew/0/wechat_pics_-214277.jpg/168",
 *         "mark": "",
 *         "url": "http://v.juhe.cn/weixin/redirect?wid=wechat_20150401071583"
 */

public class WeChatInfo {
    private String id;
    private String title;
    private String source;
    private String firstImg;
    private String mark;
    private String url;

    public WeChatInfo(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.id = jsonObject.optString("id");
            this.title = jsonObject.optString("title");
            this.source = jsonObject.optString("source");
            this.firstImg = jsonObject.optString("firstImg");
            this.mark = jsonObject.optString("mark");
            this.url = jsonObject.optString("url");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
