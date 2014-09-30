package com.tiptimes.tp.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 信号管理器
 * @author tiptimes  haoli
 *
 */
public class SignalManager {
	private static SignalManager msignalManager = new SignalManager();
	private List<Signal> signalList = new LinkedList<Signal>(); //信号集合
	private List<SignalListener> sdList = new ArrayList<SignalListener>(); //信号监听者集合
	private SignalManager(){
	}

    /**
     * 发送信号
     * @param signal
     */
	public static void sendSignal(Signal signal){
		msignalManager.mSendSignal(signal);
	}

    /**
     * 添加信号监听者
     * @param deal
     */
	public static void addSignalListener(SignalListener deal){
        msignalManager.mAddSignalListener(deal);
	}

    /**
     * 移除信号监听者
     * @param deal
     */
	public static void removeSignalListener(SignalListener deal){
        msignalManager.mRemoveSignalListener(deal);
	}


	public void mSendSignal(Signal signal) {
        boolean flag = false;
		for(int i=0; i<sdList.size(); i++){
            flag = sendToDeal(sdList.get(i), signal);
		}

        if(!flag){
            signalList.add(signal);
        }
	}

    /**
     * 信号移除
     * @param signal
     */
	private void removeSignal(Signal signal){
		signalList.remove(signal);
	}
	
	public void mAddSignalListener(SignalListener deal){
		sdList.add(deal);
		sendToDeal(deal);
	}
	
	public void mRemoveSignalListener(SignalListener deal){
		sdList.remove(deal);
	}




    /**
     * 当新的signal加入的时候调用此方法
     * @param deal
     * @param signal
     */
    private boolean sendToDeal(SignalListener deal, Signal signal){
        boolean result = false;
        try {
            Method handleMethod = deal.getClass().getMethod("handleSignal", Signal.class);//signal接受方法
            handleMethod.setAccessible(true);
            if(handleMethod.isAnnotationPresent(com.tiptimes.tp.annotation.S.class)){
                com.tiptimes.tp.annotation.S asignal=handleMethod.getAnnotation(com.tiptimes.tp.annotation.S.class);

                if(asignal.name() != null){
                    if(signal.target.equals(asignal.name())){
                        //匹配
                        result = (Boolean) handleMethod.invoke(deal, signal);
                        signal.Distribution(); //分发数量加1
                    }
                }
            }else{
                if(signal.target == null){//当过虑条件和信号标识别都为null时匹配
                    result = (Boolean) handleMethod.invoke(deal, signal);
                    signal.Distribution();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = true;
        }
        return  result;
    }

    /**
     * 新的监听者加入的时候调用此方法
     * 询问当前信号集合中有没有要发送给当前监听者的信号
     * @param deal
     */
	private void sendToDeal(SignalListener deal){
		List<Signal> removeSignals = new LinkedList<Signal>();//要移除的signal
		for(int i=0; i<signalList.size(); i++){
			Signal signal = signalList.get(i);
			try {
				Method handleMethod = deal.getClass().getMethod("handleSignal", Signal.class);//signal接受方法
				handleMethod.setAccessible(true);
				if(handleMethod.isAnnotationPresent(com.tiptimes.tp.annotation.S.class)){
					com.tiptimes.tp.annotation.S asignal=handleMethod.getAnnotation(com.tiptimes.tp.annotation.S.class);

					if(asignal.name() != null){
                        if(signal.target.equals(asignal.name())){
                            //匹配
                            Boolean result = (Boolean) handleMethod.invoke(deal, signal);
                            signal.Distribution(); //分发数量加1
                            if(result.booleanValue()){//是否要移除
                                removeSignals.add(signal);
                            }
                        }
					}
				}else{
					if(signal.target == null){//当过虑条件和信号标识别都为null时匹配
						Boolean result = (Boolean) handleMethod.invoke(deal, signal);
						signal.Distribution();
						if(result.booleanValue()){
							removeSignals.add(signal);
						}
					}
				}
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<removeSignals.size(); i++){
			removeSignal(removeSignals.get(i));
		}
	}
}
