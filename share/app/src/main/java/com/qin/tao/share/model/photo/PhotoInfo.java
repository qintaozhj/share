package com.qin.tao.share.model.photo;

import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.URLUtil;

import org.json.JSONObject;

/**
 * 已选择照片信息
 *
 * @author qintao
 */
public class PhotoInfo implements Parcelable {
    /**
     * 图片id
     */
    private String photoId;
    /**
     * 缩略图地址
     */
    private String thumb;
    /**
     * 图片路径
     */
    private String photoPath;

    /**
     * 压缩后的图片路径
     */
    private String compressPhotoPath;

    /**
     * 系统有的缩略图
     */
    private String systemThumbnail;

    /**
     * 压缩状态
     */
    CompressState state = CompressState.uncompressed;

    /**
     * 图片类型
     */
    PhotoType photoType = PhotoType.common;

    /**
     * 选中状态标识 0:未选中  1:已经选中
     */
    private int selectedStatus;

    public PhotoInfo() {

    }

    public PhotoInfo(String photoPath) {
        setPhotoPath(photoPath);
    }

    public PhotoInfo(JSONObject photo) {
        if (null == photo)
            return;
        setPhotoId(photo.optString("photoId"));
        setPhotoPath(photo.optString("url"));
        setThumb(photo.optString("thumb"));
    }

    public PhotoInfo(String photoId, String photoPath) {
        this.photoId = photoId;
        this.photoPath = photoPath;
    }

    public PhotoInfo(Parcel source) {
        if (source != null) {
            photoId = source.readString();
            photoPath = source.readString();
            thumb = source.readString();
            compressPhotoPath = source.readString();
            state = CompressState.values()[source.readInt()];
            photoType = PhotoType.values()[source.readInt()];
            selectedStatus = source.readInt();
        }
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getCompressPhotoPath() {
        return compressPhotoPath;
    }

    public void setCompressPhotoPath(String compressPhotoPath) {
        this.compressPhotoPath = compressPhotoPath;
    }

    public CompressState getState() {
        return state;
    }

    public void setState(CompressState state) {
        this.state = state;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dest != null) {
            dest.writeString(photoId);
            dest.writeString(photoPath);
            dest.writeString(thumb);
            dest.writeString(compressPhotoPath);
            dest.writeInt(state.ordinal());
            dest.writeInt(photoType.ordinal());
            dest.writeInt(selectedStatus);
        }
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            PhotoInfo returnValue = null;
            if (source != null) {
                returnValue = new PhotoInfo(source);
            }
            return returnValue;
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };

    public enum CompressState {
        uncompressed, //未压缩
        compression, //压缩中
        compressed//已压缩
    }

    public enum PhotoType {
        common, //正常图片
        addPhoto, //添加图片按钮
    }

    /**
     * 是否是网络图片
     *
     * @return
     */
    public boolean isNetPhoto() {
        return URLUtil.isNetworkUrl(this.photoPath);
    }

    public String getSystemThumbnail() {
        return systemThumbnail;
    }

    public void setSystemThumbnail(String systemThumbnail) {
        this.systemThumbnail = systemThumbnail;
    }

    /**
     * 是否已经选中
     *
     * @return true:已经选中 false:未选中
     */
    public boolean isSelected() {
        return selectedStatus == 1;
    }

    public void setSelectedStatus(boolean isSelected) {
        this.selectedStatus = isSelected ? 1 : 0;
    }
}
