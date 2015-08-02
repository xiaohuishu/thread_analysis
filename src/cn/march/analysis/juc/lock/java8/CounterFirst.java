	


	package cn.march.analysis.juc.lock.java8;
	
	
	
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;
	
	
	
	public class CounterFirst {
	
		
		private int count;
		
		private Lock lock;
		
		
		public CounterFirst() {
			
			this.lock = new ReentrantLock();
		
		}
		
		int next() {
			
			lock.lock();
			
			try{
				
				Operator<Integer> increment = () -> {
					return count++;
				};
				
				return increment.execute();
				
			}finally {
				
				lock.unlock();
				
			}
			
		}
		
	}
