package ConwaysGameOfLifeBitwise;
/**
 * BoardLife stores the information of the board, and can send it out as a string
 * It also modifies the board's information according to the rules of the game of life.
 */
public class BoardLife {
    //the board will use a long, modifying the bits of it to represent an 8x8 grid.
    private long board = 0;
    private long checkerBoard;

    /**
     * the instance method of Board, will fill the board up with the given text file.
     * @param fileName the file to give and check through to create the layout of the board.
     */
    public BoardLife(String fileName) {
        //access the file with BoardReader
        BoardReaderLife reader = new BoardReaderLife(fileName);

        //fill board with the contents of the given file. Will always be 64, the length of a long.
        for (int count = 0; count < 64; count++) {
            board = board << 1;
            if (reader.getNextCell() .equals ("#")) {
                board = setLastBoardBit();
            }
        }
    }// end BoardLife()

    /**
     * increments the board by one generation.
     * kills or repopulates according to the rules of the game of life.
     */
    public void tick() {   
        checkerBoard = board;
        for (byte position = 63; position >= 0; position--) {
            byte neighbors = neighborCount(position);
           /*
            if there are less than 2 neighbors, the cell is dead
            if there are 2 neighbors and the cell is alive, the cell is alive
            if there are more than 3 neighbors, the cell is dead
            if there are 3 neighors, the cell is alive
            I have to set evey board bit throughout the program, which is why i have to 
            set already set bits when the neighbor count is 2
            */
            if (neighbors < 2) {
                unsetBoardBit(position);
            }
            if (neighbors == 2 && (getCheckerBoardBit(position)) == 1) {
                setBoardBit(position);
            }
            else if (neighbors > 3) {
                unsetBoardBit(position);
            }
            else if (neighbors == 3) {
                setBoardBit(position);
            }
        }
    } // end tick()

    /**
     * gets the amount of neighbors a cell has
     * @param position the position of the cell to check
     * @return the amount of living cells that the position's cell is adjacent to
     */
    public byte neighborCount(byte position) {
        byte neighbors = 0;
        //the bit can't be added with 1 without overlapping rows
        if (badOddNumberCheck(position)) {
            for (byte row = -8; row < 9; row += 8) {
                for (byte column = -1; column < 1; column++) {
                    neighbors += checkNeighbor(position, row, column);
                }
            }
        }
        //the bit can't be subtracted by 1 without overlapping rows
        else if (badEvenNumberCheck(position)) {
            for (byte row = -8; row < 9; row += 8) {
                for (byte column = 0; column < 2; column++) {
                    neighbors += checkNeighbor(position, row, column);
                }
            }
        }
        //the bit can be added and subtracted by 1 without overlapping rows
        else {
            for (byte row = -8; row < 9; row += 8) {
                for (byte column = -1; column < 2; column++) {
                    neighbors += checkNeighbor(position, row, column);
                }
            }
        }
        return neighbors;
    } //end neighborCount()

    /**
     * @param position the position of the cell
     * @param row the row around (or on) the cell to check
     * @param column the column around (or on) the cell to check
     * @return a 1 if a neighbor is present, or a 0 if no neighbor is present
     */
    private byte checkNeighbor(byte position, byte row, byte column) {
        if (position + row > 63 || position + row < 0) {
            return 0b0000;
        }
        if (position + row + column == position) {
            return 0b0000;
        }
        if (getCheckerBoardBit((byte)(position + row + column)) == 1) {
            return 0b0001;
        }
        return 0b0000;
    } // end checkNeighbor()

    /**
     * checks if the position is one of the numbers that would be on the side of the bit grid that is a multiple of 8
     * @param position the position to look at
     * @return whether the position is one of the bad even numbers
     */
    private boolean badEvenNumberCheck(byte position) {
        if (position % 8 == 0) {
            return true;
        }
        return false;
    } // end badEvenNumberCheck()

    /**
     * checks if the position is one of the numbers that would be on the side of the bit grid that is one below a multiple of 8
     * @param position the position to look at
     * @return whether the position is one of the bad odd numbers
     */
    private boolean badOddNumberCheck(byte position) {
        if (position % 8 == 7) {
            return true;
        }
        return false;
    } // end badOddNumberCheck()

    /**
     * unsets the designated board bit
     * @param position which board bit to unset
     */
    private void unsetBoardBit(byte position) {
        long mask = ~1L << position;
        board = mask & board;
    } // end unsetBoardBit()

    /**
     * sets the designated board bit
     * @param position which board bit to set
     */
    private void setBoardBit(byte position) {
        long mask = 1L << position;
        board = mask | board;
    } // end setBoardBit()

    /**
     * sets the last board bit
     * @return the board, with the last board bit set.
     */
    private long setLastBoardBit() {
        byte mask = 0b0001;
        board = mask | board;
        return board;
    } // end setLastBoardBit()

    /**
     * gets the checkerBoard bit of the designated position
     * @param position the position to check
     * @return a 1 or 0, whether there was a 1 or 0 there.
     */
    private byte getCheckerBoardBit(byte position) {
        long shiftBoard = checkerBoard >> position;
        if ((shiftBoard & 1) == 1) {
            return 0b0001;
        }
        return 0b0000;
    } // end getCheckerBoardBit()

    /**
     * returns the specified bit of board.
     * returns a string, as it is used only for toString()
     * @param position the position to check
     * @return the specified bit of board
     */
    private String getBoardBitString(int position) {
        long mask = 1L << position; 
        if ((board & mask) != 0) {
            return "1";
        }
        return "0";
    } // end getBoardBitString()

    /**
     * converts the Board into a String, split into 8 rows.
     * @return the Board as a String
     */
    public String toString() {
        //I can't use Long.toBinaryString(), not only because it crops out leading zeroes, but I need them split into rows of 8.
        //I also couldn't figure out why String.concat wouldn't work, so I had to go with String +=
        String output = "";
        
        for (byte i = 63; i >= 0; i--) {
                output += getBoardBitString(i);
                if ((i % 8 == 0) && (i != 0)) {
                    output += "\n";
                }
        }
        return output;
    } // end toString()
}