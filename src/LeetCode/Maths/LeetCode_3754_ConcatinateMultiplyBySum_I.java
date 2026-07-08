package LeetCode.Maths;

public class LeetCode_3754_ConcatinateMultiplyBySum_I {
    public static void main(String[] args) {

        int n = 105203;

        System.out.println(sumAndMultiply(n));
    }

    /*
        Math Approach :

        Traverse each digit of the number.

        For every non-zero digit:

            1. Add it to the digit sum.
            2. Build a new number by concatenating
               only the non-zero digits.

        Finally, return:

            concatenatedNumber × digitSum
     */

    static long sumAndMultiply(int n) {

        // Stores the number formed using only non-zero digits.
        int concatenatedNumber = 0;

        // Stores the sum of all non-zero digits.
        int digitSum = 0;

        // Keeps track of the current place value.
        int place = 1;

        while (n > 0) {

            // Extract the last digit.
            int digit = n % 10;

            // Ignore zero digits.
            if (digit != 0) {

                // Add digit to the total sum.
                digitSum += digit;

                /*
                    Build the concatenated number.

                    Example:

                    105203

                    becomes

                    1523
                 */
                concatenatedNumber += digit * place;

                place *= 10;
            }

            // Remove the last digit.
            n /= 10;
        }

        // Return concatenatedNumber × digitSum.
        return 1L * concatenatedNumber * digitSum;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

d = number of digits in n

---------------------------------------------------------

Time Complexity: O(d)

Reason:

Each digit is processed exactly once.

Overall:

O(d)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Only a few integer variables are used.

No extra data structures are required.

Overall:

O(1)

---------------------------------------------------------

Key Observation:

Instead of first constructing a string or
storing digits separately, we can simultaneously:

1. Compute the digit sum.
2. Build the concatenated number using place values.

This allows the solution to be completed
in a single traversal of the digits.

---------------------------------------------------------
*/