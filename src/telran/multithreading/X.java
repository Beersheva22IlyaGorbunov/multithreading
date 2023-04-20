package telran.multithreading;

import java.util.HashSet;

public class X extends Thread {
	final static Object res1 = new Object();
	final static Object res2 = new Object();
	final static Object res3 = new Object();
	
	private void m1() {
		synchronized (res1) {
			synchronized (res2) {
				
			}
		}
	}
	
	private void m2() {
		synchronized (res2) {
			synchronized (res3) {
				
			}
		}
	}
	
	private void m3() {
		synchronized (res1) {
			synchronized (res3) {
				
			}
		}
	}
}
