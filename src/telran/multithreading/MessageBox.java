package telran.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageBox {
	String message;
	Lock lock = new ReentrantLock();
	Condition waitingConsumer = lock.newCondition();
	Condition waitingProducer = lock.newCondition();
	
	public void put(String message) throws InterruptedException {
		lock.lock();
		try {
			while(this.message != null) {
				waitingProducer.await();
			}
			this.message = message;
			waitingConsumer.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public String take() throws InterruptedException {
		lock.lock();
		try {
			while(message == null) {
				waitingConsumer.await();
			}
			String res = message;
			message = null;
			waitingProducer.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}
	
	public String get() {
		lock.lock();
		try {
			String res = message;
			message = null;
			if (res != null) {
				waitingProducer.signal();
			}
			return res;
		} finally {
			lock.unlock();
		}
	}
}
