	

	package cn.march.analysis.model.producer_consumer;
	
	
	
	import java.util.HashSet;
	import java.util.Set;
	
	
	/**
	 * 消费者线程：
	 * 		从产品queue中进行取物品(调用SharedQueue.remove()操作);
	 * 
	 * @author antsmarth
	 *
	 */
	public class Consumer extends Thread{
	
		//消费的产品集合
		private final Set seenObjects = new HashSet();
		//消费的总数
		private int total = 0;
		//产品集合
		private final SharedQueue queue;
		
		public Consumer(SharedQueue queue) {
			
			this.queue = queue;
			
		}
		
		public void run() {
			
			try {
				
				do {
					
					Object obj = queue.remove();
					
					if(obj == null)
						break;
					
					if(!seenObjects.contains(obj)) {
						
						++total;
						seenObjects.add(obj);
						
					}
					
				}while(true);
				
			}catch(InterruptedException ex) {
				
				ex.printStackTrace();
			}
			
		}
		
		
	}
