	


	package cn.march.analysis.juc.lock.java8.revise;
	
	
	
	public class Counter {
		
		private int count = 0;
		
		private final SynchronizerRevise sync;
		
		
		
		public Counter() {
			
			sync = new SynchronizerRevise();
		
		}
		
		public int next(Operator<Integer> operator) {
			
			sync.lock();
			
			try {
				
				return operator.execute(count);
				
			}finally {
				
				sync.unlock();
				
			}
			
		}
		
		
		public static void main(String[] args) {
			
			Counter count1 = new Counter();
			
			for(int i = 0; i < 10; i++) {
				
	
				System.out.println(count1.next( count -> {
					
					count1.count = count++;
					
					return ++count;
				}));	
				
			}
			
			
		}
	
	}
