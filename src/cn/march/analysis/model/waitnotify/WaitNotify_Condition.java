	


	package cn.march.analysis.model.waitnotify;
	
	
	
	import java.util.concurrent.locks.Condition;
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;
	
	
	/**
	 * 使用Lock中Condition对象控制线程A,B分别输出：
	 * 
	 * 		A --> 1 2 3
	 * 		B --> 4 5 6
	 * 		C --> 7 8 9
	 * 
	 * Condition对象的作用与Object -> wait(),notify()方法的作用相类似
	 * 
	 * 但是一个Lock对象可以创建多个Condition对(lock.newCondition());
	 * 
	 * 这样有一个好处就是：
	 * 
	 * 		Object -> notify() 唤醒的线程对象是随机的,是不可控的,如果唤醒的对象不是现在程序所需要的则对象会继续睡眠
	 * 		Condition -> singal() 则是可控的,自己可以控制唤醒的具体是哪个对象,是不是自己所需要的;
	 * 
	 * 在这个例子中,我们主要实现对value的操作,由于用到lock所以直接将value定义成int
	 * 
	 * 基本思路：
	 * 
	 * 		创建一个Lock对象：
	 * 			Lock lock = new ReetrantLock();
	 * 		创建2个Condition分别对应线程A,B:
	 * 			Condition aCondition = lock.newCondition();
	 * 			Condition bCondition = lock.newCondition();	
	 * 		线程A：
	 * 			输出 1 2 3
	 * 			唤醒线程B -> bCondition.singal();
	 * 			等待线程B执行 -> aCondtion.await();
	 * 			输出 7 8 9
	 * 		
	 * 		线程B：
	 * 			判断value值是否小于3,若小于则直接等待线程A执行 -> aCondition.await();
	 * 			输出 4 5 6
	 * 			唤醒线程A -> aCondition.singal(); 			
	 * 
	 * 
	 * @author antsmarth
	 *
	 */
	public class WaitNotify_Condition {
	
		//声明value变量类
		static class testInt {
	
			public int value = 1;
	
		}
	
		//测试方法
		public static void main(String[] args) {
	
			//锁对象
			final Lock lock = new ReentrantLock();
			//Condition对象
			final Condition aCondition = lock.newCondition();
			final Condition bCondition = lock.newCondition();
	
			final testInt num = new testInt();
	
			//线程A
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
	
			//线程B
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
