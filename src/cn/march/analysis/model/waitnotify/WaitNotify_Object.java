	


	package cn.march.analysis.model.waitnotify;
	
	
	
	import java.util.concurrent.atomic.AtomicInteger;
	
	
	/**
	 * 使用Object中wait,notify方法控制线程A,B分别输出：
	 * 
	 * 		A --> 1 2 3
	 * 		B --> 4 5 6
	 * 		C --> 7 8 9
	 * 
	 * 基本思路：
	 * 		定义一个实例变量val(AtomicInteger)本身就是线程安全,不需要加锁处理
	 * 		线程A：
	 * 			开始输出1 2 3(val初始为1,每输出一次val increase+1)
	 * 			调用obj.notify()唤醒线程B
	 * 			若val值小于6则调用obj.wait()让线程A等待
	 *			之后val值小于9则直接输出val(7 8 9)
	 *	
	 *		线程B：
	 *			若val小于3,则调用obj.wait()让线程B等待A输出
	 *			之后val若小于6,则输出val(4 5 6)
	 *			输出完之后,唤醒线程A
	 * 
	 * @author antsmarth
	 *
	 */
	public class WaitNotify_Object {
	
		//Int原子操作对象
		private AtomicInteger val = new AtomicInteger(1);
	
		//输出val的值,并自增
		private void printValAndIncrease() {
	
			System.out.println(Thread.currentThread().getName() + ", val Value: "
					+ val);
			val.incrementAndGet();
	
		}
	
		//测试方法
		public static void main(String[] args) {
	
			WaitNotify_Object obj = new WaitNotify_Object();
	
			//线程A
			Runnable printA = () -> {
	
				while (obj.val.get() <= 3)
					obj.printValAndIncrease();
	
				synchronized (obj) {
	
					System.out.println("printA printed 1,2,3; notify printB");
					obj.notify();
	
				}
	
				try {
	
					while (obj.val.get() <= 6) {
	
						synchronized (obj) {
					
							System.out.println("wait in printA");
							obj.wait();
						
						}
	
					}
	
					System.out.println("wait end printA");
	
				} catch (InterruptedException ex) {
	
					ex.printStackTrace();
	
				}
	
				while (obj.val.get() <= 9)
					obj.printValAndIncrease();
	
				System.out.println("printA exits...");
	
			};
	
			//线程B
			Runnable printB = () -> {
	
				while (obj.val.get() < 3) {
	
					synchronized (obj) {
	
						try {
	
							System.out
									.println("printB wait for printA printed 1,2,3");
							obj.wait();
							System.out
									.println("printB waited for printA printed 1,2,3");
	
						} catch (InterruptedException ex) {
	
							ex.printStackTrace();
	
						}
					}
	
				}
	
				while (obj.val.get() <= 6)
					obj.printValAndIncrease();
	
				synchronized (obj) {
	
					System.out.println("notify in printB");
					obj.notify();
	
				}
	
				System.out.println("notify end printB");
				System.out.println("printB exits...");
	
			};
	
			Thread threadA = new Thread(printA);
			threadA.setName("printA");
	
			Thread threadB = new Thread(printB);
			threadB.setName("printB");
	
			threadA.start();
			threadB.start();
		}
	
	}
