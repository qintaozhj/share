package com.qin.tao.share.tools.image.core.display;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.qin.tao.share.tools.image.core.assist.LoadedFrom;
import com.qin.tao.share.tools.image.core.imageaware.ImageAware;

/** 
/** 圆直角图片+淡入效果(用于榜单)
 * Created by Wang.Yu on 2016/8/23.
 */
public class RoundedFadeInBitmapDisplayer extends CustomRoundedBitmapDisplayer
{
	private int durationMillis;

	public RoundedFadeInBitmapDisplayer(int durationMillis, int cornerRadiusPixels)
	{
		super(cornerRadiusPixels, Matrix.ScaleToFit.CENTER);
		this.durationMillis = durationMillis;
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
		animate(imageAware.getWrappedView(), durationMillis);
	}
}
