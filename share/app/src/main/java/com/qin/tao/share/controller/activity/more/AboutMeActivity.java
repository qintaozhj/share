package com.qin.tao.share.controller.activity.more;

import android.widget.ImageView;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.tools.image.LoadImageUtils;
import com.qin.tao.share.tools.image.core.DisplayImageOptions;
import com.qin.tao.share.tools.image.core.ImageLoader;
import com.qin.tao.share.tools.image.core.display.RoundedBitmapDisplayer;
import com.qin.tao.share.widget.title.TitleView;

/**
 * @author qintao on 2017/9/13 11:35
 *         关于我们
 */

public class AboutMeActivity extends BaseActivity {
    @Override
    public void receiveParam() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_about_me);
        TitleView titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("关于").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {

            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        DisplayImageOptions options = LoadImageUtils.getBuilder().displayer(new RoundedBitmapDisplayer(20, 0)).build();
        ImageLoader.getInstance().displayImage(R.mipmap.ic_launcher, imageView, options);
        BaseTextView tv_version = (BaseTextView) findViewById(R.id.tv_version);
        tv_version.setText("版本：V1.0");
    }

    @Override
    public void initData() {

    }
}
