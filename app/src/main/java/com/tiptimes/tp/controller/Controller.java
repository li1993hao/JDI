package com.tiptimes.tp.controller;

import android.os.Bundle;
import android.view.View;

import com.tiptimes.tp.common.ActionDeal;
import com.tiptimes.tp.common.ParameterMap;
import com.tiptimes.tp.common.SignalListener;

/**
 * 控制器接口定义
 * 定义控制器的主要行为
 *
 */
public interface Controller extends SignalListener{
    /**
     * 类似于activity中的同名方法,
     * 用于动态绑定
     * @param id
     * @return
     */
	View findViewById(int id);

    /**
     * 对界面控件的初始化操作
     * 都放在这里
     */
	void initView(Bundle savedInstanceState);

    /**
     * 数据的初始化放在此处
     */
	void initData();

    /**
     * 网络请求的方法
     * @param actionDeal 通用的网络请求构件
     * @param params   请求参数
     */
	void actionPerformed(ActionDeal actionDeal, ParameterMap params);

}
