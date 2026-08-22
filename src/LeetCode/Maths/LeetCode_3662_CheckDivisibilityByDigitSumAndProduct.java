package LeetCode.Maths;

public class LeetCode_3662_CheckDivisibilityByDigitSumAndProduct {
    public static void main(String[] args) {

        int n = 99;

        System.out.println(checkDivisibility(n));
    }

    /*
        Digit Sum + Digit Product Approach : For a given number n, calculate:
                    1. Sum of all digits
                    2. Product of all digits

        The number is divisible if: n % (digitSum + digitProduct) == 0

        We process each digit using modulo (%) and integer division (/).
     */
    static boolean checkDivisibility(int n) {

        int digitSum = 0;
        int digitProduct = 1;

        // Keep the original number for the final divisibility check.
        int original = n;

        /*
            Extract every digit from right to left.
                n % 10 -> extracts the last digit.
                n / 10 -> removes the last digit.
         */
        while (n > 0) {

            int digit = n % 10;
            n /= 10;

            // Add the current digit to the digit sum.
            digitSum += digit;

            // Multiply the current digit into the product.
            digitProduct *= digit;
        }

//            The number is divisible by the sum of its digit sum and digit product.
        return original % (digitSum + digitProduct) == 0;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: d = number of digits in n

---------------------------------------------------------

Time Complexity: O(d)

Reason: Each digit of the number is processed exactly once.

Overall: O(d)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a constant number of variables are used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The digit sum and digit product can both be calculated in a single traversal of the digits.

For every digit:
    digit = n % 10
    n = n / 10

After calculating both values, simply check: original % (digitSum + digitProduct) == 0

---------------------------------------------------------
*/