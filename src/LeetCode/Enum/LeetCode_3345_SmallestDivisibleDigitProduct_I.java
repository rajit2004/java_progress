package LeetCode.Enum;

public class LeetCode_3345_SmallestDivisibleDigitProduct_I {
    public static void main(String[] args) {

        int n = 10;
        int t = 2;

        System.out.println(smallestNumber(n, t));
    }

    /*
        Brute Force Approach : Start from the given number n.

        For every number:

        1. Compute the product of its digits.
        2. Check whether the product is divisible by t.
        3. If yes, return the current number.
        4. Otherwise, continue checking the next number.

        Since a valid number is guaranteed to exist, the loop will eventually terminate.
     */
    static int smallestNumber(int n, int t) {

        while (true) {

            int product = 1;
            int num = n;

            // Compute the product of all digits.
            while (num > 0) {

                product *= (num % 10);

                num /= 10;
            }

            // Check whether the product is divisible by t.
            if (product % t == 0) {

                return n;
            }

            // Try the next number.
            n++;
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

d = number of digits in the answer
k = number of integers checked before finding the answer

---------------------------------------------------------

Time Complexity: O(k × d)

Reason:

For every candidate number:

1. Traverse all of its digits.
2. Compute the product.
3. Check divisibility.

Overall: O(k × d)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few integer variables are used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The constraints are small enough to check numbers one by one.

For each candidate, simply compute the product of its digits and return the first number whose digit product is divisible by t.

---------------------------------------------------------
*/