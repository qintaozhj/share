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
import com.qin.tao.share.model.juhe.WeChatInfo;
import com.qin.tao.share.tools.image.LoadImageUtils;
import com.qin.tao.share.tools.image.core.DisplayImageOptions;
import com.qin.tao.share.tools.image.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qintao on 2017/9/11 17:15
 */

public class WeChatAdapter extends BaseAdapter {

    private List<WeChatInfo> weChatInfoList = new ArrayList<>();
    private DisplayImageOptions options;
    private Context context;

    public WeChatAdapter(Context context) {
        this.context = context;
        this.options = LoadImageUtils.getBuilder(R.drawable.public_default_bg).build();
    }

    public void setData(List<WeChatInfo> weChatInfoList) {
        this.weChatInfoList.clear();
        if (!CollectionUtils.isEmpty(weChatInfoList)) {
            this.weChatInfoList.addAll(weChatInfoList);
        }
        notifyDataSetChanged();
    }

    public void addData(List<WeChatInfo> weChatInfoList) {
        if (!CollectionUtils.isEmpty(weChatInfoList)) {
            this.weChatInfoList.addAll(weChatInfoList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return weChatInfoList.size();
    }

    @Override
    public WeChatInfo getItem(int position) {
        return weChatInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.joke_we_chat_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (BaseTextView) convertView.findViewById(R.id.tv_title);
            viewHolder.contentView = convertView.findViewById(R.id.contentView);
            viewHolder.img_joke = (ImageView) convertView.findViewById(R.id.img_joke);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 左边item绑定值
        WeChatInfo weChatInfo = weChatInfoList.get(position);
        if (weChatInfo != null) {
            viewHolder.tv_title.setText(weChatInfo.getTitle());
            String imgUrl = weChatInfo.getFirstImg();
            ImageLoader.getInstance().displayImage(imgUrl, viewHolder.img_joke, options);
            viewHolder.contentView.setTag(weChatInfo);
            viewHolder.contentView.setOnClickListener(onBaseClickListener);
        }

        return convertView;

    }

    class ViewHolder {
        BaseTextView tv_title;
        ImageView img_joke;
        View contentView;
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            WeChatInfo weChatInfo = (WeChatInfo) v.getTag();
            if (listener != null) {
                listener.onItemClick(weChatInfo);
            }
        }
    };

    public interface IOnItemClickListener {
        void onItemClick(WeChatInfo weChatInfo);
    }

    private IOnItemClickListener listener;

    public void setListener(IOnItemClickListener l) {
        listener = l;
    }

}
