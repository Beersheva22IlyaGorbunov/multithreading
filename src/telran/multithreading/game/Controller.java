package telran.multithreading.game;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class Controller {
	public static int MIN_PLAYERS = 2;
	public static int MAX_PLAYERS = 10;
	public static int MIN_DIST = 100;
	public static int MAX_DIST = 1000;
	
	public static Menu getMenu(InputOutput io) {
		return new Menu("Multithreading game", 
				Item.of("Start new race", Controller::startNewRace),
				Item.exit());
	}

	public static void startNewRace(InputOutput io) {
		int playersNumber = io.readInt(String.format("Enter a number of players in range %d-%d", MIN_PLAYERS, MAX_PLAYERS),
				String.format("You must enter a number in a range %d-%d",  MIN_PLAYERS, MAX_PLAYERS), MIN_PLAYERS, MAX_PLAYERS);
		int raceDistance = io.readInt(String.format("Enter a distance of race in range %d-%d", MIN_DIST, MAX_DIST),
				String.format("You must enter a distance in range %d-%d", MIN_DIST, MAX_DIST), MIN_DIST, MAX_DIST);
		Race newRace = new Race(raceDistance, playersNumber);
		newRace.start();
		try {
			newRace.join();
		} catch (InterruptedException e) {}
		io.writeString(String.format("Player %d have finished first\n", newRace.getWinner()));
	}
}
