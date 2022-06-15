package ConwaysGameOfLifeBitwise;
import java.io.*;
import java.util.Scanner;

public class BoardReaderLife {
	Scanner input;
	String currentLine;
	/**
	 * Initializes this board reader, given a file.
	 * @param The path to the file to read.
	 * @throws FileNotFoundException
	 */
	public BoardReaderLife(String fileName)
	{
		try 
		{
			input = new Scanner(new File(fileName));			
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error!  File \"" + fileName + "\" was not found!");
			input = new Scanner("");
		}
		currentLine = "";
	}
	
	/** Reads the next character as a String.
	    @return Returns the next character in this file as a string.  
                    Returns "end" if the end of the file has been reached.
	    
	    */
	public String getNextCell()
	{
		//the line's empty, grab another one.
		if (currentLine.equals(""))
		{
			if (input.hasNextLine())
			{
				currentLine = input.nextLine();
			}
			else
			{
				return "end";
			}
		}
		
		// Remove one character from the string.
		String value = currentLine.substring(0, 1);
		currentLine = currentLine.substring(1);
		
		return value;
	}
}