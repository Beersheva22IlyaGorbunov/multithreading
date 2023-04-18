package telran.multithreading.game;

import telran.view.InputOutput;
import telran.view.Menu;
import telran.view.StandardInputOutput;

public class MultihreadingGameAppl {

	public static void main(String[] args) {
		InputOutput io = new StandardInputOutput();
		Menu mainMenu = Controller.getMenu(io);
		
		mainMenu.perform(io);
	}

}
