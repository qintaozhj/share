package com.qin.tao.share.controller.adapter.juhe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.model.juhe.TopLineInfo;
import com.qin.tao.share.tools.image.LoadImageUtils;
import com.qin.tao.share.tools.image.core.DisplayImageOptions;
import com.qin.tao.share.tools.image.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qintao on 2017/9/11 17:15
 */

public class JokeTopLineAdapter extends BaseAdapter {

    private List<TopLineInfo> topLineInfoList = new ArrayList<>();
    private DisplayImageOptions options;
    private Context context;

    public JokeTopLineAdapter(Context context) {
        this.context = context;
        this.options = LoadImageUtils.getBuilder(R.drawable.public_default_bg).build();
    }

    public void setData(List<TopLineInfo> welfareInfoList) {
        this.topLineInfoList.clear();
        if (!CollectionUtils.isEmpty(welfareInfoList)) {
            this.topLineInfoList.addAll(welfareInfoList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return topLineInfoList.size();
    }

    @Override
    public TopLineInfo getItem(int position) {
        return topLineInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.joke_top_line_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_time = (BaseTextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_title = (BaseTextView) convertView.findViewById(R.id.tv_title);
            viewHolder.contentView = convertView.findViewById(R.id.contentView);
            viewHolder.img_joke = (ImageView) convertView.findViewById(R.id.img_joke);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 左边item绑定值
        TopLineInfo jokeInfo = topLineInfoList.get(position);
        if (jokeInfo != null) {
            viewHolder.tv_time.setText(jokeInfo.getDate());
            viewHolder.tv_title.setText(jokeInfo.getTitle());
            String imgUrl = jokeInfo.getThumbnail_pic_s();
            ImageLoader.getInstance().displayImage(imgUrl, viewHolder.img_joke, options);
            viewHolder.contentView.setTag(jokeInfo);
            viewHolder.contentView.setOnClickListener(onBaseClickListener);
        }

        return convertView;

    }

    class ViewHolder {
        BaseTextView tv_time, tv_title;
        ImageView img_joke;
        View contentView;
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            TopLineInfo topLineInfo = (TopLineInfo) v.getTag();
            if (listener != null) {
                listener.onItemClick(topLineInfo);
            }
        }
    };

    public interface IOnItemClickListener {
        void onItemClick(TopLineInfo topLineInfo);
    }

    private IOnItemClickListener listener;

    public void setListener(IOnItemClickListener l) {
        listener = l;
    }

}
