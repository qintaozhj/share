package com.qin.tao.share.app.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/8/29.
 */
public class BaseScoreListView extends BaseListView
{
	public BaseScoreListView(Context context)
	{
		super(context);
	}

	public BaseScoreListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public BaseScoreListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	/**
	 * 重写该方法，达到使ListView适应ScrollView的效果
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
