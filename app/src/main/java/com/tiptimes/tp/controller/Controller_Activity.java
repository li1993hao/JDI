package com.tiptimes.tp.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tiptimes.tp.common.ActionDeal;
import com.tiptimes.tp.common.DynamicCode;
import com.tiptimes.tp.common.ParameterMap;
import com.tiptimes.tp.common.SignalManager;
import com.tiptimes.tp.common.ThreadPoolManager;

/**
 * activity基类
 * 
 * @author tiptimes LH
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class Controller_Activity extends Activity implements Controller {
    /**
     * 实现动态邦迪和action请求的工具类
     */
	private DynamicCode dynamicCode = new DynamicCode(this);


    /**
     * 在屏幕中间显示提示信息
     * @param msg  要显示的字符串
     */
	public void showCentenrToast(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

    /**
     * 在屏幕下方显示提示信息
     * @param msg 要显示的字符串
     */
	public void showBottomToast(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	}

    /**
     * 显示带文字的等待对话框
     * @param msg 要显示的字符串
     */
	protected void showWaitDialog(String msg) {

	}

    /**
     * 显示默认的对话框
     */
	protected void showDefaultWaitDialog() {
		
	}

    /**
     * 隐藏等待对话框
     */
	protected void hideWaitDialog() {
	}

    /**
     * 执行动态绑定
     */
	public void dynBind() {
		dynamicCode.bind();
		initView();
		initData();
	}


    /**
     * 进行控制器销毁前的清理工作
     */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		ThreadPoolManager.getInstance().controllerDestory(this); //通知线程池控件被销毁
		SignalManager.removeSignalListener(this);    //把自己从信号监听者中移除
        super.onDestroy();
    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SignalManager.addSignalListener(this); //成为信号监听者
	}


	@Override
	public void actionPerformed(ActionDeal actionDeal, ParameterMap params) {
		// TODO Auto-generated method stub
		dynamicCode.actionPerformed(actionDeal, params);
	}
	
	/**
	 * 默认接受act方法，如需扩展，在子类中覆盖此方法
	 */
	protected void back() {
		this.finish();
	}

    /**
     * 覆盖系统返回按键
     * @param keyCode
     * @param event
     * @return
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
