package com.tiptimes.tp.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;

import com.tiptimes.tp.constant.Constants;

/**
 * 应用程序的实例类
 * @author tiptimes LH
 * 
 */
public class Application extends android.app.Application {
	private static Application application; //单例
	public static String tempPath; //二级缓冲路径

	private static Map<String, Object> iChache; //一级缓冲路径

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		application = this;
		
		iChache = new HashMap<String, Object>(); //初始化一级缓冲
		
		File cacheLocation;  //缓冲文件夹
		File tempLocation;  //临时文件夹


		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
            //存在外部存储介质
			cacheLocation = new File(Environment.getExternalStorageDirectory()
					+ Constants.IMAGECACHE_DIR);
			tempPath = Environment.getExternalStorageDirectory()
					+ Constants.FILE_TEMP;
			tempLocation = new File(tempPath);

		} else {
            //不存在外部存在介质
			cacheLocation = new File(getFilesDir() + Constants.IMAGECACHE_DIR);
			tempPath = getFilesDir() + Constants.FILE_TEMP;
			tempLocation = new File(tempPath);
		}


		if (!cacheLocation.exists()) {
            //创建缓冲文件夹
			cacheLocation.mkdirs();
		}

		if (!tempLocation.exists()) {
            //创建临时文件夹
			tempLocation.mkdirs();
		}


	}


	public static Application getApplication() {
		return application;
	}

    /**
     * 清空缓冲一级缓冲
     */
	public void clearIChache(){
		iChache.clear();
	}
	

	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
        iChache.clear();
	}
}
