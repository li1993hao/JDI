package com.tiptimes.tp.common;

import com.alibaba.fastjson.JSON;
import com.tiptimes.tp.util.ACache;

import java.io.File;
import java.io.Serializable;

import haihemoive.Application;

/**
 * 轻量级的数据缓冲管理类
 * 可以用来存储用户配置信息和一些轻量级的数据
 * @author haoli
 */
public class Preference{
	final static String PREFERENCE_DIR = "preference"; //配置目录
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

    public static synchronized<T> T get(Class<T> cls){
        T object = null;
        if(cls.isAssignableFrom(Serializable.class)){
            //序列号存储配置信息
            object =  (T)aCache.getAsObject(cls.getName().hashCode()+"");
        }else{
            String jsonStr = aCache.getAsString(cls.getName());
            object = JSON.parseObject(jsonStr,cls);
        }
        return object;

    }

    public static synchronized<T> T set(T object){
        Class cls = object.getClass();
        if(cls.isAssignableFrom(Serializable.class)){
            aCache.put(cls.getName().hashCode()+"",(Serializable)object);
        }else{
            aCache.put(cls.getName().hashCode()+"",JSON.toJSONString(object));
        }
        return object;
    }


    public static synchronized<T> T set(T object, int saveTime){
        Class cls = object.getClass();
        if(cls.isAssignableFrom(Serializable.class)){
            aCache.put(cls.getName().hashCode()+"",(Serializable)object,saveTime);
        }else{
            aCache.put(cls.getName().hashCode()+"",JSON.toJSONString(object),saveTime);
        }
        return object;
    }

}
