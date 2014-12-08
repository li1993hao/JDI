package com.tiptimes.tp.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 网络请求参数封装信息
 */
public class ParameterMap extends HashMap<String, String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterMap(String... keyAndValue){
		for(int i=0; i<keyAndValue.length; i+=2){
			super.put(keyAndValue[i], keyAndValue[i+1]);
		}
	}

    public  ParameterMap(Map<String, String> params){
        this.putAll(params);
    }

	public ParameterMap(){
		super();
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		if(object instanceof ParameterMap){
			ParameterMap other = (ParameterMap)object;
			Set<String> keySet = this.keySet();
			for(String key:keySet){
				if(other.containsKey(key)){
					if(!other.get(key).equals(this.get(key))){
						return false;
					}
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
		
		return true;
	}

}
