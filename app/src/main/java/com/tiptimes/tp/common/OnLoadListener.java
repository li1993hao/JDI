package com.tiptimes.tp.common;


/**
 * 通用的下载进度监听器
 *
 * @author haoli
 * @param <T>
 */
public interface OnLoadListener<T> {
    void loading(int prorgess);
    void loadFail(Message message);

    /**
     *
     * @param o  完成数据
     * @param str 标识
     */
    void loaded(T o, String str);
}
