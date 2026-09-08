package LeetCode.Maths;

public class LeetCode_3870_CountCommasInRange {
    public static void main(String[] args) {

        int n = 2500;

        System.out.println("Brute Force: " + countCommas(n));
        System.out.println("Optimized: " + countCommasALT(n));
    }


    /*
        Approach 1: Brute Force

        Every number greater than 999 contains at least one comma when written with standard comma formatting.
            Example:
            999  -> 0 commas
            1000 -> 1 comma
            2500 -> 1 comma

        So, iterate from 1 to n and count every number greater than 999.
    */
    static int countCommas(int n) {
        int res = 0;

        for (int a = 1; a <= n; ++a) {
            if (a > 999)
                res++;
        }

        return res;
    }

    /*
        Approach 2: Optimized

        All numbers from 1000 to n contain exactly one comma for the given problem constraints.

        Count of such numbers: n - 999
        If n <= 999, the answer is 0.
    */
    static int countCommasALT(int n) {
        return Math.max(n - 999, 0);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Brute Force

Time Complexity: O(n)

- Iterates through every number from 1 to n.

Space Complexity: O(1)

Key Observation: Every number greater than 999 contributes one comma.


Approach 2: Optimized

Time Complexity: O(1)
Space Complexity: O(1)

Key Observation: Numbers from 1000 to n are exactly n - 999 numbers, so the answer is max(n - 999, 0).
---------------------------------------------------------
*/