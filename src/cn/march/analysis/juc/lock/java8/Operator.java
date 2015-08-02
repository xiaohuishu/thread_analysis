	


	package cn.march.analysis.juc.lock.java8;
	
	
	
	@FunctionalInterface
	interface Operator<T> {
		
		T execute();
		
	}