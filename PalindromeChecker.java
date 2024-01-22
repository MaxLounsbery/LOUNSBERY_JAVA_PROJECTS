/**
 * checks if a String is a palindrome or not
 */
public class PalindromeChecker {
    static final String toCheck = "ARPRA";
    public static void main(String[] args) {
        System.out.println("The string to check is: " + toCheck);
        if (isPalindrome(toCheck)) {
            System.out.println("This string is a palindrome");
        }
        else {
            System.out.println("This string is not a palindrome");
        }
    }

    private static boolean isPalindrome(String toCheck) {
        for (int i = 0; i < (toCheck.length() / 2); i++) {
            if (toCheck.charAt(i) != toCheck.charAt(toCheck.length() - i - 1)) { //if the character at toCheck[i] isn't the same as the offset of toCheck[length-1 - i], it's not a palindrome
                return false;
            }
        }
        return true;
    }
}
