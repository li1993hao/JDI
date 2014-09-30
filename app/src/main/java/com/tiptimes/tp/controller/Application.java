package com.tiptimes.tp.controller;

/**
 * 应用程序的实例类
 * @author tiptimes LH
 * 
 */
public class Application extends android.app.Application {
	private static Application application; //单例


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		application = this;
	}

	public static Application getApplication() {
		return application;
	}


}
