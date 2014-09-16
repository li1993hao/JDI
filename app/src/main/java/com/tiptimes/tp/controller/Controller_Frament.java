package com.tiptimes.tp.controller;

import android.os.Bundle;
import android.view.View;

import android.app.Fragment;
import com.tiptimes.tp.common.ActionDeal;
import com.tiptimes.tp.common.DynamicCode;
import com.tiptimes.tp.common.ParameterMap;
import com.tiptimes.tp.common.SignalManager;
import com.tiptimes.tp.common.ThreadPoolManager;

/**
 * 为frament赋予控制器行为
 */
@SuppressWarnings("rawtypes")
public abstract class Controller_Frament extends Fragment implements Controller{
	private DynamicCode dynamicCode = new DynamicCode(this);
	@Override
	public View findViewById(int id) {
		// TODO Auto-generated method stub
		if(getView() != null){
			return getView().findViewById(id);
		}
		return null;
	}



	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ThreadPoolManager.getInstance().controllerDestory(this);
		SignalManager.removeSignalListener(this);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SignalManager.addSignalListener(this);
	}

	@Override
	public void dynBind() {
		// TODO Auto-generated method stub
		dynamicCode.bind();
		initView();
		initData();
	}


	@Override
	public void actionPerformed( ActionDeal actionDeal, ParameterMap params) {
		// TODO Auto-generated method stub
		dynamicCode.actionPerformed(actionDeal, params);
	}

}
