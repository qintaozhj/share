package com.qin.tao.share.controller.activity.more;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseTextView;

/**
 * @author qintao on 2017/9/13 11:35
 *         意见反馈.联系我们
 */

public class FeedBackActivity extends BaseActivity {
    @Override
    public void receiveParam() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_feed_back);
        final BaseTextView textView = (BaseTextView) findViewById(R.id.text);
        final Animation scaleAnimation = AnimationUtils.loadAnimation(FeedBackActivity.this, R.anim.scale_anim);
       /* final Animation scaleSmallAnimation = AnimationUtils.loadAnimation(FeedBackActivity.this, R.anim.scale_small_anim);
        final Animation scaleBigAnimation = AnimationUtils.loadAnimation(FeedBackActivity.this, R.anim.scale_big_anim);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        textView.startAnimation(scaleSmallAnimation);
                        break;

                    case MotionEvent.ACTION_UP:
                        scaleSmallAnimation.cancel();
                        textView.startAnimation(scaleBigAnimation);
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        scaleSmallAnimation.cancel();
                        textView.startAnimation(scaleBigAnimation);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });*/
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.startAnimation(scaleAnimation);

            }
        });
    }

    @Override
    public void initData() {

    }
}
