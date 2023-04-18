package telran.multithreading.game;

public class Player extends Thread {
	int playerNumber;
	int distance;
	Integer[] finished;

	public Player(int playerNumber, int distance, Integer[] finished) {
		this.playerNumber = playerNumber;
		this.distance = distance;
		this.finished = finished;
	}

	@Override
	public void run() {
		while (distance > 0) {
			distance--;
			int nextStep = (int)Math.floor(Math.random() * (Race.MAX_SLEEP - Race.MIN_SLEEP + 1) + Race.MIN_SLEEP);
			try { 
				sleep(nextStep);
			} catch (InterruptedException e) {}
		}
		if (finished[0] == null) {
			finished[0] = playerNumber;
		}
	}

}
