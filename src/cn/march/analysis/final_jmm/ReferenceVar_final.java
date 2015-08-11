	



	package cn.march.analysis.final_jmm;
	
	
	
	/**
	 * 
	 * 使用final声明对象引用(int [])referVal_final
	 * 对其进行多线程操作是否是线程安全;
	 * @author antsmarth
	 *
	 */
	public class ReferenceVar_final {
	
		//操作对象
		final int[] referVar_final;
	
		static ReferenceVar_final instance;
	
		public ReferenceVar_final() {
	
			referVar_final = new int[1];
			referVar_final[0] = 1;
	
		}
	
		public static void writerInstance() {
	
			instance = new ReferenceVar_final();
	
		}
	
		public static void writerReferVar() {
	
			instance.referVar_final[0] = 2;
	
		}
	
		public static void reader() {
	
			if (instance != null)
				System.out.println(Thread.currentThread().getName()
						+ ", referVar_final values: " + instance.referVar_final[0]);
	
		}
	
		public static void main(String[] args) {
				
			int i = 0;
	
			while (i++ <= 100) {
				
				//线程A
				new Thread(() -> {
	
					ReferenceVar_final.writerInstance();
	
				}).start();
	
				//线程B
				new Thread(() -> {
	
					ReferenceVar_final.writerReferVar();
	
				}).start();
	
				//线程C
				new Thread(() -> {
	
					ReferenceVar_final.reader();
	
				}).start();
			}
		}
	
	}
