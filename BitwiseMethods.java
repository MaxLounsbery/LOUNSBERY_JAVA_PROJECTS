/**
 * I use this class as a template for bitwise operations
 */
class BitwiseMethods {
    public static void main(String[] args) {
       System.out.println(setSecondBit(5)); // 7
 
       System.out.println(unsetSecondBit(7)); // 5
 
       System.out.println(setBit(1, 12)); //4097
 
       System.out.println(swapVariables(2, 1)); //1

       System.out.println(isSet(3, 1)); //true
    }
     /**
     * @param number the integer to modify
     * @return the modified int
     */
    public static int setSecondBit(int number) {
       int mask = 0b00000010; // 2
       // Example:
       // number = 01001001
       // mask =   00000010
       // result = 01001011
       return number | mask;
    }
    /**
     * @param number the integer to modify
     * @return the modified int
     */
    public static int unsetSecondBit(int number) {
       int mask = ~0b00000010; // ~2
       // Example:
       // number = 01001011
       // mask =   11111101
       // result = 01001001
       return number & mask;
    }
    /**
     * @param number the integer to modify
     * @param bitIndex the position to set
     * @return the modified int
     */
    public static int setBit(int number, int bitIndex) {
       int mask = 0b00000001 << bitIndex;
 
       return number | mask;
    }
    /**
     * @param a integer to swap
     * @param b integer to swap
     * @return integer a, though this can be modified as a part of a method to maintain b.
     */
    public static int swapVariables(int a, int b) {
       a = b ^ a;
       b = a ^ b;
       a = a ^ b;
       return a;
    }
    /**
     * @param a the integer to check
     * @param b the position of the bit to check, from right to left
     * @return whether a is set or not in position b
     */
    public static boolean isSet(int a, int b) {
        if ((a >> b) % 2 == 1) {
            return true;
        }
        return false;
    }
 }
 