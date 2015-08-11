


	package cn.march.analysis.final_jmm;
	
	
	
	/**
	 * 使用final声明基本数据类型(int)intVar_final
	 * 对其进行多线程操作是否是线程安全;
	 * @author antsmarth
	 *
	 */
	public class BasicVar_final {
	
		//操作数
		final int intVar_final ;
	
		//对象引用
		static BasicVar_final instance;
	
		// public volatile boolean isFlag = false;
	
		int intVar = 1;
	
		public BasicVar_final() {
			
			System.out.println("constructer....");
	
			intVar_final = 2;
			intVar = 2;
	
	
		}
	
		/*
		 * public static void writerIntVar() {
		 * 
		 * System.out.println("writeIntVar...");
		 * 
		 * instance = new BasicVar_final();
		 * 
		 * }
		 * 
		 * public static void readIntVar() {
		 * 
		 * BasicVar_final obj = instance;
		 * 
		 * System.out.println(Thread.currentThread().getName() +
		 * ", intVar_final value: " + obj.intVar_final);
		 * 
		 * System.out.println("-------------------");
		 * 
		 * System.out.println(Thread.currentThread().getName() + ", intVar value: "
		 * + obj.intVar);
		 * 
		 * System.out.println();
		 * 
		 * }
		 */
		public static void main(String[] args) throws InterruptedException {
					
			//int i = 0;
	
			//线程A
			new Thread(() -> {
	
				instance = new BasicVar_final();
	
			}).start();
	
			//线程B
			new Thread(() -> {
	
				BasicVar_final obj = instance;
	
				System.out.println(Thread.currentThread().getName()
						+ ", intVar_final value: " + obj.intVar_final);
	
				System.out.println("-------------------");
	
				System.out.println(Thread.currentThread().getName()
						+ ", intVar value: " + obj.intVar);
	
				System.out.println();
	
			}).start();
	
		}
	
	}
