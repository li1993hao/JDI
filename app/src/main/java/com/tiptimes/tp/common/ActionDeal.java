package com.tiptimes.tp.common;

/**
 * 网络请求的通用构件
 * @author haoli
 * @param <T>
 */
public interface ActionDeal<T> {
    /**
     * 执行请求
     */
	void doAction();

    /**
     * 回调入口
     * @param acitonBundle
     */
	void handleResult(ActionBundle<T> acitonBundle);
}
