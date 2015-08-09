
package cn.march.analysis.model.akka;

import akka.actor.UntypedActor;


public class HelloWorker extends UntypedActor{

	
	
	public void onReceive(Object message) throws Exception {
		
		System.out.println("Worker 收到消息: " + message);
		
		if(message instanceof String) {
			
			String text  = doWork((String) message);
			getSender().tell(text, getSelf());
				
		}else
			unhandled(message);
		
		
	}
	
	private String doWork(String str) {
		
		try {

			Thread.sleep(1000 * 20);
		
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		
		}
		
		
		return "return: " + str + ".";
		
	}

}
