package com.tiptimes.tp.widget;

import com.tiptimes.tp.common.ActionDeal;
@SuppressWarnings("rawtypes") 
public interface ActionBinder {
    /**
     * 绑定方法
     * @param actionDeal
     */
	public void bindActionMethod(ActionDeal actionDeal);
}
