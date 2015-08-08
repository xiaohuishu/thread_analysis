package cn.march.analysis.model.producer_consumer;

import java.util.HashSet;
import java.util.Set;

public class Consumer extends Thread{

	private final Set seenObjects = new HashSet();
	private int total = 0;
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
