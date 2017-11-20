package com.qin.tao.share.app.base;

import android.view.View;
import android.view.View.OnLongClickListener;

/**
 *
 */
public abstract class OnBaseLongClickListener implements OnLongClickListener {

    public abstract boolean onBaseLongClick(View v);

    @Override
    public boolean onLongClick(View clickedView) {
        return onBaseLongClick(clickedView);
    }

}
