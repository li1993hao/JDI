package com.tiptimes.tp.common;

import com.tiptimes.tp.constant.Constants;
import com.tiptimes.tp.util.HttpRespondInfo;
import com.tiptimes.tp.util.HttpUtil;
import com.tiptimes.tp.util.L;

/**
 * action请求的执行类
 * 执行类在执行期间关联其宿主控制器
 * 宿主控制器的状态变化会影响执行过程和结果
 * 这里默认当宿主控制器destroy时
 * 执行结果丢弃
 *
 * 还有中应用场景是当宿主控制器处在不可见状态时
 * 执行结果丢弃，这里没有采用这种处理方式。
 * @author tiptimes haoli
 *
 */
public class ActionService implements Runnable ,ControllerObserver {
	private ActionInfo actionInfo; //请求信息对象
	private volatile int controllerStatus = Constants.CONTROLLER_STATUS_HOLDER; //控制器状态
   
   
   	public ActionService(ActionInfo acitonInfo){
   		actionInfo = acitonInfo;
   	}
   	


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {

		HttpRespondInfo res = null;
        if(actionInfo.getMethod() == ActionInfo.QequestMethod.POST){
            res = HttpUtil.postRequest(actionInfo.getUrl(), actionInfo.getParams());
        }else{
            res = HttpUtil.getRequest(actionInfo.getUrl(), actionInfo.getParams());
        }

		final ActionBundle actionBundle = new ActionBundle();//请求结果封装对象
		actionBundle.msg = Constants.ERROR_UNKNOW;
		if(res.isNormal()){
            //正常返回，网络连接成功，并且请求数据成功
			if(actionInfo.getDataClass().equals(String.class)){
                //请求数据类型为string,不对服务器返回数据做任何解析，直接返回
				actionBundle.isNomal = true;
				actionBundle.data = res.getInfo();
			}else{
				try {
                    //采用默认解析器
                    if(actionInfo.getDataParser() == null){
                        JDIDataParser jdiDataParser = new JDIDataParser();
                        jdiDataParser.parse(res.getInfo(),actionBundle,actionInfo);
                    }else{
                        //用特定解析器解析
                        actionInfo.getDataParser().parse(res.getInfo(),actionBundle,actionInfo);
                    }
				} catch (Exception e) {
                    //数据解析出错
					actionBundle.isNomal = false;
					actionBundle.msg = Constants.ERROR_DATA;
				}
			}
		}else{
            //客户端出错
			actionBundle.isNomal = false;
			actionBundle.msg = res.getInfo();
		}
		
		if(controllerStatus == Constants.CONTROLLER_STATUS_HOLDER){
            //宿主控制器没有被destroy
			actionInfo.getHandler().post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					actionInfo.actionDeal.handleResult(actionBundle);
				}
			});
		}else{
			L.d(L.TAG, actionInfo.getUrl()+":宿主控制器destroy!");
		}
		ThreadPoolManager.removeTask(this);//从当前任务队列中移除
	}

    /**
     * 宿主控制
     */
	@Override
	public void controllerDestroy() {
		// TODO Auto-generated method stub
		controllerStatus = Constants.CONTROLLER_STATUS_DESTROY;
	}

	@Override
	public int controllerID() {
		// TODO Auto-generated method stub
		return actionInfo.getControllerID();
	}
}
