package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PrinterController {
	
	public static void main(String[] args) throws InterruptedException {
		Printer printer1 = new Printer("Print in printer 1", 100);
		Printer printer2 = new Printer("Print in printer 2", 100);
		Instant start = Instant.now();
		printer1.start();
		printer2.start();
		printer1.join();
		printer2.join();
		System.out.printf("Running time is %d millis", ChronoUnit.MILLIS.between(start, Instant.now()));
 	}

}
