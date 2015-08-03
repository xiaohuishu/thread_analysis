	



	package cn.march.analysis.juc.lock;
	
	
	
	import java.util.concurrent.locks.AbstractQueuedSynchronizer;
	
	
	//锁机制测试类
	public class simpleQueuedSynchronizer {
	
		//测试实例变量i++操作
		public int i = 0;
	
		//线程中断标志位
		private static boolean isFlag = false;
	
		/**
		 * 使用自定义的Sync锁类来进行加锁,解锁操作;
		 */
		void increaseI() {
	
			sync.lock();
	
			try {
				
				i++;
			
			} finally {
			
				sync.release(1);
			
			}
	
		}
	
		//测试方法
		public static void main(String[] args) {
	
			simpleQueuedSynchronizer simSync = new simpleQueuedSynchronizer();
	
			for (int j = 0; j < 10; j++) {
	
				new Thread(() -> {
	
					while (!isFlag) {
	
						if (simSync.i >= 10000)
							isFlag = true;
	
						System.out.println(Thread.currentThread().getName()
								+ ", simSync i value : " + simSync.i);
	
						try {
	
							Thread.sleep(3000);
	
						} catch (Exception e) {
	
							e.printStackTrace();
	
						}
	
						simSync.increaseI();
	
					}
	
				}).start();
	
			}
	
		}
	
		//锁对象
		private final Sync sync;
	
		//构造方法
		public simpleQueuedSynchronizer() {
	
			//实例化非公平锁;
			sync = new NonfairSync();
	
		}
	
		//解锁操作
		public void unlocked() {
			sync.release(1);
		}
	
		//定义一个Sync抽象类继承AbstractQueuedSynchronizer类,实现一些操作方法
		static abstract class Sync extends AbstractQueuedSynchronizer {
	
			
			private static final long serialVersionUID = 1L;
	
			
			//加锁抽象方法
			abstract void lock();
	
			//非公平锁拿到线程状态
			final boolean nonfairTryAcquire(int acquires) {
	
				final Thread current = Thread.currentThread();
	
				int c = getState();
	
				System.out.println(current.getName() + " , state : " + c);
	
				if (c == 0) {
					if (compareAndSetState(0, acquires)) {
						setExclusiveOwnerThread(current);
						return true;
					}
				} else if (current == getExclusiveOwnerThread()) {
					int nextc = c + acquires;
					if (nextc < 0) // overflow
						throw new Error("Maximum lock count exceeded");
					setState(nextc);
					return true;
				}
				return false;
			}
	
			//对线程进行释放操作
			protected final boolean tryRelease(int releases) {
				
				int c = getState() - releases;
				if (Thread.currentThread() != getExclusiveOwnerThread())
					throw new IllegalMonitorStateException();
				boolean free = false;
				if (c == 0) {
					free = true;
					setExclusiveOwnerThread(null);
				}
				setState(c);
				return free;
			
			}
	
			protected final boolean isHeldExclusively() {
				// While we must in general read state before owner,
				// we don't need to do so to check if current thread is owner
				return getExclusiveOwnerThread() == Thread.currentThread();
			}
	
			final ConditionObject newCondition() {
				return new ConditionObject();
			}
	
			// Methods relayed from outer class
	
			final Thread getOwner() {
				return getState() == 0 ? null : getExclusiveOwnerThread();
			}
	
			final int getHoldCount() {
				return isHeldExclusively() ? getState() : 0;
			}
	
			final boolean isLocked() {
				return getState() != 0;
			}
	
		}
	
		//具体锁的实现类,非公平锁
		static final class NonfairSync extends Sync {
			
			private static final long serialVersionUID = 7316153563782823691L;
	
			/**
			 * 加锁操作：
			 * 		采用乐观锁:
			 * 		利用CAS操作
			 */
			final void lock() {
				
				if (compareAndSetState(0, 1))
					setExclusiveOwnerThread(Thread.currentThread());
				else
					acquire(1);
			
			}
	
			protected final boolean tryAcquire(int acquires) {
				return nonfairTryAcquire(acquires);
			}
		}
	
	}
