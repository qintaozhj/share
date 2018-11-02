package com.qin.tao.share.controller.adapter.juhe;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.database.CacheInfoDao;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.app.utils.ToastUtils;
import com.qin.tao.share.model.cache.CacheEnum;
import com.qin.tao.share.model.cache.CacheInfo;
import com.qin.tao.share.tools.image.LoadImageUtils;
import com.qin.tao.share.tools.image.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qintao on 2017/9/11 17:15
 */

public class CollectionAdapter extends BaseAdapter {

    private List<CacheInfo> cacheInfoList = new ArrayList<>();
    private DisplayImageOptions options;
    private Context context;
    private final int ITEM_JOKE_TEXT = 0;
    private final int ITEM_JOKE_IMG = ITEM_JOKE_TEXT + 1;
    private final int TYPE_COUNT = 2;

    public CollectionAdapter(Context context) {
        this.context = context;
        this.options = LoadImageUtils.getBuilder(R.drawable.public_default_bg).build();
    }

    public void setData(List<CacheInfo> welfareInfoList) {
        this.cacheInfoList.clear();
        if (!CollectionUtils.isEmpty(welfareInfoList)) {
            this.cacheInfoList.addAll(welfareInfoList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cacheInfoList.size();
    }

    @Override
    public CacheInfo getItem(int position) {
        return cacheInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderJokeText viewHolderJokeText = null;
        int type = getItemViewType(position);
        if (null == convertView) {
            if (type == ITEM_JOKE_TEXT) {
                convertView = LayoutInflater.from(context).inflate(R.layout.joke_text_item_layout, null);
                viewHolderJokeText = new ViewHolderJokeText(convertView);
                convertView.setTag(viewHolderJokeText);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.joke_image_item_layout, null);
            }
        } else {
            if (type == ITEM_JOKE_TEXT) {
                viewHolderJokeText = (ViewHolderJokeText) convertView.getTag();
            }
        }
        CacheInfo cacheInfo = cacheInfoList.get(position);
        if (type == ITEM_JOKE_TEXT) {
            viewHolderJokeText.bindData(cacheInfo);
        } else {
            // viewHolderThree.bindData(cacheInfo);
        }
        return convertView;

    }


    @Override
    public int getItemViewType(int position) {
        int returnValue = ITEM_JOKE_TEXT;
        CacheInfo cacheInfo = cacheInfoList.get(position);
        if (cacheInfo != null) {
            CacheEnum cacheEnum = CacheEnum.getType(cacheInfo.getType());
            if (cacheEnum == CacheEnum.JOKE_TEXT)
                returnValue = ITEM_JOKE_TEXT;
            else if (cacheEnum == CacheEnum.JOKE_IMG)
                returnValue = ITEM_JOKE_IMG;
        }
        return returnValue;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }


    class ViewHolderJokeText {
        BaseTextView tv_time, tv_copy, tv_collection, tv_content;

        public ViewHolderJokeText(View convertView) {
            tv_time = (BaseTextView) convertView.findViewById(R.id.tv_time);
            tv_copy = (BaseTextView) convertView.findViewById(R.id.tv_copy);
            tv_collection = (BaseTextView) convertView.findViewById(R.id.tv_collection);
            tv_content = (BaseTextView) convertView.findViewById(R.id.tv_content);
        }

        public void bindData(CacheInfo cacheInfo) {
            if (cacheInfo == null)
                return;
            tv_time.setText(cacheInfo.getTime() + "");
            tv_content.setText(cacheInfo.getContent());
            tv_collection.setTag(R.id.cache_info, cacheInfo);
            tv_collection.setOnClickListener(onBaseClickListener);
            tv_copy.setTag(R.id.cache_info, cacheInfo);
            tv_copy.setOnClickListener(onBaseClickListener);
            tv_collection.setText(cacheInfo.isCollection() ? "已收藏" : "收藏");
        }
    }


    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            CacheInfo cacheInfo = (CacheInfo) v.getTag(R.id.cache_info);
            switch (v.getId()) {
                case R.id.tv_copy:
                    try {
                        // 从API11开始android推荐使用android.content.ClipboardManager
                        // 从API11之前android推荐使用android.text.ClipboardManager
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(cacheInfo.getContent());
                        ToastUtils.showText(context, "复制成功");
                    } catch (Exception e) {

                    }
                    break;
                case R.id.tv_collection:
                    CacheInfo info = CacheInfoDao.getDao().queryBuilder(CacheInfo.class, "Id", cacheInfo.getId());
                    if (info != null) {
                        cacheInfo.setCollection(false);
                        CacheInfoDao.getDao().delete(CacheInfo.class, info);
                        ToastUtils.showText(context, "取消收藏");
                    } else {
                        cacheInfo.setCollection(true);
                        CacheInfoDao.getDao().insert(CacheInfo.class, cacheInfo);
                        ToastUtils.showText(context, "收藏成功");
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    };

}
