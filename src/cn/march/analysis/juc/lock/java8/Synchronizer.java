	


	package cn.march.analysis.juc.lock.java8;
	
	
	
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;
	
	
	
	public class Synchronizer<T> {
	
		private final Lock lock;
	
		Synchronizer() {
	
			this.lock = new ReentrantLock();
	
		}
	
		public T execute(Operator<T> operator) {
	
			lock.lock();
	
			try {
	
				return operator.execute();
	
			} finally {
	
				lock.unlock();
	
			}
		}
	
	}
