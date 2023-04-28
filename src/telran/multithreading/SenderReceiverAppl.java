package telran.multithreading;

import java.util.ArrayList;
import java.util.List;

import telran.multithreading.consumers.Receiver;
import telran.multithreading.producers.Sender;

public class SenderReceiverAppl {

	private static final int N_MESSAGES = 200000;
	private static final int N_RECEIVERS = 10;
	static List<Receiver> receivers = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {
		MessageBox messageBox = new MessageBox();
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		for (int i = 0; i < N_RECEIVERS; i++) {
			Receiver newReceiver = new Receiver(messageBox);
			receivers.add(newReceiver);
			newReceiver.start();
		}
		sender.join();
		receivers.forEach(receiver -> receiver.interrupt());
		receivers.forEach(receiver -> {
			try {
				receiver.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		System.out.println(Receiver.count.get());
	}

}
