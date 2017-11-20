package com.qin.tao.share.widget.tabview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseTextView;


/**
 * @author qintao
 *         created at 2016/6/1 15:51
 */

public class TabView extends RelativeLayout {

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabView(Context context) {
        super(context);
        initView(context);
    }

    private BaseTextView tv_tableName;
    private BaseTextView tv_num;
    private Context context;
    private Drawable drawableSelected = null;
    private Drawable drawableDefault = null;

    private void initView(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_main_table, this, true);
        tv_tableName = (BaseTextView) view.findViewById(R.id.tv_tableName);
        tv_num = (BaseTextView) findViewById(R.id.tv_num);
    }

    public void setText(String tabName, Drawable drawableDefault, Drawable drawableSelected) {
        if (TextUtils.isEmpty(tabName) || null == drawableDefault || null == drawableSelected)
            return;
        tv_tableName.setText(tabName);
        this.drawableDefault = drawableDefault;
        this.drawableSelected = drawableSelected;
    }

    public String getText() {
        return tv_tableName.getText().toString();
    }

    public void setNum(int num) {
        tv_num.setVisibility(num > 0 ? View.VISIBLE : View.GONE);
        tv_num.setText(String.valueOf(num));
    }

    @Override
    public void setSelected(boolean selected) {
        tv_tableName.setTextColor(getResources().getColor(selected ? R.color.yc_red : R.color.yc_70b));
        tv_tableName.setCompoundDrawablesWithIntrinsicBounds(null, selected ? drawableSelected : drawableDefault, null, null);
        super.setSelected(selected);
    }

}
