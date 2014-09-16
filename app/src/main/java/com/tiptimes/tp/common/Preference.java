package com.tiptimes.tp.common;
import java.io.File;

import com.alibaba.fastjson.JSON;
import com.tiptimes.tp.controller.Application;
import com.tiptimes.tp.util.ACache;

/**
 * 系统或者用户配置等信息管理类
 * @author haoli
 */
public class Preference {
	final static String PREFERENCE_DIR = "preference"; //保存key值
	private static final int MAX_SIZE = 1000 * 1000 * 10; // 10 mb
	private static final int MAX_COUNT = Integer.MAX_VALUE; // 不限制存放数据的数量
	private static ACache aCache;
	private static Preference preference = new Preference();
	
	private Preference(){
		String path = Application.getApplication().getFilesDir().getAbsolutePath()+"/"+PREFERENCE_DIR;
		aCache = ACache.get(new File(path),MAX_SIZE,MAX_COUNT);
	}
	
	public static Preference getInstance(){
		return preference;
	}
	
	public static synchronized UserInfo getUserInfo(){
		String jsonStr =aCache.getAsString(PREFERENCE_DIR);
		if(jsonStr == null){
			return null;
		}
		return JSON.parseObject(jsonStr, UserInfo.class);
	}


	public static synchronized void setUserInfo(UserInfo userInfo){
		if(userInfo != null){
			String jsonStr = JSON.toJSONString(userInfo);
			aCache.put(PREFERENCE_DIR, jsonStr);
		}
	}


	
	public static synchronized void setUserInfo(UserInfo userInfo, int saveTime){
		if(userInfo != null){
			String jsonStr = JSON.toJSONString(userInfo);
			aCache.put(PREFERENCE_DIR, jsonStr,saveTime);
		}
	}
	
}
