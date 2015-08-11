	


	package cn.march.analysis.model.producer_consumer;
	
	
	
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	
	
	/**
	 * 生产者线程:
	 * 		向产品queue中添加物品(调用SharedQueue.add()操作);
	 * 
	 * @author antsmarth
	 *
	 */
	public class Producer extends Thread{
	
		//从文件中读取物品
		private final static String FILENAME = "input.txt";
		//产品集合
		private final SharedQueue queue;
		
		public Producer(SharedQueue queue) {
			
			this.queue = queue;
			
		}
		
		@Override
		public void run() {
			
			BufferedReader rd = null;
			
			try {
				
				rd = new BufferedReader(new FileReader(FILENAME));
				
				String inputLine = null;
				while((inputLine = rd.readLine()) != null) {
					
					String[] inputWords = inputLine.split(" ");
					
					for(String inputWord : inputWords) 
						queue.add(inputWord);
					
				}
				
				queue.add(null);
				
			}catch(InterruptedException ex) {
				
				ex.printStackTrace();
				
			}catch(IOException io) {
				
				io.printStackTrace();
			
			}finally {
				
				try {
					
					if(rd != null) 
						rd.close();
					
				}catch(IOException ex) {
					
					ex.printStackTrace();
					
				}
				
			}
			
		}
		
	}
