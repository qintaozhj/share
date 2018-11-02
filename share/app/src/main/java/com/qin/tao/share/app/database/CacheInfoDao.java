package com.qin.tao.share.app.database;

import android.content.Context;

import com.qin.tao.share.app.base.BaseApplication;
import com.qin.tao.share.model.cache.CacheInfo;

/**
 * @author qintao
 *         created at 2016/6/15 17:37
 */

public class CacheInfoDao extends CacheDaoManger<CacheInfo> {
    private static CacheInfoDao dao;

    private CacheInfoDao(Context context) {
        super(context);

    }

    public static CacheInfoDao getDao() {
        if (dao == null)
            dao = new CacheInfoDao(BaseApplication.appContext);
        return dao;
    }

}
