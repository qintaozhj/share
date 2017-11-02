package com.qin.tao.share.app.base;

import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 
 * @author lxl
 *		2015-1-19-上午11:30:31
 *		
 *		
 *
 */
public abstract class OnBaseItemClickListener implements OnItemClickListener
{

	private final static long DEFAULT_DELAY_TIME = 500;//默认时间为500毫秒
	private Map<View, Long> lastClickMap;

	public OnBaseItemClickListener()
	{
		lastClickMap = new WeakHashMap<View, Long>();
	}

	@Override
	public final void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Long previousClickTimestamp = lastClickMap.get(view);
		long currentTimestamp = SystemClock.uptimeMillis();

		lastClickMap.put(view, currentTimestamp);
		if (previousClickTimestamp == null || (currentTimestamp - previousClickTimestamp.longValue() > DEFAULT_DELAY_TIME))
		{
			onBaseItemClick(parent, view, position, id);
		}
	}

	public abstract void onBaseItemClick(AdapterView<?> parent, View view, int position, long id);
}
