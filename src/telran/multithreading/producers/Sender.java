package telran.multithreading.producers;

import java.util.concurrent.BlockingQueue;

import telran.multithreading.MessageBox;

public class Sender extends Thread {
	private BlockingQueue<String> messageBox;
	private int nMessages;
	
	public Sender(BlockingQueue<String> messageBox, int nMessages) {
		this.messageBox = messageBox;
		this.nMessages = nMessages;
	}
	
	@Override
	public void run() {
		for (int i = 1; i <= nMessages; i++) {
			try {
				messageBox.put("message" + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
