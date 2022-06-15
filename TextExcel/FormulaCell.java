package TextExcel;

public class FormulaCell extends Cell {

      private static final String[] COLHEADER = { "", "A", "B", "C", "D", "E", "F", "G" }; //used for converting cells to data

      Spreadsheet cells;
      
      /**
       * chops off brackets of formula
       * 
       * @param inputted formula
       */
      public FormulaCell(String formula, Spreadsheet spreadsheet) {
            super(formula);
            cells = spreadsheet;
      }

      /**
       * overrides ToString for formula
       * 
       * @returns "<formula>"
       */
      public String toString() {
            String formula = super.print().substring(1, super.print().length() - 1); // removes the parenthesis so it can be worked on more easily
            if (formula.contains("sum")) {
                  String solution = solveSum(formula);
                  return solution;
            }

            else if (formula.contains("avg")) {
                  String solution = solveAvg(formula);
                  return solution;
            }
            String solution = solve(formula);
            //System.out.println(solution);
            return solution;
      }

      /**
       * turns a formula sequence into a single number, as a String
       * @param formula the formula to solve
       * @return the solved formula as a single number
       */
      private String solve(String formula) {
            
            formula = formula.trim(); //removes surrounding spaces within parenthesis
            if (formula.indexOf(" ") == -1) { //if no spaces are found, it is a single value. It could still be a cell reference, however.
                  if (Character.isLetter(formula.charAt(0))) { //checks if the first character is a letter, meaning the whole formula is just referencing another cell
                        return Double.toString(getCell(formula));
                  }

                  else return formula;
            }
            //there's guarunteed still things to work on, so no need for an else

            String stringNum1 = formula.substring(0, formula.indexOf(" "));
            double num1 = stringToDouble(stringNum1); //the first of the terms

            String operator = formula.substring(formula.indexOf(" ") + 1, formula.indexOf(" ") + 2); //designates the String operator to be the substring between the first and second space of formula
            
            /*
             * if a space is added to the end of formula, less if-checks can be used, and it will be less complicated.
             * If the formula had more terms than just two, it doesn't matter, as the string will be trimmed regardless.
             * If the formula was just the two terms, then I can simply find the index of the third space to space things out.
             * Otherwise, I would have to if-else whether there was a third space or not, and do very similar but different statements accordingly.
             */
            formula = formula.concat(" ");

            int secondOccurence = findNthOccurrence(formula, 2); //designates the second occurence of the space to be used for later
            int thirdOccurence = findNthOccurrence(formula, 3);  //designates the third occurence of the space to be used for later

            String stringNum2 = formula.substring(secondOccurence + 1, thirdOccurence);
            double num2 = stringToDouble(stringNum2); //the second of the terms

            String combinedNumber = math(num1, operator, num2);

            return solve(combinedNumber + formula.substring(thirdOccurence)); //combines the remainder of the String, by adding the number to the string without the two terms, and solves the remainder
      }

      /**
       * converts a formula into a sum. Ranges are handled within spreadsheet.
       * @param formula the formula to sum up.
       * @return the sum of the range as is in a String.
       */
      private String solveSum(String formula) {
            formula = formula.trim();
            String range = formula.substring(formula.indexOf(" ") + 1); //removes the "sum" from it. we already know it'll sum.
            String solution = cells.sumRange(range);
            return solution;
      }

      /**
       * converts a formula into an average. Ranges are handled within spreadsheet.
       * @param formula the formula to average.
       * @return the average of the range as is in a String.
       */
      private String solveAvg(String formula) {
            formula = formula.trim();
            String range = formula.substring(formula.indexOf(" ") + 1); //removes the "avg" from it. we already know it'll sum.
            String solution = cells.averageRange(range);
            return solution;
      }

      /**
       * converts a string into a double, expecting no spaces within and regardless of whether the string represents a cell or not.
       * @return the string as a double
       */
      private double stringToDouble(String string) {
            if (Character.isLetter(string.charAt(0))) {
                  return getCell(string);
            }
            return Double.parseDouble(string);
      }

      /**
       * combines two numbers into a String with the operator
       * @param num1 the first number to be operated
       * @param operator the operator to affect the numbers
       * @param num2 the second number to be operated
       * @return the operated, combined number
       */
      private String math(double num1, String operator, double num2) {
            if (operator.equals("+")) {
                  return Double.toString(num1 + num2);
            }
            if (operator.equals("-")) {
                  return Double.toString(num1 - num2);
            }
            if (operator.equals("*")) {
                  return Double.toString(num1 * num2);
            }
            if (operator.equals("/")) {
                  return Double.toString(num1 / num2);
            }
            return Double.toString(num1 % num2);
      }

      /**
       * @return the cell requested
       */
      private double getCell(String cell) {
            double number = 0;

            int column = getColumnNumber(cell.substring(0, 1));
            int row = Integer.parseInt(cell.substring(1));

            if (cells.getCellType(row, column).equals("String") || cells.getCellType(row, column).equals("")) {
                  System.out.println("\n\n\nERROR: CELL WITHIN FORMULA IS A STRING OR CLEARED CELL.\n\n\n");
                  return 0;
            }
            number = cells.getCellValue(row, column);

            return number;
      }

      /**
       * finds the Nth occurence of space within a String. 
       * Used to get positioning within Strings for formulas.
       * @param str the String to be checked through
       * @param N the number of occurence to be checked
       * @return the position of the Nth occurence of space, or -1 if not present.
       */
      static int findNthOccurrence(String str, int N) {
            int occur = 0;
            // Loop to find the Nth
            // occurrence of the character
            for (int i = 0; i < str.length(); i++) {
                  if (str.charAt(i) == ' ') {
                  occur += 1;
                  }

                  if (occur == N) {
                        return i;
                  }
            }
            return -1;
      }

      /**
       * converts from the spreadsheet's letter heading into a number to be used for arrays
       *
       * @param column the column to be converted
       * @return the number of the letter
       */
      public int getColumnNumber(String column) {
      column = column.toUpperCase();
      int columnNum = -1;
      for (int i = 0; i < COLHEADER.length; i++) {
         if (COLHEADER[i].equals(column)) {
            columnNum = i;
         }
      }
      return columnNum;
   }
}