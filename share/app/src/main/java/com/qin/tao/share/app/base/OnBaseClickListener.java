package com.qin.tao.share.app.base;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 
 * @author lxl
 *		2015-1-12-下午2:59:36
 *		
 *		OnClickListener基类
 *				说明:	取消implements OnClickListener,统一修改为implements OnBaseClickListener.
 *							必须实现方法onBaseClick(View v)
 *				功能:
 *							1.防止快速点击按钮造成错误.
 *									
 */
public abstract class OnBaseClickListener implements OnClickListener
{
	private final static long DEFAULT_DELAY_TIME = 500;//默认时间为500毫秒
	private final long minimumInterval;
	private Map<View, Long> lastClickMap;

	/**
	 * 子类直接实现onBaseClickListener即可,不需要实现onClick
	 * @param v
	 */
	public abstract void onBaseClick(View v);

	public OnBaseClickListener()
	{
		this(DEFAULT_DELAY_TIME);
	}

	/**
	 *  开放接口,可以自定义延迟时间
	 * @param minimumIntervalMsec :毫秒单位
	 */
	public OnBaseClickListener(long minimumIntervalMsec)
	{
		this.minimumInterval = minimumIntervalMsec;
		this.lastClickMap = new WeakHashMap<View, Long>();
	}

	@Override
	public void onClick(View clickedView)
	{
		Long previousClickTimestamp = lastClickMap.get(clickedView);
		long currentTimestamp = SystemClock.uptimeMillis();

		lastClickMap.put(clickedView, currentTimestamp);
		if (previousClickTimestamp == null || (currentTimestamp - previousClickTimestamp.longValue() > minimumInterval))
		{

			onBaseClick(clickedView);
		}
	}

}
