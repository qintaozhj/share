package com.qin.tao.share.app.config;


import com.qin.tao.share.app.database.CacheOpenHelper;

/**
 * 数据库配置文件
 */
public class DatabaseConfig {

    protected static int CACHE_VERSION = 1;

    //数据库名称
    protected static String CACHE_NAME = "cache.db";

    public static void initDatabase() {
        //应用运行时创建表
        //防止表过多卡主线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //缓存数据库创建
                new CacheOpenHelper();
            }
        }).start();

    }

    /**
     * @author lxl
     *         2015-2-9-下午3:07:59
     *         <p>
     *         数据库类型 名称 管理
     */
    public enum DatabaseEnum {

        /**
         * 本地缓存专用数据库
         */
        CACHE(DatabaseConfig.CACHE_NAME, DatabaseConfig.CACHE_VERSION);


        private String databaseName = "";
        private int version = 0;

        private DatabaseEnum(String databaseName, int version) {
            this.databaseName = databaseName;
            this.version = version;
        }

        /**
         * 返回数据库名字
         *
         * @return
         */
        public String getDatabaseName() {
            return this.databaseName;
        }

        /**
         * 返回数据库版本
         *
         * @return
         */
        public int getDatabaseVersion() {
            return this.version;
        }
    }
}
