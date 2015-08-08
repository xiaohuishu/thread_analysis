package cn.march.analysis.model.waitnotify;

import java.util.concurrent.atomic.AtomicInteger;

public class WaitNotify_Object {

	private AtomicInteger val = new AtomicInteger(1);

	private void printValAndIncrease() {

		System.out.println(Thread.currentThread().getName() + ", val Value: "
				+ val);
		val.incrementAndGet();

	}

	public static void main(String[] args) {

		WaitNotify_Object obj = new WaitNotify_Object();

		Runnable printA = () -> {

			while (obj.val.get() <= 3)
				obj.printValAndIncrease();

			synchronized (obj) {

				System.out.println("printA printed 1,2,3; notify printB");
				obj.notify();

			}

			try {

				while (obj.val.get() <= 6) {

					synchronized (obj) {
				
						System.out.println("wait in printA");
						obj.wait();
					
					}

				}

				System.out.println("wait end printA");

			} catch (InterruptedException ex) {

				ex.printStackTrace();

			}

			while (obj.val.get() <= 9)
				obj.printValAndIncrease();

			System.out.println("printA exits...");

		};

		Runnable printB = () -> {

			while (obj.val.get() < 3) {

				synchronized (obj) {

					try {

						System.out
								.println("printB wait for printA printed 1,2,3");
						obj.wait();
						System.out
								.println("printB waited for printA printed 1,2,3");

					} catch (InterruptedException ex) {

						ex.printStackTrace();

					}
				}

			}

			while (obj.val.get() <= 6)
				obj.printValAndIncrease();

			synchronized (obj) {

				System.out.println("notify in printB");
				obj.notify();

			}

			System.out.println("notify end printB");
			System.out.println("printB exits...");

		};

		Thread threadA = new Thread(printA);
		threadA.setName("printA");

		Thread threadB = new Thread(printB);
		threadB.setName("printB");

		threadA.start();
		threadB.start();
	}

}
