package com.qin.tao.share.controller.activity.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.app.utils.SDCardUtils;
import com.qin.tao.share.controller.adapter.photo.PhotoOriginalAdapter;
import com.qin.tao.share.model.photo.PhotoInfo;
import com.qin.tao.share.tools.image.cache.memory.MemoryCache;
import com.qin.tao.share.tools.image.core.ImageLoader;
import com.qin.tao.share.tools.image.utils.MemoryCacheUtils;
import com.qin.tao.share.widget.photoview.HackyViewPager;
import com.qin.tao.share.widget.photoview.PhotoView;
import com.qin.tao.share.widget.title.TitleView;

import java.util.List;


/**
 * @author qintao
 * @time 2017/6/23 17:09
 * 图片原图界面
 */
public class PhotoOriginalActivity extends BaseActivity {
    private List<PhotoInfo> mPhotoList;
    private int position;
    private TitleView titleView;
    private HackyViewPager viewPager;
    private PhotoOriginalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void receiveParam() {
        Intent intent = getIntent();
        if (intent.hasExtra(IntentKey.POSITION)) {
            position = intent.getIntExtra(IntentKey.POSITION, 0);
        }
        if (intent.hasExtra(IntentKey.PHOTO_PICK_TARGET_PHOTO_LIST)) {
            mPhotoList = intent.getParcelableArrayListExtra(IntentKey.PHOTO_PICK_TARGET_PHOTO_LIST);
        }
    }

    @Override
    public void initData() {
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_photo_look_over);
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setRightText("保存").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {
                PhotoInfo info = mPhotoList.get(position);
                //String key = MemoryCacheUtils.generateKey(info.getPhotoPath());
                MemoryCache memoryCache = ImageLoader.getInstance().getMemoryCache();
                List<String> keys = MemoryCacheUtils.findCacheKeysForImageUri(info.getPhotoPath(), memoryCache);
                if (!CollectionUtils.isEmpty(keys)) {
                    Bitmap bitmap = memoryCache.get(keys.get(0));
                    if (bitmap != null && !bitmap.isRecycled()) {
                        SDCardUtils.saveImageToGallery(PhotoOriginalActivity.this, bitmap);
                    }
                }
            }
        });

        if (!CollectionUtils.isEmpty(mPhotoList) && position < mPhotoList.size()) {
            String text = getString(R.string.photo_detail_indicator, position + 1, mPhotoList.size());
            titleView.setTitle(text);
        }

        viewPager = (HackyViewPager) findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        adapter = new PhotoOriginalAdapter(mPhotoList, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            PhotoOriginalActivity.this.position = position;
            View view = viewPager.findViewWithTag(position);
            View view_pre = viewPager.findViewWithTag(position - 1);
            View view_next = viewPager.findViewWithTag(position + 1);
            String text = getString(R.string.photo_detail_indicator, position + 1, mPhotoList.size());
            titleView.setTitle(text);
            if (view != null && view instanceof PhotoView) {
                ((PhotoView) view).setScale(1.0f);
            }
            if (view_pre != null && view_pre instanceof PhotoView) {
                ((PhotoView) view_pre).setScale(1.0f);
            }
            if (view_next != null && view_next instanceof PhotoView) {
                ((PhotoView) view_next).setScale(1.0f);
            }
        }
    }

}
