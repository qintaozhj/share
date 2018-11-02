package com.qin.tao.share.app.database;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import com.qin.tao.share.app.utils.CollectionUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Qintao
 *         2017-8-27-下午12:59:35
 *         <p>
 *         本地缓存管理类:
 *         已包含增加,删除,更新,清表功能
 */
public abstract class CacheDaoManger<T> {
    protected CacheOpenHelper cacheOpenHelper;
    //	private Context context;

    /**
     * Cache db 静态锁，因为SQLite Helper有缓存，如果在使用过程中有关闭数据的操作，就会有多线程问题<br>
     * 因此在每次打开数据库和关闭数据库的周期当中都用本锁独占
     */
    public static final Object gLocker = new Object();

    public CacheDaoManger(Context context) {
        //		this.context = context;
    }

    protected boolean openHelper() {
        //		if (this.context != null)
        this.cacheOpenHelper = CacheOpenHelper.getHelper();

        if (cacheOpenHelper == null || !cacheOpenHelper.isOpen())
            return false;

        return true;
    }

    protected void closeHelper() {
        if (cacheOpenHelper != null && cacheOpenHelper.isOpen())
            cacheOpenHelper.close();
    }

    /**
     * 插入
     *
     * @param clazz
     * @param t
     */
    public int insert(Class<T> clazz, T t) {
        synchronized (gLocker) {
            int value = -1;
            if (!openHelper() || clazz == null)
                return value;
            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null)
                    value = cacheOpenHelper.getDao(clazz).create(t);
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
                value = -1;
            } catch (SQLException e) {
                value = -1;
            } finally {
                closeHelper();
            }
            return value;
        }
    }

    public void insertAll(final Class<T> clazz, final List<T> t) throws Exception {
        synchronized (gLocker) {
            if (!openHelper() || clazz == null || t == null)
                return;
            try {
                final Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    dao.callBatchTasks(new Callable<Boolean>() {
                        public Boolean call() throws Exception {
                            for (T t2 : t) {
                                dao.create(t2);
                            }
                            return true;
                        }
                    });
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeHelper();
            }
        }
    }

    /**
     * 限制了条数的插入
     *
     * @param clazz
     * @param t
     * @param maxNum
     * @throws Exception
     */
    public void insertAllWithLimitNum(final Class<T> clazz, final List<T> t, int maxNum, String columnName, boolean ascending) throws Exception {
        synchronized (gLocker) {
            if (!openHelper() || clazz == null || CollectionUtils.isEmpty(t))
                return;
            try {
                final Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    dao.callBatchTasks(new Callable<Boolean>() {
                        public Boolean call() throws Exception {
                            for (T t2 : t) {
                                dao.create(t2);
                            }
                            return true;
                        }
                    });
                    //				List<T> list = dao.queryForAll();
                    QueryBuilder<T, ?> queryBuilder = dao.queryBuilder();
                    queryBuilder.orderBy(columnName, ascending);
                    PreparedQuery<T> prepare = queryBuilder.prepare();
                    List<T> list = dao.query(prepare);
                    if (!CollectionUtils.isEmpty(list) && list.size() > maxNum) {
                        dao.delete(list.subList(maxNum, list.size()));
                    }
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeHelper();
            }
        }
    }

    /**
     * 更新
     */
    public void update(Class<T> clazz, T t) {
        synchronized (gLocker) {
            if (!openHelper() || clazz == null || t == null)
                return;

            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null)
                    cacheOpenHelper.getDao(clazz).createOrUpdate(t);
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
        }
    }

    public void updateAll(final Class<T> clazz, final List<T> t) throws Exception {
        synchronized (gLocker) {
            if (!openHelper() || clazz == null || t == null)
                return;

            try {
                final Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    dao.callBatchTasks(new Callable<Boolean>() {
                        public Boolean call() throws Exception {
                            for (T t2 : t) {
                                dao.update(t2);
                            }
                            return true;
                        }
                    });
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
        }
    }

    /**
     * 删除
     */
    public void deleteBuilder(Class<T> clazz, String columnName, Object value) {
        synchronized (gLocker) {
            if (!openHelper() || clazz == null || TextUtils.isEmpty(columnName) || value == null)
                return;
            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    DeleteBuilder<T, ?> deletekBuilder = dao.deleteBuilder();
                    deletekBuilder.where().eq(columnName, value);
                    deletekBuilder.delete();
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeHelper();
            }
        }
    }

    public boolean delete(Class<T> clazz, T t) {
        synchronized (gLocker) {
            boolean result = false;
            if (!openHelper() || clazz == null || t == null)
                return result;
            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    dao.delete(t);
                    result = true;
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
            return result;
        }
    }

    public void delete(Class<T> clazz, Collection<T> list) {
        synchronized (gLocker) {
            if (!openHelper() || clazz == null || list == null)
                return;
            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null)
                    dao.delete(list);
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
        }
    }

    /**
     * 查询
     */
    //	public List<T> queryAll(Class<T> clazz)
    //	{
    //		List<T> list = null;
    //		if (!openHelper() || clazz == null)
    //			return list;
    //		try
    //		{
    //			Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
    //			if (dao != null)
    //				list = dao.queryForAll();
    //		} catch (SQLException e)
    //		{
    //		} finally
    //		{
    //			closeHelper();
    //		}
    //		return list;
    //	}
    public List<T> queryAll(Class<T> clazz) {
        synchronized (gLocker) {
            List<T> list = null;
            if (!openHelper() || clazz == null)
                return list;
            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null)
                    list = dao.queryForAll();
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
            return list;
        }
    }

    /**
     * 查询符合记录的条数
     *
     * @param clazz
     * @param columnName
     * @param value
     * @param columnName2
     * @param value2
     * @return
     */
    public long queryCount(Class<T> clazz, String columnName, Object value, String columnName2, Object value2) {
        synchronized (gLocker) {
            long count = 0;
            if (!openHelper() || clazz == null)
                return count;
            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    QueryBuilder<T, ?> queryBuilder = dao.queryBuilder();
                    Where<T, ?> where = queryBuilder.where();
                    where.eq(columnName, value).and().eq(columnName2, value2);
                    count = queryBuilder.countOf();
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
            return count;
        }
    }

    /**
     * 查询符合记录的条数
     *
     * @param clazz
     * @param columnName
     * @param value
     * @param columnName2
     * @param value2
     * @param columnName3
     * @param value3
     * @return
     */
    public long queryCount(Class<T> clazz, String columnName, Object value, String columnName2, Object value2, String columnName3, Object value3) {
        synchronized (gLocker) {
            long count = 0;
            if (!openHelper() || clazz == null)
                return count;
            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    QueryBuilder<T, ?> queryBuilder = dao.queryBuilder();
                    Where<T, ?> where = queryBuilder.where();
                    where.eq(columnName, value).and().eq(columnName2, value2).and().eq(columnName3, value3);
                    count = queryBuilder.countOf();
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
            return count;
        }
    }

    public T queryById(Class<T> clazz, Integer id) {
        synchronized (gLocker) {
            T t = null;
            if (!openHelper() || clazz == null)
                return t;

            try {
                Dao<T, Integer> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null)
                    t = dao.queryForId(id);
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
            return t;
        }
    }

    public T queryById(Class<T> clazz, String id) {
        synchronized (gLocker) {
            T t = null;
            if (!openHelper() || TextUtils.isEmpty(id))
                return t;
            try {
                Dao<T, String> dao = cacheOpenHelper.getDao(clazz);
                if (dao != null)
                    t = dao.queryForId(id);
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
            return t;
        }
    }

    public T queryBuilder(Class<T> clazz, String columnName, Object value) {
        synchronized (gLocker) {
            T t = null;
            if (!openHelper() || clazz == null || TextUtils.isEmpty(columnName) || value == null)
                return t;
            try {
                Dao<T, String> dao = cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    QueryBuilder<T, ?> queryBuilder = dao.queryBuilder();
                    queryBuilder.where().eq(columnName, value);
                    t = queryBuilder.queryForFirst();
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeHelper();
            }
            return t;
        }
    }

    /**
     * 清除表
     */

    public boolean clearTable(Class<T> clazz) {
        synchronized (gLocker) {
            boolean result = false;
            if (!openHelper() || clazz == null)
                return result;
            try {
                Dao<T, String> dao = cacheOpenHelper.getDao(clazz);
                if (dao != null && dao.isTableExists())
                    TableUtils.clearTable(cacheOpenHelper.getConnectionSource(), clazz);
                result = true;
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeHelper();
            }
            return result;
        }
    }

    public void clearTable(List<Class<T>> classes) {
        if (classes != null && !classes.isEmpty()) {
            for (int i = 0; i < classes.size(); i++) {
                clearTable(classes.get(i));
            }
        }
    }

    /**
     * 根据传参 查询集合
     */
    public List<T> queryBuilderLits(Class<T> clazz, String columnName, Object value) {
        return queryBuilderLits(clazz, columnName, value, null, false);
    }

    /**
     * 根据列名查询并排序
     *
     * @param clazz
     * @param columnName      查询的属性字段名
     * @param value           属性值
     * @param orderColumnName 排序的字段，不需要排序可传null
     * @param ascending       true 升序   false 降序
     * @return
     */
    public List<T> queryBuilderLits(Class<T> clazz, String columnName, Object value, String orderColumnName, boolean ascending) {
        synchronized (gLocker) {
            List<T> list = null;
            if (!openHelper() || clazz == null || TextUtils.isEmpty(columnName) || value == null)
                return list;
            try {
                Dao<T, String> dao = cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    QueryBuilder<T, ?> queryBuilder = dao.queryBuilder();
                    queryBuilder.where().eq(columnName, value);
                    if (!TextUtils.isEmpty(orderColumnName))
                        queryBuilder.orderBy(orderColumnName, ascending);
                    list = queryBuilder.query();
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeHelper();
            }
            return list;
        }
    }

    /**
     * 删除表数据
     * 传2个参数
     */
    public void deleteBuilder(Class<T> clazz, String columnName, Object value, String columnName2, Object value2) {
        synchronized (gLocker) {
            if (!openHelper() || clazz == null || TextUtils.isEmpty(columnName) || value == null || TextUtils.isEmpty(columnName2) || value == null)
                return;
            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    DeleteBuilder<T, ?> deletekBuilder = dao.deleteBuilder();
                    deletekBuilder.where().eq(columnName, value).and().eq(columnName2, value2);
                    deletekBuilder.delete();
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeHelper();
            }
        }
    }

    /**
     * @param clazz
     * @param columnName  查询字段1
     * @param value       字段1的值
     * @param columnName2 查询字段2
     * @param value2      字段2的值
     * @return
     */
    public List<T> queryBuilderLits(Class<T> clazz, String columnName, Object value, String columnName2, Object value2) {
        return queryBuilderLits(clazz, columnName, value, columnName2, value2, null, false);
    }

    /**
     * @param clazz
     * @param columnName      查询字段1
     * @param value           字段1的值
     * @param columnName2     查询字段2
     * @param value2          字段2的值
     * @param orderColumnName 排序的字段
     * @param ascending       true 升序,false 降序
     * @return
     */
    public List<T> queryBuilderLits(Class<T> clazz, String columnName, Object value, String columnName2, Object value2, String orderColumnName,
                                    boolean ascending) {
        synchronized (gLocker) {
            List<T> list = null;
            if (!openHelper() || clazz == null || TextUtils.isEmpty(columnName) || value == null || TextUtils.isEmpty(columnName2) || value == null)
                return list;
            try {
                Dao<T, String> dao = cacheOpenHelper.getDao(clazz);
                if (dao != null) {
                    QueryBuilder<T, ?> queryBuilder = dao.queryBuilder();
                    queryBuilder.where().eq(columnName, value).and().eq(columnName2, value2);
                    if (!TextUtils.isEmpty(orderColumnName))
                        queryBuilder.orderBy(orderColumnName, ascending);
                    list = queryBuilder.query();
                }
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeHelper();
            }
            return list;
        }
    }

    /**
     * 修改数据
     */

    public void updates(Class<T> clazz, T t) {
        synchronized (gLocker) {
            if (!openHelper() || clazz == null || t == null)
                return;

            try {
                Dao<T, ?> dao = this.cacheOpenHelper.getDao(clazz);
                if (dao != null)
                    cacheOpenHelper.getDao(clazz).update(t);
            } catch (IllegalStateException ise) {
                ise.printStackTrace();
            } catch (SQLException e) {
            } finally {
                closeHelper();
            }
        }
    }

}
