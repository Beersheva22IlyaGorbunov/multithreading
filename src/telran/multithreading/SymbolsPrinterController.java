package telran.multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SymbolsPrinterController {
	
	public static void main(String[] args) throws IOException {
		boolean isRun = true;
		
		BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
		System.out.println("Enter symbols string for printing");
		String symbols = reader.readLine();
		
		SymbolsPrinter printer = new SymbolsPrinter(symbols);
		printer.start();
		
		while(isRun) {
			if (reader.readLine().equals("q")) {
				isRun = false;
			} else {
				printer.interrupt();
			}
		}
	}
	
}
