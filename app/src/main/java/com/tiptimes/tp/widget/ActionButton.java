package com.tiptimes.tp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.tiptimes.tp.common.ActionDeal;
/**
 *界面元素和控制器绑定事件的机制
 *可对所有android控件进行扩展
 * @author tiptimes
 *
 */
public class ActionButton extends Button implements ActionBinder {

	public ActionButton(Context context){
		super(context);
	}
	public ActionButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public ActionButton(Context arg0, AttributeSet arg1, int arg3){
		super(arg0, arg1,arg3);
	}
	@Override
	@SuppressWarnings("rawtypes")
	public void bindActionMethod( final ActionDeal actionDeal) {
		// TODO Auto-generated method stub
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				actionDeal.doAction();
			}
		});
	}
	
	
}
