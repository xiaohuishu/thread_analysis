	



	package cn.march.analysis.juc.lock.java8.revise;
	
	
	
	import java.util.concurrent.locks.AbstractQueuedSynchronizer;
	
	public class SynchronizerRevise {
	
		private final Sync sync;
		
		public SynchronizerRevise() {
			
			sync = new NonfairSync();
			
		}
		
		public void unlock() {
			
			sync.tryRelease(1);
			
		}
		
		public void lock() {
			
			sync.lock();
			
		}
		
		static class NonfairSync extends Sync {
	
			private static final long serialVersionUID = 1L;
	
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
		
		static abstract class Sync extends AbstractQueuedSynchronizer {
			
			private static final long serialVersionUID = 1L;
	
			abstract void lock();
			
			//非公平锁拿到线程状态
			final boolean nonfairTryAcquire(int acquires) {
	
				final Thread current = Thread.currentThread();
	
				int c = getState();
	
				//System.out.println(current.getName() + " , state : " + c);
	
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
			
			final boolean isLocked() {
			
				return getState() != 0;
			
			}
			
		}
		
		
	}
