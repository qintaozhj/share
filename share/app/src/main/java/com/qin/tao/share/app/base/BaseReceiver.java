package com.qin.tao.share.app.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 广播接收器.
 */
public class BaseReceiver extends BroadcastReceiver
{
	private BroadcastReceiverRegisterState mRegisterState = BroadcastReceiverRegisterState.UnRegister;

	public void setRegisterState(BroadcastReceiverRegisterState state)
	{
		this.mRegisterState = state;
	}

	public BroadcastReceiverRegisterState getRegisterState()
	{
		return this.mRegisterState;
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		// TODO 子类重载
	}

	public enum BroadcastReceiverRegisterState
	{
		UnRegister, Registered
	}

}
