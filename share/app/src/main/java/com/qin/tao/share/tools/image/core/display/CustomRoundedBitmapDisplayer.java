package com.qin.tao.share.tools.image.core.display;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.qin.tao.share.tools.image.core.assist.LoadedFrom;
import com.qin.tao.share.tools.image.core.imageaware.ImageAware;
import com.qin.tao.share.tools.image.core.imageaware.ImageViewAware;

/**
 * 可定制直角.圆角（发现顶部图片）
 * Created by xjunjie@gmail.com on 2016/8/2.
 */
public class CustomRoundedBitmapDisplayer implements BitmapDisplayer
{
	protected final int cornerRadius;
	protected Matrix.ScaleToFit scale;

	public CustomRoundedBitmapDisplayer(int cornerRadiusPixels)
	{
		this(cornerRadiusPixels, Matrix.ScaleToFit.START);
	}

	protected CustomRoundedBitmapDisplayer(int cornerRadiusPixels, Matrix.ScaleToFit scale)
	{
		this.cornerRadius = cornerRadiusPixels;
		this.scale = scale;
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom)
	{
		if (!(imageAware instanceof ImageViewAware))
		{
			throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
		}

		//CustomRoundedRectDrawable drawable = new CustomRoundedRectDrawable(Color.RED,this.cornerRadius );
		CustomRoundedRectDrawable drawable = new CustomRoundedRectDrawable(bitmap, this.cornerRadius, this.scale);
		drawable.showLeftBottomRect(true);
		drawable.showRightBottomRect(true);

		imageAware.setImageDrawable(drawable);
	}

	static class RoundedRectDrawable extends Drawable
	{
		final static double COS_45 = Math.cos(Math.toRadians(45));
		final static float SHADOW_MULTIPLIER = 1.5f;
		protected final Paint mPaint;
		protected final RectF mBoundsF;
		protected final Rect mBoundsI;
		protected float mRadius;
		protected float mPadding;
		protected boolean mInsetForPadding = false;
		protected boolean mInsetForRadius = true;
		protected BitmapShader mBitmapShader;
		protected Matrix.ScaleToFit mScaleToFit;
		private RectF mBitmapRect;

		public RoundedRectDrawable(int backgroundColor, float radius)
		{
			this.mRadius = radius;
			this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
			this.mPaint.setColor(backgroundColor);
			this.mBoundsF = new RectF();
			this.mBoundsI = new Rect();
		}

		/**
		 * 填充图片
		 *
		 * @param bitmap 图片资源
		 * @param radius 圆角
		 * @param scale  图片缩放方式.目前支持：START：短边填充；CENTER：其他；填充
		 */
		public RoundedRectDrawable(Bitmap bitmap, float radius, Matrix.ScaleToFit scale)
		{
			this.mRadius = radius;
			this.mPaint = new Paint();

			this.mBitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
			this.mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

			this.mPaint.setAntiAlias(true);
			this.mPaint.setShader(mBitmapShader);

			this.mBoundsF = new RectF();
			this.mBoundsI = new Rect();
			this.mScaleToFit = scale;
		}

		static float calculateVerticalPadding(float maxShadowSize, float cornerRadius, boolean addPaddingForCorners)
		{
			if (addPaddingForCorners)
			{
				return (float) (maxShadowSize * SHADOW_MULTIPLIER + (1 - COS_45) * cornerRadius);
			} else
			{
				return maxShadowSize * SHADOW_MULTIPLIER;
			}
		}

		static float calculateHorizontalPadding(float maxShadowSize, float cornerRadius, boolean addPaddingForCorners)
		{
			if (addPaddingForCorners)
			{
				return (float) (maxShadowSize + (1 - COS_45) * cornerRadius);
			} else
			{
				return maxShadowSize;
			}
		}

		void setPadding(float padding, boolean insetForPadding, boolean insetForRadius)
		{
			if (padding == this.mPadding && this.mInsetForPadding == insetForPadding && this.mInsetForRadius == insetForRadius)
			{
				return;
			}
			this.mPadding = padding;
			this.mInsetForPadding = insetForPadding;
			this.mInsetForRadius = insetForRadius;
			updateBounds(null);
			//			invalidateSelf();
		}

		private void updateBounds(Rect bounds)
		{
			if (null == bounds)
			{
				bounds = getBounds();
			}
			this.mBoundsF.set(bounds.left, bounds.top, bounds.right, bounds.bottom);

			if (this.mBitmapRect != null && this.mBitmapShader != null)
			{
				// Resize the original bitmap to fit the new bound
				Matrix shaderMatrix = new Matrix();

				//				if (mBitmapRect.width() == mBoundsF.width() && mBitmapRect.height() == mBoundsF.height())
				//				{
				//					//图片尺寸.和显示尺寸匹配.直接填充
				//					shaderMatrix.setRectToRect(mBitmapRect, mBoundsF, Matrix.ScaleToFit.FILL);
				//				}

				if (this.mScaleToFit == Matrix.ScaleToFit.START)
				{
					//以短边方式.填充
					//确定短边
					final float scaleWidth = this.mBoundsF.width() / this.mBitmapRect.width();
					final float scaleHeight = this.mBoundsF.height() / this.mBitmapRect.height();

					float scale = (scaleWidth > scaleHeight) ? scaleWidth : scaleHeight;
					//                    Logger.i("RoundRectDrawable", "W:" + mBoundsF.width() + "," + mBitmapRect.width());
					//                    Logger.i("RoundRectDrawable", "H:" + mBoundsF.height() + "," + mBitmapRect.height());
					//                    Logger.i("RoundRectDrawable", scaleWidth + "," + scaleHeight + " >>> " + scale);
					shaderMatrix.postScale(scale, scale);
				} else if (this.mScaleToFit == Matrix.ScaleToFit.CENTER)
				{
					if (mBitmapRect.width() < mBoundsF.width() && mBitmapRect.height() < mBoundsF.height())
					{
						shaderMatrix.setRectToRect(this.mBitmapRect, this.mBoundsF, Matrix.ScaleToFit.CENTER);
					} else
					{
						//以短边方式.居中裁
						//确定短边
						float scaleWidth = this.mBoundsF.width() / this.mBitmapRect.width();
						float scaleHeight = this.mBoundsF.height() / this.mBitmapRect.height();
						float scale = (scaleWidth > scaleHeight) ? scaleWidth : scaleHeight;

						shaderMatrix.postScale(scale, scale);

						float scaledHeight = scale * (this.mBitmapRect.height());
						float scaledWidth = scale * (this.mBitmapRect.width());

						float dx = (this.mBoundsF.width() - scaledWidth) / 2;
						float dy = (this.mBoundsF.height() - scaledHeight) / 2;
						shaderMatrix.postTranslate(dx, dy);
					}
				} else
				{
					shaderMatrix.setRectToRect(this.mBitmapRect, this.mBoundsF, Matrix.ScaleToFit.FILL);
				}

				this.mBitmapShader.setLocalMatrix(shaderMatrix);
			}
			this.mBoundsI.set(bounds);

			if (this.mInsetForPadding)
			{
				float vInset = calculateVerticalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
				float hInset = calculateHorizontalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
				this.mBoundsI.inset((int) Math.ceil(hInset), (int) Math.ceil(vInset));
				// to make sure they have same bounds.
				this.mBoundsF.set(this.mBoundsI);
			}
		}

		@Override
		protected void onBoundsChange(Rect bounds)
		{
			super.onBoundsChange(bounds);
			updateBounds(bounds);
		}

		@Override
		public void draw(Canvas canvas)
		{
			canvas.drawRoundRect(this.mBoundsF, this.mRadius, this.mRadius, this.mPaint);
		}

		@Override
		public void setAlpha(int alpha)
		{
			// not supported because older versions do not support
		}

		@Override
		public void setColorFilter(ColorFilter colorFilter)
		{
			// not supported because older versions do not support
		}

		@Override
		public int getOpacity()
		{
			return PixelFormat.TRANSLUCENT;
		}

		public float getRadius()
		{
			return this.mRadius;
		}

		void setRadius(float radius)
		{
			if (radius == this.mRadius)
			{
				return;
			}
			this.mRadius = radius;
			updateBounds(null);
		}

		public void setColor(int color)
		{
			this.mPaint.setColor(color);
		}
	}

	/**
	 * 实现原理.
	 * 1.绘制4个圆角
	 * 2.补圆角为直角
	 */
	class CustomRoundedRectDrawable extends RoundedRectDrawable
	{
		private boolean leftTopRect = false;
		private boolean rightTopRect = false;
		private boolean leftBottomRect = false;
		private boolean rightBottomRect = false;

		public CustomRoundedRectDrawable(int backgroundColor, float radius)
		{
			super(backgroundColor, radius);
		}

		/**
		 * 填充图片
		 *
		 * @param bitmap 图片资源
		 * @param radius 圆角
		 * @param scale  图片缩放方式.目前支持：START：短边填充；CENTER：其他；填充
		 */
		public CustomRoundedRectDrawable(Bitmap bitmap, float radius, Matrix.ScaleToFit scale)
		{
			super(bitmap, radius, scale);
		}

		@Override
		public void draw(Canvas canvas)
		{
			super.draw(canvas);

			if (leftTopRect)
			{
				canvas.drawRect(buildLeftTopRect(), mPaint);
			}

			if (rightTopRect)
			{
				canvas.drawRect(buildRightTopRect(), mPaint);
			}

			if (rightBottomRect)
			{
				canvas.drawRect(buildRightBottomRect(), mPaint);
			}

			if (leftBottomRect)
			{
				canvas.drawRect(buildLeftBottomRect(), mPaint);
			}
		}

		private RectF buildLeftTopRect()
		{
			RectF rectF = new RectF();
			rectF.left = mBoundsF.left;
			rectF.top = mBoundsF.top;
			rectF.right = mBoundsF.left + mRadius * 2.0f;
			rectF.bottom = mBoundsF.top + mRadius * 2.0f;

			return rectF;
		}

		private RectF buildRightTopRect()
		{
			RectF rectF = new RectF();
			rectF.left = mBoundsF.right - mRadius * 2.0f;
			rectF.top = mBoundsF.top;
			rectF.right = mBoundsF.right;
			rectF.bottom = mBoundsF.top + mRadius * 2.0f;

			return rectF;
		}

		private RectF buildRightBottomRect()
		{
			RectF rectF = new RectF();
			rectF.left = mBoundsF.right - mRadius * 2.0f;
			rectF.top = mBoundsF.bottom - mRadius * 2.0f;
			rectF.right = mBoundsF.right;
			rectF.bottom = mBoundsF.bottom;

			return rectF;
		}

		private RectF buildLeftBottomRect()
		{
			RectF rectF = new RectF();
			rectF.left = mBoundsF.left;
			rectF.top = mBoundsF.bottom - mRadius * 2.0f;
			rectF.right = mBoundsF.left + mRadius * 2.0f;
			rectF.bottom = mBoundsF.bottom;

			return rectF;
		}

		protected Path buildConvexPath()
		{
			Path path = new Path();

			path.moveTo(mBoundsF.left, (mBoundsF.top + mBoundsF.bottom) / 2.0f);
			path.lineTo(mBoundsF.left, mBoundsF.top + mRadius);
			if (leftTopRect)
			{
				path.lineTo(mBoundsF.left, mBoundsF.top);
			} else
			{
				RectF rectF = new RectF(mBoundsF.left, mBoundsF.top, mBoundsF.left + mRadius * 2.0f, mBoundsF.top + mRadius * 2.0f);
				path.arcTo(rectF, 180.0f, 90.0f);
			}

			path.lineTo(mBoundsF.right - mRadius, mBoundsF.top);
			if (rightTopRect)
			{
				path.lineTo(mBoundsF.right, mBoundsF.top);
			} else
			{
				RectF rectF = new RectF(mBoundsF.right - mRadius * 2.0f, mBoundsF.top, mBoundsF.right, mBoundsF.top + mRadius * 2.0f);
				path.arcTo(rectF, 270.0f, 90.0f);
			}

			path.lineTo(mBoundsF.right, mBoundsF.bottom - mRadius);
			if (rightBottomRect)
			{
				path.lineTo(mBoundsF.right, mBoundsF.bottom);
			} else
			{
				RectF rectF = new RectF(mBoundsF.right - mRadius * 2.0f, mBoundsF.bottom - mRadius * 2.0f, mBoundsF.right, mBoundsF.bottom);
				path.arcTo(rectF, 0.0f, 90.0f);
			}

			path.lineTo(mBoundsF.left + mRadius, mBoundsF.bottom);
			if (leftBottomRect)
			{
				path.lineTo(mBoundsF.left, mBoundsF.bottom);
			} else
			{
				RectF rectF = new RectF(mBoundsF.left, mBoundsF.bottom - mRadius * 2.0f, mBoundsF.left + mRadius * 2.0f, mBoundsF.bottom);
				path.arcTo(rectF, 90.0f, 90.0f);
			}

			path.close();
			return path;
		}

		protected int getColor()
		{
			return mPaint.getColor();
		}

		@Override
		public void setColor(int color)
		{
			super.setColor(color);
		}

		@Override
		public void setRadius(float radius)
		{
			super.setRadius(radius);
		}

		/**
		 * 左上角.需要显示直角
		 */
		public void showLeftTopRect(boolean show)
		{
			this.leftTopRect = show;
		}

		/**
		 * 右上角.需要显示直角
		 */
		public void showRightTopRect(boolean show)
		{
			this.rightTopRect = show;
		}

		/**
		 * 右下角.需要显示直角
		 */
		public void showRightBottomRect(boolean show)
		{
			this.rightBottomRect = show;
		}

		/**
		 * 左下角.需要显示直角
		 */
		public void showLeftBottomRect(boolean show)
		{
			this.leftBottomRect = show;
		}
	}
}