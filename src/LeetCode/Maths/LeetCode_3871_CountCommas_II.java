package LeetCode.Maths;

public class LeetCode_3871_CountCommas_II {
    public static void main(String[] args) {

        long[] inputs = {1L, 999L, 1000L, 2500L, 1_000_000L, 1_000_000_000L};

        for (long n : inputs) {
            System.out.println("n = " + n + " | Brute: " + countCommasBrute(n) + " | Optimized: " + countCommas(n));
        }

        // Edge cases
        System.out.println("Edge (n = 0) -> " + countCommas(0) + " (expected: 0)");
        System.out.println("Edge (n = 999) -> " + countCommas(999) + " (expected: 0)");
    }


    /*
        Approach 1: Brute Force

        For every number i from 1 to n, count how many commas it contains in standard comma formatting.

        A number with d digits has (d - 1) / 3 commas.
            Example:
                999       (3 digits) -> (3-1)/3 = 0 commas
                1000      (4 digits) -> (4-1)/3 = 1 comma
                1,000,000 (7 digits) -> (7-1)/3 = 2 commas

        Sum this over all numbers from 1 to n.
        Note: Only feasible for small n; O(n * digits) is too slow for large n.
    */
    static long countCommasBrute(long n) {
        long res = 0;

        for (long i = 1; i <= n; i++) {
            int digits = Long.toString(i).length();
            res += (digits - 1) / 3;   // commas = (digits - 1) / 3
        }

        return res;
    }

    /*
        Approach 2: Optimized (Contribution of each comma threshold)

        Every number in the range [10^3, 10^6 - 1] contributes 1 comma.
        Every number in the range [10^6, 10^9 - 1] contributes 2 commas.
        In general, for each power p = 1000, 10^6, 10^9, ... every number in [p, 10^3 * p - 1] contributes one extra comma.

        So for each threshold p <= n, add (n - p + 1) to the answer.
        Then advance p *= 1000.

        Example (n = 2500):
            p = 1000 -> add 2500 - 1000 + 1 = 1501
            p = 10^6 -> stop (p > n)
            Answer = 1501
    */
    static long countCommas(long n) {
        long p = 1000, res = 0;

        while (p <= n) {
            res += n - p + 1;   // count of numbers in [p, n] contributing one extra comma
            p *= 1000;          // next comma threshold: 10^6, 10^9, ...
        }

        return res;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Brute Force

Time Complexity: O(n * log10(n))

- Loops from 1 to n, and for each number computes its digit count.

Space Complexity: O(1)

Key Observation: Commas in a number with d digits = (d - 1) / 3.


Approach 2: Optimized

Time Complexity: O(log1000(n))  — at most ~5 iterations for n up to 10^15

- Each iteration advances p by a factor of 1000.

Space Complexity: O(1)

Key Observation: For every threshold p = 1000, 10^6, 10^9, ..., every number >= p and <= n contributes exactly one extra comma.
So the total contribution of p is max(n - p + 1, 0).
---------------------------------------------------------

*/