package com.tiptimes.tp.controller;

import android.os.Bundle;

import com.tiptimes.R;
import com.tiptimes.tp.annotation.S;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.widget.ActionButton;


public class example2Controller extends AController{
    private ActionButton IB_bt;
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
    @S(name = "c1")
    @Override
    public boolean handleSignal(Signal signal) {
        // TODO Auto-generated method stub
        showCentenrToast(signal.action);
        return true;
    }

}
