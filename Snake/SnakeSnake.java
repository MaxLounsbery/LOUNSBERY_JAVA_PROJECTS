package Snake;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

  /**
   * The snake, which will recieve commands from SnakeGame such as playerUp or playerRight, and will then shift all the cells accordingly.
   */
public class SnakeSnake {
    private static final int START_X_OFFSET = 4; //starts the snake to x=1/z the board size, with z being the board x length
    private static final int START_Y_OFFSET = 2; //starts the snake to y=1/z the board size, with z being the board y length
    private static final int STARTING_LENGTH = 4;

    private static ArrayList<Character> moveHistory; //I don't want to create a new array for every single move just so that the segments know which way to go.
                                                    //Because of that, moveHistory will remember the moves done by being numerically linked to snakePosition with their ordering.
    private static ArrayList<Point> snakePosition;
    /**
     * Initializes the SnakeSnake class, setting the starting points
     * @param STARTING_LENGTH the starting length of the snake
     * @param BOARD_X_SIZE the width of the board excluding borders
     * @param BOARD_Y_SIZE the height of the board excluding borders
     */
    public SnakeSnake(int BOARD_X_SIZE, int BOARD_Y_SIZE) {
        snakePosition = new ArrayList<Point>();
        moveHistory = new ArrayList<Character>();
        int headXPosition = BOARD_X_SIZE / START_X_OFFSET;
        int headYPosition = (BOARD_Y_SIZE / START_Y_OFFSET) + 1; //the +1 is so that odd numbers work correctly and will have a centered snake
        for (int i = 0; i < STARTING_LENGTH; i++) {
            snakePosition.add(new Point(headXPosition - i, headYPosition)); //every segment is 1 to the left of the last
            moveHistory.add('D'); //by default, the snake is moving right
        }
    }

    public static void UpdateSnake(char latestMove, boolean appleCollected) {
        Point lastSegment = null; 
        //set each segment to move how the one right before it moved last
        for (int i = snakePosition.size(); i > 0; i--) {
            moveHistory.set(i, moveHistory.get(i - 1));
        }
        moveHistory.set(0, latestMove);

        if (appleCollected) {
            lastSegment = snakePosition.get(snakePosition.size() - 1); //duplicates the last point in the snake if an apple was collected
        }

        //move each segment according to what it has now
        for (int i = 0; i < snakePosition.size(); i++) {
            moveSegment(i, latestMove);
        }

        if (appleCollected) {
            snakePosition.add(lastSegment);
        }
    }

    private static void moveSegment(int segment, char move) {
        if (move == 'W') {
            snakePosition.get(segment).translate(0, -1); //W will move the snake up, but will move the y value down 1 as a larger Y value will represent going further down on the screen.
            return;
        }

        if (move == 'A') {
            snakePosition.get(segment).translate(-1, 0);
            return;
        }

        if (move == 'S') {
            snakePosition.get(segment).translate(0, 1);
            return;
        }
        //assuming the move is D at this point, as it should be nothing else. The SnakeGame class must manage retaining the last move made if a button is not pushed.
        snakePosition.get(segment).translate(1, 0);
    }

    public Point getHeadPosition() {
        return snakePosition.get(0);
    }

    public ArrayList<Point> getsnakePosition() {
        return snakePosition;
    }
}
