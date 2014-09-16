package com.tiptimes.tp.common;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tiptimes.R;
import com.tiptimes.tp.annotation.Action;
import com.tiptimes.tp.controller.Controller;
import com.tiptimes.tp.util.L;
import com.tiptimes.tp.widget.ActionBinder;

/**
 * 动态绑定组件
 *
 * @author haoli
 *
 */
@SuppressWarnings("rawtypes")
public class DynamicCode {	
	private static Map<String, Integer>viewIdMap;//R文件中id和id名称的映射

	static{
        /**
         * 初始化id名称映射
         */
		viewIdMap = new HashMap<String, Integer>();
		Field[] idFields = R.id.class.getDeclaredFields();
		for (int i = 0; i < idFields.length; i++) {
			Field idField = idFields[i];
			try {
				viewIdMap.put(idField.getName(), idField.getInt(R.id.class));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}




	private HashMap<ActionDeal, Field> actionDealMap = new HashMap<ActionDeal, Field>();

    private Controller mController;
	
	
	public DynamicCode(Controller controller){
		mController = controller;
	}
	/**
	 * 界面控件的绑定
	 * action事件的绑定
	 */
	public void bind(){
		Field[] fields = mController.getClass().getDeclaredFields(); //获得controller所以字段

        ArrayList<Field> IBFields = new ArrayList<Field>(); //控件句柄集合
		ArrayList<Field> ActionDealFields = new ArrayList<Field>();//actionDeal字段集合

		for (int i = 0; i < fields.length; i++) {
			if(ActionDeal.class.isAssignableFrom(fields[i].getType())){
                //字段是ActionDeal类型
				try {
					fields[i].setAccessible(true);
					actionDealMap.put((ActionDeal)fields[i].get(mController), fields[i]);
				} catch (Exception e){
					//不会出现此情况
				}

                ActionDealFields.add(fields[i]);
			}
			String filedName = fields[i].getName();
			if (filedName.startsWith("IB_")) {
                //是控件句柄
				IBFields.add(fields[i]);
			}
		}
		String[] packageStrs = mController.getClass().getPackage().getName()
				.split("\\.");
		String moduleName = packageStrs[packageStrs.length - 1]; //模块名称

		for (int i = 0; i < IBFields.size(); i++) {
			Field field = IBFields.get(i);
			String idName = moduleName+"_"+ field.getName().replace("IB_", "");//控件id

			if(viewIdMap.containsKey(idName)){
                //符合命名规则
				try {
					field.setAccessible(true);
					field.set(mController,
							mController.findViewById(viewIdMap.get(idName)));//为句柄赋值
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
                    L.e(L.TAG,field.getName()+"绑定控件失败!,请确定绑定双方数据类型是否一致！");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				L.e(L.TAG, "绑定控件不存在，请确定是否命名规则有误！");
			}
		}
		bindAction(ActionDealFields);
	}

    /**
     * 为控件绑定actionDeal
     * 控件必须实现ActionBinder接口
     * @param fields
     */
	 private void bindAction(ArrayList<Field> fields){
		for(int i=0; i<fields.size(); i++){
			if(fields.get(i).isAnnotationPresent(Action.class)){
				Action aa = fields.get(i).getAnnotation(Action.class);
				String actionListenerStr  = aa.actionListener();
				if(!actionListenerStr.equals("")){
                    //有要绑定的控件
					try {
						 Field field = mController.getClass().getDeclaredField(actionListenerStr);
						 field.setAccessible(true);
						 Object o = field.get(mController);
						 if(o instanceof ActionBinder){
							ActionBinder actionListener = (ActionBinder)o;
			
							fields.get(i).setAccessible(true);
							ActionDeal actionDeal = (ActionDeal)fields.get(i).get(mController);
							actionListener.bindActionMethod(actionDeal);
						 }else{
							L.e(L.TAG, "绑定控件必须继承ActionBinder!");
						 }
					} catch (NoSuchFieldException e) {
						L.e(L.TAG,fields.get(i).getName()+ "绑定控件不存在，请确定声明了此变量!");
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
                        e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
                        e.printStackTrace();
					}
				}
				
			}
			
			
		}
	
	}

	public HashMap<ActionDeal, Field> getActionDealMap() {
		return actionDealMap;
	}

    /**
     * 网络请求行为的连接构件
     * @param actionDeal
     * @param params
     */
	public void actionPerformed(ActionDeal actionDeal, ParameterMap params){
			Field field = actionDealMap.get(actionDeal); //当前actionDeal的field

            if(field == null){
                L.e(L.TAG,"actionDeal参数必须是当前controller的成员变量!");
                return;
            }

			ActionInfo actionInfo = new ActionInfo();  //封装请求信息
			Type type = field.getGenericType() ;  //获得请求数据类型
			Class clz = null;

			if(type instanceof ParameterizedType){
				ParameterizedType pt =  (ParameterizedType)type; //取得范型
				Type p = pt.getActualTypeArguments()[0]; //获取范型变量类型
				String pStr = p.toString(); //范型变量类型的字符串表示

                //是否是线性集合类型
				if(pStr.matches("^java\\.util\\..*List<.+>.*")){
					actionInfo.seIsList(true);
					String dataClassName = pStr.replaceAll(".*<|>.*", ""); //获取集合元素类型
                    try{
                        clz = Class.forName(dataClassName); //得到元素类型的类信息
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
				}else{
					actionInfo.seIsList(false);
					if(p instanceof Class){
						clz = (Class)p;
					}else{
						L.e(L.TAG, field.getName()+"范型非法!");
						return;
					}
				}
			}else{
				L.e(L.TAG, field.getName()+"必须是范型字段!");
				return;
			}

            if(!field.isAnnotationPresent(Action.class)){
                L.e(L.TAG,"actionDeal参数必须有action注解!");
            }else{
                Action aa = field.getAnnotation(Action.class);
                actionInfo.setUrl(aa.url());
                actionInfo.setControllerID(mController.hashCode());
                actionInfo.setParams(params);
                actionInfo.setDataClass(clz);
                actionInfo.actionDeal = actionDeal;
                ThreadPoolManager.getInstance().execActions(actionInfo);
            }
	}
	 
	 
}
