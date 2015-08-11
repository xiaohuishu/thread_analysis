	


	package cn.march.analysis.model.waitnotify;
	
	
	
	import java.util.concurrent.locks.Condition;
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;
	
	
	
	public class WaitNotify_Condition {
	
		static class testInt {
	
			public int value = 1;
	
		}
	
		public static void main(String[] args) {
	
			final Lock lock = new ReentrantLock();
			final Condition aCondition = lock.newCondition();
			final Condition bCondition = lock.newCondition();
	
			final testInt num = new testInt();
	
			Runnable printA = () -> {
	
				lock.lock();
	
				try {
	
					System.out.println("printA start write...");
	
					while (num.value <= 3) {
	
						System.out.println("printA print: " + num.value);
						num.value++;
	
					}
	
					bCondition.signal();
	
				} finally {
	
					lock.unlock();
	
				}
	
				try {
	
					lock.lock();
	
					aCondition.await();
	
					System.out.println("printA start write...");
	
					while (num.value <= 9) {
	
						System.out.println("printA print: " + num.value);
						num.value++;
	
					}
	
				} catch (InterruptedException ex) {
	
					ex.printStackTrace();
	
				} finally {
	
					lock.unlock();
	
				}
	
			};
	
			Runnable printB = () -> {
	
				try {
	
					lock.lock();
	
					while (num.value <= 3)
						bCondition.await();
	
				} catch (InterruptedException ex) {
	
					ex.printStackTrace();
	
				} finally {
	
					lock.unlock();
	
				}
	
				try {
	
					lock.lock();
					
					System.out.println("printB start write...");
					
					while (num.value <= 6) {
	
						System.out.println("printB print: " + num.value);
						num.value++;
					}
	
					aCondition.signal();
	
				} finally {
	
					lock.unlock();
	
				}
	
			};
			
			Thread thread_a = new Thread(printA);
			Thread thread_b = new Thread(printB);
			
			thread_a.start();
			thread_b.start();
			
		}
	
	}
