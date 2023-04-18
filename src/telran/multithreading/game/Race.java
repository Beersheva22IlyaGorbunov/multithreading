package telran.multithreading.game;

import java.util.concurrent.atomic.AtomicInteger;

public class Race extends Thread {
	public static int MIN_SLEEP = 2;
	public static int MAX_SLEEP = 5;
	
	private int distance;
	private Player[] players;
	private int playerQuantity;
	public AtomicInteger finished = new AtomicInteger(-1);
	
	public Race(int distance, int playerQuantity) {
		this.distance = distance;
		this.playerQuantity = playerQuantity;
		this.players = new Player[playerQuantity];
	}
	
	public int getWinner() {
		return finished.get();
	}
	
	@Override
	public void run() {
		for (int i = 0; i < playerQuantity; i++) {
			Player newPlayer = new Player(i + 1, distance, finished);
			players[i] = newPlayer;
			newPlayer.start();
		}
		for (Player player: players) {
			try {
				player.join();
			} catch (InterruptedException e) {}
		}
	}
	
}
