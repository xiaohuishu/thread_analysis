	


	package cn.march.analysis.model.producer_consumer;
	
	
	
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	
	
	
	public class Producer extends Thread{
	
		private final static String FILENAME = "input.txt";
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
