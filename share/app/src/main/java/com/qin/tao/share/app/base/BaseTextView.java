package com.qin.tao.share.app.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class BaseTextView extends TextView
{

//	private static Typeface font = null;

	public BaseTextView(Context context)
	{
		super(context);
//		setFont();
	}

	public BaseTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

//		setFont();
	}

	public BaseTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
//		setFont();
	}



//	public static Typeface getCustomFont()
//	{
//		if (font == null)
//			font = Typeface.createFromAsset(HaoChangApplication.appContext.getAssets(), "fonts/fonts.ttf");
//		return font;
//	}
//
//	/**
//	 * 增加方正字体库
//	 */
//	private void setFont()
//	{
//		if (this.isInEditMode() == false)
//		{
//			setTypeface(getCustomFont(), Typeface.NORMAL);
//		}
//	}

}
