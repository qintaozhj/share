package com.qin.tao.share.app.utils;

import android.content.Context;
import android.text.format.DateUtils;

import com.qin.tao.share.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormat {
    /**
     * <p>秒，分，小时，小时，天，月，年</p>
     * <p>年-月，月-日，时-分，分-秒</p>
     * <p>年-月-日，时-分-秒</p>
     * <p>年-月-日    时-分-秒</p>
     */
    public enum TimeType {
        //秒
        ss("ss"),
        //分
        mm("mm"),
        //小时24
        HH("HH"),
        //小时12
        hh("hh"),
        //天
        dd("dd"),
        //月
        MM("MM"),
        //年
        yyyy("yyyy"),
        //年-月
        yyyy_MM("yyyy-MM"),
        //月-日
        MM_dd("MM-dd"),
        //时：分
        HH_mm("HH:mm"),
        //分：秒
        mm_ss("mm:ss"),
        //年-月-日
        yyyy_MM_dd("yyyy-MM-dd"),
        //时：分：秒
        HH_mm_ss("HH:mm:ss"),
        //年-月-日 时：分：秒
        yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
        //年-月-日 时：分
        yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
        //年/月/日 时：分
        slashes_yyyy_MM_dd_HH_mm("yyyy/MM/dd HH:mm"),
        //月/日 时：分
        slashes_MM_dd_HH_mm("MM/dd HH:mm"),
        //中文
        tMM_dd("MM月dd日"),
        //中文
        td("d日"),
        //中文
        tM("M月"),
        //中文
        tMM_dd_hh_mm("MM月dd日 HH:mm");
        String timeTemplate = "yyyy-MM-dd HH:mm:ss";

        TimeType(String template) {
            timeTemplate = template;
        }

        String getTemplate() {
            return timeTemplate;
        }
    }


    /**
     * 2017年11月9日12:34:29 --> 通用时间格式一
     * ·发布时间＜10分钟，显示：刚刚
     * ·10分钟≤发布时间＜60分钟，以分钟为单位显示
     * ·文本格式：[分钟数]分钟前
     * ·1小时≤发布时间＜24小时，以小时为单位显示
     * ·文本格式：[小时数]小时前
     * ·1天≤发布时间＜7天，显示日期
     * ·文本格式：[天数]天前
     * ·7天≤发布时间＜365天，显示日期
     * ·文本格式：[月份]-[日期]
     * ·发布时间≥365天，显示月
     * ·文本格式：[年份]-[月份]
     */
    public static String getCommonFormatTime1(Context context, String createTime) {
        return getCommonFormatTime1(context, getStringToDate(createTime), System.currentTimeMillis());
    }

    /**
     * 通用时间格式一  Wang Zu Song
     * 支持10位
     */
    public static String getCommonFormatTime1(Context context, final long createTime, final long serverTime) {
        String returnVale = "";
        SimpleDateFormat dateFormat = null;
        String strTimeLengthCheck = String.valueOf(createTime);
        long create = createTime;
        if (strTimeLengthCheck.length() == 10)
            create = createTime * 1000;

        strTimeLengthCheck = String.valueOf(serverTime);
        long server = serverTime;
        if (strTimeLengthCheck.length() == 10)
            server = serverTime * 1000;

        if (create > 0 && create <= server) {
            long dif = server - create;
            int year = (int) (dif / DateUtils.YEAR_IN_MILLIS);
            //设计要求31天为一个月
            //int month = (int) (dif / (DateUtils.WEEK_IN_MILLIS * 4 + DateUtils.DAY_IN_MILLIS * 3));
            int day = (int) (dif / DateUtils.DAY_IN_MILLIS);
            int hour = (int) (dif / DateUtils.HOUR_IN_MILLIS);
            int minute = (int) (dif / DateUtils.MINUTE_IN_MILLIS);
            // 大于一年、7天≤发布时间＜365天，在4.5.0修订，将时间格式 由“yyyy-MM”==》“yyyy/MM”，“MM-dd”===》“MM/dd”
            if (year > 0)//大于一年
            {
                dateFormat = new SimpleDateFormat(context.getString(R.string.time_format_month_year_slashes));
                returnVale = dateFormat.format(new Date(create));
            } else if (7 <= day && day < 365)//7天≤发布时间＜365天，显示日期
            {
                dateFormat = new SimpleDateFormat(context.getString(R.string.time_format_month_day_slashes));
                returnVale = dateFormat.format(new Date(create));
            } else if (1 <= day && day < 7)//1天≤发布时间＜7天，显示日期
            {
                returnVale = context.getString(R.string.time_format_elapsed_day, day);
            } else if (1 <= hour && hour < 24)//1小时≤发布时间＜24小时，以小时为单位显示
            {
                returnVale = context.getString(R.string.time_format_elapsed_hour, hour);
            } else if (10 <= minute && minute < 60)//10分钟≤发布时间＜60分钟，以分钟为单位显示
            {
                returnVale = context.getString(R.string.time_format_elapsed_minute, minute);
            } else if (minute < 10)//·发布时间＜10分钟，显示：刚刚
            {
                returnVale = context.getString(R.string.time_format_elapsed_just);
            }
        }

        return returnVale;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString
     * @return
     */
    private static long getStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

}
