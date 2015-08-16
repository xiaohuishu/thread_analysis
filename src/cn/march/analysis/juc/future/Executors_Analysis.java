	


	package cn.march.analysis.juc.future;

	import java.util.concurrent.Executors;

	
	
	/**
	 * JUC中Executors Pool(线程池测试分析)
	 * @author antsmarth
	 *
	 */
	public class Executors_Analysis {
	
		public static void main(String[] args) {
			
			Executors.newCachedThreadPool();
			Executors.newFixedThreadPool(1);
			
		}
		
		
	}
