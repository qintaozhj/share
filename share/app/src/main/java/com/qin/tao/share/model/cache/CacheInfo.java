package com.qin.tao.share.model.cache;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2018/1/4.
 * 本地缓存数据
 */
@DatabaseTable(tableName = "tb_cache")
public class CacheInfo implements Parcelable {
    @DatabaseField(columnName = "id", generatedId = true)
    private long Id;
    @DatabaseField(columnName = "type")
    private String type;//缓存类型
    @DatabaseField(columnName = "content")
    private String content;//缓存内容或者title
    @DatabaseField(columnName = "imgUrl")
    private String imgUrl;//缓存图片url
    @DatabaseField(columnName = "Url")
    private String url;//缓存超链url
    @DatabaseField(columnName = "time")
    private String time;//
    @DatabaseField(columnName = "collection")
    private boolean isCollection = false;

    public CacheInfo() {
    }

    protected CacheInfo(Parcel in) {
        Id = in.readLong();
        type = in.readString();
        content = in.readString();
        imgUrl = in.readString();
        url = in.readString();
        time = in.readString();
    }

    public static final Creator<CacheInfo> CREATOR = new Creator<CacheInfo>() {
        @Override
        public CacheInfo createFromParcel(Parcel in) {
            return new CacheInfo(in);
        }

        @Override
        public CacheInfo[] newArray(int size) {
            return new CacheInfo[size];
        }
    };

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(type);
        dest.writeString(content);
        dest.writeString(imgUrl);
        dest.writeString(url);
        dest.writeString(time);
    }
}
