package FracCalc;

/**
 * The Object Class for fractions, to store and process information for FracCalc.
 * I could have added the Math package for its absolute value processing, but I wasn't sure if it would be a good idea to add the extra package & Math object just to replace some if checks.
 */
public class Fraction {
    private int numerator;
    private int denominator;
 
    /**
     * @return this Fraction's numerator
     */
    private int getNum() {
       return numerator;
    }
 
    /**
     * @return this Fraction's denominator
     */
    private int getDenom() {
       return denominator;
    }
 
    /**
     * @return this Fraction's whole number (numerator/denominator)
     */
    private int getWhole() {
       int wholeNumber = numerator / denominator;
       return wholeNumber;
    }
 
    /**
     * One of the constructors for this class, which will parse Strings given to it into the num and denom.
     * @param fraction a string with a format of one of the following: #, #/#, #_#/#.
     */
    public Fraction(String fraction) {
       //no underscore will not have a slash, so this reduces if checks for whole numbers. Your elseif statements have the same total checks combined for all 3 numbers as this, so it doesn't really matter in the end.
       if (fraction.contains("/")) {
          if (fraction.contains("_")) {
             //use indexOf to get the position until _ and then use substring to isolate integers within.
             int lengthTillSpace = fraction.indexOf("_");
             int wholeNumber = Integer.valueOf(fraction.substring(0, lengthTillSpace));
             
             int lengthTillSlash = fraction.indexOf("/");
             numerator = Integer.valueOf(fraction.substring((lengthTillSpace + 1), lengthTillSlash));
             
             denominator = Integer.valueOf(fraction.substring((lengthTillSlash + 1)));
 
             //for many of these checks, it is simpler to combine the denominator and whole number, as if they are each negative, then it is entirely up to the numerator on whether the fraction is positive or negative
             //denomCo is also used for adjusting the numerator according to the wholenumber & denominator.
             int denomCo = denominator * wholeNumber;
 
             //I think this is the minimum amount of if-checks possible, after thinking about it for a while.
             if (denomCo > 0) {
                if (numerator > 0) {
                   numerator = (numerator + denomCo);
                   absoluteValueDenom();
                }
                else {
                   numerator = (numerator - denomCo);
                   absoluteValueDenom();
                }
             }
 
             else {
                if (numerator < 0) {
                   numerator *= -1;
                   denomCo *= -1;
                   numerator = (numerator + denomCo);
                   absoluteValueDenom();
                }
                else {
                   numerator = (denomCo - numerator);
                   absoluteValueDenom();
                }
             }
          }
          else {
             int lengthTillSlash = fraction.indexOf("/");
 
             numerator = Integer.valueOf(fraction.substring(0, lengthTillSlash));
             denominator = Integer.valueOf(fraction.substring((lengthTillSlash + 1)));
          }
       }
       else {
          numerator = Integer.valueOf(fraction);
          denominator = 1;
       }
    } // end Fraction constructor.
    
    /**
     * The other constructor, more basic than the last, and simply transfers given ints.
     * @param numeratorToUse which is the numerator of this new fraction
     */
    public Fraction(int numeratorToUse, int denominatorToUse) {
       numerator = numeratorToUse;
       denominator = denominatorToUse;
    
    } // end Fraction constructor.
  
    /**
     * multiplies this fraction with another
     * @param secondFrac the other fraction to multiply
     */
    public void multiply(Fraction secondFrac) {
       int otherNumerator = secondFrac.getNum();
       int otherDenominator = secondFrac.getDenom();
 
       numerator *= otherNumerator;
       denominator *= otherDenominator;
    } // end multiply()
 
    /**
     * divides this fraction.
     * @param secondFrac the other fraction that the first is divided by
     */
    public void divide(Fraction secondFrac) {
       int otherNumerator = secondFrac.getNum();
       int otherDenominator = secondFrac.getDenom();
       
       numerator *= otherDenominator;
       denominator *= otherNumerator;
    } // end divide()
 
    /**
     * adds two fractions together.
     * @param secondFrac the other fraction to be added to the first/
     */
    public void add(Fraction secondFrac) {
       int otherNumerator = secondFrac.getNum();
       int otherDenominator = secondFrac.getDenom();
 
       //otherDenominator is not really used besides multiplication, so you don't need to multiply it by denominator.
       numerator *= otherDenominator;
       otherNumerator *= denominator;
       denominator *= otherDenominator;
       
 
       numerator += otherNumerator;
    } // end add()
 
    /**
     * subtracts this fraction by another.
     * @param secondFrac the other fraction to subtract this one by
     */
    public void subtract(Fraction secondFrac) {
       int otherNumerator = secondFrac.getNum();
       int otherDenominator = secondFrac.getDenom();
 
       otherNumerator *= denominator;
       //otherDenominator is not really used besides multiplication, so you don't need to multiply it by denominator.
       numerator *= otherDenominator;
       denominator *= otherDenominator;
 
       numerator -= otherNumerator;
    } // end (subtract)
 
    /**
     * no longer used in current code, but could still be good for a user.
     * simplifies the code while returning the whole number.
     * @return wholeNumber after the fraction has been simplified.
     */
    public int simplifyFraction() {
       int gcdVar = gcd(numerator, denominator);
       //a GCD must be positive. I know i can use java.math but that seems inneficient, creating a whole object just to skip an if check.
       if (gcdVar < 0) {
          gcdVar *= -1;
       }
       int wholeNumber = numerator / denominator;
       numerator %= denominator;
       numerator /= gcdVar;
       denominator /= gcdVar;
       return wholeNumber;
    } // end simplifyFraction()
 
    /**
     * gcd finds the greatest common denominator between two numbers.
     * @param num1 the first number to be used
     * @param num2 the second number to find
     * @return the greatest common divisor between the two
     */
     //I admit, I got this from the web. I can however, explain how it operates(ask me in person if you want me to explain it further, or the text is not adequate). It will constantly check if num2 is equal to zero, and if it is, return num1(0 can have any factor). after the first recursion, it will start checking if the num1 from the previous loop modulo the num2 from the previous loop equals 0, and if it does, return the num2 from the previous loop. This is essentialy checking if these ever-changing numbers are able to go into each other evenly, or if num1 is the greatest common denominator.
    public static int gcd(int num1, int num2) {
       if (num2 == 0) return num1;
       else return gcd(num2, num1 % num2);
    } // end gcd()
 
    /**
     * toString creates temporary values for the num and denom, due to the bug in VScode involving toString and the debugger
     * @return a String composite of the fraction's wholeNumber(if any) and numerator and denominator
     */
    public String toString() {
       
       String output;
       
       int wholeNumber = getWhole();
       
       int gcdVar = gcd(numerator, denominator);
       
       if (gcdVar < 0) {
          gcdVar *= -1;
       }
       
       int tempNum = (numerator % denominator) / gcdVar;
       int tempDenom = denominator / gcdVar;
       //simplifies math to signify whether the fraction as a whole is negative or not, compared to it being split between three possible variables.
       String negative = "";
 
       //the whole number will be negative if only one of them are negative, effectively a XOR gate on its own.
       //processing negatives in a clean, aesthetically pleasing way.
       if (wholeNumber < 0) {
          negative = "-";
          if (tempNum < 0) {
             tempNum *= -1;         
          }
          else {
             tempDenom *= -1;
          }
       wholeNumber *= -1;
       }
       else if ((tempNum < 0) && (tempDenom < 0)) {
          tempDenom *= -1;
          tempNum *= -1;
       }
       //this is only if the fraction would have a negative denominator(negative sign should be in numerator), and its whole number is 0
       else if (tempDenom < 0) {
          tempDenom *= -1;
          negative = "-";
       }
 
       //processing the output of a fraction in the correct way.
       if (wholeNumber != 0) {
          if (tempNum == 0) {
             output = negative + wholeNumber + "";
          }
          else {
             output = negative + wholeNumber + "_" + tempNum + "/" + tempDenom;
          }
       }
       else {
          output = negative + tempNum + "/" + tempDenom;
          tempNum = (numerator % denominator) / gcdVar;
          tempDenom = denominator / gcdVar;
          if (wholeNumber != 0) {
             if (tempNum == 0) {
                output = wholeNumber + "";
             }
             else {
             output = wholeNumber + "_" + tempNum + "/" + tempDenom;
             }
          }
          else {
             output = tempNum + "/" + tempDenom;
          }
       }
    
       return output;
       
    } // end toString
 
    /**
     * absoluteValueDenom replaces the Math.abs() method, as I don't think an extra pacakge + object was worth it.
     */
    private void absoluteValueDenom() {
       if (denominator < 0) {
          denominator *= -1;
       }
    } // end absoluteValueDenom()
 
 }