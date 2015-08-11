	

	package cn.march.analysis.model.producer_consumer;
	
	
	/**
	 * 客户端类
	 * @author antsmarth
	 *
	 */
	public class ConditionExample {
	
		//测试方法
		public static void main(String[] args) throws InterruptedException {
			
			//创建产品集合
			SharedQueue queue = new SharedQueue(10);
			
			/**
			 * 创建消费者,生产者;
			 */
			Thread producer = new Producer(queue);
			
			Thread consumer = new Consumer(queue);
			
			//启动线程
			producer.start();
			consumer.start();
			
			producer.join();
			consumer.join();
			
		}
		
		
	}
