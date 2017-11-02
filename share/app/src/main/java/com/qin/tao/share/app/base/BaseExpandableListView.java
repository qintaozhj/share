package com.qin.tao.share.app.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class BaseExpandableListView extends ExpandableListView
{
	public BaseExpandableListView(Context context)
	{
		super(context);
		init();
	}

	public BaseExpandableListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public BaseExpandableListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	private void init()
	{
		this.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
	}
}
