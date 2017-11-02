package com.qin.tao.share.app.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class BaseListView extends ListView
{
	public BaseListView(Context context)
	{
		super(context);
		init();
	}

	public BaseListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public BaseListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	private void init()
	{
		this.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
	}
}
