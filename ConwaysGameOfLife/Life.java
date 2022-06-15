package ConwaysGameOfLife;

import java.util.Scanner;

public class Life {
	public static void main(String[] args)
	{
		// Create a Board, using a filename.
		Board allCells = new Board("C:\\CS\\AP_CS_ML\\LounsberyMax\\Conways\\sampleData.txt");

		// Test printing out the cells
		System.out.println(allCells);
		boolean done = false;
		Scanner reader = new Scanner(System.in);

		System.out.println("Welcome to the game of life!");
		System.out.println("please edit sampleData.txt to edit the starting board configuration.");
		System.out.println("type \"quit\" to exit the program."); 
		System.out.println("type \"tick\" to go to the next stage of the board.");

		while (!done) {
			System.out.println(allCells);
			if (reader.hasNext("quit")) {
				done = true;
				break;
			}
			else {
				allCells.tick();
				System.out.println(allCells);
				reader.next();
			}
		}
		System.out.println("goodbye!");
		reader.close();
	}
}