package telran.multithreading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Deadlock {
	static long[] firstArr = new long[100000];
	static long[] secondArr = new long[100000];
	
	public static void main(String[] args) throws InterruptedException {
		Arrays.fill(firstArr, 0, firstArr.length-1, 5);
		Arrays.fill(secondArr, 0, secondArr.length-1, 15);
		Thread first = new DeadlockThread(true, firstArr, secondArr);
		Thread second = new DeadlockThread(false, firstArr, secondArr);
		first.start();
		second.start();
		first.join();
		second.join();
	}
}
