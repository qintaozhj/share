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
import com.qin.tao.share.model.juhe.JokeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qintao on 2017/9/11 17:15
 */

public class JokeTextAdapter extends BaseAdapter {

    private List<JokeInfo> welfareInfoList = new ArrayList<>();
    private Context context;

    public JokeTextAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<JokeInfo> welfareInfoList) {
        this.welfareInfoList.clear();
        if (!CollectionUtils.isEmpty(welfareInfoList)) {
            this.welfareInfoList.addAll(welfareInfoList);
        }
        notifyDataSetChanged();
    }

    public void addData(List<JokeInfo> welfareInfoList) {
        if (!CollectionUtils.isEmpty(welfareInfoList)) {
            this.welfareInfoList.addAll(welfareInfoList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return welfareInfoList.size();
    }

    @Override
    public JokeInfo getItem(int position) {
        return welfareInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.joke_text_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_time = (BaseTextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_content = (BaseTextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_copy = (BaseTextView) convertView.findViewById(R.id.tv_copy);
            viewHolder.tv_collection = (BaseTextView) convertView.findViewById(R.id.tv_collection);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 左边item绑定值
        JokeInfo jokeInfo = welfareInfoList.get(position);
        if (jokeInfo != null) {
            viewHolder.tv_time.setText(jokeInfo.getTime());
            viewHolder.tv_content.setText(jokeInfo.getContent());
            viewHolder.tv_copy.setTag(jokeInfo);
            viewHolder.tv_copy.setOnClickListener(onBaseClickListener);
            viewHolder.tv_collection.setTag(jokeInfo);
            viewHolder.tv_collection.setOnClickListener(onBaseClickListener);
            viewHolder.tv_collection.setText(jokeInfo.isCollection() ? "已收藏" : "收藏");
        }
        return convertView;
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            JokeInfo jokeInfo = (JokeInfo) v.getTag();
            switch (v.getId()) {
                case R.id.tv_copy:
                    try {
                        // 从API11开始android推荐使用android.content.ClipboardManager
                        // 从API11之前android推荐使用android.text.ClipboardManager
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(jokeInfo.getContent());
                        ToastUtils.showText(context, "复制成功");
                    } catch (Exception e) {

                    }
                    break;
                case R.id.tv_collection:
                    if (!jokeInfo.isCollection()) {
                        CacheInfo info = new CacheInfo();
                        info.setContent(jokeInfo.getContent());
                        info.setType(CacheEnum.JOKE_TEXT.getType());
                        info.setTime(jokeInfo.getTime());
                        info.setCollection(true);
                        jokeInfo.setCollection(true);
                        CacheInfoDao.getDao().insert(CacheInfo.class, info);
                        ToastUtils.showText(context, "收藏成功");
                    } else {
                        jokeInfo.setCollection(false);
                        CacheInfoDao.getDao().deleteBuilder(CacheInfo.class, "content", jokeInfo.getContent(), "type", CacheEnum.JOKE_TEXT.getType());
                        ToastUtils.showText(context, "取消收藏");
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    class ViewHolder {
        BaseTextView tv_time, tv_content, tv_copy, tv_collection;
    }
}
