package LeetCode.Enum;

public class LeetCode_1952_ThreeDivisors {
    public static void main(String[] args) {

        int n = 4;

        System.out.println(isThree(n));
    }

    /*
        Brute Force Approach :

        Count the divisors of n excluding:

            1
            n

        If exactly one divisor exists in between,
        then n has exactly three divisors:

            1, divisor, n

        Otherwise, return false.
    */
    static boolean isThree(int n) {

        // Stores the number of divisors excluding 1 and n.
        int count = 0;

        // Check every possible divisor.
        for (int i = 2; i <= n / 2; i++) {

            if (n % i == 0)
                count++;

            // More than one divisor means there are more than three divisors.
            if (count > 1)
                return false;
        }

        // Exactly one divisor means there are exactly three divisors.
        return count == 1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason:

The loop checks every integer from: 2 to n / 2

In the worst case, this requires O(n) iterations.

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Only one integer variable (count) is used.

No extra data structures are required.

---------------------------------------------------------

Key Observation:

A number has exactly three divisors only if
there is exactly one divisor between: 1 and n

The algorithm counts these intermediate
divisors and immediately stops if more than
one is found.

---------------------------------------------------------
*/