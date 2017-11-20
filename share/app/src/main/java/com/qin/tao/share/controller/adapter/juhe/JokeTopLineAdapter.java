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
import com.qin.tao.share.app.utils.TimeFormat;
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
    private final int ITEM_NUM_ONE = 0;
    private final int ITEM_NUM_THREE = ITEM_NUM_ONE + 1;
    private final int TYPE_COUNT = 2;

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
        ViewHolderOne viewHolderOne = null;
        ViewHolderThree viewHolderThree = null;
        int type = getItemViewType(position);
        if (null == convertView) {
            if (type == ITEM_NUM_ONE) {
                convertView = LayoutInflater.from(context).inflate(R.layout.joke_top_line_item_one, null);
                viewHolderOne = new ViewHolderOne(convertView);
                convertView.setTag(viewHolderOne);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.joke_top_line_item_three, null);
                viewHolderThree = new ViewHolderThree(convertView);
                convertView.setTag(viewHolderThree);
            }
        } else {
            if (type == ITEM_NUM_ONE) {
                viewHolderOne = (ViewHolderOne) convertView.getTag();
            } else {
                viewHolderThree = (ViewHolderThree) convertView.getTag();
            }
        }
        TopLineInfo jokeInfo = topLineInfoList.get(position);
        if (type == ITEM_NUM_ONE) {
            viewHolderOne.bindData(jokeInfo);
        } else {
            viewHolderThree.bindData(jokeInfo);
        }
        return convertView;

    }


    @Override
    public int getItemViewType(int position) {
        int returnValue = ITEM_NUM_ONE;
        TopLineInfo jokeInfo = topLineInfoList.get(position);
        if (jokeInfo != null && jokeInfo.getPicList() != null) {
            int picNum = jokeInfo.getPicList().size();
            if (picNum == 0)
                returnValue = ITEM_NUM_ONE;
            else if (picNum == 1)
                returnValue = ITEM_NUM_ONE;
            else if (picNum == 2)
                returnValue = ITEM_NUM_ONE;
            else
                returnValue = ITEM_NUM_THREE;
        }
        return returnValue;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }


    class ViewHolderOne {
        BaseTextView tv_time, tv_title, tv_author_name;
        ImageView img_joke_one;
        View contentView;

        public ViewHolderOne(View convertView) {
            tv_time = (BaseTextView) convertView.findViewById(R.id.tv_time);
            tv_title = (BaseTextView) convertView.findViewById(R.id.tv_title);
            tv_author_name = (BaseTextView) convertView.findViewById(R.id.tv_author_name);
            img_joke_one = (ImageView) convertView.findViewById(R.id.img_joke_one);
            contentView = convertView.findViewById(R.id.contentView);
        }

        public void bindData(TopLineInfo jokeInfo) {
            if (jokeInfo == null)
                return;
            tv_title.setText(jokeInfo.getTitle());
            tv_time.setText(TimeFormat.getCommonFormatTime1(context, jokeInfo.getDate()));
            tv_author_name.setText(jokeInfo.getAuthor_name());
            List<String> picList = jokeInfo.getPicList();
            String imgUrl = null;
            if (!CollectionUtils.isEmpty(picList)) {
                imgUrl = picList.get(0);
                if (TextUtils.isEmpty(imgUrl)) {
                    imgUrl = picList.get(1);
                }
            }
            ImageLoader.getInstance().displayImage(imgUrl, img_joke_one, options);
            contentView.setTag(R.id.top_info, jokeInfo);
            contentView.setOnClickListener(onBaseClickListener);
        }
    }


    class ViewHolderThree {
        BaseTextView tv_time, tv_title, tv_author_name;
        ImageView img_joke_one, img_joke_two, img_joke_three;
        View contentView;

        public ViewHolderThree(View convertView) {
            tv_time = (BaseTextView) convertView.findViewById(R.id.tv_time);
            tv_title = (BaseTextView) convertView.findViewById(R.id.tv_title);
            tv_author_name = (BaseTextView) convertView.findViewById(R.id.tv_author_name);
            img_joke_one = (ImageView) convertView.findViewById(R.id.img_joke_one);
            img_joke_two = (ImageView) convertView.findViewById(R.id.img_joke_two);
            img_joke_three = (ImageView) convertView.findViewById(R.id.img_joke_three);
            contentView = convertView.findViewById(R.id.contentView);
        }

        public void bindData(TopLineInfo jokeInfo) {
            if (jokeInfo == null)
                return;
            tv_title.setText(jokeInfo.getTitle());
            tv_time.setText(TimeFormat.getCommonFormatTime1(context, jokeInfo.getDate()));
            tv_author_name.setText(jokeInfo.getAuthor_name());
            List<String> picList = jokeInfo.getPicList();
            String imgUrlOne = null;
            String imgUrlTwo = null;
            String imgUrlThree = null;
            if (!CollectionUtils.isEmpty(picList) && picList.size() == 3) {
                imgUrlOne = picList.get(0);
                imgUrlTwo = picList.get(1);
                imgUrlThree = picList.get(2);
            }
            ImageLoader.getInstance().displayImage(imgUrlOne, img_joke_one, options);
            ImageLoader.getInstance().displayImage(imgUrlTwo, img_joke_two, options);
            ImageLoader.getInstance().displayImage(imgUrlThree, img_joke_three, options);
            contentView.setTag(R.id.top_info, jokeInfo);
            contentView.setOnClickListener(onBaseClickListener);
        }
    }

    private OnBaseClickListener onBaseClickListener = new OnBaseClickListener() {
        @Override
        public void onBaseClick(View v) {
            TopLineInfo topLineInfo = (TopLineInfo) v.getTag(R.id.top_info);
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
