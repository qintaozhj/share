package com.qin.tao.share.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseTextView;


/**
 * 
 *
 *	通用toast提示 (需完善)
 */
public class ToastUtils
{
	private volatile static ToastUtils globalBoast = null;
	public static final int TOAST_TIME = 2000;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Internal reference to the {@link Toast} object that will be displayed.
	 */
	private Toast internalToast;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Private constructor creates a new {@link Toast} from a given
	 * {@link Toast}.
	 * 
	 * @throws NullPointerException
	 *         if the parameter is <code>null</code>.
	 */
	private ToastUtils(Toast toast)
	{
		// null check
		if (toast == null)
		{
			throw new NullPointerException("Boast.Boast(Toast) requires a non-null parameter.");
		}

		internalToast = toast;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Make a standard {@link ToastUtils} that just contains a text view.
	 * 
	 * @param context
	 *        The context to use. Usually your {@link android.app.Application}
	 *        or {@link android.app.Activity} object.
	 * @param text
	 *        The text to show. Can be formatted text.
	 * @param duration
	 *        How long to display the message. Either {@link Toast#LENGTH_SHORT} or
	 *        {@link Toast#LENGTH_LONG}
	 */
	@SuppressLint("ShowToast")
	public static ToastUtils makeText(Context context, CharSequence text, int duration)
	{
		return new ToastUtils(InnerCreater(context, text, duration));
	}

	/**
	 * Make a standard {@link ToastUtils} that just contains a text view with the
	 * text from a resource.
	 * 
	 * @param context
	 *        The context to use. Usually your {@link android.app.Application}
	 *        or {@link android.app.Activity} object.
	 * @param resId
	 *        The resource id of the string resource to use. Can be formatted
	 *        text.
	 * @param duration
	 *        How long to display the message. Either {@link Toast#LENGTH_SHORT} or
	 *        {@link Toast#LENGTH_LONG}
	 * 
	 * @throws Resources.NotFoundException
	 *         if the resource can't be found.
	 */
	@SuppressLint("ShowToast")
	public static ToastUtils makeText(Context context, int resId, int duration) throws Resources.NotFoundException
	{
		return new ToastUtils(InnerCreater(context, resId, duration));
	}

	/**
	 * Make a standard {@link ToastUtils} that just contains a text view. Duration
	 * defaults to {@link Toast#LENGTH_SHORT}.
	 * 
	 * @param context
	 *        The context to use. Usually your {@link android.app.Application}
	 *        or {@link android.app.Activity} object.
	 * @param text
	 *        The text to show. Can be formatted text.
	 */
	@SuppressLint("ShowToast")
	public static ToastUtils makeText(Context context, CharSequence text)
	{
		return new ToastUtils(InnerCreater(context, text, Toast.LENGTH_SHORT));
	}

	/**
	 * Make a standard {@link ToastUtils} that just contains a text view with the
	 * text from a resource. Duration defaults to {@link Toast#LENGTH_SHORT}.
	 * 
	 * @param context
	 *        The context to use. Usually your {@link android.app.Application}
	 *        or {@link android.app.Activity} object.
	 * @param resId
	 *        The resource id of the string resource to use. Can be formatted
	 *        text.
	 * 
	 * @throws Resources.NotFoundException
	 *         if the resource can't be found.
	 */
	@SuppressLint("ShowToast")
	public static ToastUtils makeText(Context context, int resId) throws Resources.NotFoundException
	{
		return new ToastUtils(InnerCreater(context, resId, Toast.LENGTH_SHORT));
	}

	public static ToastUtils make(Context context, View vContent, int duration)
	{
		return new ToastUtils(InnerCreater(context, vContent, duration));
	}

	@SuppressLint("InflateParams")
	private static Toast InnerCreater(Context context, int resId, int duration)
	{
		Toast returnValue = null;
		if (context != null)
			returnValue = InnerCreater(context, context.getString(resId), duration);
		return returnValue;
	}

	/**
	 * @update xjunjie@gmail.com  2015-2-4 下午3:10:17
	 * 		更新显示位置为 底部 150 像素
	 * @param context
	 * @param info
	 * @param duration
	 * @return
	 */
	@SuppressLint("InflateParams")
	private static Toast InnerCreater(Context context, CharSequence info, int duration)
	{
		Toast returnValue = null;
		if (context != null)
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			View vContent = inflater.inflate(R.layout.view_toast, null);
			BaseTextView tvContent = (BaseTextView) vContent.findViewById(R.id.tvContent);
			tvContent.setText(info);
			tvContent.bringToFront();
			tvContent.getBackground().setAlpha(120);
			returnValue = new Toast(context.getApplicationContext());
			returnValue.setGravity(Gravity.BOTTOM, 0, 50);
			returnValue.setDuration(duration);
			returnValue.setView(vContent);
		}
		return returnValue;
	}

	private static Toast InnerCreater(Context context, View vContent, int duration)
	{
		Toast returnValue = null;
		if (context != null && vContent != null)
		{
			returnValue = new Toast(context.getApplicationContext());
			returnValue.setGravity(Gravity.CENTER, 0, 0);
			returnValue.setDuration(duration);
			returnValue.setView(vContent);
		}
		return returnValue;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Show a standard {@link ToastUtils} that just contains a text view.
	 * 
	 * @param context
	 *        The context to use. Usually your {@link android.app.Application}
	 *        or {@link android.app.Activity} object.
	 * @param text
	 *        The text to show. Can be formatted text.
	 * @param duration
	 *        How long to display the message. Either {@link Toast#LENGTH_SHORT} or
	 *        {@link Toast#LENGTH_LONG}
	 */
	public static void showText(Context context, CharSequence text, int duration)
	{
		if (context == null)
			return;
		ToastUtils.makeText(context, text, duration).show();
	}

	/**
	 * Show a standard {@link ToastUtils} that just contains a text view with the
	 * text from a resource.
	 * 
	 * @param context
	 *        The context to use. Usually your {@link android.app.Application}
	 *        or {@link android.app.Activity} object.
	 * @param resId
	 *        The resource id of the string resource to use. Can be formatted
	 *        text.
	 * @param duration
	 *        How long to display the message. Either {@link Toast#LENGTH_SHORT} or
	 *        {@link Toast#LENGTH_LONG}
	 * 
	 * @throws Resources.NotFoundException
	 *         if the resource can't be found.
	 */
	public static void showText(Context context, int resId, int duration) throws Resources.NotFoundException
	{
		if (context == null)
			return;
		ToastUtils.makeText(context, resId, duration).show();
	}

	/**
	 * Show a standard {@link ToastUtils} that just contains a text view. Duration
	 * defaults to {@link Toast#LENGTH_SHORT}.
	 * 
	 * @param context
	 *        The context to use. Usually your {@link android.app.Application}
	 *        or {@link android.app.Activity} object.
	 * @param text
	 *        The text to show. Can be formatted text.
	 */
	public static void showText(Context context, CharSequence text)
	{
		if (context == null)
			return;
		ToastUtils.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Show a standard {@link ToastUtils} that just contains a text view with the
	 * text from a resource. Duration defaults to {@link Toast#LENGTH_SHORT}.
	 * 
	 * @param context
	 *        The context to use. Usually your {@link android.app.Application}
	 *        or {@link android.app.Activity} object.
	 * @param resId
	 *        The resource id of the string resource to use. Can be formatted
	 *        text.
	 * 
	 * @throws Resources.NotFoundException
	 *         if the resource can't be found.
	 */
	public static void showText(Context context, int resId) throws Resources.NotFoundException
	{
		ToastUtils.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 *  Show a standard {@link ToastUtils} that just contains a  view .
	 * @param view
	 * @param duration
	 *        How long to display the message. Either {@link Toast#LENGTH_SHORT} or
	 *        {@link Toast#LENGTH_LONG}
	 */
	public static void show(Context context, View view, int duration)
	{
		if (context != null && view != null)
			ToastUtils.make(context, view, duration).show();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Close the view if it's showing, or don't show it if it isn't showing yet.
	 * You do not normally have to call this. Normally view will disappear on
	 * its own after the appropriate duration.
	 */
	public void cancel()
	{
		internalToast.cancel();
	}

	/**
	 * Show the view for the specified duration. By default, this method cancels
	 * any current notification to immediately display the new one. For
	 * conventional {@link Toast#show()} queueing behaviour, use method
	 * {@link #show(boolean)}.
	 * 
	 * @see #show(boolean)
	 */
	public void show()
	{
		show(true);
	}

	/**
	 * Show the view for the specified duration. This method can be used to
	 * cancel the current notification, or to queue up notifications.
	 * 
	 * @param cancelCurrent
	 *        <code>true</code> to cancel any current notification and replace
	 *        it with this new one
	 * 
	 * @see #show()
	 */
	public void show(boolean cancelCurrent)
	{
		// cancel current
		if (cancelCurrent && (globalBoast != null))
		{
			globalBoast.cancel();
		}
		// save an instance of this current notification
		globalBoast = this;
		internalToast.show();
	}

}
