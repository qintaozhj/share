package com.qin.tao.share.controller.receiver;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.log.Logger;
import com.qin.tao.share.app.utils.SystemUtils;
import com.qin.tao.share.controller.activity.MainActivity;
import com.qin.tao.share.controller.activity.web.WebViewActivity;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageReceiver extends XGPushBaseReceiver {
    public static final String LogTag = "XINGE";

    /**
     * 展示通知信息，通知到达app，会调用此方法,目测没什么用
     *
     * @param context
     * @param notifyShowedRlt
     */
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult notifyShowedRlt) {
        if (context == null || notifyShowedRlt == null)
            return;
        Logger.d("您有1条新消息, 通知被展示 ， " + notifyShowedRlt.toString());
    }

    /**
     * 反注册，暂未使用
     *
     * @param context
     * @param errorCode
     */
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null)
            return;
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        Logger.d(LogTag, text);
    }

    /**
     * 设置tag，暂未使用
     *
     * @param context
     * @param errorCode
     * @param tagName
     */
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null)
            return;
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = tagName + "==设置成功";
        } else {
            text = tagName + "==设置失败,错误码：" + errorCode;
        }
        Logger.d(LogTag, text);
    }

    /**
     * 删除tag，暂未使用
     *
     * @param context
     * @param errorCode
     * @param tagName
     */
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null)
            return;
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = tagName + "==删除成功";
        } else {
            text = tagName + "==删除失败,错误码：" + errorCode;
        }
        Logger.d(LogTag, text);
    }

    /**
     * 通知点击事件  在这里处理逻辑
     * actionType=1为该消息被清除，actionType=0为该消息被点击
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null)
            return;
        String text = "";
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + message;
            // 获取自定义key-value
            String customContent = message.getCustomContent();
            if (!TextUtils.isEmpty(customContent)) {
                try {
                    JSONObject obj = new JSONObject(customContent);
                    // key1为前台配置的key
                    String url = "";
                    String title = "";
                    if (!obj.isNull("url"))
                        url = obj.getString("url");
                    if (!obj.isNull("title"))
                        title = obj.getString("title");
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(IntentKey.KEY_FROM, "XG");
                    intent.putExtra(IntentKey.KEY_URL, url);
                    intent.putExtra(IntentKey.KEY_TITLE, title);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + message;
        }
        Logger.d(LogTag, text);
    }

    /**
     * 监听注册结果
     *
     * @param context
     * @param errorCode
     * @param message
     */
    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        if (context == null || message == null)
            return;
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            // 在这里拿token
            String token = message.getToken();
            text = token + "注册成功";
        } else {
            text = message + "注册失败，错误码：" + errorCode;
        }
        Logger.d(LogTag, text);
    }

    /**
     * 消息透传,暂未使用
     *
     * @param context
     * @param message
     */
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        String text = "收到消息:" + message.toString();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Logger.d(LogTag, "get custom value:" + value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理消息的过程...
        Logger.d(LogTag, text);
    }

}
