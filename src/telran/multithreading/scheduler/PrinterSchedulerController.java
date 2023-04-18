package telran.multithreading.scheduler;

import telran.view.InputOutput;
import telran.view.StandardInputOutput;

public class PrinterSchedulerController {
	private static Printer[] printers;
	private static InputOutput io  = new StandardInputOutput();
	
	public static void main(String[] args) {
		int threadsNumber = io.readInt("Enter number of threads", 
				"Number of threads should be more than 0 and less than 20", 1, 20);
		int amount = io.readInt("Enter amount of numbers", 
				"Enered number should be more than 0", 0, Integer.MAX_VALUE);
		int portionSize = io.readInt("Enter portion size", 
				"Enered number should be more than 0", 0, Integer.MAX_VALUE);
		
		while (amount % portionSize != 0) {
			io.writeString("Amount of numbers should divide to portion size without reminder\n");
			portionSize = io.readInt("Enter portion size", 
					"Enered number should be more than 0", 0, Integer.MAX_VALUE);
		}
		
		buildPrinters(threadsNumber, amount, portionSize);
		
		startPrinting();
	}
	
	private static void buildPrinters(int threadsNumber, int amount, int portionSize) {
		printers = new Printer[threadsNumber];
		
		Printer lastPrinter = new Printer(null, threadsNumber, amount, portionSize, io);
		printers[threadsNumber - 1] = lastPrinter;
		
		for (int i = threadsNumber - 1; i > 0; i--) {
			Printer newPrinter = new Printer(printers[i], i, amount, portionSize, io);
			printers[i - 1] = newPrinter;
		}
		
		lastPrinter.setNextPrinter(printers[0]);
	}

	private static void startPrinting() {
		for (Printer printer: printers) {
			printer.start();
		}

		printers[0].interrupt();
	}
	
}
