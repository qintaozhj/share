/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.qin.tao.share.tools.image.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Provides application storage paths
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public final class StorageUtils
{
	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
	private static final String INDIVIDUAL_DIR_NAME = "uil-images";

	private StorageUtils()
	{
	}

	/**
	 * Returns application cache directory. Cache directory will be created on SD card
	 * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted and app has appropriate permission. Else -
	 * Android defines cache directory on device's file system.
	 *
	 * @param context Application context
	 * @return Cache {@link File directory}.<br />
	 * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
	 * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
	 */
	public static File getCacheDirectory(Context context)
	{
		return getCacheDirectory(context, true);
	}

	/**
	 * XXX 米虫.扩展 Add by xjunjie@gmail.com
	 */
	public static File getCacheDirectoryByMC(Context context, String cacheDir)
	{
		return getCacheDirectory(context, true, cacheDir);
	}

	/**
	 * Returns application cache directory. Cache directory will be created on SD card
	 * <i>("/Android/data/[app_package_name]/cache")</i> (if card is mounted and app has appropriate permission) or
	 * on device's file system depending incoming parameters.
	 *
	 * @param context        Application context
	 * @param preferExternal Whether prefer external location for cache
	 * @param cacheDir XXX 米虫.扩展 Add by xjunjie@gmail.com
	 * @return Cache {@link File directory}.<br />
	 * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
	 * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
	 */
	public static File getCacheDirectory(Context context, boolean preferExternal, String cacheDir)
	{
		File appCacheDir = null;
		String externalStorageState;
		try
		{
			externalStorageState = Environment.getExternalStorageState();
		} catch (NullPointerException e)
		{ // (sh)it happens (Issue #660)
			externalStorageState = "";
		} catch (IncompatibleClassChangeError e)
		{ // (sh)it happens too (Issue #989)
			externalStorageState = "";
		}
		if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context))
		{
			appCacheDir = getExternalCacheDir(context, cacheDir);
		}
		if (appCacheDir == null)
		{
			appCacheDir = context.getCacheDir();
		}
		if (appCacheDir == null)
		{
			String dataPath = context.getFilesDir().getPath();
			String cacheDirPath = (dataPath.endsWith(File.separator) ? dataPath : dataPath + File.separator) + context.getPackageName() + File.separator
					+ "cache" + File.separator;
			L.w("Can't define system cache directory! '%s' will be used.", cacheDirPath);
			appCacheDir = new File(cacheDirPath);
		}
		return appCacheDir;
	}

	public static File getCacheDirectory(Context context, boolean preferExternal)
	{
		return getCacheDirectory(context, preferExternal, null);
	}

	/**
	 * XXX 米虫.扩展 Add by xjunjie@gmail.com
	 */
	public static File getIndividualCacheDirectoryByMC(Context context, String cacheDir)
	{
		File appCacheDir = getCacheDirectoryByMC(context, cacheDir);
		File individualCacheDir = new File(appCacheDir, INDIVIDUAL_DIR_NAME);
		if (!individualCacheDir.exists())
		{
			if (!individualCacheDir.mkdir())
			{
				individualCacheDir = appCacheDir;
			}
		}
		return individualCacheDir;
	}

	/**
	 * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
	 * created on SD card <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is mounted and app has
	 * appropriate permission. Else - Android defines cache directory on device's file system.
	 *
	 * @param context Application context
	 * @return Cache {@link File directory}
	 */
	public static File getIndividualCacheDirectory(Context context)
	{
		return getIndividualCacheDirectory(context, INDIVIDUAL_DIR_NAME);
	}

	/**
	 * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
	 * created on SD card <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is mounted and app has
	 * appropriate permission. Else - Android defines cache directory on device's file system.
	 *
	 * @param context Application context
	 * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
	 * @return Cache {@link File directory}
	 */
	public static File getIndividualCacheDirectory(Context context, String cacheDir)
	{
		File appCacheDir = getCacheDirectory(context);
		File individualCacheDir = new File(appCacheDir, cacheDir);
		if (!individualCacheDir.exists())
		{
			if (!individualCacheDir.mkdir())
			{
				individualCacheDir = appCacheDir;
			}
		}
		return individualCacheDir;
	}

	/**
	 * Returns specified application cache directory. Cache directory will be created on SD card by defined path if card
	 * is mounted and app has appropriate permission. Else - Android defines cache directory on device's file system.
	 *
	 * @param context  Application context
	 * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
	 * @return Cache {@link File directory}
	 */
	public static File getOwnCacheDirectory(Context context, String cacheDir)
	{
		File appCacheDir = null;
		if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context))
		{
			appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
		}
		if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs()))
		{
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}

	/**
	 * Returns specified application cache directory. Cache directory will be created on SD card by defined path if card
	 * is mounted and app has appropriate permission. Else - Android defines cache directory on device's file system.
	 *
	 * @param context  Application context
	 * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
	 * @return Cache {@link File directory}
	 */
	public static File getOwnCacheDirectory(Context context, String cacheDir, boolean preferExternal)
	{
		File appCacheDir = null;
		if (preferExternal && MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context))
		{
			appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
		}
		if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs()))
		{
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}

	/**
	 * 返回文件路径为: 外部存储设备/Android/data/com.xxxx.xxxx/cache
	 * 同时,创建文件 外部存储设备/Android/data/com.xxxx.xxxx/cache/.nomedia
	 * @param context 上下文
	 * @param cacheDir
	 * @return
	 */
	private static File getExternalCacheDir(Context context, String cacheDir)
	{
		File appCacheDir = null;
		boolean isNeedDefaultAppCacheDir = false;
		if (!TextUtils.isEmpty(cacheDir))
		{
			//XXX 米虫.扩展 Add by xjunjie@gmail.com
			appCacheDir = new File(cacheDir);
		} else
		{
			isNeedDefaultAppCacheDir = true;
		}

		if (isNeedDefaultAppCacheDir)
		{
			File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
			appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
		}
		if (!appCacheDir.exists())
		{
			if (!appCacheDir.mkdirs())
			{
				L.w("Unable to create external cache directory");
				return null;
			}
			try
			{
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e)
			{
				L.i("Can't create \".nomedia\" file in application external cache directory");
			}
		}
		return appCacheDir;
	}
	//	private static File getExternalCacheDir(Context context)
	//	{
	//		File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
	//		File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
	//		if (!appCacheDir.exists())
	//		{
	//			if (!appCacheDir.mkdirs())
	//			{
	//				L.w("Unable to create external cache directory");
	//				return null;
	//			}
	//			try
	//			{
	//				new File(appCacheDir, ".nomedia").createNewFile();
	//			} catch (IOException e)
	//			{
	//				L.i("Can't create \".nomedia\" file in application external cache directory");
	//			}
	//		}
	//		return appCacheDir;
	//	}

	private static boolean hasExternalStoragePermission(Context context)
	{
		int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}
}
