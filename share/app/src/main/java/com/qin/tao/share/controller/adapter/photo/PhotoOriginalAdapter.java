package com.qin.tao.share.controller.adapter.photo;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.qin.tao.share.R;
import com.qin.tao.share.app.utils.CollectionUtils;
import com.qin.tao.share.model.photo.PhotoInfo;
import com.qin.tao.share.tools.image.LoadImageUtils;
import com.qin.tao.share.tools.image.core.DisplayImageOptions;
import com.qin.tao.share.tools.image.core.ImageLoader;
import com.qin.tao.share.widget.photoview.PhotoView;
import com.qin.tao.share.widget.photoview.PhotoViewAttacher;

import java.util.List;


/**
 * @author qintao
 * @time 2017/6/23 17:07
 * 图片原图适配器
 */
public class PhotoOriginalAdapter extends PagerAdapter
{
	private List<PhotoInfo> mPhotoList;
	private DisplayImageOptions options;
	private LayoutInflater layoutInflater;
	private Activity mContext;

	public PhotoOriginalAdapter(List<PhotoInfo> list, Activity context)
	{
		super();
		mPhotoList = list;
		layoutInflater = LayoutInflater.from(context);
		options = LoadImageUtils.getBuilder(R.drawable.public_default_bg).build();
		mContext = context;
	}

	public void setData(List<PhotoInfo> list)
	{
		mPhotoList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		return CollectionUtils.isEmpty(mPhotoList) ? 0 : mPhotoList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == arg1;
	}

	public PhotoInfo getItem(int position)
	{
		PhotoInfo item = null;
		if (position >= 0 && position < mPhotoList.size())
		{
			item = mPhotoList.get(position);
		}
		return item;
	}

	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}

	public void setSelected(int position, boolean isSelected)
	{
		PhotoInfo item = getItem(position);
		if (item != null)
		{
			item.setSelectedStatus(isSelected);
		}
	}

	public boolean isSelected(int position)
	{
		boolean isSelected = false;
		PhotoInfo item = getItem(position);
		if (item != null)
		{
			isSelected = item.isSelected();
		}
		return isSelected;
	}

	//添加数据
	@Override
	public Object instantiateItem(final ViewGroup viewPager, final int position)
	{
		PhotoInfo photoInfo = getItem(position);
		View root = layoutInflater.inflate(R.layout.photo_detail_item, viewPager, false);

		PhotoView pv = (PhotoView) root.findViewById(R.id.pv);

		pv.setTag(position);
		pv.setMaximumScale(3.0f);
		pv.setMinimumScale(1.0f);
		pv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener()
		{

			@Override
			public void onPhotoTap(View view, float x, float y)
			{
				mContext.finish();
			}
		});

		if (photoInfo != null)
		{
			String photoPath = photoInfo.getPhotoPath();

			if (!TextUtils.isEmpty(photoPath))
			{
				if (URLUtil.isNetworkUrl(photoPath))
				{
					ImageLoader.getInstance().displayImage(photoPath, pv, options);
				} else
				{
					ImageLoader.getInstance().displayLocalResource(photoPath, pv, options);
				}
			} else
			{
				ImageLoader.getInstance().displayImage("", pv, options);
			}
		}
		viewPager.addView(root);
		return root;
	}

	//删除数据
	@Override
	public void destroyItem(ViewGroup viewPager, int position, Object object)
	{
		viewPager.removeView((View) object);
	}

}
