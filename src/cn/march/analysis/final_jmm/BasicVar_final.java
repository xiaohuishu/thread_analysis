package cn.march.analysis.final_jmm;

public class BasicVar_final {

	final int intVar_final;

	static BasicVar_final instance;

	// public volatile boolean isFlag = false;

	int intVar = 1;

	public BasicVar_final() {

		System.out.println("constructer....");

		intVar = 2;

		intVar_final = 2;

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

		new Thread(() -> {

			instance = new BasicVar_final();

		}).start();

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
