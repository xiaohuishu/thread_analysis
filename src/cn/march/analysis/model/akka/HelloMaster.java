package cn.march.analysis.model.akka;

import akka.actor.UntypedActor;

public class HelloMaster extends UntypedActor{


	@Override
	public void onReceive(Object message) throws Exception {
	
		System.out.println("Master接收到的消息: " + message);
	
	}

}
