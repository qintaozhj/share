package com.qin.tao.share.tools.image.core.display;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.qin.tao.share.tools.image.core.assist.LoadedFrom;
import com.qin.tao.share.tools.image.core.imageaware.ImageAware;

/**
 * 圆形图片.淡入.设置
 * @author xjunjie@gmail.com 2016-3-30下午12:21:26
 */
public class CircleFadeInBitmapDisplayer extends CircleBitmapDisplayer
{
	private final int durationMillis;

	private final boolean animateFromNetwork;
	private final boolean animateFromDisc;
	private final boolean animateFromMemory;

	/**
	 * @param durationMillis Duration of "fade-in" animation (in milliseconds)
	 */
	public CircleFadeInBitmapDisplayer(int durationMillis)
	{
		this(durationMillis, true, true, true);
	}

	/**
	 * @param durationMillis     Duration of "fade-in" animation (in milliseconds)
	 * @param animateFromNetwork Whether animation should be played if image is loaded from network
	 * @param animateFromDisc    Whether animation should be played if image is loaded from disc cache
	 * @param animateFromMemory  Whether animation should be played if image is loaded from memory cache
	 */
	public CircleFadeInBitmapDisplayer(int durationMillis, boolean animateFromNetwork, boolean animateFromDisc, boolean animateFromMemory)
	{
		this.durationMillis = durationMillis;
		this.animateFromNetwork = animateFromNetwork;
		this.animateFromDisc = animateFromDisc;
		this.animateFromMemory = animateFromMemory;
	}

	/**
	 * Animates {@link ImageView} with "fade-in" effect
	 *
	 * @param imageView      {@link ImageView} which display image in
	 * @param durationMillis The length of the animation in milliseconds
	 */
	public static void animate(View imageView, int durationMillis)
	{
		if (imageView != null)
		{
			AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
			fadeImage.setDuration(durationMillis);
			fadeImage.setInterpolator(new DecelerateInterpolator());
			imageView.startAnimation(fadeImage);
		}
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom)
	{
		super.display(bitmap, imageAware, loadedFrom);
		if ((animateFromNetwork && loadedFrom == LoadedFrom.NETWORK) || (animateFromDisc && loadedFrom == LoadedFrom.DISC_CACHE)
				|| (animateFromMemory && loadedFrom == LoadedFrom.MEMORY_CACHE))
		{
			animate(imageAware.getWrappedView(), durationMillis);
		}
	}
}
