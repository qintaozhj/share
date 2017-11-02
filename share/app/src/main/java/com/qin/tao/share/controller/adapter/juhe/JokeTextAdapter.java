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
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.app.utils.ToastUtils;
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
        }
        return convertView;
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            try {
                JokeInfo jokeInfo = (JokeInfo) v.getTag();
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 从API11之前android推荐使用android.text.ClipboardManager
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(jokeInfo.getContent());
                ToastUtils.showText(context, "复制成功");
            } catch (Exception e) {

            }
        }
    };

    class ViewHolder {
        BaseTextView tv_time, tv_content, tv_copy;
    }
}
