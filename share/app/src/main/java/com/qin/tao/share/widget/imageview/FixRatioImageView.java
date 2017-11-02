package com.qin.tao.share.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.qin.tao.share.R;

/**
 * Fix ratio imageView, ratio = width/height, default = 1;
 */
public class FixRatioImageView extends ImageView {
    float ratio = 1.0f;

    public FixRatioImageView(Context context) {
        super(context);
        initWithAttribute(context, null);
    }

    public FixRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithAttribute(context, attrs);
    }

    public FixRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttribute(context, attrs);
    }

    private void initWithAttribute(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixRatioImageView);
            if (typedArray != null) {
                ratio = typedArray.getFloat(R.styleable.FixRatioImageView_ratio, ratio);
                typedArray.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize / ratio), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setRatio(float ratio) {
        if (ratio > 0)
            this.ratio = ratio;
        requestLayout();
    }
}
