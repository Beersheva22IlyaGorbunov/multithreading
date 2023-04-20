package telran.multithreading;

import java.util.Arrays;

public class DeadlockThread extends Thread {
	boolean isFirst;
	long[] firstArr;
	long[] secondArr;
	
	public DeadlockThread(boolean isFirst, long[] firstArr, long[] secondArr) {
		this.isFirst = isFirst;
		this.firstArr = firstArr;
		this.secondArr = secondArr;
	}
	
	@Override 
	public void run() {
		if (isFirst) {
			runFirstThread();
		} else {
			runSecondThread();
		}
	}
	
	private void runFirstThread() {
		synchronized (firstArr) {
			Arrays.stream(firstArr).anyMatch(num -> num == 6);
			synchronized (secondArr) {
				Arrays.stream(secondArr).anyMatch(num -> num == 6);
			}
		}
		
	}
	
	private void runSecondThread() {
		synchronized (secondArr) {
			Arrays.stream(secondArr).anyMatch(num -> num == 6);
			synchronized (firstArr) {
				Arrays.stream(firstArr).anyMatch(num -> num == 6);
			}
		}
	}
}
