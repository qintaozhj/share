package com.qin.tao.share.app.base;

/**
 *@author qintao
 *created at 2016/6/1 14:30
 */

public interface IEventConstant
{
	/**
	 * 简介：当网络发生变化时，会发出本事件广播<br>
	 * 参数：args[0]当前网络的状态<br>
	 */
	int EVENT_NET_STATE_CHANGE = 1;

	/**
	 * 用户注销
	 */
	int EVENT_LOGOUT = EVENT_NET_STATE_CHANGE + 1;

	/**
	 * 被踢
	 */
	int EVENT_BE_KICK_OUT = EVENT_LOGOUT + 1;

	/**
	 * 购物车数据变化
	 */
	int EVENT_SHOP_CART = EVENT_BE_KICK_OUT + 1;

	/**
	 * 点餐页单纯购物车数据统计
	 */
	int EVENT_ORDER_SHOP_CART = EVENT_SHOP_CART + 1;

	/**
	 * 购物车价格变化
	 */
	int EVENT_SHOP_CART_PRICE = EVENT_ORDER_SHOP_CART + 1;

	/**
	 * 加入购物车成功
	 */
	int ADD_SHOP_CART_SUCCESS = EVENT_SHOP_CART_PRICE + 1;

	/**
	 * 营业外商品数量变化
	 */
	int NON_OPERNATE_GOODS = ADD_SHOP_CART_SUCCESS + 1;
	/**
	* 取消支付中
	*/
	int CANCEL_PAY = NON_OPERNATE_GOODS + 1;

	/**
	 * 修改桌台账单id
	 */
	int EVENT_DESK_ID = CANCEL_PAY + 1;

	/**
	 * 分区信息变化
	 */
	int EVENT_PART_ID = EVENT_DESK_ID + 1;

	/**
	 * 图片删除变化
	 */
	int EVENT_PHOTO_DEL_ID = EVENT_PART_ID + 1;

}
