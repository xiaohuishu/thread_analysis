	


	package cn.march.analysis.juc.lock.java8;
	
	
	
	
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;
	
	
	
	public class CounterSecond {
	
		private int count;
		
		private final Synchronizer sync;
		
		
		public CounterSecond() {
			
			sync = new Synchronizer();
			
		}
		
		int next() {
			
			return sync.execute(() -> {
				return count++;
			});
			
		}
		
		static class Synchronizer {
			
			private final Lock lock;
			
			Synchronizer() {
				
				this.lock = new ReentrantLock();
				
			}
			
			
			private int execute(Operator<Integer> operator) {
				
				lock.lock();
				
				try {
					
					return operator.execute();
					
				}finally {
					
					lock.unlock();
					
				}
			}
			
			
			
		}
		
		
	}
