package MasterMind;
/**
  Will use the MasterMind class in order to let the user play MasterMind with inputs.
  */
  
import java.util.Scanner;

public class MasterMindPlay {
   
   //setting up a static variable
   static int ROUND_LIMIT = 10;
   /**
    main() only has one command here, in order to let the user continue to play again after every game if they so choose.
    */
   public static void main (String args[]) {            
      newGame();
   }
   /**
    newGame() is partially recursive, where if the user so chooses they can repeat a new game.
    newGame() intializes various integers for the main while loop of the game, which are reset each time newGame() calls itself.
    */
   public static void newGame() {
      //initializing a scanner
      Scanner reader = new Scanner(System.in);
      
      //initializing a new game of MasterMind, which will have a new set of numbers if on a new game.
      MasterMind game = new MasterMind(); 
      
      //initializing integers for the game.
      boolean finished = false;
      int round = 0;
      
      /**
       this while loop is the main chunk of MasterMindPlay's class.
       it gets user input, which it checks on for special sequences before sending it to MasterMindPlay.
       it also checks the players round, and will exit out once the players round is above 10.
       */
      while (!finished) {
         System.out.println("\nPlease input 4 numbers to try and guess the code, ranging from 1 to 6.\nType \"quit\" to leave.\nYou are on round: " + round);
         String toCheck = reader.nextLine();
         if (toCheck .equals("quit")) {
            System.out.println("You quit the game! The code was " + game + ".\nIf you want to play another round, type \"again\", or type anything else to leave.");
            finished = true;
            break;
         }
         //a debug/cheat solution to test the program. Sets the toCheck value to 0000, as the program crashes when checking text.
         if (toCheck .equals("givemethesolution")) {
            System.out.println(game);
            toCheck = "0000";
         }
         game.check(toCheck);
         finished = game.checkWin();
         
         if (finished) {
            System.out.println("You guessed the code correctly! It took you " + round + " rounds to win.\nIf you want to play another round, type \"again\", or type anything else to leave.");
            finished = true;
            break;
         } 
         
         if (round >= ROUND_LIMIT) {
            System.out.println("You could not guess the code in time! The code was " + game + ".\nIf you want to play another round, type \"again\", or type anything else to leave.");
            finished = true;
         }
         
         round++;
      }
      
      //checking to see if the user wants to play again, and re-calling the function if so.
      String checkContinue = reader.nextLine();
      reader.close();
      if (checkContinue .equals("again")) {
         newGame();
      }
   }
}