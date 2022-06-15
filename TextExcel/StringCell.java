package TextExcel;

public class StringCell extends Cell {

/**
 * cuts off quotations of string
 * 
 * @param inputted text
 */
    public StringCell(String text) {
        super(text);
    }

    public String toString() {
        String text = super.print();
        text = text.substring(1, text.length() - 1);
        return text;
    }
}