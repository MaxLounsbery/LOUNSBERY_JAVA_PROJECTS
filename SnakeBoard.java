import java.util.ArrayList;
import java.awt.Point;

public class SnakeBoard {
    public static final int BOARD_X_SIZE = 52;
    public static final int BOARD_Y_SIZE = 42;
    public static final int START_X_OFFSET = 4; //starts the snake to x=1/z the board size, with z being the board x length
    public static final int START_Y_OFFSET = 2; //starts the snake to y=1/z the board size, with z being the board y length

    public static final char EMPTY_SPACE_CHARACTER = '　';
    public static final char BORDER_CHARACTER = '▢';
    public static final char APPLE_CHARACTER = '∘';

    public static int[][] board;
    public static ArrayList<Point> snake;
    public static void main(String[] args) {
        initializeBoard();
        initializeSnake();
        printBoard();
    }

    private static void initializeBoard() {
        board = new int[BOARD_Y_SIZE][BOARD_X_SIZE];
        for (int row = 0; row < BOARD_Y_SIZE; row++) {
            for (int col = 0; col < BOARD_X_SIZE; col++) {
                board[row][col] = 0;
            }
        }
    }

    private static void initializeSnake() {
        snake = new ArrayList<Point>();
        snake.add(new Point((BOARD_Y_SIZE / START_Y_OFFSET),(BOARD_X_SIZE / START_X_OFFSET)));
    }

    private static void printBoard() {
        for (int row = 0; row < BOARD_Y_SIZE; row++) {
            for (int col = 0; col < BOARD_X_SIZE; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }
}
