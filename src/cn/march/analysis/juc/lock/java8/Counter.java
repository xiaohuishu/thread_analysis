	


	package cn.march.analysis.juc.lock.java8;
	
	
	
	public class Counter {
	
		private static int count;
	
		final Synchronizer<Integer> sync;
	
		final Operator<Integer> operator;
	
		public Counter() {
	
			this(new Synchronizer<Integer>());
	
		}
	
		public Counter(Synchronizer<Integer> sync) {
	
			this(sync, () -> {
				return count++;
			});
	
		}
	
		public Counter(Synchronizer<Integer> sync, Operator<Integer> operator) {
	
			this.sync = sync;
			this.operator = operator;
	
		}
	
		public int next() {
	
			return sync.execute(operator);
	
		}
	
	}
