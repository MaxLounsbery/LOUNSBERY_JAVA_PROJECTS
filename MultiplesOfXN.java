/**
 *The purpose of this project is to sum all multiples of x(3) or n(5)
 *that fall below maxNum(1000).
 */
public class MultiplesOfXN {
    //I could have done final variables here, but i feel allMultiples() is more flexible if it asks for the numbers itself.
    public static void main(String[] args) {
       int maxNum = 1000000;
       int x = 2;
       int n = 5;
       long result = allMultiples(maxNum, x, n);
       System.out.println(result);
    }
    /**
     *calculates the sum of all factors of num1 and num2 less than the max.
     *@param max all factors calculated will be below the max.
     *@param num1 the first of the two multiples. Cannot be zero.
     *@param num2 the second of the two multiples. Cannot be zero.
     *@return the sum.
     */
    private static long allMultiples(int max, int num1, int num2) {
       //total should be long due to immense growth that is able to happen with higher maxes.
       long total = 0;
       for (int i = 0; i < max; i++) {
          if (i % num1 == 0 || i % num2 == 0) {
             total += i;
          }
       }
       return total;
    }
 }