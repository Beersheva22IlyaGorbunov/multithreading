package telran.multithreading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;
import java.util.stream.IntStream;

public class ListOperationsController {

	private static final int N_NUMBERS = 100000;
	private static final int N_THREADS = 100;
	private static final int UPDATE_PROB = 50;
	private static final int N_RUNS = 1000;

	public static void main(String[] args) {
		Integer[] numbers = new Integer[N_NUMBERS];
		Arrays.fill(numbers, 100);
		List<Integer> list = new LinkedList<>(Arrays.asList(numbers));
		
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		Lock writeLock = lock.writeLock();
		Lock readLock = lock.readLock();
		AtomicInteger count = new AtomicInteger(0);
		
		ListOperations[] operations = new ListOperations[N_THREADS];
		IntStream.range(0, N_THREADS)
			.forEach((i) -> {
				operations[i] = new ListOperations(UPDATE_PROB, list, N_RUNS, readLock, writeLock, count);
				operations[i].start();
			});
		Arrays.stream(operations).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		System.out.printf("Number of iteration %d, number of waiting lock operations %d", N_RUNS * N_THREADS, count.get());
	}

}
