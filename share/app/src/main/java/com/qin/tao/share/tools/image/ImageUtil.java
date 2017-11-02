package com.qin.tao.share.tools.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageUtil {
    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将字节数组转换为ImageView可调用的Bitmap对象
     *
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    /**
     * 将图片内容解析成字节数组
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }

    //	/**
    //	 * 读取SD卡下的图片
    //	 * @param fileName
    //	 * @return
    //	 */
    //	// 读取SD卡下的图片
    //	public InputStream getBitmapInputStreamFromSDCard(String fileName)
    //	{
    //		if (SDCardUtils.isAvailable())
    //		{
    //			String filePath = SDCardConfig.ROOT.getPath() + File.separator + fileName;
    //			File file = new File(filePath);
    //			try
    //			{
    //				FileInputStream fileInputStream = new FileInputStream(file);
    //				return fileInputStream;
    //			} catch (Exception e)
    //			{
    //				e.printStackTrace();
    //			}
    //
    //		}
    //		return null;
    //	}
}
