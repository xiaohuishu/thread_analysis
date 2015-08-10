	


	package cn.march.analysis.model.akka;
	
	
	
	import static java.util.concurrent.TimeUnit.SECONDS;
	import scala.concurrent.duration.Duration;
	import akka.actor.ActorRef;
	import akka.actor.ActorIdentity;
	import akka.actor.Identify;
	import akka.actor.Terminated;
	import akka.actor.UntypedActor;
	import akka.actor.ReceiveTimeout;
	import akka.japi.Procedure;
	
	
	/**
	 * 远程调用Master(实体);
	 * 		调度者：
	 * 
	 * 			接收客户端发送过来的消息,分发给相对应的Worker(实体)进行处理返回结果
	 * 
	 * 所有实体类都需要继承UntypedActor类(Java8 lambda表达式除外)
	 * 
	 * @author antsmarth
	 *
	 */
	public class LookupActor extends UntypedActor {
	
		//Worker工作处理实体的url;
		private final String path;
		
		//计算操作
		private ActorRef calculator = null;
	
		public LookupActor(String path) {
			this.path = path;
			sendIdentifyRequest();
		}
	
		private void sendIdentifyRequest() {
		
			//解析path，得到calculator
			getContext().actorSelection(path).tell(new Identify(path), getSelf());
			getContext()
					.system()
					.scheduler()
					.scheduleOnce(Duration.create(3, SECONDS), getSelf(),
							ReceiveTimeout.getInstance(),
							getContext().dispatcher(), getSelf());
		
		}
	
		@Override
		public void onReceive(Object message) throws Exception {
			
			if (message instanceof ActorIdentity) {
			
				calculator = ((ActorIdentity) message).getRef();
				if (calculator == null) {
					System.out.println("Remote actor not available: " + path);
				} else {
					getContext().watch(calculator);
					getContext().become(active, true);
				}
	
			} else if (message instanceof ReceiveTimeout) {
				sendIdentifyRequest();
	
			} else {
				System.out.println("Not ready yet");
	
			}
		}
	
		Procedure<Object> active = new Procedure<Object>() {
			@Override
			public void apply(Object message) {
				if (message instanceof Op.MathOp) {
					// send message to server actor
					calculator.tell(message, getSelf());
	
				} else if (message instanceof Op.AddResult) {
					Op.AddResult result = (Op.AddResult) message;
					System.out.printf("Add result: %d + %d = %d\n", result.getN1(),
							result.getN2(), result.getResult());
	
				} else if (message instanceof Op.SubtractResult) {
					Op.SubtractResult result = (Op.SubtractResult) message;
					System.out.printf("Sub result: %d - %d = %d\n", result.getN1(),
							result.getN2(), result.getResult());
	
				} else if (message instanceof Terminated) {
					System.out.println("Calculator terminated");
					sendIdentifyRequest();
					getContext().unbecome();
	
				} else if (message instanceof ReceiveTimeout) {
					// ignore
	
				} else {
					unhandled(message);
				}
	
			}
		};
	}
