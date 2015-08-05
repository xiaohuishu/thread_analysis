



	package cn.march.analysis.juc.future;

	
	
	
	import java.util.concurrent.Callable;
	import java.util.concurrent.ExecutionException;
	import java.util.concurrent.FutureTask;
	import cn.march.analysis.juc.lock.java8.Counter;


	
	
	public class Callable_Future {
	
	
		public static void main(String[] args) throws InterruptedException, ExecutionException {
			
			Counter counter = new Counter();
			
			
			Callable<?> call = () -> {
				
				int i = 0;
				
				int sum = 0;
				
				while(++i <= 1000)
					sum += counter.next();
				
				return sum;
				
			};
			
			
			FutureTask<?> futureTask = new FutureTask<>(call);
			
			
			Thread thread = new Thread(futureTask);
			
			thread.start();
			
			int k = 0;
			
			while(++k < 5) {
				
				Thread.sleep(2000);
				
				System.out.println("在此期间可以做其他事情......");
				
			}
			
			System.out.println("计算完成,结果是: " + futureTask.get());
			
		}
		
		
	}
	
	
	
