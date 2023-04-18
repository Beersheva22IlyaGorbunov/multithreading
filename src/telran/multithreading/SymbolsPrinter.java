package telran.multithreading;

public class SymbolsPrinter extends Thread {
	private static final long timeout = 1000;
	private String symbols;
	
	public SymbolsPrinter(String symbols) {
		this.symbols = symbols;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		int i = 0;
		while (true) {
			System.out.print(symbols.charAt(i));
			try {
				sleep(timeout);
			} catch (InterruptedException e) {
				i++;
				if (i == symbols.length()) {
					i = 0;
				}
			}
		}
	}
}
