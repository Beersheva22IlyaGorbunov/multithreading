package telran.multithreading.scheduler;

import telran.view.InputOutput;

public class Printer extends Thread {
	private Printer nextPrinter;
	private int number;
	private int portion;
	private int amount;
	private InputOutput io;
	
	public Printer(Printer nextPrinter, int number, int amount, int portion, InputOutput io) {
		this.nextPrinter = nextPrinter;
		this.number = number;
		this.portion = portion;
		this.amount = amount;
		this.io = io;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < (amount / portion); i++) {
			try {
				sleep(100000);
			} catch (InterruptedException e) {}
			io.writeLine(Integer.toString(number).repeat(portion));
			nextPrinter.interrupt();
		}
	}
	
	public void setNextPrinter(Printer nextPrinter) {
		this.nextPrinter = nextPrinter;
	}

}
