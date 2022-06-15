package TextExcel;

public class Cell {
    private String cellCommand; // represents the initial user command that started the cell.

    /**
     * the default Cell constructor. Used for empty cells.
     */
    public Cell() {
        cellCommand = "<EMPTY>";
    }

    /**
     * the Cell constructor which will take a command, and change the initial user's
     * input according to that.
     * 
     * @param command the command which initiated the cell
     */
    public Cell(String command) {
        cellCommand = command;
    } // end Cell()

    /**
     * the Cell toString.
     * 
     * @return the cell's spreadsheet representation.
     *         If a number cell, returns it as a double, if a string, returns the
     *         string.
     *         If the cell is null, returns nothing.
     */
    public String toString() {
        return "";
    } // end toString()

    /**
     * @return the initial command which initialized the cell
     */
    public String print() {
        return cellCommand;
    } // end print()
} // end Cell