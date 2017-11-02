package com.qin.tao.share.tools.image;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.qin.tao.share.app.base.BaseApplication;
import com.qin.tao.share.app.config.DeviceConfig;
import com.qin.tao.share.app.config.SDCardConfig;
import com.qin.tao.share.tools.image.core.DisplayImageOptions;
import com.qin.tao.share.tools.image.core.ImageLoader;
import com.qin.tao.share.tools.image.core.ImageLoaderConfiguration;
import com.qin.tao.share.tools.image.core.assist.QueueProcessingType;

import java.util.Locale;

/**
 * 图片加载
 *
 * @author qintao
 */
public class LoadImageUtils {
    private static DisplayImageOptions options = null;

    /**
     * 设置加载属性
     *
     * @param drawableId drawableId:传0就崩
     * @return
     */
    public static DisplayImageOptions.Builder getBuilder(int drawableId) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565);
        if (drawableId != 0) {
            Context context = BaseApplication.getContext();
            if (context != null) {
                Drawable drawable = context.getResources().getDrawable(drawableId);
                if (drawable != null)
                    builder.showImageForEmptyUri(drawable).showImageOnFail(drawable).showImageOnLoading(drawable);
            }
        }
        return builder;
    }

    public static DisplayImageOptions.Builder getBuilder() {
        return getBuilder(0);
    }

    /**
     * 初始化
     */
    public static void initImageLoader(Context applicationContext) {

        setOptions();
        ActivityManager systemService = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryNormal = systemService.getMemoryClass();
        int memoryLarge = systemService.getLargeMemoryClass();
        Log.d("memory", String.format(Locale.ENGLISH, "%1$d,%2$d", memoryNormal, memoryLarge));
        // 使用可用内存的1/8作为图片缓存
        final int cacheSize = 1024 * 1024 * memoryLarge / 8;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(applicationContext)//
                .defaultDisplayImageOptions(options)//
                .diskCacheExtraOptions(DeviceConfig.displayWidthPixels(), DeviceConfig.displayHeightPixels(), null).denyCacheImageMultipleSizesInMemory()//
                .tasksProcessingOrder(QueueProcessingType.LIFO)//
                .threadPoolSize(3)//线程数量
                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
                //				.denyCacheImageMultipleSizesInMemory() // 解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
                // 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                //				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
                .diskCacheFileCount(1000)// 缓存文件的最大个数
                .diskCacheDirPath(SDCardConfig.IMAGE_CACHE_ROOT)//缓存文件夹路径
                //				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                //				.writeDebugLogs()// 输出log
                //				.memoryCacheSize(cacheSize)
                //				.memoryCache(new WeakMemoryCache())
                .memoryCacheSize(cacheSize)//
                .disabledToDuplicateLoadUriToDisplayImageAware()//
                .build();
        ImageLoader.getInstance().init(config);
    }

    private static void setOptions() {
        /**
         * //设置图片在下载期间显示的图片 showStubImage(R.drawable.ic_launcher)
         *
         * //设置图片Uri为空或是错误的时候显示的图片 showImageForEmptyUri(R.drawable.ic_empty)
         *
         * //设置图片加载/解码过程中错误时候显示的图片 showImageOnFail(R.drawable.ic_error)
         *
         * //设置图片在下载前是否重置，复位 resetViewBeforeLoading()
         *
         * //设置下载的图片是否缓存在内存中 cacheInMemory()
         *
         * //设置下载的图片是否缓存在SD卡中 cacheOnDisc()
         *
         * //设置图片的解码类型 bitmapConfig(Bitmap.Config.RGB_565)
         *
         * //设置图片的解码配置 decodingOptions(android.graphics.BitmapFactory.Options
         * decodingOptions)
         *
         * //设置图片下载前的延迟 delayBeforeLoading(int delayInMillis)
         *
         * //设置额外的内容给ImageDownloader extraForDownloader(Object extra)
         *
         * //设置图片加入缓存前，对bitmap进行设置 preProcessor(BitmapProcessor preProcessor)
         *
         * //设置显示前的图片，显示后这个图片一直保留在缓存中 postProcessor(BitmapProcessor
         * postProcessor)
         *
         * //设置图片以如何的编码方式显示 imageScaleType(ImageScaleType imageScaleType)
         *
         *
         * displayer(BitmapDisplayer displayer)
         * displayer：
         * RoundedBitmapDisplayer（int roundPixels）设置圆角图片
         * FakeBitmapDisplayer（）这个类什么都没做
         * FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间 SimpleBitmapDisplayer()正常显示一张图片
         *
         *
         * imageScaleType(ImageScaleType imageScaleType)
         * imageScaleType:
         * 	EXACTLY:			图像将完全按比例缩小的目标大小
         *  EXACTLY_STRETCHED:	图片会缩放到目标大小完全
         * IN_SAMPLE_INT:		图像将被二次采样的整数倍
         * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
         * NONE:图片不会调整
         */
        options = new DisplayImageOptions.Builder().cacheInMemory(true)// 是否緩存都內存中
                .cacheOnDisk(true)// 是否緩存到sd卡上
                .bitmapConfig(Config.RGB_565)////设置图片的解码类型
                .resetViewBeforeLoading(false)//设置图片在下载前是否重置，复位
                .build();
    }
}
