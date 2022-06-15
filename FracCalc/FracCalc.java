package FracCalc;

/**
 The main FracCalc class, which will use Fraction to help the user solve fraction problems
 */
import java.util.Scanner;

public class FracCalc {
	/**
    The command line for FracCalc, with a constant while loop to endlessly check user input while the user wants it.
    */
   public static void main(String[] args) {
   	// main variables for the user input and loop control
      Scanner console_input = new Scanner(System.in);
      boolean done = false;
   
      // Instructions  
      System.out.println("Welcome to FracCalc! I calculate fraction equations.");
      System.out.println("You can enter in a plain number, mixed fraction, or standard fraction for your equations");
      System.out.println("The syntax for an equation is: \"5_3/4 + 6_7/2\", or \"5/3 * 7/1\"");
      System.out.println("Type \"quit\" to exit the program.");
   	
   	// Calculator
      do {
      
      	// Get the input from the user
         System.out.println("Please enter a fraction to be calculated, or quit");
         String user_input = console_input.nextLine();
      	
         if (user_input.equalsIgnoreCase("quit") )
         {
            done = true;
         }
         else
         {
            Fraction result = calculate(user_input);
            System.out.println("The answer is: " + result);
         }
      	
      // Exit condition met?  If not go around again!
      } while (!done);
   	
      console_input.close();
   	
   } // end main
	
   /**
    * the calculate() method of FracCalc will parse user input into fractions and operators.
    * the fractions will be sent to the Fraction class to be parsed into proper Fraction objects.
    * operators will be used to call the math methods within Fraction, and get a fully-processed fraction.
    * @param input which contains the user's input for the fraction.
    * @return operatedFrac, the users fully completed fraction.
    */
   public static Fraction calculate(String input) {
      Fraction operatedFrac = null;
      //as long as the user follow the syntax, a simple fraction simplification with no operator will have no space in it
      if (input.contains(" ")) {
      // initializing the variables that will be used within calculate() if the user is performing an operation, and not simplification.
         int lengthTillFirstSpace = input.indexOf(" ");
         int lengthTillSecondSpace = input.lastIndexOf(" ");
      
         operatedFrac = new Fraction(input.substring(0, lengthTillFirstSpace));
         Fraction operatorFrac = new Fraction(input.substring(lengthTillSecondSpace + 1));
         String operator = input.substring(lengthTillFirstSpace + 1,lengthTillSecondSpace);
      
         if (operator .equals ("+")) {
            operatedFrac.add(operatorFrac);
         }
         else if (operator .equals ("-")) {
            operatedFrac.subtract(operatorFrac);
         }
         else if (operator .equals ("*")) {
            operatedFrac.multiply(operatorFrac);
         }
         else {
            operatedFrac.divide(operatorFrac);
         }
      
      }
      //I know we don't need to allow for single-fraction parsing/simplification, but it could be nice for the user.
      //Plus, it makes debugging/testing the fraction parsing so much easier.
      else {
         operatedFrac = new Fraction(input);
      }
      return operatedFrac;
   	
   } // end calculate
	
} // end FracCalc class