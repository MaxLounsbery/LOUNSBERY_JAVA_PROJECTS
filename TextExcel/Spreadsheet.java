package TextExcel;

public class Spreadsheet {

   // final variables
   private static final int ROW_NUM = 10;
   private static final int COL_NUM = 7;
   private static final String[] COLHEADER = { "", "A", "B", "C", "D", "E", "F", "G", "H"};

   // double array for the 10x7 grid
   Cell[][] cells = new Cell[ROW_NUM][COL_NUM];

   //TODO: NO MAGIC NUMBERS

   /**
    * the constructor of the spreadsheet, initializes the cells to be placed within the array.
    */
   public Spreadsheet() {
      for (int i = 0; i < ROW_NUM; i++) {
         for (int y = 0; y < COL_NUM; y++) {
            cells[i][y] = new Cell();
         }
      }
   }

   /**
    * prints out the entirety of the spreadsheet with cells and header
    */
   public void printSpreadsheet() {
      System.out.print(" ");
      for (int i = 0; i <= COL_NUM; i++) {
         System.out.print(makeTwelve(COLHEADER[i].toString()));
         System.out.print("|");
      }
      System.out.println();
      printDivider();
      for (int i = 0; i < ROW_NUM; i++) {
         System.out.print(makeTwelve(String.valueOf(i + 1)) + " ");
         for (int y = 0; y < COL_NUM; y++) {
            System.out.print("|");
            System.out.print(makeTwelve(cells[i][y].toString()));
         }
         System.out.print("|");
         System.out.println();
         printDivider();
      }
   }

   /**
    * prints the horizontal divider for the printout of the spreadsheet
    */
   private void printDivider() {
      System.out.print("-");
      for (int y = 0; y < COL_NUM + 1; y++) {
         System.out.print("------------+");
      }
      System.out.println();
   }

   /**
    * makes the input twelve characters and truncates if it goes over
    * 
    * @param input the inputted characters to be modified
    * @returns the modified input of the characters
    */
   private String makeTwelve(String input) {
      int numCount = input.length();
      boolean emptySide = true;
      if (numCount > 12) {
         input = input.substring(0, 11) + "\u001b[31m>\u001b[0m"; //makes the greater than sign red, so the user knows it's not theirs.
      } else {
         while (numCount < 12) {

            // pad the left side
            if (emptySide == true) {
               input = " " + input;
               emptySide = false;
            }

            // then pad the right side
            else {
               input = input + " ";
               emptySide = true;
            }

            // until it equals 12
            numCount++;
         }
      }
      return input;
   }

   /**
    * Given a column and row, ascertains whether that is a valid cell position, and returns the status of the cell
    *
    * @param column the column of the cell to be checked
    * @param row the row of the cell to be checked
    * @return the cell's status as a string, and the phrase, "SRB2SfBMPpnuK62ghHFIhU3J0gYYD0tLR7xo7JJChpN6WZSLks5YvS9UtyFD" if that is an invalid cell
    */ 
   public String cellExaminer(String column, int row) {
      String cellStatus = "SRB2SfBMPpnuK62ghHFIhU3J0gYYD0tLR7xo7JJChpN6WZSLks5YvS9UtyFD"; // hopefully the user doesn't put this into a string cell, as this is the error checker for an invalid cell
      if (row > ROW_NUM || row < 1) {
         return cellStatus;
      }

      int col = getColumnNumber(column);
      if (col == -1) {
         return cellStatus;
      }
      cellStatus = cells[row - 1][col - 1].print();
      return cellStatus;
   }

   /**
    * clears the whole board, setting all cells to empty cells.
    */
   public void clear() {
      for (int i = 0; i < ROW_NUM; i++) {
         for (int y = 0; y < COL_NUM; y++) {
            cells[i][y] = new Cell();
         }
      }
      System.out.println("Spreadsheet Cleared.");
   }

   /**
    * clears a specified cell, setting it to an empty cell
    *
    * @param cell the cell that will be cleared
    */
   public void clear(String cell) {
      String column = cell.substring(0, 1);
      int row = Integer.parseInt(cell.substring(1));
      cells[row - 1][getColumnNumber(column) - 1] = new Cell();
      System.out.println(cell + " cleared.");
   }

   /**
    * clears a range of cells, setting them to empty
    * @param range the range to clear, should be given as "[cell1] - [cell2]"
    */
   public void clearRange(String range) {
      int col1 = getColumnNumber(range.substring(0, 1)) - 1;
      int row1 = Integer.parseInt(range.substring(1, range.indexOf(" "))) - 1; //makes the row the value of the first letter, to right before the next space.

      range = range.substring(range.indexOf("-")); //removes the leading cell, as it could be 2 or 3 characters.
                                                   //range is now "- [cell]"

      int col2 = getColumnNumber(range.substring(2, 3)) - 1;
      int row2 = Integer.parseInt(range.substring(3)) - 1;

      for (int row = row1; row <= row2; row++) {
         for (int col = col1; col <= col2; col++) {
            cells[row][col] = new Cell();
         }
      }
   }
   
   /**
    * a tester method, fills the board with number cells
    */
   public void fill() {
      for (int i = 0; i < ROW_NUM; i++) {
         for (int y = 0; y < COL_NUM; y++) {
            cells[i][y] = new NumberCell("5");
         }
      }
      System.out.println("Board filled.");
   }

   /**
    * creates a new number cell with a specified number, expressed as a string
    *
    * @param number the value of the number cell
    * @param column the column of the cell
    * @param row the row of the cell
    * @return "NO ERROR" if no error is present, and "ERROR: INVALID CELL CONSTRUCTION. SHOULD BE A LETTER FROM A TO G FOLLOWED BY A NUMBER FROM 1 TO 10" if the column and row do not match a cell.
    */
   public String assignNumber(String number, String column, int row) {
      String errorCheck = checkError(column, row);
      if (!errorCheck.equals("NO ERROR")) {
         return errorCheck;
      }

      else {
         int col = getColumnNumber(column);
         cells[row - 1][col - 1] = new NumberCell(number);
      }

      return errorCheck;
   }
   
   /**
    * creates a new formula cell with a specified input, expressed as a string
    *
    * @param formula the formula to be stored within the cell
    * @param column the column of the cell
    * @param row the row of the cell
    * @return "NO ERROR" if no error is present, and "ERROR: INVALID CELL CONSTRUCTION. SHOULD BE A LETTER FROM A TO G FOLLOWED BY A NUMBER FROM 1 TO 10" if the column and row do not match a cell.
    */
   public String assignFormula(String formula, String column, int row) {
      String errorCheck = checkError(column, row);
         if (!errorCheck.equals("NO ERROR")) {
            return errorCheck;
         }

         else {
            int col = getColumnNumber(column);
            cells[row - 1][col - 1] = new FormulaCell(formula, this);
         }
         return errorCheck;
   }
         
   /**
    * creates a new string cell with a specified string, expressed as a string
    *
    * @param number the string to be stored within the cell
    * @param column the column of the cell
    * @param row the row of the cell
    * @return "NO ERROR" if no error is present, and "ERROR: INVALID CELL CONSTRUCTION. SHOULD BE A LETTER FROM A TO G FOLLOWED BY A NUMBER FROM 1 TO 10" if the column and row do not match a cell.
    */
   public String assignString(String text, String column, int row) {
      String errorCheck = checkError(column, row);
      if (!errorCheck.equals("NO ERROR")) {
         return errorCheck;
      }

      else {
         int col = getColumnNumber(column);
         cells[row - 1][col - 1] = new StringCell(text);
      }

      return errorCheck;
   }

   /**
    * returns the value of a cell, used for formulacells
    * @param row the row to return. 1 based
    * @param column the column to return. 1 based
    * @return the value of the cell requested
    */
   public double getCellValue(int row, int column) {
      String cellString = cells[row - 1][column - 1].toString();
      Double value = Double.parseDouble(cellString);

      return value;
   }

   /**
    * returns the cell type
    * @param row the row of the cell
    * @param column the column of the cell
    * @return the cell type as "Number", "String", "Formula", or ""
    */
   public String getCellType(int row, int column) {
        row--;
        column--;
        if (cells[row][column] instanceof NumberCell) {
            return "Number";
        }
        if (cells[row][column] instanceof StringCell) {
            return "String";
        }
        if (cells[row][column] instanceof FormulaCell) {
            return "Formula";
        }
        else {
            return "";
        }
   }

   /**
    * sums the total of a range of cells. I thought for a bit, but couldn't find a way to get a range method as java only allows for the returning of 1 value.
    * @param range the range of cells. Should be in the format of "[cell1] - [cell2]"
    * @return the sum.
    */
   public String sumRange(String range) {
      double solution = 0;

      int col1 = getColumnNumber(range.substring(0, 1)) - 1;
      int row1 = Integer.parseInt(range.substring(1, range.indexOf(" "))) - 1; //makes the row the value of the first letter, to right before the next space.

      range = range.substring(range.indexOf("-")); //removes the leading cell, as it could be 2 or 3 characters.
                                                   //range is now "- [cell]"

      int col2 = getColumnNumber(range.substring(2, 3)) - 1;
      int row2 = Integer.parseInt(range.substring(3)) - 1;

      if (col1 > col2) {
         //swap the two values so that the nested for loop works no matter how the user puts it in.
         int tempCol = col1;
         col1 = col2;
         col2 = tempCol;
      }

      if (row1 > row2) {
         int tempRow = row1;
         row1 = row2;
         row2 = tempRow;
      }

      for (int row = row1; row <= row2; row++) {
         for (int col = col1; col <= col2; col++) {
            if (cells[row][col] instanceof NumberCell ) { //if this cell is a number, add it.
               double value = Double.parseDouble(cells[row][col].toString()); //the value to add is the parsed double of the cell at the row, col
               solution += value;
            }
         }
      }

      return Double.toString(solution);
   }

    /**
    * averages the total of a range of cells. I thought for a bit, but couldn't find a way to get a range method as java only allows for the returning of 1 value.
    * @param range the range of cells. Should be in the format of "[cell1] - [cell2]"
    * @return the average.
    */
    public String averageRange(String range) {
      double solution = 0;

      int col1 = getColumnNumber(range.substring(0, 1)) - 1;
      int row1 = Integer.parseInt(range.substring(1, range.indexOf(" "))) - 1; //makes the row the value of the first letter, to right before the next space.

      range = range.substring(range.indexOf("-")); //removes the leading cell, as it could be 2 or 3 characters.
                                                   //range is now "- [cell]"

      int col2 = getColumnNumber(range.substring(2, 3)) - 1;
      int row2 = Integer.parseInt(range.substring(3)) - 1;

      if (col1 > col2) {
         //swap the two values so that the nested for loop works no matter how the user puts it in.
         int tempCol = col1;
         col1 = col2;
         col2 = tempCol;
      }

      if (row1 > row2) {
         int tempRow = row1;
         row1 = row2;
         row2 = tempRow;
      }

      int count = 0;
      for (int row = row1; row <= row2; row++) {
         for (int col = col1; col <= col2; col++) {
            if (cells[row][col] instanceof NumberCell ) { //if this cell is a number, add it.
               double value = Double.parseDouble(cells[row][col].toString()); //the value to add is the parsed double of the cell at the row, col
               solution += value;
               count++;
            }
         }
      }

      solution /= count;
      return Double.toString(solution);
   }

   /**
    * checks a given column/row input, and determines if that column and row are valid within the cell
    *
    * @param column the column to be checked
    * @param row the row to be checked
    * @return "ERROR: INVALID CELL CONSTRUCTION. SHOULD BE A LETTER FROM A TO G FOLLOWED BY A NUMBER FROM 1 TO 10" if an error is present, and "NO ERROR" if this is a valid cell configuration.
    */
   private String checkError(String column, int row) {
      String error = "ERROR: INVALID CELL CONSTRUCTION. SHOULD BE A LETTER FROM A TO G FOLLOWED BY A NUMBER FROM 1 TO 10";

      if (row > ROW_NUM || row < 1) {
         return error;
      }

      int col = getColumnNumber(column);

      if (col == -1) {
         return error;
      }

      return "NO ERROR";
   }
   /**
    * sorts a range of cells in ascending or descending order using insertion sort
    * @param range the cell boundaries to sort. Must be given in the form of "[cell1] - [cell2]"
    * @param ascend whether to sort in ascending or descending order. A value of true implies ascending.
    */
   public void sortRange(String range, boolean ascend) {

      int col1 = getColumnNumber(range.substring(0, 1)) - 1;
      int row1 = Integer.parseInt(range.substring(1, range.indexOf(" "))) - 1; //makes the row the value of the first letter, to right before the next space.

      range = range.substring(range.indexOf("-")); //removes the leading cell, as it could be 2 or 3 characters.
                                                   //range is now "- [cell]"

      int col2 = getColumnNumber(range.substring(2, 3)) - 1;
      int row2 = Integer.parseInt(range.substring(3)) - 1;

      if (col1 > col2) {
         //swap the two values so that the nested for loop works no matter how the user puts it in.
         int tempCol = col1;
         col1 = col2;
         col2 = tempCol;
      }

      if (row1 > row2) {
         int tempRow = row1;
         row1 = row2;
         row2 = tempRow;
      }

      //converting the 2d array into a 1d array for much simpler sorting.
      int colLength = col2 - col1 + 1;
      int rowLength = row2 - row1 + 1;
      double[] oneDArray = new double[colLength * rowLength];

      int oneDArrPosition = 0; // col and row below will not correlate correctly with a one dimensional array, so another value is needed.
      for (int row = row1; row <= row2; row++) {
         for (int col = col1; col <= col2; col++) { // we want row-major order, so columns will change before rows.
            oneDArray[oneDArrPosition] = Double.parseDouble(cells[row][col].toString());
            oneDArrPosition++;
         }
      }
      insertionSort(oneDArray, ascend);

      //convert the 1d array back into the spreadsheet.
      oneDArrPosition = 0;
      for (int row = row1; row <= row2; row++) {
         for (int col = col1; col <= col2; col++) { // we want row-major order, so columns will change before rows.
            cells[row][col] = new NumberCell(Double.toString(oneDArray[oneDArrPosition]));
            oneDArrPosition++;
         }
      }
   }

   /**
    * sorts the given array
    * @param array the array to sort
    * @param ascend whether to sort from smallest num to biggest num, or biggest num to smallest num. True implies smallest to biggest
    */
   private void insertionSort(double[] array, boolean ascend) {
      int size = array.length;
      if (ascend) { //instead of checking within the for loop, it would be more processor-efficient to just check now, even if it's a bit repetitive code-wise. 
         for (int step = 1; step < size; step++) {
           double key = array[step];
           int j = step - 1;

            // compare key with each element on the left of it until an element smaller than it is found
            while (j >= 0 && key < array[j]) {
               array[j + 1] = array[j];
               --j;
           }

         // Place key at after the element just smaller than it.
         array[j + 1] = key;
         }
         return;
      }
      //we're not ascending, we're descending
      for (int step = 1; step < size; step++) {
         double key = array[step];
         int j = step - 1;

         // compare key with each element on the left of it until an element smaller than it is found
         while (j >= 0 && key > array[j]) { // descension difference is here, the loop continues as long as the key is larger than the comparison
            array[j + 1] = array[j];
            --j;
         }

      // Place key at after the element just smaller than it.
      array[j + 1] = key;
      }
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