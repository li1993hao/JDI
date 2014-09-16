package com.tiptimes.tp.common;

/**
 * 网络请求返回信息封装
 * @author tiptimes haoli
 *
 */
public class ActionBundle<T>{
	public boolean isNomal; //是否正常
	public String msg; //错误信息
	public T data;  //返回数据
}
