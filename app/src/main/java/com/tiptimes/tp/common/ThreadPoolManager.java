package com.tiptimes.tp.common;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tiptimes.tp.constant.Constants;
import com.tiptimes.tp.controller.Controller;
import com.tiptimes.tp.util.L;
/**
 * 线程池 的管理类
 * 
 * @author tiptimes haoli
 *
 */
public class ThreadPoolManager {
	private static ThreadPoolManager threaPoolManager;
	private static ExecutorService executorService; //执行网络请求的服务类
	private static List<Runnable> actionTask = new LinkedList<Runnable>(); //任务队列
	
	private ThreadPoolManager(){
		executorService = Executors.newCachedThreadPool();
		
	}
	public static synchronized ThreadPoolManager getInstance(){
		if(threaPoolManager==null){
			threaPoolManager = new ThreadPoolManager();
		}
		return threaPoolManager;
	}

    /**
     * 网络请求动作
     * @param acitonInfo 请求信息封装对象
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execActions(ActionInfo acitonInfo){
		ActionService actionService = new ActionService(acitonInfo);
		//如果线程队列里面已在执行当前action则拒绝执行
		if(hasTask(acitonInfo)){
			 ActionBundle ab = new ActionBundle();
			 ab.isNomal =false;
			 ab.msg = Constants.MSG_ONTASK;
		}else{
			addTask(actionService);
			executorService.execute(actionService);	
		}
		L.d(L.TAG,"当前Action任务数量:"+actionTask.size()+"个");
	}

    public static void execRunable(Runnable run){
           executorService.execute(run);
    }

    /**
     * 图片下载任务
     * @param imageLoadInfo
     */
	public void execImageDownload(ImageLoadInfo imageLoadInfo){
		executorService.execute(new ImageLoadService(imageLoadInfo));
	}


	public static synchronized boolean hasTask(ActionInfo action){
		return actionTask.contains(action);
	}
	
	public static synchronized void removeTask(Runnable runnable){
		actionTask.remove(runnable);
	}
	
	public static synchronized void addTask(ActionService action){
		actionTask.add(action);
	}
	
	public  void controllerDestory(Controller controller){
		int controllerID= controller.hashCode();
		for(Runnable task:actionTask){
            if(task instanceof ControllerObserver){
                ControllerObserver observer = (ControllerObserver)task;
                if(observer.controllerID()==controllerID){
                    observer.controllerDestroy();
                }
            }

		}
	}
}
