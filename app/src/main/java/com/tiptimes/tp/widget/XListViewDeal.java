package com.tiptimes.tp.widget;

import com.tiptimes.tp.common.ActionDeal;

public abstract class XListViewDeal<T>  implements ActionDeal<T>{
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void onRefresh();

	public abstract void onLoadMore();

}
