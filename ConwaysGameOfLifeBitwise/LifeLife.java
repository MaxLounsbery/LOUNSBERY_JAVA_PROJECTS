package ConwaysGameOfLifeBitwise;

import java.util.Scanner;
/**
 * LifeLife uses the BoardLife class and the BoardReaderLife class in order to 
 * play the game of life. The Scanner class is also used for use input.
 */
public class LifeLife {
	/**
	 * Our main method. creates the board object, and uses a for-loop to get input from the user
	 * on whether to tick the board, or to quit the program.
	 * @param args The command line arguments.
	 * @throws FileNotFoundException if the designated file cannot be found.
	 */
	public static void main(String[] args)
	{
		// Create a Board, using a filename.
		BoardLife allCells = new BoardLife("C:\\CS\\AP_CS_ML\\LounsberyMax\\GameOfLife\\sampleData.txt");

		// Test printing out the cells
		System.out.println(allCells);
		boolean done = false;
		Scanner reader = new Scanner(System.in);

		System.out.println("Welcome to the game of life!");
		System.out.println("please edit sampleData.txt to edit the starting board configuration.");
		System.out.println("type \"quit\" to exit the program."); 
		System.out.println("type \"tick\" to go to the next stage of the board.\n");
		while (!done) {
			System.out.println(allCells);
			if (reader.hasNext("quit")) {
				done = true;
				break;
			}
			else {
				allCells.tick();
				System.out.println();
				reader.next();
			}
		}
		System.out.println("goodbye!");
		reader.close();
	}
}