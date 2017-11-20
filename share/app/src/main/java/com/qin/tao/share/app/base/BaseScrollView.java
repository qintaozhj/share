package com.qin.tao.share.app.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * ScrollView 基类.方便统一维护
 * <br/>setOverScrollMode 为 Never
 */
public class BaseScrollView extends ScrollView
{
	public BaseScrollView(Context context)
	{
		super(context);
		this.init();
	}

	public BaseScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.init();
	}

	public BaseScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.init();
	}

	private void init()
	{
		if (this.getOverScrollMode() != ScrollView.OVER_SCROLL_NEVER)
			this.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
	}
}
