package com.qin.tao.share.widget.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.base.OnBaseClickListener;


/**
 * @author qintao
 *         created at 2016/6/1 11:35
 */

public class TitleView extends RelativeLayout {
    private ImageView img_back;
    private RelativeLayout backLayout;
    private BaseTextView tv_title;
    private BaseTextView tv_right;
    private ITitleOnClickListener mTitleOnClickListener;

    public void setTitleOnClickListener(ITitleOnClickListener mTitleOnClickListener) {
        this.mTitleOnClickListener = mTitleOnClickListener;
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TitleView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_title, this, true);
        //View view_status_bar = view.findViewById(R.id.view_status_bar);
        tv_right = (BaseTextView) view.findViewById(R.id.tv_right);
        backLayout = (RelativeLayout) view.findViewById(R.id.backLayout);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        tv_title = (BaseTextView) view.findViewById(R.id.tv_title);
        backLayout.setOnClickListener(mOnBaseClickListener);
        tv_right.setOnClickListener(mOnBaseClickListener);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            view_status_bar.setVisibility(VISIBLE);
//            int statusBarHeight = getStatusBarHeight(context);
//            view_status_bar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, statusBarHeight));
//        }
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public TitleView setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public TitleView setTitle(int resource) {
        tv_title.setText(resource);
        return this;
    }


    public TitleView setRightText(CharSequence cha) {

        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(cha);
        return this;
    }


    public interface ITitleOnClickListener {
        void onBackClick();

        void onDoneClick();
    }

    private OnBaseClickListener mOnBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            switch (v.getId()) {
                case R.id.backLayout:
                    if (null != mTitleOnClickListener)
                        mTitleOnClickListener.onBackClick();
                    break;
                case R.id.tv_right:
                    if (null != mTitleOnClickListener)
                        mTitleOnClickListener.onDoneClick();
                    break;
                default:
                    break;
            }
        }
    };
}
