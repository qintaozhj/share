package com.qin.tao.share.app.log;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Log工具类
 *
 * @author xp
 */
public class Logger {
    public static final String TAG = "Logger";
    private static boolean info_open = true;
    private static boolean verbose_open = true;
    private static boolean debug_open = true;
    private static boolean warn_open = true;
    private static boolean error_open = true;
    private static List<Long> list = new ArrayList<Long>();
    public static final int FLAG_START = 0;
    public static final int FLAG_NORMAL = 1;
    public static final int FLAG_END = 2;
    private static long tempTime;

    /**
     * 调用log.i,2个参数的Log.i，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void i(String msg, int flag) {
        if (info_open)
            if (flag == FLAG_START) {
                Log.i(TAG, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.i(TAG, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.i(TAG, msg);
            }
    }

    /**
     * 调用log.i,3个参数的Log.i，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void i(String tag, String msg, int flag) {
        if (info_open) {
            if (flag == FLAG_START) {
                Log.i(tag, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.i(tag, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.i(tag, msg);
            }
        }
    }

    /**
     * 调用log.i,4个参数的Log.i，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param tr
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void i(String tag, String msg, Throwable tr, int flag) {
        if (info_open)
            if (flag == FLAG_START) {
                Log.i(tag, msg, tr);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.i(tag, msg + ",used time=" + (endTime - tempTime), tr);
            } else if (flag == FLAG_NORMAL) {
                Log.i(tag, msg, tr);
            }
    }

    /**
     * 调用log.v,2个参数的Log.v，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void v(String msg, int flag) {
        if (verbose_open)
            if (flag == FLAG_START) {
                Log.v(TAG, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.v(TAG, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.v(TAG, msg);
            }
    }

    /**
     * 调用log.v,3个参数的Log.v，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void v(String tag, String msg, int flag) {
        if (verbose_open) {
            if (flag == FLAG_START) {
                Log.v(tag, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.v(tag, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.v(tag, msg);
            }
        }
    }

    /**
     * 调用log.v,4个参数的Log.v，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param tr
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void v(String tag, String msg, Throwable tr, int flag) {
        if (verbose_open)
            if (flag == FLAG_START) {
                Log.v(tag, msg, tr);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.v(tag, msg + ",used time=" + (endTime - tempTime), tr);
            } else if (flag == FLAG_NORMAL) {
                Log.v(tag, msg, tr);
            }
    }

    /**
     * 调用log.d,2个参数的Log.d，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void d(String msg, int flag) {
        if (debug_open)
            if (flag == FLAG_START) {
                Log.d(TAG, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.d(TAG, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.d(TAG, msg);
            }
    }

    /**
     * 调用log.d,3个参数的Log.d，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void d(String tag, String msg, int flag) {
        if (debug_open) {
            if (flag == FLAG_START) {
                Log.d(tag, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.d(tag, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.d(tag, msg);
            }
        }
    }

    /**
     * 调用log.d,4个参数的Log.d，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param tr
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void d(String tag, String msg, Throwable tr, int flag) {
        if (debug_open)
            if (flag == FLAG_START) {
                Log.d(tag, msg, tr);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.d(tag, msg + ",used time=" + (endTime - tempTime), tr);
            } else if (flag == FLAG_NORMAL) {
                Log.d(tag, msg, tr);
            }
    }

    /**
     * 调用log.w,2个参数的Log.w，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void w(String msg, int flag) {
        if (warn_open)
            if (flag == FLAG_START) {
                Log.w(TAG, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.w(TAG, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.w(TAG, msg);
            }
    }

    /**
     * 调用log.w,3个参数的Log.w，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void w(String tag, String msg, int flag) {
        if (warn_open) {
            if (flag == FLAG_START) {
                Log.w(tag, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.w(tag, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.w(tag, msg);
            }
        }
    }

    /**
     * 调用log.w,4个参数的Log.w，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param tr
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void w(String tag, String msg, Throwable tr, int flag) {
        if (warn_open)
            if (flag == FLAG_START) {
                Log.w(tag, msg, tr);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.w(tag, msg + ",used time=" + (endTime - tempTime), tr);
            } else if (flag == FLAG_NORMAL) {
                Log.w(tag, msg, tr);
            }
    }

    /**
     * 调用log.e,2个参数的Log.e，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void e(String msg, int flag) {
        if (error_open)
            if (flag == FLAG_START) {
                Log.e(TAG, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.e(TAG, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.e(TAG, msg);
            }
    }

    /**
     * 调用log.e,3个参数的Log.e，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void e(String tag, String msg, int flag) {
        if (error_open) {
            if (flag == FLAG_START) {
                Log.e(tag, msg);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.e(tag, msg + ",used time=" + (endTime - tempTime));
            } else if (flag == FLAG_NORMAL) {
                Log.e(tag, msg);
            }
        }
    }

    /**
     * 调用log.e,4个参数的Log.e，
     * 如果测试开始和结束用的时间， flag用Logger.FLAG_START和Flag_END，这两个须是成对出现
     *
     * @param tag，标记，一般为类名
     * @param msg，要打印的消息
     * @param tr
     * @param flag，标志位，Logger.FLAG_START和Flag_END记录开始和结束的时间，FLAG_NORMAL是标准Log
     */
    public static void e(String tag, String msg, Throwable tr, int flag) {
        if (error_open)
            if (flag == FLAG_START) {
                Log.e(tag, msg, tr);
                long startTime = System.currentTimeMillis();
                if (list != null)
                    list.add(startTime);
            } else if (flag == FLAG_END) {
                long endTime = System.currentTimeMillis();
                if (list != null && list.size() > 0) {
                    tempTime = list.remove(list.size() - 1);
                } else {
                    tempTime = endTime;
                }
                Log.e(tag, msg + ",used time=" + (endTime - tempTime), tr);
            } else if (flag == FLAG_NORMAL) {
                Log.e(tag, msg, tr);
            }
    }

    public static void setInfo_open(boolean info_open) {
        Logger.info_open = info_open;
    }

    public static void setVerbose_open(boolean verbose_open) {
        Logger.verbose_open = verbose_open;
    }

    public static void setDebug_open(boolean debug_open) {
        Logger.debug_open = debug_open;
    }

    public static void setWarn_open(boolean warn_open) {
        Logger.warn_open = warn_open;
    }

    public static void setError_open(boolean error_open) {
        Logger.error_open = error_open;
    }

    public static void setList(List<Long> list) {
        Logger.list = list;
    }

    public static void setTempTime(long tempTime) {
        Logger.tempTime = tempTime;
    }

    public static void setLogEnable(boolean isEnable) {
        if (!isEnable) {
            Logger.setDebug_open(false);
            Logger.setError_open(false);
            Logger.setInfo_open(false);
            Logger.setVerbose_open(false);
            Logger.setWarn_open(false);
        } else {
            Logger.setDebug_open(true);
            Logger.setError_open(true);
            Logger.setInfo_open(true);
            Logger.setVerbose_open(true);
            Logger.setWarn_open(true);
        }
    }

    /**
     * 调用log.i
     *
     * @param msg
     */
    public static void i(String msg) {
        if (info_open)
            Log.i("Logger", msg);
    }

    public static void i(String tag, String msg) {
        if (info_open)
            Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (info_open)
            Log.i(tag, msg, tr);
    }

    /**
     * 调用log.v
     *
     * @param msg
     */
    public static void v(String msg) {
        if (verbose_open)
            Log.v("Logger", msg);
    }

    public static void v(String tag, String msg) {
        if (verbose_open)
            Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (verbose_open)
            Log.v(tag, msg, tr);
    }

    /**
     * 调用log.d
     *
     * @param msg
     */
    public static void d(String msg) {
        if (debug_open)
            Log.d("Logger", msg);
    }

    public static void d(String tag, String msg) {
        if (debug_open)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (debug_open)
            Log.d(tag, msg, tr);
    }

    /**
     * 调用log.w
     *
     * @param msg
     */
    public static void w(String msg) {
        if (warn_open)
            Log.w("Logger", msg);
    }

    public static void w(String tag, String msg) {
        if (warn_open)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (warn_open)
            Log.w(tag, msg, tr);
    }

    /**
     * 调用log.e
     *
     * @param msg
     */
    public static void e(String msg) {
        if (error_open)
            Log.e("Logger", msg);
    }

    public static void e(String tag, String msg) {
        if (error_open)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (error_open)
            Log.e(tag, msg, tr);
    }

    public static boolean isInfo_open() {
        return info_open;
    }

    public static boolean isVerbose_open() {
        return verbose_open;
    }

    public static boolean isDebug_open() {
        return debug_open;
    }

    public static boolean isWarn_open() {
        return warn_open;
    }

    public static boolean isError_open() {
        return error_open;
    }

}
