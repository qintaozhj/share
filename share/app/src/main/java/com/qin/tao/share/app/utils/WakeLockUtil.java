package com.qin.tao.share.app.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

/**
 * 锁屏管理
 * @author lixiaoli
 *
 */
public class WakeLockUtil
{
	private static WakeLock wakeLock = null;

	/**
	 * 保持亮度锁定
	 */
	public static void acquire(Context context)
	{
		if (wakeLock == null && context != null)
		{
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "haochang");
		}
		if (wakeLock != null)
			wakeLock.acquire();
	}

	/**
	 * 解除锁定
	 */
	public static void release()
	{
		if (wakeLock != null)
			wakeLock.release();
	}
}
