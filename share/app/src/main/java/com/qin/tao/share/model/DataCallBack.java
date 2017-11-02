package com.qin.tao.share.model;

/**
 * @author qintao on 2017/9/11 17:22
 *         所有数据返回接口处理
 */

public interface DataCallBack<T> {
    void onSuccess(T t);

    void onFail(String msg);
}
