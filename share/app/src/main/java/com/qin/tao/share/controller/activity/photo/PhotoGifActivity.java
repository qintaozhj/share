package com.qin.tao.share.controller.activity.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.widget.title.TitleView;


/**
 * @author qintao
 * @time 2017/6/23 17:09
 * 显示gif图片界面
 */
public class PhotoGifActivity extends BaseActivity {

    private TitleView titleView;
    private SimpleDraweeView gifImageView;
    private String urlPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);//初始化框架
        super.onCreate(savedInstanceState);
    }

    @Override
    public void receiveParam() {
        Intent intent = getIntent();
        if (intent.hasExtra(IntentKey.PHOTO_GIF_PATH)) {
            urlPath = intent.getStringExtra(IntentKey.PHOTO_GIF_PATH);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_gif_look_over);
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("趣图").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {
            }
        });
        gifImageView = (SimpleDraweeView) findViewById(R.id.gifImageView);
        titleView.post(new Runnable() {
            @Override
            public void run() {
                onStepOfShowGif();
            }
        });
    }

    private void onStepOfShowGif() {

        Uri uri = Uri.parse(urlPath);//错误的地址
        gifImageView.setImageURI(uri);//开始下载
        //重试,创建DraweeController对象
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)//URL地址
                .setTapToRetryEnabled(true)//开启点击重试
                .setAutoPlayAnimations(true)
                .build();//构建

        gifImageView.setController(controller);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();
    }
}
