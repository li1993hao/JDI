package com.tiptimes.tp.util;

import java.util.Date;

public class PerAnalysisTool {
	private long time;
	private String tag;
	public PerAnalysisTool(String tag){
		this.tag = tag;
	}
	public void start(){
		time = new Date().getTime();
	}
	public void stop(){
		long etime = new Date().getTime() -time;
		L.d(L.TAG, tag+"执行时间:"+etime+"毫秒("+etime/1000.0f+"秒)");
	}
}
