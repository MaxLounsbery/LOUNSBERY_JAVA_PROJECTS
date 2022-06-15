package TextExcel;

import java.util.Scanner;

/**
 * The main TextExcel class, which will process commands in a main loop and
 * modify a spreadsheet accordingly
 * 
 * @param none
 * @returnsnone
 */
public class TextExcel {
    public static void main(String[] args) {
        boolean continueLoop = true; // sets up the command loop.
        Scanner reader = new Scanner(System.in); // initializing the Scanner object, reader, to process user command.
        Spreadsheet sheet = new Spreadsheet();
        System.out.println("Welcome to TextExcel!");
        System.out.println("Type 'help' to see commands.");

        while (continueLoop) { // the primary command loop for the user to control the spreadsheet.
            String input = reader.nextLine().trim();

            /*
             *I want to have an else statement running through the entire thing, 
             *but it would also be really nice for the user if they didn't have 
             *to worry about upper/lowercase. If a method within this 
             *occurs, hasAMethodRun will be made true, letting the else-if 
             *statement at the end run, even if it gets broken midway through.
             */
            boolean hasAMethodRun = false; 

            if (input.contains("=")) { //the user is assigning a cell

                String col = input.substring(0, 1); // cell is cut off right before the first space, le
               
                int row;

                try { //If the user inputs a letter row with a letter row, It will throw a NumberFormatException, as parseInt cannot parse text.
                    row = Integer.parseInt(input.substring(1, input.indexOf(" ")));
                } catch(NumberFormatException e){ //if that happens, make row = -1, so it will immediately be detected in the next statement.
                    row = -1;
                }

                if (input.contains("\"")) { //the input contains a double quote, and is a string designation
                    String text = input.substring(input.indexOf("\""));
                    String errorCheck = sheet.assignString(text, col, row);

                    if (!errorCheck.equals("NO ERROR")) {
                        System.out.println(errorCheck);
                    }
                }

                else if (input.contains("(")) { //the input contains a parenthesis, and is a formula
                    String formula = input.substring(input.indexOf("("));
                    String errorCheck = sheet.assignFormula(formula, col, row);

                    if (!errorCheck.equals("NO ERROR")) {
                        System.out.println(errorCheck);
                    }
                }

                else { //the input is a number
                    String number = input.substring(input.lastIndexOf(" ") + 1); //the first position of numbers, after the last space.
                    String errorCheck = sheet.assignNumber(number, col, row);

                    if (!errorCheck.equals("NO ERROR")) {
                        System.out.println(errorCheck);
                    }
                }
                hasAMethodRun = true;
            }
            input = input.toUpperCase(); //making everything uppercase makes telling what the user did easier.

            if (input.length() < 4 && input.length() > 1) { // if the input length is 3 or less, the user has to be querying a cell. If it's only 1 long, it's a typo.
                String col = input.substring(0, 1);
                int row;
                try { //If the user inputs a letter row with a letter row, It will throw a NumberFormatException, as parseInt cannot parse text.
                    row = Integer.parseInt(input.substring(1));
                } catch(NumberFormatException e){ //if that happens, make row = -1, so it will immediately be detected in the next statement.
                    row = -1;
                }

                if (row > 10 || row < 1) {
                    System.out.println("ERROR: INVALID ROW DESIGNATION. SHOULD BE A NUMBER FROM 1-10");
                }

                else {
                    String cellStatus = sheet.cellExaminer(col, row);

                    if (cellStatus.equals("SRB2SfBMPpnuK62ghHFIhU3J0gYYD0tLR7xo7JJChpN6WZSLks5YvS9UtyFD")) {
                        System.out.println("ERROR: INVALID LETTER FOR CELL");
                    }
                    else {
                        System.out.println(input + " = " + cellStatus);
                    }
                }
                hasAMethodRun = true;
            }

            else if (input.contains("SORT")) {
                if (input.substring(4, 5).equals("A")) { // the user is ascend sorting
                    sheet.sortRange(input.substring(6), true); //past the 5th index, the remainder should only be the cell ranges.
                }

                else { // the user is descend sorting.
                    sheet.sortRange(input.substring(6), false); //past the 5th index, the remainder should only be the cell ranges.
                }
                hasAMethodRun = true;
            }

            else if (input.contains("CLEAR")) { // the user is clearing something
                if (!input.contains(" ")) { // if the input doesn't contain a space, the user will only be clearing it
                    sheet.clear();
                }

                else {
                    if (input.contains("-")) {
                        sheet.clearRange(input.substring(input.indexOf(" ") + 1));
                    }

                    else {
                        sheet.clear(input.substring(input.lastIndexOf(" ") + 1)); // specifies the cell to be cleared by removing everything but the cell
                    }
                }
                hasAMethodRun = true;
            }

            else if (input.equalsIgnoreCase("QUIT")) { // the user is quitting
                continueLoop = false;
                hasAMethodRun = true;
            }

            else if (input.equalsIgnoreCase("PRINT")) { // the user is printing the spreadsheet
                sheet.printSpreadsheet();
                hasAMethodRun = true;
            }

            else if (input.equalsIgnoreCase("HELP")) { // the user is asking for general help
                help();
                hasAMethodRun = true;
            }

            else if (input.contains("HELP")) { // the user wants specific help
                help(input); 
                hasAMethodRun = true;
            }

            else if (input.contains("FILL")) {
                sheet.fill();
                hasAMethodRun = true;
            }

            else if (!hasAMethodRun){ // the user gave an unknown command
                System.out.println("ERROR: UNKNOWN ENTRY OR COMMAND");
            }
        }
        System.out.println("Goodbye.");
        reader.close();
        
    } // end main()

    /**
     * prints out the general help information
     */
    public static void help() {
        System.out.println("The commands you can use are: ");
        System.out.println("QUIT, which will quit the program.");
        System.out.println("PRINT, which will print the spreadsheet you've made.");
        System.out.println("CLEAR, which will clear your spreadsheet or specified cells.");
        System.out.println("A1, or any other cell name, to print that cell out.");
        System.out.println("=, which can be used with a cell and a number to assign cells.");
        System.out.println("(), which can be used to assign the specified formula to a cell.");
        System.out.println("SORTA, which will sort the specified cell range into acending order.");
        System.out.println("SORTB, which will sort the specified cell range into descending order.");
        System.out.println("HELP, which can be used with any of the previous commands for more specific info. on each.");
        
    } // end help()

    /**
     * prints out specific help information
     * 
     * @param input the user's initial command which started the help sequence
     */
    public static void help(String input) {
        if (input.contains("QUIT")) {
            System.out.println("QUIT will exit out of the spreadsheet, closing the program. No progress will be saved.");
            return;
        }

        if (input.contains("PRINT")) {
            System.out.println("PRINT will print out the spreadsheet you've made by assigning cells to various spots.");
            return;
        }

        if (input.contains("CLEAR")) {
            System.out.println("CLEAR can be used standalone to clear the entirety of the board, or with the syntax CLEAR [CELL] to clear a cell's information.\nCLEAR can also "); //TODO: add the clear info.
            return;
        }

        if (input.contains("A1")) {
            System.out.println("A1, or any other cell name, will print out what you designated that cell to be. \nIf you have stated C6 to be '537.8', C6 can be entered to show you 537.8");
            return;
        }

        if (input.contains("=")) {
            System.out.println("= can be used after a cell's name with the syntax [CELL] = [ASSIGNMENT] to assign cells. \nYou can make the cell's assignment a number or a series of text.");
            return;
        }

        if (input.contains("()")) {
            System.out.println("() can be an assignment to make a cell be a formula. \nThe formula can contain numbers or other cells with the syntax [CELL] = ([NUMBER/CELL] [OPERATOR, LIKE + OR *] [NUMBER/CELL]).\nYou can chain any amount of numbers or cells together, as long as there is an operator in between the two.");
            return;
        }

        if (input.contains("SORT")) {
            System.out.println("SORTA or SORTB can be used to sort a rectangle of cells. \nThe Syntax SORT[A OR B] [CELL #1] - [CELL #2] can be used to execute a sort.\nThe two cells inputed will form a rectangle within the two points. \nAll cells within the rectangle will be sorted in ascending or descending order.");
            return;
        }

        System.out.println("ERROR: HELP USED WITHOUT A KNOWN HELP MATCH");
        
    } // end help(String)
    
} // end TextExcel