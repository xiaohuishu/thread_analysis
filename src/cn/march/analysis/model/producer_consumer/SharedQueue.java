package cn.march.analysis.model.producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedQueue {

	
	private Object[] elems = null;
	
	private int current = 0;
	
	private int placeIndex = 0;
	
	private int removeIndex = 0;
	
	private final Lock lock = new ReentrantLock();
	
	private final Condition isEmpty = lock.newCondition();
	
	private final Condition isFull = lock.newCondition();
	
	
	public SharedQueue(int capacity) {
		
		this.elems = new Object[capacity];
		
	}
	
	
	public SharedQueue() {
		
		this.elems = new Object[16];
		
	}
	
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
