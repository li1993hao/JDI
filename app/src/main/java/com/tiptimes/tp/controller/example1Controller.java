package com.tiptimes.tp.controller;

import com.tiptimes.tp.annotation.*;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.widget.ActionButton;

/**
 * 例子
 * @author haoli
 */
public class example1Controller extends AController {
	private ActionButton IB_bt;

	@Override
	public void initView() {

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
    @S(name = "c1")
	@Override
	public boolean handleSignal(Signal signal) {
		// TODO Auto-generated method stub
        showCentenrToast(signal.action);
		return true;
	}
}
