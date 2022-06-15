package MasterMind;
/**
 The object class for the mastermind game.
 */

import java.util.Random;

public class MasterMind {
   
   private int peg1;
   private int peg2;
   private int peg3;
   private int peg4;
   
   private boolean gameWon = false;
      
   public MasterMind() {
      Random random = new Random();
      
      int HIGHEST_NUMBER = 6;
      int LOWEST_NUMBER = 1;
      
      peg1 = random.nextInt(HIGHEST_NUMBER) + LOWEST_NUMBER;
      peg2 = random.nextInt(HIGHEST_NUMBER) + LOWEST_NUMBER;
      peg3 = random.nextInt(HIGHEST_NUMBER) + LOWEST_NUMBER;
      peg4 = random.nextInt(HIGHEST_NUMBER) + LOWEST_NUMBER;
   }
   
   public boolean checkWin() {
      return gameWon;
   }
   
   public void check(String toCheck) {
      //makes it so the user doesn't have to be particular with their spaces or not.
      toCheck = toCheck.replaceAll("\\s+","");
      int guess1 = Integer.parseInt(Character.toString(toCheck.charAt(0)));
      int guess2 = Integer.parseInt(Character.toString(toCheck.charAt(1)));
      int guess3 = Integer.parseInt(Character.toString(toCheck.charAt(2)));
      int guess4 = Integer.parseInt(Character.toString(toCheck.charAt(3)));
      
      int numberCorrect = 0;
      int positionCorrect = 0;
      
      if (guess1 == peg1) {
         positionCorrect++;
      }
      
      if ((guess1 == peg2) || (guess1 == peg3) || (guess1 == peg4)) {
         numberCorrect++;
      }
      
      if (guess2 == peg2) {
         positionCorrect++;
      }
      
      if ((guess2 == peg1) || (guess2 == peg3) || (guess2 == peg4)) {
         numberCorrect++;
      }
      
      if (guess3 == peg3) {
         positionCorrect++;
      }
      
      if ((guess3 == peg1) || (guess3 == peg2) || (guess3 == peg4)) {
         numberCorrect++;
      }
      
      if (guess4 == peg4) {
         positionCorrect++;
      }
      
      if ((guess4 == peg1) || (guess4 == peg2) || (guess4 == peg3)) {
         numberCorrect++;
      }
      
      System.out.println("You had " + positionCorrect + " guesses with the right position and number, and " + numberCorrect + "guesses with a number that appears in a different spot.");
      
      if (positionCorrect == 4) {
         gameWon = true;
      }
      
   }
   
   public String toString() {
      return ("" + peg1 + peg2 + peg3 + peg4);
   }
}