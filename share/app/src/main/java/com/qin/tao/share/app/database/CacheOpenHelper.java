package com.qin.tao.share.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.qin.tao.share.app.base.BaseApplication;
import com.qin.tao.share.app.config.DatabaseConfig;
import com.qin.tao.share.model.cache.CacheInfo;

import java.sql.SQLException;

/**
 * @author qinTao
 *         2017-8-27-下午12:59:33
 *         <p>
 *         本地缓存数据管理
 */
public class CacheOpenHelper extends OrmLiteSqliteOpenHelper {
    private static CacheOpenHelper helper;

    public static CacheOpenHelper getHelper() {
        return getHelper(BaseApplication.appContext);
    }

    public static CacheOpenHelper getHelper(Context context) {
        if (helper == null || !helper.isOpen())
            helper = new CacheOpenHelper();
        return helper;
    }

    public CacheOpenHelper() {
        //避免内存泄露,直接使用Application对象
        super(BaseApplication.appContext, DatabaseConfig.DatabaseEnum.CACHE.getDatabaseName(), null, DatabaseConfig.DatabaseEnum.CACHE.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
        for (Class<?> classes : mDbClasses) {
            try {
                TableUtils.createTable(connectionSource, classes);
                DaoManager.createDao(connectionSource, classes);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //不考虑版本升级时候，数据的迁移
        for (Class<?> classes : mDbClasses) {
            try {
                TableUtils.dropTable(connectionSource, classes, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        onCreate(database, connectionSource);
        //版本升级时，数据迁移示例
//        for (int i = oldVersion; i < newVersion; i++) {
//            switch (i) {
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 3://版本3--->4 record.db表 新增字段 is_local_import 是否由本地导入
//                    database.execSQL("ALTER TABLE " + AccompanyInfo.TABLE_NAME + " ADD COLUMN " + AccompanyInfo.ISLOCAL + " " + "text" + " default " + "0");
//                    break;
//                case 4://版本4--->5 record.db表 数据去重.并迁移本地伴奏文件数据
//                    RecordTransferHelper helper = new RecordTransferHelper();
//                    helper.transferRecordData(database);
//                    break;
//                case 5:
//                    try {
//                        addAColumn(database, AccompanyDownloadInfo.TABLE_NAME, "music_sequence", "TEXT", "0");
//                        TableUtils.createTableIfNotExists(connectionSource, HostMusicInfo.class);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 6:
//                    try {
//                        // 版本6--->7 record.db 表AccompanyDownloadInfo.TABLE_NAME新增原唱关联字段
//                        //1.5.0新增原唱数据字段
//                        addAColumn(database, AccompanyDownloadInfo.TABLE_NAME, AccompanyInfo.ORIGINAL_BEAT_URL, "TEXT", "");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }
    }

    /**
     * 向已存在表里添加一列
     *
     * @param db
     * @param tableName
     * @param columns
     * @param columnsType 新增列类型,如：text
     * @return
     */
    private boolean addAColumn(SQLiteDatabase db, String tableName, String columns, String columnsType, String defaultValue) {
        try {
            if (!TextUtils.isEmpty(defaultValue))
                db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columns + " " + columnsType + " default " + defaultValue);
            else
                db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columns + " " + columnsType);
            return true;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Class<?>[] mDbClasses = {
            CacheInfo.class
    };
}
