package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class KolhozAppl {

	private static final int N_TRUCKS = 1000;
	private static final int LOAD = 1;
	private static final int N_RUNS = 100000;

	public static void main(String[] args) {
		Truck[] trucks = new Truck[N_TRUCKS];
		startTrucks(trucks);
		Instant start = Instant.now();
		waitTrucks(trucks);
		displayResult(start);
	}

	private static void startTrucks(Truck[] trucks) {
		for (int i = 0; i < N_TRUCKS; i++) {
			trucks[i] = new Truck(LOAD, N_RUNS);
			trucks[i].start();
		}
	}

	private static void waitTrucks(Truck[] trucks) {
		Arrays.stream(trucks).forEach(truck -> {
			try {
				truck.join();
			} catch (InterruptedException e) {}
		});
	}
	
	private static void displayResult(Instant start) {
		System.out.printf("Running time %d, elevator1 contains %d tons, elevator2 contains %d tons\n",
				ChronoUnit.MILLIS.between(start, Instant.now()), Truck.getElevator1(), Truck.getElevator2());
	}
	
}
