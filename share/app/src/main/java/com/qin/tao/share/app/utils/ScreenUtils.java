package com.qin.tao.share.app.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 *@author qintao
 *created at 2016/6/4 17:14
 */

public class ScreenUtils
{

	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	public static int getScreenHight(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}
}
