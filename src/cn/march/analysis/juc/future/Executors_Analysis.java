	



	package cn.march.analysis.juc.future;
	
	
	
	
	import java.util.concurrent.ExecutionException;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	import java.util.concurrent.Future;
	
	class Count_Test {
	
		static int i = 0;
	
	}
	
	/**
	 * JUC中Executors Pool(线程池测试分析)
	 * 
	 * @author antsmarth
	 *
	 */
	public class Executors_Analysis {
	
		public static void main(String[] args) throws InterruptedException,
				ExecutionException {
	
	
			
			// Executors.newCachedThreadPool();
			ExecutorService execute = Executors.newFixedThreadPool(4);
	
			Future<Integer> count = execute.submit(() -> {
	
				int sum = 0;
	
				while (Count_Test.i++ <= 1000)
					sum += Count_Test.i;
				return sum;
				
	
			});
			
	
			/*Future<?> run_future = execute.submit(() -> {
	
				while (Count_Test.i++ <= 1000)
					System.out.println(Thread.currentThread().getName()
							+ " , Count_Test i value: " + Count_Test.i);
	
			});*/
			
			
			Future<Integer> count_run = null;
			
			while (count.isDone()) {
				
				count_run = execute
						.submit(() -> {
							
							int j = 0;
							
							while(j <= 1000)
								System.out.println("你可以做任何事情");
	
						}, count.get());
			
				System.out.println(count_run.get());
				
			}
			
		}
	}
