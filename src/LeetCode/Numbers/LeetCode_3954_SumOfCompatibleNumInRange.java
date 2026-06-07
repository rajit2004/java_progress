package LeetCode.Numbers;

public class LeetCode_3954_SumOfCompatibleNumInRange {
    public static void main(String[] args) {
        System.out.println(sumOfGoodIntegers(2,3));
    }

    /*
        Brute Force Approach:

        We only need to check numbers in the range: [n-k, n+k]

        For every number x in this range:

        If:
            (n & x) == 0

        then n and x do not share any common set bit in their binary representation, making x a compatible number.

        Add all such compatible numbers to the answer.
     */

    static int sumOfGoodIntegers(int n, int k) {
        int sum = 0;

        // Ensure the range starts from at least 1.
        int start = Math.max(1, n - k);

        // Upper limit of the valid range.
        int end = n + k;

        // Check every number within the range.
        for (int x = start; x <= end; x++) {

            /*
                Bitwise AND:

                If (n & x) == 0 => then n and x have no bit position where both contain a set bit (1).

                Example:

                n = 2  -> 0010
                x = 5  -> 0101

                0010
              & 0101
              ------
                0000

                Compatible => include in sum.
             */
            if ((n & x) == 0) {
                sum += x;
            }
        }

        return sum;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(2k + 1)

Reason: The range checked is: [n-k, n+k]

Number of elements: (n+k) - (n-k) + 1 = 2k + 1

Each iteration performs a constant-time bitwise AND operation.

Therefore: Time Complexity = O(k)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few integer variables are used. No extra arrays or data structures are created.

---------------------------------------------------------

Key Observation:

Two numbers are compatible if they do not share any common set bit.

This can be checked efficiently using: (n & x) == 0 instead of examining every bit manually.

---------------------------------------------------------
*/