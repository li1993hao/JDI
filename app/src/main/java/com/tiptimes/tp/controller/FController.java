package com.tiptimes.tp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.app.Fragment;
import android.view.ViewGroup;

import com.tiptimes.tp.common.ActionDeal;
import com.tiptimes.tp.common.DynamicCode;
import com.tiptimes.tp.common.ParameterMap;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.common.SignalManager;
import com.tiptimes.tp.common.ThreadPoolManager;

/**
 * 为frament赋予控制器行为
 */
@SuppressWarnings("rawtypes")
public abstract class FController extends Fragment implements Controller{
	private DynamicCode dynamicCode;
    private View mview;
	@Override
	public View findViewById(int id) {
		// TODO Auto-generated method stub
	    return mview.findViewById(id);

	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mview = inflater.inflate(dynamicCode.getLayoutId(),null);
        dynamicCode.bind();
        initView(savedInstanceState);
        initData();
        return mview;
    }


    public String getModuleName(){
        return  dynamicCode.getModuleName();
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
        dynamicCode = new DynamicCode(this);
        SignalManager.addSignalListener(this);


    }


	@Override
	public void actionPerformed( ActionDeal actionDeal, ParameterMap params) {
		// TODO Auto-generated method stub
		dynamicCode.actionPerformed(actionDeal, params);
	}


    public void sendSignal(String target, String action) {
        SignalManager.sendSignal(new Signal.Bulider().setTarget(target).setAction(action).Build());
    }

    public void sendSignal(String target, boolean booelanValue) {
        SignalManager.sendSignal(new Signal.Bulider().setTarget(target).setBooleanValue(booelanValue).Build());
    }

    public void sendSignal(String target, Object object) {
        SignalManager.sendSignal(new Signal.Bulider().setTarget(target).setObjectValue(object).Build());
    }
}
