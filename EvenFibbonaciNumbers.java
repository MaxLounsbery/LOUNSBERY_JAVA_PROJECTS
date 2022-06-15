/**
 * The purpose of this code is to find the sum of all even fibonnaci numbers below 4 million.
 */
public class EvenFibbonaciNumbers {
    public static final int MAXIMUM_FIB = 4000000;
    public static void main(String[] args) {
        // I use an array for simplicity of checking and changing between the previous two terms.
        int[] previous = {1, 1};
        int total = 0;
        for (int i = 0; (previous[0] < MAXIMUM_FIB || previous[1] < MAXIMUM_FIB); i++) {
            int newNum = previous[0] + previous[1];
            if (newNum % 2 == 0) {
                total += newNum;
            }
            previous[(i % 2)] = newNum;
        }
        System.out.println(total);
    }
}