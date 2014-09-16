package com.tiptimes.tp.common;


/**
 * @author haoli
 * @param <T>
 */
public interface OnLoadListener<T> {
    void loading(int prorgess);
    void loadFail(Message message);
    void loaded(T o, String url);
}
