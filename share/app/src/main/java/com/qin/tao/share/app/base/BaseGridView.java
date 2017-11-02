package com.qin.tao.share.app.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by qt on 2016/6/4.
 */
public class BaseGridView extends GridView
{

	public BaseGridView(Context context)
	{
		super(context);
        init();
	}

	public BaseGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
        init();
	}

	public BaseGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
        init();
	}

    private void init()
    {
        this.setOverScrollMode(GridView.OVER_SCROLL_NEVER);
    }
}
