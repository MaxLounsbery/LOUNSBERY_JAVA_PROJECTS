package ConwaysGameOfLife;
/**
 * Board stores the information of the board.
 * It can access the information itself, or 
 * send it out as a string.
 */
public class Board {
    //throughout the program I use these instance variables in place of board.length, as I would guess that 
    //checking a variable in memory would be faster than using a getter method.
    public static final int ROW_BOUNDS = 20;
    public static final int COLUMN_BOUNDS = 20;
    //the board will use a 2d String array
    private String[][] board;
    //in order to not check newly-changed cells mid processing, a checkerboard(get it?) must be made to examine a clean slate
    private String[][] checkerBoard = new String[ROW_BOUNDS][COLUMN_BOUNDS];

    /**
     * the instance method of Board, will fill the board up with the given text file.
     * @param fileName the file to give and check through
     */
    public Board(String fileName) {
        //access the file with BoardReader
        BoardReader reader = new BoardReader(fileName);
        //create space in memory for the board.
        board = new String[ROW_BOUNDS][COLUMN_BOUNDS];
        //fill board with the contents of the given file.
        for (int row = 0;row < ROW_BOUNDS; row++) {
            for (int column = 0; column < COLUMN_BOUNDS; column++) {
                //the next cell is made in advance to avoid a double-check.
                String next = reader.getNextCell();
                //if next isn't done, apply it
                if (next != "end") {
                    board[row][column] = next;
                }
            }
        }
    }

    /**
     * increments the board by one generation.
     * kills or repopulates according to the rules of the game of life.
     */
    public void tick() {   
        //wrap the entire process into a nested for loop, as I want an action to happen per cell
        //making checkerBoard equal to board, so it can be checked accurately.
        for (int row = 0; row < ROW_BOUNDS; row++) {
            for (int column = 0; column < COLUMN_BOUNDS; column++) {
                checkerBoard[row][column] = board[row][column];
            }
        } //why are arrays pass by reference when primitives and strings aren't?
        //wrap the entire process of checking/changing into a nested for loop, as I want an action to happen per cell
        for (int row = 0; row < ROW_BOUNDS; row++) {
            for (int column = 0; column < COLUMN_BOUNDS; column++) {
                //check the amount of cells nearby, and store the value
                int cells = checkNear(row, column);

                //if there are less than 2 cells, this cell is dead.(no support)
                //if there are more than 5 cells, this cell is dead.(overcrowding)
                //if there are exactly 5, this cell is alive.(repopulation/nourished)
                //if there are more than 3 cells, this cell is dead.(overcrowding)
                //if there are exactly 3, this cell is alive.(repopulation/nourished)
                if (cells < 2) {
                    board[row][column] = ".";
                }
                else if (cells > 3) {
                    board[row][column] = ".";
                }
                else if (cells == 3) {
                    board[row][column] = "#";
                }
            }
        }
    }// end of tick

    /**
     * checks the amount of nearby alive cells(#) surrounding the given cell
     * @param row the row of the cell to check
     * @param column the column of the cell to check
     * @return the amount of living cells surrounding this cell
     */
    public int checkNear(int row, int column) {
        int cells = 0;
        //the grid must check all adjacent (or 1 around) the given point.
        //this means I need a nested for loop, where the checked row(checkR) is the row minus 1.
        //and where the checked column(checkC) is the column minus 1. both get incremented in the for loop, but
        //the check will be skipped if they are out of bounds.
        for(int checkR = -1; checkR <= 1; checkR ++) {
            //if the row is out of bounds, don't increment
            int combRow = checkR + row;
            if (combRow < 0 || combRow >= ROW_BOUNDS) {
                //I know you don't like continues, but here it just saves else checks. I'm not sure if it's better to do a continue or an else, but there you have it.
                continue;
            }
            for (int checkC = -1; checkC <= 1; checkC++) {
                int combCol = checkC + column;
                //if the column is out of bounds, don't increment
                if (combCol < 0 || combCol >= COLUMN_BOUNDS) {
                    continue;
                }
                //if the point is the one to check, don't increment
                //if the point is the one being checked around, don't increment
                else if (combRow == row && combCol == column) {
                    continue;
                }
                //if the point is alive, increment the alive count.
                else if (checkerBoard[combRow][combCol].equals("#")) {
                    cells++;
                }
            }
        }
        return cells;
    } //end of checkNear

    /**
     * @return the current board as a string, by turning each row into a new line.
     */
    public String toString() {
        String output = "";
        for (int row = 0; row < board.length; row++) {
            for (int collumn = 0; collumn < board[row].length; collumn++) {
               output += (board[row][collumn] + " ");
            }
            output += "\n";
         }
        return output;
    }
}