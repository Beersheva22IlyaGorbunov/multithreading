package telran.multithreading.consumers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {
	private BlockingQueue<String> messageBox;
	public static AtomicInteger count = new AtomicInteger(0);
	public Receiver(BlockingQueue<String> messageBox) {
		this.messageBox = messageBox;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				printMsg(messageBox.take());
			} catch (InterruptedException e) {
				boolean restMsgs = true;
				do { 
					String message = messageBox.poll();
					if (message == null) {
						restMsgs = false;
					} else {
						printMsg(message);
					}
				} while (restMsgs);
				break;
			}
		}
	}

	private void printMsg(String msg) {
		count.incrementAndGet();
		System.out.printf("Thread: %s, received message: %s\n", getName(), msg);
	}
}
