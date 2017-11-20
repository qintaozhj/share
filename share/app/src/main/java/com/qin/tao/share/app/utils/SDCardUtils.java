package com.qin.tao.share.app.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.qin.tao.share.app.config.SDCardConfig;
import com.qin.tao.share.app.log.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * SD 卡 相关
 *
 */
public class SDCardUtils {

    /**
     * 获取 SD 卡当前状态是否可用
     *
     * @return true:可用 		false:不可用
     */
    public static boolean isAvailable() {
        boolean returnValue = false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(SDCardConfig.PHOTO_CACHE_ROOT);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        String path = "share" + File.separator + "photo" + File.separator + fileName;
        ToastUtils.showText(context, "保存成功:" + path);
    }

    /**
     * 扩展 SD 卡可用大小
     *
     * @return 单位 MB
     */
    public static float sizeOfAvailable() {
        if (!SDCardUtils.isAvailable()) {
            return 0;
        }
        StatFs stat = new StatFs(SDCardConfig.ROOT.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        float value = (blockSize * availableBlocks) / 1024f / 1024f;
        return value;
    }

    /**
     * 扩展 SD 卡总共大小
     *
     * @return 单位 MB
     */
    public static float sizeOfTotal() {
        if (!SDCardUtils.isAvailable()) {
            return 0;
        }
        StatFs stat = new StatFs(SDCardConfig.ROOT.toString());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        float value = (blockSize * totalBlocks) / 1024f / 1024f;
        return value;
    }

    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            file.createNewFile();
            Logger.e("文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    private static final String TAG = SDCardUtils.class.getSimpleName();

    /**
     * 通过文件路径获取文件名。
     * <p>不带文件类型后缀 hasFiletype传false</p>
     */
    public static String getFilenameWithPath(String filaPath, boolean hasFiletype) {
        if (TextUtils.isEmpty(filaPath))
            return null;
        if (!filaPath.contains("/")) {
            Logger.e(TAG, "路径未包含分隔符/");
            return null;
        }
        int index = filaPath.lastIndexOf("/");
        if (index == filaPath.length() - 1) {
            Logger.e(TAG, "路径以分隔符/结尾");
            return null;
        }
        String lastString = filaPath.substring(index + 1);
        if (hasFiletype)
            return lastString;
        if (lastString.contains(".")) {
            int pointIndex = lastString.indexOf(".");
            if (pointIndex == lastString.length() - 1) {
                Logger.e(TAG, "文件名以.结尾");
                return null;
            }
            return lastString.substring(0, pointIndex);
        } else
            return null;
    }

    /**
     * 通过路径截取文件类型
     */
    public static String getFileType(String filePath) {
        String filenameWithType = getFilenameWithPath(filePath, true);
        if (TextUtils.isEmpty(filenameWithType))
            return null;
        if (filenameWithType.contains(".")) {
            int index = filenameWithType.lastIndexOf(".");
            if (index == filenameWithType.length() - 1) {
                Logger.e(TAG, "文件名以.结尾");
                return null;
            }
            return filenameWithType.substring(index);
        }
        return null;
    }

    /**
     * 文件、文件夹是否存在
     */
    public static boolean isPathExist(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        File file = new File(path);
        if (file.exists())
            return true;
        else
            return false;
    }

    /**
     * creat folder
     */
    public static boolean createFolder(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        File file = new File(path);
        if (file.exists()) {
            if (!file.isDirectory())//same name file
                return false;
            else
                //alread exist;
                return false;
        }
        file.mkdirs();
        if (file.exists())
            return true;
        return false;
    }

    /**
     * create if no exsit
     */
    public static void createFolderIfNotExist(String path) {
        if (isPathExist(path))
            return;
        createFolder(path);
    }

    /**
     * 路径指向类型
     */
    public static PathType isFileOrFolder(String path) {
        if (!isPathExist(path))
            return PathType.NOTEXIST;
        File file = new File(path);
        if (file.isFile())
            return PathType.FILE;
        else if (file.isDirectory())
            return PathType.FOLDER;
        else
            return PathType.UNKNOWN;
    }

    /**
     * create file, file only
     */
    public static boolean createFile(String filePath) {
        if (isPathExist(filePath))
            return true;
        if (TextUtils.isEmpty(filePath))
            return false;
        File file = new File(filePath);
        return createFile(file);
    }

    public static boolean createFile(File file) {
        if (file == null)
            return false;
        String directory = file.getParent();
        if (null != directory)
            createFolderIfNotExist(directory);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * file size
     */
    public static long getFileSize(String filePath) {
        if (!isPathExist(filePath))
            return 0;
        File file = new File(filePath);
        return file.length();
    }

    /**
     * copy file/directory on the same storage
     */
    public static boolean renameFile(String original, String dest) {
        if (!isPathExist(original))
            return false;
        boolean creatDest = createFile(dest);
        if (!creatDest)//create fail
            return false;
        File file = new File(original);
        return file.renameTo(new File(dest));
    }

    public static boolean copyFile(File oldFile, File newFile) {
        boolean returnValue = false;
        InputStream inStream = null;
        FileOutputStream fs = null;
        if (createFile(newFile)) {
            try {
                int byteread = 0;
                if (oldFile.exists()) { //文件存在时
                    inStream = new FileInputStream(oldFile); //读入原文件
                    fs = new FileOutputStream(newFile);
                    byte[] buffer = new byte[1024 * 100];
                    while ((byteread = inStream.read(buffer)) != -1) {
                        fs.write(buffer, 0, byteread);
                    }
                    returnValue = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != inStream) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != fs) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return returnValue;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean returnValue = false;
        InputStream inStream = null;
        FileOutputStream fs = null;
        if (createFile(newPath)) {
            try {
                int byteread = 0;
                File oldfile = new File(oldPath);
                if (oldfile.exists()) { //文件存在时
                    inStream = new FileInputStream(oldPath); //读入原文件
                    fs = new FileOutputStream(newPath);
                    byte[] buffer = new byte[1444];
                    while ((byteread = inStream.read(buffer)) != -1) {
                        fs.write(buffer, 0, byteread);
                    }
                    returnValue = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != inStream) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != fs) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return returnValue;
    }

    /**
     * copy imageloader file for share image
     */
    public static String getImageLoaderDisFileCopy(String url) {
        //		if(TextUtils.isEmpty(url))
        //			return null;
        //		File file = ImageLoader.getInstance().getDiscCache().get(url);
        //		if (file != null && file.exists())
        //		{
        //			saveSpecificBoundImageFile(file.getAbsolutePath(), SDCardConfig.APPLICATION_SYSTEM_MORE_SHARE);
        //			if(new File(SDCardConfig.APPLICATION_SYSTEM_MORE_SHARE).exists())
        //				return SDCardConfig.APPLICATION_SYSTEM_MORE_SHARE;
        //			else
        //				return null;
        //		} else
        return null;
    }

    /**
     * decode image to specify bounds image file
     *
     * @param sourcePath
     */
    private static void saveSpecificBoundImageFile(String sourcePath, String destPath) {
        if (TextUtils.isEmpty(sourcePath) || TextUtils.isEmpty(destPath) || !new File(sourcePath).exists())
            return;
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(sourcePath, option);
        int maxLength = 160;
        option.inJustDecodeBounds = false;
        if (option.outWidth > maxLength || option.outHeight > maxLength) {
            //try to release weight
            int scaleWidth = option.outWidth / maxLength;
            int scaleHeight = option.outHeight / maxLength;
            int zoom = (scaleWidth > scaleHeight ? scaleWidth : scaleHeight);
            int i = 1;
            while (Math.pow(2, i) < zoom) {
                ++i;
            }
            option.inSampleSize = (int) Math.pow(2, i);
        }
        try {
            Bitmap finalBitmap = BitmapFactory.decodeFile(sourcePath, option);
            if (null == finalBitmap)
                return;
            finalBitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(destPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteLocalPath(String path) {
        switch (isFileOrFolder(path)) {
            case FILE:
                deleteSingleFile(path);
                break;
            case FOLDER:
                deleteFolder(path);
                break;
            case UNKNOWN:
                break;
            case NOTEXIST:
                break;
        }

    }

    /**
     * 批量删除多个文件
     *
     * @param pathList
     */
    public static void deleteFiles(List<String> pathList) {
        try {
            if (CollectionUtils.isEmpty(pathList))
                return;
            for (int i = 0; i < pathList.size(); i++) {
                String path = pathList.get(i);
                if (!TextUtils.isEmpty(path)) {
                    File file = new File(path);
                    if (file != null && file.isFile() && file.exists()) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteSingleFile(final String path) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {

            @Override
            public void run() {

                File file = new File(path);
                if (file.canWrite()) {
                    File tempFile = new File(file.getParent(), SDCardUtils.getFilenameWithPath(path, false) + "_temp" + SDCardUtils.getFileType(path));
                    file.renameTo(tempFile);
                    tempFile.delete();
                }
            }
        });
    }

    public static void deleteFolder(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (null == files)
                file.delete();//only folder left
            else {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        if (files[i].exists())
                            files[i].delete();
                    } else if (files[i].isDirectory()) {
                        deleteFolder(files[i].getPath());
                    }
                }
            }
            if (file.exists())
                file.delete();//delete empty folder
        }
    }

    public enum PathType {
        FILE(), FOLDER(), UNKNOWN(), NOTEXIST();
    }

    public class FileExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
        }
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean returnValue = false;
        if (TextUtils.isEmpty(sPath))
            return returnValue;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return returnValue;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean returnValue = false;
        if (!TextUtils.isEmpty(sPath)) {
            File file = new File(sPath);
            // 路径为文件且不为空则进行删除
            if (file != null && file.isFile() && file.exists()) {
                file.delete();
                returnValue = true;
            }
        }
        return returnValue;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean returnValue = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                returnValue = deleteFile(files[i].getAbsolutePath());
                if (!returnValue)
                    break;
            } //删除子目录
            else {
                returnValue = deleteDirectory(files[i].getAbsolutePath());
                if (!returnValue)
                    break;
            }
        }
        if (!returnValue)
            return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取 路径下 的子文件夹和文件 总数
     *
     * @param path
     * @return
     */
    public static int getFilesCount(String path) {
        int returnValue = 0;
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                String[] files = file.list();
                if (files != null)
                    returnValue = files.length;
            }
        }
        return returnValue;
    }
}
