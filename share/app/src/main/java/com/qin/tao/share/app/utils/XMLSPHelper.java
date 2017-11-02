package com.qin.tao.share.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *@author qintao
 *created at 2016/6/3 14:34
 */

public class XMLSPHelper
{
	/**
	 * xml文件的名字
	 */
	private String xmlName = "";
	/**
	 * 上下文
	 */
	public Context context = null;

	/**
	 * 创建一个XML保存数据类的对象
	 * 
	 * @param xmlName
	 *            xml文件名字
	 */
	public XMLSPHelper(Context context, String xmlName)
	{
		this.xmlName = xmlName;
		this.context = context;
	}

	/**
	 * 将某个值写到某个键中去
	 * 
	 * @param keyName
	 *            键
	 * @param value
	 *            值
	 */
	public boolean setValue(String keyName, String value)
	{
		SharedPreferences settings = context.getSharedPreferences(xmlName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(keyName, value);
		return editor.commit();
	}

	/**
	 * 将某个值写到某个键中去
	 * 
	 * @param keyName
	 *            键
	 * @param value
	 *            值
	 */
	public boolean setValue(String keyName, int value)
	{
		SharedPreferences settings = context.getSharedPreferences(xmlName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(keyName, value);
		return editor.commit();
	}

	/**
	 * 将某个值写到某个键中去
	 * 
	 * @param keyName
	 *            键
	 * @param value
	 *            值
	 */
	public boolean setValue(String keyName, long value)
	{
		SharedPreferences settings = context.getSharedPreferences(xmlName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(keyName, value);
		return editor.commit();
	}

	/**
	 * 将某个值写到某个键中去
	 * 
	 * @param keyName
	 *            键
	 * @param value
	 *            值
	 */
	public boolean setValue(String keyName, boolean value)
	{
		SharedPreferences settings = context.getSharedPreferences(xmlName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(keyName, value);
		return editor.commit();
	}

	/**
	 * 根据键获得数值
	 * 
	 * @param keyName
	 *            键
	 * @return 值
	 */
	public String getSValue(String keyName, String defaultValue)
	{
		String tempValue = null;
		SharedPreferences user = context.getSharedPreferences(xmlName, 0);
		tempValue = user.getString(keyName, defaultValue);
		return tempValue;
	}

	/**
	 * 根据键获得数值
	 * 
	 * @param keyName
	 *            键
	 * @return 值
	 */
	public int getIValue(String keyName, int defaultValue)
	{
		int tempValue = 0;
		SharedPreferences user = context.getSharedPreferences(xmlName, 0);
		tempValue = user.getInt(keyName, defaultValue);
		return tempValue;
	}

	/**
	 * 根据键获得数值
	 * 
	 * @param keyName
	 *            键
	 * @return 值
	 */
	public long getLValue(String keyName, long defaultValue)
	{
		long tempValue = 0;
		SharedPreferences user = context.getSharedPreferences(xmlName, 0);
		tempValue = user.getLong(keyName, defaultValue);
		return tempValue;
	}

	/**
	 * 根据键获得数值
	 * 
	 * @param keyName
	 *            键
	 * @return 值
	 */
	public boolean getBValue(String keyName, boolean defaultValue)
	{
		boolean tempValue = false;
		SharedPreferences user = context.getSharedPreferences(xmlName, 0);
		tempValue = user.getBoolean(keyName, defaultValue);
		return tempValue;
	}

	/**
	 * 将某个值写到某个键中去
	 * 
	 * @param keyName
	 *            键
	 * @param value
	 *            值
	 */
	public void setValue(String keyName, float value)
	{
		SharedPreferences settings = context.getSharedPreferences(xmlName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(keyName, value);
		editor.commit();
	}

	/**
	 * 根据键获得数值
	 * 
	 * @param keyName
	 *            键
	 * @return 值
	 */
	public float getFValue(String keyName, float defaultValue)
	{
		float tempValue = 0;
		SharedPreferences user = context.getSharedPreferences(xmlName, 0);
		tempValue = user.getFloat(keyName, defaultValue);
		return tempValue;
	}

	/**
	 * 删除某个键
	 * @param keyName 键
	 */
	public void remove(String keyName)
	{
		SharedPreferences settings = context.getSharedPreferences(xmlName, 0);
		if (settings.contains(keyName))
		{
			SharedPreferences.Editor editor = settings.edit();
			editor.remove(keyName);
			editor.commit();
		}
	}

	/**
	 * 清空所有内容
	 */
	public void removeAll()
	{
		SharedPreferences settings = context.getSharedPreferences(xmlName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
}
