/**
 *The goal of this project is to find the lowest common factor of all numbers from MIN(1) to MAX(20)
 */
public class SmallestFactorFromNtoX {

    public static final int MIN = 1;
    public static final int MAX = 20;
    
    public static void main(String[] args) {
        //I know that the goal is for this to be from 1-20, so i could start factor at SOME number, saving a bit of time.
        //However, I want this to be adjustable, meaning I have to start at 0 for this.
        boolean done = false;
        int factor = 0;
            while (!done) {
                factor++;
                if (isFactor(factor)) {
                done = true;
            }
        }
        System.out.println(factor);
    }
    
    public static boolean isFactor(int factor) {
       for (int i = MIN; i <= MAX; i++) {
            if (factor % i != 0) {
                return false;
            }
        }
        return true;
    }
 }