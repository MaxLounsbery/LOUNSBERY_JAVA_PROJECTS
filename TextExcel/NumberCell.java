package TextExcel;

public class NumberCell extends Cell {

    /**
     * checks if number has no decimal and adds .0 if it does not
     * sets new number to solution
     * 
     * @param inputted formula
     * @returns
     */
    public NumberCell(String number) {
        super(number);
    }

    /**
     * overrides of the Cell toString
     * 
     * @return the number of this cell with a decimal
     */
    public String toString() { 
        String number = super.print();
        if (!number.contains(".")) {
            number = number.concat(".0");
            
        }

        return number;
    }

    /**
     * gets the number representation of this cell, as opposed to a string
     * representation
     * 
     * @returns the cell's number.
     */
    public double getNumber() {
        double number = Double.parseDouble(super.toString());
        return number;
    }
}