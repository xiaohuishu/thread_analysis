	


	package cn.march.analysis.model.akka;
	
	
	
	import static java.util.concurrent.TimeUnit.SECONDS;
	import java.util.Random;
	import scala.concurrent.duration.Duration;
	import akka.actor.ActorRef;
	import akka.actor.ActorSystem;
	import akka.actor.Props;
	import com.typesafe.config.ConfigFactory;
	
	
	/**
	 * Client类,向Master进行发送计算消息(远程调用)
	 * @author antsmarth
	 *
	 */
	public class LookupApplication {
	
		//测试方法
		public static void main(String[] args) {
	
			//先向服务器注册calculator(Worker)Actor
			if (args.length == 0 || args[0].equals("Calculator"))
				startRemoteCalculatorSystem();
	
			//向Master发送消息,开始计算
			if (args.length == 0 || args[0].equals("Lookup"))
				startRemoteLookupSystem();
	
		}
	
		/**
		 * 向服务器注册calculator(Worker)Actor消息
		 */
		public static void startRemoteCalculatorSystem() {
	
			//注册
			final ActorSystem system = ActorSystem.create("CalculatorSystem",
					ConfigFactory.load(("calculator")));
	
			system.actorOf(Props.create(CalculatorActor.class), "calculator");
	
			System.out.println("Started CalculatorSystem");
	
		}
	
		/**
		 * 通过url(path在配置文件中先配置好端口,类等信息)获取注册好的calculator实体
		 * 并注册LookupActor(Master)实体类
		 * 之后开始发送计算消息,开始计算之后返回结果;
		 */
		public static void startRemoteLookupSystem() {
	
			//注册
			final ActorSystem system = ActorSystem.create("LookupSystem",
					ConfigFactory.load("remotelookup"));
	
			//Worker实体
			final String path = "akka.tcp://CalculatorSystem@127.0.0.1:2552/user/calculator";
	
			final ActorRef actor = system.actorOf(
					Props.create(LookupActor.class, path), "lookupActor");
	
			System.out.println("Started LookupSystem");
	
			final Random r = new Random();
	
			//设置一个时间调度;
			system.scheduler().schedule(Duration.create(1, SECONDS),
	
			//开始发送Add,Sub计算消息
			Duration.create(1, SECONDS), new Runnable() {
				@Override
				public void run() {
					if (r.nextInt(100) % 2 == 0) {
						actor.tell(new Op.Add(r.nextInt(100), r.nextInt(100)), null);
					} else {
						actor.tell(new Op.Subtract(r.nextInt(100), r.nextInt(100)),
								null);
					}
	
				}
			}, system.dispatcher());
	
		}
	
	}
