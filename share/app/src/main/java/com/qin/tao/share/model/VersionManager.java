package com.qin.tao.share.model;

import android.content.Context;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;

/**
 * @author qintao on 2017/9/14 21:58
 */

public class VersionManager {
    private  void cpUpdateCheck(Context context) {
        BDAutoUpdateSDK.uiUpdateAction(context, new MyUICheckUpdateCallback());
    }


    private class MyUICheckUpdateCallback implements UICheckUpdateCallback {

        @Override
        public void onNoUpdateFound() {

        }

        @Override
        public void onCheckComplete() {
        }

    }
}
