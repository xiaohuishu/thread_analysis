



	package cn.march.analysis.juc.lock.java8.revise;
	
	
	
	@FunctionalInterface
	interface Operator<T> {
	
		T execute(T initValue);
		
	}
