package com.qin.tao.share.tools.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView.ScaleType;

public class ResizeImage
{
	/**
	 * 图片压缩大小
	 */
	public static final int IMAGE_SIZE = 20;

	private ResizeImage()
	{

	}

	/**
	 * 按比例缩放图片
	 * 
	 * @param bitmap
	 * @param scale
	 * @return
	 */
	public static Bitmap scaleBitmap(Bitmap bitmap, float scale)
	{
		if (bitmap == null)
		{
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap tempBitmap = null;
		try
		{
			tempBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		} catch (OutOfMemoryError e)
		{
			System.gc();
			if (tempBitmap != null)
			{
				tempBitmap.recycle();
				tempBitmap = null;
			}
			// tempBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
			// matrix, true);
			System.err.println("缩放图片内存溢出---->" + e.toString());
		}
		return tempBitmap;
	}

	/**
	 * 将图片缩放到指定大小
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap scaleBitmap(Bitmap bitmap, float w, float h)
	{
		if (bitmap == null)
		{
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleW = w / (float) width;
		float scaleH = h / (float) height;
		matrix.postScale(scaleW, scaleH);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return bitmap;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth)
		{
			if (width > height)
			{
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else
			{
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}

			final float totalPixels = width * height;

			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)
			{
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	/**
	 * 通过原图和宽高度及缩放类型创建新图片
	 * 
	 * @param unscaledBitmap
	 * @param dstWidth
	 * @param dstHeight
	 * @param scalingLogic
	 * @return
	 */
	public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScaleType scalingLogic)
	{
		Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
		Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
		Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaledBitmap);
		canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
		return scaledBitmap;

	}

	// 根据dstWOrH计算原图应该截取的截图合适的高宽比例
	public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScaleType scalingLogic)
	{

		if (scalingLogic == ScaleType.CENTER_CROP)
		{

			final float srcAspect = (float) srcWidth / (float) srcHeight;

			final float dstAspect = (float) dstWidth / (float) dstHeight;

			if (srcAspect > dstAspect)
			{

				final int srcRectWidth = (int) (srcHeight * dstAspect);

				final int srcRectLeft = (srcWidth - srcRectWidth) / 2;

				return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);

			} else
			{

				final int srcRectHeight = (int) (srcWidth / dstAspect);

				final int scrRectTop = (srcHeight - srcRectHeight) / 2;

				return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);

			}

		} else
		{

			return new Rect(0, 0, srcWidth, srcHeight);

		}

	}

	// 根据dstWOrH计算原图应该截取的期望图的高宽比例图

	public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScaleType scalingLogic)
	{

		if (scalingLogic == ScaleType.FIT_XY)
		{

			final float srcAspect = (float) srcWidth / (float) srcHeight;

			final float dstAspect = (float) dstWidth / (float) dstHeight;

			if (srcAspect > dstAspect)
			{

				return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));

			} else
			{

				return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);

			}

		} else
		{

			return new Rect(0, 0, dstWidth, dstHeight);

		}

	}

	/**
	 * 根据放大倍数获得截取图scale缩放的图片
	 * 
	 * 
	 * @param unscaledBitmap
	 *            the bitmap of source
	 * 
	 * @param scale
	 *            the scale you want
	 * 
	 * @param scalingLogic
	 *            it is ScaleType
	 * 
	 * @return the scaled bitmap
	 */

	public static Bitmap createBMScaleBitmap(Bitmap unscaledBitmap, Float scale, ScaleType scalingLogic)
	{

		int dstWidth = (int) (unscaledBitmap.getWidth() * scale);

		int dstHeight = (int) (unscaledBitmap.getHeight() * scale);

		return createScaledBitmap(unscaledBitmap, dstWidth, dstHeight, scalingLogic);

	}

}