package com.qin.tao.share.tools.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

public class DecodeImage
{
	private static final String TAG = "DecodeImage";

	private DecodeImage()
	{
	}

	// 将Drawable转化为Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable)
	{
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 通过资源和资源id解析出bitmap对象
	 * 
	 * @param res
	 * @param resId
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId)
	{
		if (res != null)
		{
			return BitmapFactory.decodeResource(res, resId);
		}
		return null;
	}

	/**
	 * 通过资源id和高、宽度解析出图片
	 * 
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
	{

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		BitmapFactory.decodeResource(res, resId, options);
		options.inSampleSize = ResizeImage.calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		try
		{
			return BitmapFactory.decodeResource(res, resId, options);
		} catch (OutOfMemoryError e)
		{
			Log.e(TAG, "decodeSampledBitmapFromResource内存溢出，如果频繁出现这个情况，可以尝试配置增加内存缓存大小");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过完整文件名和高宽度解析出图片
	 * 
	 * @param filename
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight)
	{
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		BitmapFactory.decodeFile(filename, options);
		options.inSampleSize = ResizeImage.calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		try
		{
			return BitmapFactory.decodeFile(filename, options);
			// return BitmapFactory.decodeStream(inputStream, null, options);
		} catch (OutOfMemoryError e)
		{
			Log.e(TAG, "decodeSampledBitmapFromFile内存溢出，如果频繁出现这个情况，可以尝试配置增加内存缓存大小");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过文件描述和宽高度解析出图片
	 * 
	 * @param fileDescriptor
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight)
	{

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
		options.inSampleSize = ResizeImage.calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		try
		{
			return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
		} catch (OutOfMemoryError e)
		{
			Log.e(TAG, "decodeSampledBitmapFromDescriptor内存溢出，如果频繁出现这个情况，可以尝试配置增加内存缓存大小");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得圆角图片
	 * @param uri
	 * @return
	 */
	public static Bitmap decodeUriAsBitmap(Uri uri, Context context, int imageHeadW)
	{
		Bitmap bitmap = null;
		try
		{
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
			bitmap = ResizeImage.scaleBitmap(bitmap, imageHeadW / 500f);
			bitmap = ImageEffect.getRoundedCornerBitmap(bitmap, 8);
		} catch (FileNotFoundException e)
		{
			System.out.println("-----------裁剪方法2---------------" + e.toString());
		}
		return bitmap;
	}

}
