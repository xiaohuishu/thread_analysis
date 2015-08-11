	

	package cn.march.analysis.model.producer_consumer;
	
	
	
	public class ConditionExample {
	
		public static void main(String[] args) throws InterruptedException {
			
			SharedQueue queue = new SharedQueue(10);
			
			
			Thread producer = new Producer(queue);
			
			Thread consumer = new Consumer(queue);
			
			producer.start();
			consumer.start();
			
			producer.join();
			consumer.join();
			
		}
		
		
	}
