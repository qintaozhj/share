package com.qin.tao.share.controller.adapter.gank;

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
import com.qin.tao.share.model.gank.WelfareInfo;
import com.qin.tao.share.tools.image.LoadImageUtils;
import com.qin.tao.share.tools.image.core.DisplayImageOptions;
import com.qin.tao.share.tools.image.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qintao on 2017/9/11 17:15
 */

public class WelFareAdapter extends BaseAdapter {

    private List<WelfareInfo> welfareInfoList = new ArrayList<>();
    private DisplayImageOptions mItemDisplayImageOptions;
    private Context context;

    public WelFareAdapter(Context context) {
        this.context = context;
        mItemDisplayImageOptions = LoadImageUtils.getBuilder(R.drawable.public_default_bg).build();
    }

    public void setData(List<WelfareInfo> welfareInfoList) {
        this.welfareInfoList.clear();
        if (!CollectionUtils.isEmpty(welfareInfoList)) {
            this.welfareInfoList.addAll(welfareInfoList);
        }
        notifyDataSetChanged();
    }

    public void addData(List<WelfareInfo> welfareInfoList) {
        if (!CollectionUtils.isEmpty(welfareInfoList)) {
            this.welfareInfoList.addAll(welfareInfoList);
        }
        notifyDataSetChanged();
    }

    public List<WelfareInfo> getData() {
        return welfareInfoList;
    }

    @Override
    public int getCount() {
        if (welfareInfoList.size() == 0) {
            return 0;
        }

        // 计算list的行数
        int count = welfareInfoList.size() % 2;
        if (count > 0) {
            count = welfareInfoList.size() / 2 + 1;
        } else
            count = welfareInfoList.size() / 2;
        return count;
    }

    public int getSize() {
        return welfareInfoList.size();
    }

    @Override
    public WelfareInfo getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.welfare_item_two_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageLeft = (ImageView) convertView.findViewById(R.id.imageLeft);
            viewHolder.imageRight = (ImageView) convertView.findViewById(R.id.imageRight);
            viewHolder.tv_dec_left = (BaseTextView) convertView.findViewById(R.id.tv_dec_left);
            viewHolder.tv_dec_right = (BaseTextView) convertView.findViewById(R.id.tv_dec_right);
            viewHolder.rightItem = convertView.findViewById(R.id.rightItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 左边item绑定值
        WelfareInfo welfareInfo = welfareInfoList.get(2 * position);
        WelfareInfo welfareInfoR = welfareInfoList.get(2 * position + 1);
        if (welfareInfo != null) {
            ImageLoader.getInstance().displayImage(welfareInfo.getUrl(), viewHolder.imageLeft, mItemDisplayImageOptions);
            viewHolder.tv_dec_left.setText(welfareInfo.getDesc());
            viewHolder.imageLeft.setTag(2 * position);
            viewHolder.imageLeft.setOnClickListener(onBaseClickListener);
        }
        // 对右边item绑定值
        if (welfareInfoR != null) {
            ImageLoader.getInstance().displayImage(welfareInfoR.getUrl(), viewHolder.imageRight, mItemDisplayImageOptions);
            viewHolder.tv_dec_right.setText(welfareInfoR.getDesc());
            viewHolder.imageRight.setTag(2 * position + 1);
            viewHolder.imageRight.setOnClickListener(onBaseClickListener);
            viewHolder.rightItem.setVisibility(View.VISIBLE);
        }
        return convertView;

    }

    class ViewHolder {
        ImageView imageLeft, imageRight;
        BaseTextView tv_dec_left, tv_dec_right;
        View rightItem;
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            int position = (int) v.getTag();
            if (lookPhotoListener != null)
                lookPhotoListener.lookPhoto(position);
        }
    };

    public interface ILookPhotoListener {
        void lookPhoto(int position);
    }

    private ILookPhotoListener lookPhotoListener;

    public void setLookPhotoListener(ILookPhotoListener l) {
        lookPhotoListener = l;
    }
}
