package com.tiptimes.tp.controller;

import android.os.Bundle;

import com.tiptimes.R;
import com.tiptimes.tp.annotation.Action;
import com.tiptimes.tp.annotation.SignalFilter;
import com.tiptimes.tp.common.ActionBundle;
import com.tiptimes.tp.common.ActionDeal;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.widget.ActionButton;

/**
 * 例子
 * @author haoli
 */
public class example1Controller extends Controller_Activity {
	private ActionButton IB_bt;

	
	@Action(url="http://172.16.1.2:5000/n/wsn/ControlOther.asp?mote=2&led=1&status=1",actionListener="IB_bt")
	private ActionDeal<String> ac = new ActionDeal<String>() {
		
		@Override
		public void handleResult(ActionBundle<String> acitonBundle) {
			// TODO Auto-generated method stub
			if(acitonBundle.isNomal){
                //success do something
                String mdata = acitonBundle.data;
            }else{
                //faile
                showCentenrToast(acitonBundle.msg);
            }
		}
		
		@Override
		public void doAction() {
			// TODO Auto-generated method stub
			actionPerformed(this, null);
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		dynBind();//后面不要加其他东西!
	}
	@Override
	public void initView() {
        //控件初始化入口
	}
	
	
	@Override
	public void initData() {
        //数据初始化入口
	}

    /**
     * 处理传过来的信号
     * @param signal
     * @return
     */
    @SignalFilter(signalRex = "xxx")
	@Override
	public boolean handleSignal(Signal signal) {
		// TODO Auto-generated method stub
		return true;
	}
}
