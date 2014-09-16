package com.tiptimes.tp.util;

public class HttpRespondInfo {
    private  String info;
    private boolean isNormal;
	public HttpRespondInfo(String info, boolean isNormal){
		this.info = info;
		this.isNormal = isNormal;
	}
	public String getInfo() {
		return info;
	}
	public boolean isNormal() {
		return isNormal;
	}
	
	
}
