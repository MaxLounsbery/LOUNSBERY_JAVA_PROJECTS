package Snake;
import java.util.ArrayList;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyListener;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent; //https://stackoverflow.com/questions/18037576/how-do-i-check-if-the-user-is-pressing-a-key https://www.youtube.com/watch?v=GAn5evoACsM
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLabel;
 /*
  * whenever the user presses a key, save the latest key pressed by going through the method thing. Maybe google or ask why you can't do the other method if you want.
  * every time the delayed timer goes off, pair up the latest key to its number (seen here: https://docs.oracle.com/en/java/javase/14/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_A ) and process that bad boy
  * figure out the graphics stuffs. you could totally do text but you've gone way too far and it would be weird opening a graphics tab with no graphics.
  * remove all the tutorial stuff, and also clean up everything that's not needed.
  */
public class SnakeGame {
    public static final int BOARD_X_SIZE = 20;
    public static final int BOARD_Y_SIZE = 9;
    public static final int TURNS_PER_SECOND = 1;

    public static final char EMPTY_SPACE_CHARACTER = ' ';
    public static final char BORDER_CHARACTER = 'B';
    public static final char SNAKE_CHARACTER = 'S';
    public static final char APPLE_CHARACTER = 'A';

    public static int score;
    public static int highScore;
    public static char[][] board;
    public static SnakeSnake snake;
    public static Timer timer;
    public static TimerTask tick;
    public static char move;
    public static JFrame frame;
    public static JPanel panel;
    public static int key;
    public static int runs;
    public static void main(String[] args) {
        snake = new SnakeSnake(BOARD_X_SIZE, BOARD_Y_SIZE);
        Timer timer = new Timer();
        TimerTask tick = new SnakeTick();

        timer.schedule(tick, (1000 / TURNS_PER_SECOND), (1000 / TURNS_PER_SECOND));

        frame = new JFrame();

        initializeFrame();
    }

    private static class SnakeTick extends TimerTask {
        public void run() {
        switch(key) {
            case 37:
                System.out.println("left");
                break;
            case 38:
                System.out.println("up");
                break;
            case 39:
                System.out.println("right");
                break;
            case 40:
                System.out.println("down");
        }
        if (runs == 10) {
            System.exit(0);
        }
        runs++;
        }
    }

    private static void initializeFrame() {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setFocusable(true);

        panel = new JPanel();

        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                switch(keycode) {
                    case KeyEvent.VK_UP:
                        key = keycode;
                        break;
                    case KeyEvent.VK_DOWN:
                        key = keycode;
                        break;
                    case KeyEvent.VK_LEFT:
                        key = keycode;
                        break;
                    case KeyEvent.VK_RIGHT:
                        key = keycode;
                        break;
                    case KeyEvent.VK_SPACE:
                        System.out.println(key);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        frame.add(panel);
    }

    public static void setVariable(int toSet) {
        key = toSet;
    }

    private static void initializeBoard() {
        board = new char[BOARD_Y_SIZE + 3][BOARD_X_SIZE + 3]; //creates the board as a characterArray, with extra space for borders
        for (int row = 0; row < BOARD_Y_SIZE + 3; row++) {
            for (int col = 0; col < BOARD_X_SIZE + 3; col++) {
                if (row == 0 || row == BOARD_Y_SIZE + 2|| col == 0 || col == BOARD_X_SIZE + 2) { // if the current point on the board being examined is on the border of it, make it a border piece
                    board[row][col] = BORDER_CHARACTER;
                }
                else {
                    board[row][col] = EMPTY_SPACE_CHARACTER;
                }
            }
        }
    }

    public static void printBoard() {
        for (int row = 0; row < BOARD_Y_SIZE + 3; row++) { //the +3 is considering the extra length provided by the borders
            for (int col = 0; col < BOARD_X_SIZE + 3; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }
}