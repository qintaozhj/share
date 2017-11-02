package com.qin.tao.share.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.qin.tao.share.R;

/**
 * 通用提示框
 *
 * @author qintao
 */
@SuppressLint("CutPasteId")
public class LoadingDialog {

    private Context context;
    private static Dialog customDialog = null;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void show() {
        if (context == null)
            return;
        //关闭正在显示的dialog
        if (customDialog != null && customDialog.isShowing()) {
            if (isExclusiveMode)//独占模式的dialog处于显示状态，不受理其他dialog的显示
                return;

            try {
                customDialog.dismiss();
            } catch (Exception e) {
                Log.e("", e.toString());
            }
            customDialog = null;
        }
        customDialog = new Dialog(context, R.style.dialog_loading);
        Window window = customDialog.getWindow();
        if (null != window) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
            try {
                customDialog.show();
            } catch (Exception e) {
                return;
            }

            window.setContentView(R.layout.common_load_anim);
            View view = window.getDecorView();
            view.setTag(view);//用 TAG 保存,当前 Dialog 的 Builder 对象信息.

            customDialog.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    //当dialog被销毁时，取消独占模式
                    if (customDialog == null || customDialog == dialog)
                        isExclusiveMode = false;
                }
            });

            final ImageView animView = (ImageView) window.getDecorView().findViewById(R.id.loadIV);
            if (animView == null)
                return;
            LinearInterpolator lirloading = new LinearInterpolator();
            animView.setImageResource(R.drawable.public_loading);
            RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(1200);//设置动画持续时间
            animation.setFillAfter(true);//保持执行后的效果
            animation.setRepeatCount(RotateAnimation.INFINITE);//无限循环
            animation.setInterpolator(lirloading);//加速器
            animView.startAnimation(animation);
        }
    }


    /**
     * 记录当前显示的dialog是否为独占模式，如果为true，当前dialog处于显示状态，其他dialog的显示请求将不被执行
     */
    private static boolean isExclusiveMode;

    /**
     * 关闭等待提示框
     */
    public static synchronized void closeDialog() {
        try {
            if (customDialog != null && customDialog.isShowing()) {
                customDialog.setOnCancelListener(null);
                customDialog.cancel();
                customDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            customDialog = null;
        }
    }

}