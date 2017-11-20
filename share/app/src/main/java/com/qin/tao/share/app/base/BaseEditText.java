package com.qin.tao.share.app.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 *封装EditText(根据UI规范对Edittext进行通用管理)
 */
public class BaseEditText extends EditText
{
	public BaseEditText(Context context)
	{
		super(context);
//		setFont();
	}

	public BaseEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
//		setFont();
	}

	public BaseEditText(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
//		setFont();
	}

//	/**
//	 * 增加方正字体库
//	 */
//	private void setFont()
//	{
//		if (this.isInEditMode() == false)
//		{
//			setTypeface(BaseTextView.getCustomFont(), Typeface.NORMAL);
//		}
//	}
}
