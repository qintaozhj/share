package com.qin.tao.share.widget.tabview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qintao
 *         created at 2016/6/1 15:49
 */

public class TabSwitcher extends LinearLayout
{

	public TabSwitcher(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.context = context;
	}

	public TabSwitcher(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
	}

	public TabSwitcher(Context context)
	{
		super(context);
		this.context = context;
	}

	private Context context;
	private List<TabView> tabView = new ArrayList<TabView>();
	private int currentSelectedPos = 0;
	private OnTabChangedListener changedListener;

	public void addTab(String tabName, Drawable defaultDrawable, Drawable selectDrawable)
	{
		int total = this.getChildCount();
		LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		TabView tabview = new TabView(context);
		tabview.setLayoutParams(params);
		tabview.setTag(total);
		tabview.setText(tabName, defaultDrawable, selectDrawable);
		this.addView(tabview);
		tabview.setOnClickListener(clickListener);
		this.tabView.add(tabview);
		if (total == 0)
			tabview.setSelected(true);
		else
			tabview.setSelected(false);
	}

	public void setOnTabChangedListener(OnTabChangedListener changedListener)
	{
		this.changedListener = changedListener;
	}

	private OnBaseClickListener clickListener = new OnBaseClickListener()
	{

		@Override
		public void onBaseClick(View v)
		{
			int pos = (Integer) v.getTag();
			setSelectedTable(pos);
		}
	};

	/**
	 * 特殊处理，设置购物车已点商品数量
	 *
	 * @param num
	 */
	public void setShopNum(int num)
	{
		if (!CollectionUtils.isEmpty(tabView))
			for (int i = 0; i < tabView.size(); i++)
			{
				if (tabView.get(i).getText().equals("购物车"))
				{
					tabView.get(i).setNum(num);
					break;
				}
			}
	}

	public boolean setSelectedTable(int position)
	{
		if (currentSelectedPos != position && null != this.tabView && this.tabView.size() > position)
		{
			int oldPos = currentSelectedPos;
			currentSelectedPos = position;

			for (int i = 0; i < this.tabView.size(); i++)
			{
				if (i == position)
				{
					this.tabView.get(i).setSelected(true);
				} else
				{
					this.tabView.get(i).setSelected(false);
				}
			}

			if (changedListener != null)
			{
				changedListener.onTabChanged(oldPos, currentSelectedPos);
			}
			return true;
		}
		return false;
	}

	public interface OnTabChangedListener
	{
		void onTabChanged(int last, int current);
	}
}
