package com.tiptimes.tp.common;

import android.os.Handler;
import android.os.Looper;

/**
 * 封装网络请求的信息
 * action在这里狭义的解释为网络
 * 请求动作，但其是可以扩展的
 * @author haoli
 */
@SuppressWarnings("rawtypes")
public class ActionInfo {
	private int controllerID; //控制器的id
	private  String url; //请求的url
	private boolean isList; //请求数据是否是线性集合
	private ParameterMap params; //请求参数
	private Class dataClass;  //请求数据类型
	private Handler handler = new Handler(Looper.getMainLooper()); //主线程的handler对象
	public Handler getHandler() {
		return handler;
	}

	public ActionDeal actionDeal; //动作请求构件


    /**
     * 请求信息比较方法，确保请求的唯一性
     * @param o
     * @return
     */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof ActionInfo){
			ActionInfo ai = (ActionInfo)o;
			if(ai.url.equals(url)){
				if(params ==null && ai.params==null){
					return true;
				}else{
					if(params!=null && ai.params==null || params==null && ai.params!=null){
						return false;
					}else{
						return params.equals(ai.params);
					}
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ParameterMap getParams() {
		return params;
	}

	public void setParams(ParameterMap params) {
		this.params = params;
	}

	public Class getDataClass() {
		return dataClass;
	}

	public void setDataClass(Class dataClass) {
		this.dataClass = dataClass;
	}

	public int getControllerID() {
		return controllerID;
	}

	public void setControllerID(int controllerID) {
		this.controllerID = controllerID;
	}

	public boolean isList() {
		return isList;
	}

	public void seIsList(boolean isList) {
		this.isList = isList;
	}

	
	
}
