package com.qin.tao.share.app.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 * @author qintao
 *
 */
public class CollectionUtils
{
	/**
	 * 判断一个集合列表是否为null或者内容为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(Collection<?> list)
	{
		if (list == null || list.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断一个HashMap是否为null或者内容为空
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?,?> map)
	{
		return map == null || map.size() == 0;
	}
}
