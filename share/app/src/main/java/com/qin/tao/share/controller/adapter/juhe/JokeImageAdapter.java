package com.qin.tao.share.controller.adapter.juhe;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.base.OnBaseClickListener;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.model.juhe.JokeInfo;
import com.qin.tao.share.tools.image.LoadImageUtils;
import com.qin.tao.share.tools.image.core.DisplayImageOptions;
import com.qin.tao.share.tools.image.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qintao on 2017/9/11 17:15
 */

public class JokeImageAdapter extends BaseAdapter {

    private List<JokeInfo> welfareInfoList = new ArrayList<>();
    private DisplayImageOptions options;
    private Context context;

    public JokeImageAdapter(Context context) {
        this.context = context;
        this.options = LoadImageUtils.getBuilder(R.drawable.public_default_bg).build();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.joke_image_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_time = (BaseTextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_content = (BaseTextView) convertView.findViewById(R.id.tv_content);
            viewHolder.img_joke = (ImageView) convertView.findViewById(R.id.img_joke);
            viewHolder.tv_img_dec = (BaseTextView) convertView.findViewById(R.id.tv_img_dec);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 左边item绑定值
        JokeInfo jokeInfo = welfareInfoList.get(position);
        if (jokeInfo != null) {

            viewHolder.tv_time.setText(jokeInfo.getTime());
            viewHolder.tv_content.setText(jokeInfo.getContent());
            String url = jokeInfo.getUrl();
            ImageLoader.getInstance().displayImage(url, viewHolder.img_joke, options);
            if (url.endsWith(".gif")) {
                viewHolder.tv_img_dec.setText("Gif");
                viewHolder.tv_img_dec.setVisibility(View.VISIBLE);
            } else
                viewHolder.tv_img_dec.setVisibility(View.GONE);
            viewHolder.img_joke.setTag(url);
            viewHolder.img_joke.setOnClickListener(onBaseClickListener);
        }

        return convertView;

    }

    class ViewHolder {
        BaseTextView tv_time, tv_content, tv_img_dec;
        ImageView img_joke;
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            String url = (String) v.getTag();
            if (photoLookListener != null && !TextUtils.isEmpty(url)) {
                if (url.endsWith(".gif"))
                    photoLookListener.showGifImage(url);
                else
                    photoLookListener.showNormalImage(url);
            }
        }
    };

    public interface IPhotoLookListener {
        void showGifImage(String url);

        void showNormalImage(String url);
    }

    private IPhotoLookListener photoLookListener;

    public void setPhotoLookListener(IPhotoLookListener l) {
        photoLookListener = l;
    }

}
