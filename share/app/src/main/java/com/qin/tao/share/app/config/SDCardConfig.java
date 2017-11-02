package com.qin.tao.share.app.config;

import android.os.Environment;

import java.io.File;

/**
 * 本地文件存储路径管理
 * Sdcard相关检测封装
 */
public class SDCardConfig {

    /**
     * 根File 不带斜线 使用前自己带斜线
     */
    public static final File ROOT = Environment.getExternalStorageDirectory();

    /**
     * 应用目录
     */
    public static final String APPLICATION_SYSTEM_PATH = ROOT + File.separator + "share" + File.separator;


    /**
     * 日志  系统目录
     */
    public static final String APPLICATION_SYSTEM_LOG = APPLICATION_SYSTEM_PATH + "log" + File.separator;


    /***
     * 图片换存目录
     */
    public static final String IMAGE_CACHE_ROOT = APPLICATION_SYSTEM_PATH + "image" + File.separator;

    /***
     * 用户主动保存图片目录
     */
    public static final String PHOTO_CACHE_ROOT = APPLICATION_SYSTEM_PATH + "photo" + File.separator;
}
