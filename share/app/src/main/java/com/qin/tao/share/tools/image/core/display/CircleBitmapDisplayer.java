package com.qin.tao.share.tools.image.core.display;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.qin.tao.share.tools.image.core.assist.LoadedFrom;
import com.qin.tao.share.tools.image.core.imageaware.ImageAware;
import com.qin.tao.share.tools.image.core.imageaware.ImageViewAware;

/**
 * 圆形图片设置
 * @author lixiaoli
 *
 */
public class CircleBitmapDisplayer implements BitmapDisplayer
{
	protected final static int DEFAULT_RADIUS = 55;//默认半径
	protected final int cornerRadius;
	protected final int margin;

	public CircleBitmapDisplayer(int cornerRadiusPixels)
	{
		this(cornerRadiusPixels, 0);
	}

	public CircleBitmapDisplayer(int cornerRadiusPixels, int marginPixels)
	{
		this.cornerRadius = cornerRadiusPixels;
		this.margin = marginPixels;
	}

	public CircleBitmapDisplayer()
	{
		this(DEFAULT_RADIUS, 0);
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom)
	{
		if (!(imageAware instanceof ImageViewAware))
		{
			throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
		}

		//		imageAware.setImageDrawable(new CircleDrawable(imageAware, bitmap, cornerRadius, margin));
		imageAware.setImageDrawable(new RoundedAvatarDrawable(bitmap));
	}

	//	public static class CircleDrawable extends Drawable
	//	{
	//
	//		protected final float cornerRadius;
	//		protected final int margin;
	//		protected final ImageAware imageAware;
	//
	//		protected final RectF mRect = new RectF(), mBitmapRect;
	//		protected final BitmapShader mBitmapShader;
	//		protected final Paint paint;
	//
	//		public CircleDrawable(ImageAware imageAware,Bitmap bitmap, int cornerRadius, int margin)
	//		{
	//			this.cornerRadius = cornerRadius;
	//			this.margin = margin;
	//			this.imageAware = imageAware;
	//
	//			mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
	//			mBitmapRect = new RectF(margin, margin, bitmap.getWidth() - margin, bitmap.getHeight() - margin);
	//
	//			paint = new Paint();
	//			paint.setAntiAlias(true);
	//			paint.setShader(mBitmapShader);
	//		}
	//
	//		@Override
	//		protected void onBoundsChange(Rect bounds)
	//		{
	//			super.onBoundsChange(bounds);
	//			mRect.set(margin, margin, bounds.width() - margin, bounds.height() - margin);
	//
	//			// Resize the original bitmap to fit the new bound
	//			Matrix shaderMatrix = new Matrix();
	//			shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
	//			mBitmapShader.setLocalMatrix(shaderMatrix);
	//
	//		}
	//
	//		@Override
	//		public void draw(Canvas canvas)
	//		{
	//			//			canvas.drawOval(mRect, paint);
	//			canvas.drawCircle(imageAware.getWidth()/2, imageAware.getHeight()/2, cornerRadius, paint);
	//		}
	//
	//		@Override
	//		public int getOpacity()
	//		{
	//			return PixelFormat.TRANSLUCENT;
	//		}
	//
	//		@Override
	//		public void setAlpha(int alpha)
	//		{
	//			paint.setAlpha(alpha);
	//		}
	//
	//		@Override
	//		public void setColorFilter(ColorFilter cf)
	//		{
	//			paint.setColorFilter(cf);
	//		}
	//	}
	public class RoundedAvatarDrawable extends Drawable
	{
		private final Bitmap mBitmap;
		private final Paint mPaint;
		private final RectF mRectF;
		private final int mBitmapWidth;
		private final int mBitmapHeight;

		public RoundedAvatarDrawable(Bitmap bitmap)
		{
			mBitmap = bitmap;
			mRectF = new RectF();
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mPaint.setShader(shader);

			// NOTE: we assume bitmap is properly scaled to current density
			mBitmapWidth = mBitmap.getWidth();
			mBitmapHeight = mBitmap.getHeight();
		}

		@Override
		public void draw(Canvas canvas)
		{
			canvas.drawOval(mRectF, mPaint);
		}

		@Override
		protected void onBoundsChange(Rect bounds)
		{
			super.onBoundsChange(bounds);

			mRectF.set(bounds);
		}

		@Override
		public void setAlpha(int alpha)
		{
			if (mPaint.getAlpha() != alpha)
			{
				mPaint.setAlpha(alpha);
				invalidateSelf();
			}
		}

		@Override
		public void setColorFilter(ColorFilter cf)
		{
			mPaint.setColorFilter(cf);
		}

		@Override
		public int getOpacity()
		{
			return PixelFormat.TRANSLUCENT;
		}

		@Override
		public int getIntrinsicWidth()
		{
			return mBitmapWidth;
		}

		@Override
		public int getIntrinsicHeight()
		{
			return mBitmapHeight;
		}

		public void setAntiAlias(boolean aa)
		{
			mPaint.setAntiAlias(aa);
			invalidateSelf();
		}

		@Override
		public void setFilterBitmap(boolean filter)
		{
			mPaint.setFilterBitmap(filter);
			invalidateSelf();
		}

		@Override
		public void setDither(boolean dither)
		{
			mPaint.setDither(dither);
			invalidateSelf();
		}

		public Bitmap getBitmap()
		{
			return mBitmap;
		}
	}
}
