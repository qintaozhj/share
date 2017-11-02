package com.qin.tao.share.tools.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * 图片信息
 * 
 * @author Administrator
 * 
 */
public class ImageInfo
{
	/**
	 * 缩略图(将图片按比例缩小,再切方块)
	 */
	public static final int ZOOM_THUMBNAIL = 1002;
	/**
	 * 图片对应的视图
	 */
	public Context context = null;
	/**
	 * 图片对应的控件
	 */
	public ImageView imageView = null;
	/**
	 * 图片在磁盘路径
	 */
	public String path = null;
	/**
	 * 图片在网络上的地址
	 */
	public String url = null;
	/**
	 * 默认资源ID
	 */
	public int defaultResId = -1;
	/**
	 * 图片宽度
	 */
	public int w = 0;
	/**
	 * 图片高度
	 */
	public int h = 0;
	/**
	 * 默认缩放模式为缩略图
	 */
	public int zoomModel = ZOOM_THUMBNAIL;
	/**
	 * 是否有渐变
	 */
	public boolean isAlpha = true;
	/**
	 * 圆角角度
	 */
	public int angle = 0;
	/**
	 * 阴影半径
	 */
	public int radius = 0;
	/**
	 * 增加属性
	 * 针对加载失败后 重新加载策略
	 */
	//是否需要重新加载
	public boolean isNeedReload = false;
	//最多重新加载的次数
	public int maxReloadCount = 0;
	//当前已加载次数
	public int currReloadCount = 0;
	//图片是否已加载成功
	public boolean isLoadSucess = false;
	/**
	 * 构造函数
	 *
	 * @param imageView
	 *            装载图片的视图
	 * @param defaultResId
	 *            默认资源ID
	 * @param path
	 *            图片在磁盘上的路径
	 * @param url
	 *            图片在网络上的地址
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @param zoomModel
	 *            缩放模式
	 */
	public ImageInfo(ImageView imageView, int defaultResId, String path, String url, int w, int h, int zoomModel)
	{
		this.imageView = imageView;
		this.defaultResId = defaultResId;
		this.path = path;
		this.url = url;
		this.w = w;
		this.h = h;
		this.zoomModel = zoomModel;
	}
}
