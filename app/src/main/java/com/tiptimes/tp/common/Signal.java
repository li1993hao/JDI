package com.tiptimes.tp.common;

/**
 * 信号
 * 
 * @author tiptimes haoli
 * 
 */
public class Signal {

	public static int MODE_SINGLE = 1;

	private int distributionCount; //信号分发数量
	public String signalFlag;      //信号标识
	public boolean booleanValue;
	public int intValue;
	public Object objectValue;

	public static class Bulider {
		private String signalFlag;
		private boolean booleanValue;
		private int intValue;
		private Object objectValue;

		public Bulider setSignalFlag(String signalFlag) {
			this.signalFlag = signalFlag;
			return this;
		}

		public Bulider setBooleanValue(boolean booleanValue) {
			this.booleanValue = booleanValue;
			return this;
		}

		public Bulider setIntValue(int intValue) {
			this.intValue = intValue;
			return this;
		}

		public Bulider setObjectValue(Object objectValue) {
			this.objectValue = objectValue;
			return this;
		}
		
		public Signal Build(){
			Signal signal= new Signal();
			signal.booleanValue = booleanValue;
			signal.intValue = intValue;
			signal.objectValue = objectValue;
			signal.signalFlag = signalFlag;
			return signal;
			
		}

	}

	public int getDistributionCount() {
		return distributionCount;
	}

	public void Distribution() {
		this.distributionCount++;
	}
}
