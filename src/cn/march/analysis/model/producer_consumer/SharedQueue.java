	

	package cn.march.analysis.model.producer_consumer;
	
	
	
	import java.util.concurrent.locks.Condition;
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;
	
	
	/**
	 * 产品集合：
	 * 		使用Lock对象来完成数据的同步：
	 * 		使用Lock.newCondition() --> Condition对象来完成互斥
	 * 			创建1个Condition代表产品队列是否为空
	 * 			创建1个Condition代表产品队列是否满
	 * 
	 * 		定义2个操作：
	 * 
	 * 			1.add()
	 * 				如果队列中的物品满了：
	 * 				if(current >= elems.length)
	 * 					isFull.await();
	 * 				则让add操作进行等待;
	 * 
	 * 				isEmpty.singal()唤醒remove()操作
	 * 			
	 * 			2.remove()
	 * 				如果队列中物品为空：
	 * 				if(current <= 0)
	 * 					isEmpty.await();
	 * 				则让remove操作进行等待
	 * 		
	 * 				isFull.singal()唤醒add()操作
	 * 	
	 * 
	 * @author antsmarth
	 *
	 */
	public class SharedQueue {
	
		//存储的物品集合
		private Object[] elems = null;
		
		//当前物品数量
		private int current = 0;
		
		private int placeIndex = 0;
		
		private int removeIndex = 0;
		
		//锁对象
		private final Lock lock = new ReentrantLock();
		
		//是否为空,是否满
		private final Condition isEmpty = lock.newCondition();
		private final Condition isFull = lock.newCondition();
		
		
		public SharedQueue(int capacity) {
			
			this.elems = new Object[capacity];
			
		}
		
		
		public SharedQueue() {
			
			this.elems = new Object[16];
			
		}
		
		/**
		 * add操作
		 * @param elem
		 * @throws InterruptedException
		 */
		public void add(Object elem) throws InterruptedException {
			
			lock.lock();
			
			while(current >= elems.length) 
				isFull.await();
			
			elems[placeIndex] = elem;
			
			placeIndex = (placeIndex + 1) % elems.length;
			
			++current;
			
			isEmpty.signal();
			
			lock.unlock();
			
		}
		
		/**
		 * remove操作
		 * @return
		 * @throws InterruptedException
		 */
		public Object remove() throws InterruptedException {
			
			Object elem = null;
			
			lock.lock();
			
			while(current <= 0)
				isEmpty.await();
			
			elem = elems[removeIndex];
			
			removeIndex = (removeIndex + 1) % elems.length;	
			
			--current;
			
			isFull.signal();
			
			lock.unlock();
			
			return elem;
			
		}
		
	}
