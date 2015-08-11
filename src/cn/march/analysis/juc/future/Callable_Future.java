



	package cn.march.analysis.juc.future;

	
	
	
	import java.util.concurrent.Callable;
	import java.util.concurrent.ExecutionException;
	import java.util.concurrent.FutureTask;
	import cn.march.analysis.juc.lock.java8.Counter;


	
	/**
	 * Callable,Future测试
	 * @author antsmarth
	 *
	 */
	public class Callable_Future {
	
	
		//测试方法
		public static void main(String[] args) throws InterruptedException, ExecutionException {
			
			Counter counter = new Counter();
			
			
			//一个计数器,计算1-1000的总和,创建Callable接口对象返回总和sum;
			Callable<?> call = () -> {
				
				int i = 0;
				
				int sum = 0;
				
				while(++i <= 1000)
					sum += counter.next();
				
				return sum;
				
			};
			
			
			/**
			 * 创建一个FutureTask对象,将计数器call对象进行构建
			 * 
			 * FutureTask代表一个任务,实现Runable,Future接口：
			 * 		可接收Runable,Callable接口对象;
			 * 		
			 */
			FutureTask<?> futureTask = new FutureTask<>(call);
			
			
			//通过futureTask构建线程对象
			Thread thread = new Thread(futureTask);
			
			//开启计数器
			thread.start();
			
			int k = 0;
			
			//FutureTask即计算1-1000总和这个操作不会堵塞,在此期间可以做其他事情
			while(++k < 5) {
				
				Thread.sleep(2000);
				
				System.out.println("在此期间可以做其他事情......");
				
			}
			
			//计算操作完成后,输出计算结果;
			System.out.println("计算完成,结果是: " + futureTask.get());
			
		}
		
		
	}
	
	
	
