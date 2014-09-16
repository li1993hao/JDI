package com.tiptimes.tp.common;


/**
 * 控制器状态观察者
 * @author haoli
 */

public interface ControllerObserver {
	 void controllerDestroy();
	 int controllerID();
}
