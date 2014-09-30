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
	public String target;      //信号目标标识
    public String action;      //动作
	public boolean booleanValue;
	public int intValue;
	public Object objectValue;

	public static class Bulider {
		private String target;
        private String action;
		private boolean booleanValue;
		private int intValue;
		private Object objectValue;

		public Bulider setTarget(String target) {
			this.target = target;
			return this;
		}

		public Bulider setBooleanValue(boolean booleanValue) {
			this.booleanValue = booleanValue;
			return this;
		}

        public  Bulider setAction(String action){
            this.action = action;
            return  this;
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
			signal.target = target;
            signal.action = action;
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
