/**
 * The purpose of this project is to find the smallest common factor of all numbers from N(1) to X(20)
 */
public class SmallestMultiple {
    public static final int MIN = 1;
    public static final int MAX = 20;
    public static void main(String[] args) {
        boolean done = false;
        int factor = 0;
        while (!done) {
            factor++;
            if(isFactor(factor)) {
                done = true;
            }
        }
    }
    /**
     * checks if a given factor is a factor of the designated final number
     * @param factor
     * @return
     */
    public static boolean isFactor(int factor) {
        for (int i = MIN; i <= MAX; i++) {
            if (factor % i != 0) {
                return false;
            }
        }
        return true;
    }
}