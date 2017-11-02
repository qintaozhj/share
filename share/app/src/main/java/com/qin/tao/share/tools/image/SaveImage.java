package com.qin.tao.share.tools.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class SaveImage
{
	/**
	 * 图片压缩大小
	 */
	public static final int IMAGE_SIZE = 20;

	private SaveImage()
	{
	}

	/**
	 * 把字节数组保存为一个文件
	 */
	@SuppressLint("SimpleDateFormat")
	public static File saveFileByBytes(byte[] b, String outputFile)
	{
		BufferedOutputStream stream = null;

		long curTime = System.currentTimeMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String title = "";// = String.valueOf(System.currentTimeMillis());
		title = dateFormat.format(new Date(curTime));
		String filename = title + ".png";
		String filePath = outputFile + filename;
		File cameraDir = new File(outputFile);
		if (!cameraDir.exists())
			cameraDir.mkdirs();
		File file = null;
		try
		{
			file = new File(outputFile);
			if (!file.exists())
			{
				file.mkdirs();
			}
			FileOutputStream fstream = new FileOutputStream(filePath);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (stream != null)
			{
				try
				{
					stream.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 把字节数组保存到相册
	 */
	@SuppressLint({ "SdCardPath" })
	public static boolean saveFileToAlbum(Context context, byte[] data, String fileName)
	{
		boolean status = false;

		if (data != null)
		{
			Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			try
			{

				ContentResolver cr = context.getContentResolver();
				String url = MediaStore.Images.Media.insertImage(cr, bmp, "", "");
				String path = getAbsoluteImagePath(Uri.parse(url), context);
				//通知刷新相册
				Uri uri = Uri.parse("file://" + path);
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
				status = true;
				status = true;
			} catch (Exception e)
			{
				status = false;
			}

		}

		return status;
	}

	/**
	 * 把字节数组保存到相册
	 */
	public static boolean saveBitmapToAlbum(Context context, Bitmap bitmap, String fileName)
	{
		boolean status = false;
		if (bitmap != null)
		{
			try
			{
				ContentResolver cr = context.getContentResolver();
				String url = MediaStore.Images.Media.insertImage(cr, bitmap, "", "");
				String path = getAbsoluteImagePath(Uri.parse(url), context);
				//通知刷新相册
				Uri uri = Uri.parse("file://" + path);
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
				status = true;
			} catch (Exception e)
			{
				status = false;
			}

		}

		return status;
	}

	/**
	 * 通过URI获取文件路径
	 * @param uri
	 * @param context
	 * @return
	 */
	public static String getAbsoluteImagePath(Uri uri, Context context)
	{
		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = ((Activity) context).managedQuery(uri, proj, // Which columns to return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		if (cursor != null)
		{
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();

			return cursor.getString(column_index);
		} else
		{
			return uri.getPath();
		}
	}

	/**
	 * 保存文件
	 */
	public static boolean saveFileByBitmap(Bitmap bm, String filePath, String fileName) throws IOException
	{
		if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(filePath) || null == bm)
			return false;
		File dirFile = new File(filePath);
		if (!dirFile.exists())
		{
			dirFile.mkdir();
		}
		File myCaptureFile = new File(dirFile, fileName);
		if (!myCaptureFile.exists())
			myCaptureFile.createNewFile();
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
		return true;
	}

	/**
	 * 保存压缩图片到本地
	 * 
	 * @param srcPath
	 * 
	 *            源路径
	 * @param width
	 *            压缩指定宽度
	 * @param height压缩指定高度
	 * @param destFilePath
	 *            目标路径
	 * @param fileName
	 *            文件名称
	 */
	public static void saveFileByCompressBitmap(String srcPath, float width, float height, String destFilePath, String fileName)
	{
		Bitmap bitmap = getimage(srcPath, width, height);
		if (bitmap != null)
		{
			try
			{
				saveFileByBitmap(bitmap, destFilePath, fileName);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 图片按比例大小压缩方法
	 * 
	 * @param srcPath
	 * @return
	 */
	private static Bitmap getimage(String srcPath, float width, float height)
	{
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = width;// 这里设置高度为800f
		float ww = height;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww)
		{// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh)
		{// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap, IMAGE_SIZE);// 压缩好比例大小后再进行质量压缩
	}

	private static Bitmap compressImage(Bitmap image, int size)
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		// 循环判断如果压缩后图片是否大于指定大小,大于继续压缩
		while (baos.toByteArray().length / 1024 >= size)
		{
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 图片按比例大小压缩方法
	 * 
	 * @param image
	 * @return
	 */
	private static Bitmap compressByBitmap(Bitmap image, float width, float height)
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024)
		{// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = width;
		float ww = height;
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww)
		{// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh)
		{// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap, IMAGE_SIZE);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 裁剪缩放图片并保存到本地
	 * 
	 * @param savePath
	 *            目标路径
	 * @param srcPath
	 *            源路径
	 * @param imageW
	 *            保存图片的宽度
	 * @param imageH
	 *            保存图片的高度
	 * @return
	 */
	public static String saveCompressImage(String savePath, String srcPath, int imageW, int imageH)
	{
		//		String newBitmapPath = null;
		//		if (srcPath == null || srcPath.equals(""))
		//		{
		//			return newBitmapPath;
		//		}
		//		try
		//		{
		//			Bitmap bitmap = getimage(srcPath, imageW, imageH);
		//			if (bitmap != null)
		//			{
		//				// newBitmapPath = savePath + "/" + System.currentTimeMillis() +
		//				// ".jpg";
		//				// FileOutputStream fos = new FileOutputStream(newBitmapPath);
		//				File dirFile = new File(savePath);
		//				if (!dirFile.exists())
		//				{
		//					dirFile.mkdir();
		//				}
		//				File myCaptureFile = new File(dirFile, "/" + System.currentTimeMillis() + ".jpg");
		//				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		//				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		//				bos.flush();
		//				bos.close();
		//			}
		//			return newBitmapPath;
		//		} catch (Exception e)
		//		{
		//			System.out.println("图片写入到SD卡异常--------->" + e.toString());
		//		}
		//		return newBitmapPath;

		String newBitmapPath = null;
		if (srcPath == null || srcPath.equals(""))
		{
			return newBitmapPath;
		}
		try
		{
			newBitmapPath = savePath + "/" + System.currentTimeMillis() + ".jpg";
			Bitmap bitmap = getimage(srcPath, imageW, imageH);
			if (bitmap != null)
			{
				FileOutputStream fos = new FileOutputStream(newBitmapPath);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
				fos.flush();
				fos.close();
			}
			return newBitmapPath;
		} catch (Exception e)
		{
			System.out.println("图片写入到SD卡异常--------->" + e.toString());
		}
		return newBitmapPath;

	}

	/**
	 * 裁剪缩放图片并保存到本地
	 * 
	 * @param savePath
	 *            目标路径
	 * @param srcPath
	 *            源路径
	 * @param imageW
	 *            保存图片的宽度
	 * @param imageH
	 *            保存图片的高度
	 * @return
	 */
	public static String saveCompressImageByBitmap(String savePath, Bitmap srcBitmap, int imageW, int imageH)
	{
		//		String newBitmapPath = null;
		//		if (savePath == null || savePath.equals(""))
		//		{
		//			return newBitmapPath;
		//		}
		//		try
		//		{
		//			Bitmap bitmap = compressByBitmap(srcBitmap, imageW, imageH);
		//			if (bitmap != null)
		//			{
		//				File dirFile = new File(savePath);
		//				if (!dirFile.exists())
		//				{
		//					dirFile.mkdir();
		//				}
		//				File myCaptureFile = new File(dirFile, "/" + System.currentTimeMillis() + ".jpg");
		//				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		//				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		//				bos.flush();
		//				bos.close();
		//			}
		//			return newBitmapPath;
		//		} catch (Exception e)
		//		{
		//			System.out.println("图片写入到SD卡异常--------->" + e.toString());
		//		}
		//		return newBitmapPath;

		String newBitmapPath = null;
		if (savePath == null || savePath.equals(""))
		{
			return newBitmapPath;
		}
		try
		{
			newBitmapPath = savePath + "/" + System.currentTimeMillis() + ".jpg";
			Bitmap bitmap = compressByBitmap(srcBitmap, imageW, imageH);
			if (bitmap != null)
			{
				FileOutputStream fos = new FileOutputStream(newBitmapPath);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
			return newBitmapPath;
		} catch (Exception e)
		{
			System.out.println("图片写入到SD卡异常--------->" + e.toString());
		}
		return newBitmapPath;

	}
}
