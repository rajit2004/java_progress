package LeetCode.DynamicProgramming;

import java.util.Arrays;

public class LeetCode_115_DistinctSubsequences {
    public static void main(String[] args) {

        LeetCode_115_DistinctSubsequences solution = new LeetCode_115_DistinctSubsequences();

        String s1 = "rabbbit";
        String t1 = "rabbit";

        System.out.println("Memoization: " + solution.numDistinctMemoization(s1, t1));
        System.out.println("Bottom-Up DP: " + solution.numDistinctBottomUp(s1, t1));
        System.out.println("Space-Optimized DP: " + solution.numDistinctSpaceOptimized(s1, t1));

        String s2 = "babgbag";
        String t2 = "bag";

        System.out.println("Memoization: " + solution.numDistinctMemoization(s2, t2));
        System.out.println("Bottom-Up DP: " + solution.numDistinctBottomUp(s2, t2));
        System.out.println("Space-Optimized DP: " + solution.numDistinctSpaceOptimized(s2, t2));
    }

    /*
        Approach 1: Recursion + Memoization

        At every position, we have two choices:
            1. Do not take s[i].
            2. Take s[i] if it matches t[j].

        If s[i] matches t[j], both choices are possible.
        Otherwise, only the not-take choice is possible.

        The state (i, j) represents the number of ways to form t[j ... m - 1] using s[i ... n - 1].
     */
    public int numDistinctMemoization(String s, String t) {
        int n = s.length();
        int m = t.length();

        int[][] memo = new int[n][m];

        for (int[] row : memo)
            Arrays.fill(row, -1);

        return solveMemoization(0, 0, s, t, memo);
    }

    private int solveMemoization(int i, int j, String s, String t,int[][] memo) {
        // An empty target can always be formed by choosing no characters.
        if (j == t.length())
            return 1;

        // The source is exhausted before the target is formed.
        if (i == s.length())
            return 0;

        // Return the previously calculated result for this state.
        if (memo[i][j] != -1)
            return memo[i][j];

        // Skip the current source character.
        int notTake = solveMemoization(i + 1, j, s, t, memo);
        int take = 0;

        // Use the current source character when it matches the target.
        if (s.charAt(i) == t.charAt(j))
            take = solveMemoization(i + 1, j + 1, s, t, memo);

        return memo[i][j] = take + notTake;
    }

    /*
        Approach 2: Bottom-Up Dynamic Programming

        dp[i][j] represents the number of ways to form t[j ... m - 1] using s[i ... n - 1].

        Base case: dp[i][m] = 1
        An empty target can be formed from any suffix of s by selecting no characters.
        The table is filled from right to left because each state depends on states with larger indices.
     */
    public int numDistinctBottomUp(String s, String t) {
        int n = s.length();
        int m = t.length();

        int[][] dp = new int[n + 1][m + 1];

        // An empty target can be formed in exactly one way.
        for (int i = 0; i <= n; i++)
            dp[i][m] = 1;

        // Fill the table from the end of both strings.
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {

                // Do not use the current character from s.
                dp[i][j] = dp[i + 1][j];

                // Use the current character if it matches t[j].
                if (s.charAt(i) == t.charAt(j))
                    dp[i][j] += dp[i + 1][j + 1];
            }
        }

        return dp[0][0];
    }

    /*
        Approach 3: Space-Optimized Dynamic Programming

        The bottom-up solution only needs the current row and the previous row.
        We can reduce the table to a one-dimensional array.

        dp[j] represents the number of ways to form t[0 ... j - 1] using the source characters processed so far.

        The variable previousDiagonal stores the old value of dp[j - 1], which represents dp[i - 1][j - 1].
     */
    public int numDistinctSpaceOptimized(String s, String t) {
        int sourceLength = s.length();
        int targetLength = t.length();

        int[] dp = new int[targetLength + 1];
        dp[0] = 1;

        // Process one source character at a time.
        for (int i = 1; i <= sourceLength; i++) {
            int previousDiagonal = 1;

            // Update from left to right while preserving the diagonal value.
            for (int j = 1; j <= targetLength; j++) {
                int currentValue = dp[j];

                // If the characters match, include the take possibility.
                if (s.charAt(i - 1) == t.charAt(j - 1))
                    dp[j] += previousDiagonal;

                // The old dp[j] is needed as the diagonal value for the next j.
                previousDiagonal = currentValue;
            }
        }

        return dp[targetLength];
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = s.length()
m = t.length()

---------------------------------------------------------

Approach 1: Recursion + Memoization

Time Complexity: O(n * m)

There are n * m possible states, and each state is calculated once.

Space Complexity: O(n * m)

The memoization table uses O(n * m) space, and the recursion stack uses O(n + m) space in the worst case.

---------------------------------------------------------

Approach 2: Bottom-Up Dynamic Programming

Time Complexity: O(n * m)

Every cell in the two-dimensional DP table is calculated once.

Space Complexity: O(n * m)

The complete DP table is stored.

---------------------------------------------------------

Approach 3: Space-Optimized Dynamic Programming

Time Complexity: O(n * m)

The strings are processed using two nested loops.

Space Complexity: O(m)

Only one row of the DP table is stored.

---------------------------------------------------------

Key Observation:

For every matching pair of characters, we can either use the character from s or skip it. Therefore:
    dp[i][j] = dp[i + 1][j]

    If s[i] == t[j]: dp[i][j] += dp[i + 1][j + 1]

The three implementations use the same recurrence with different ways of storing and computing the states.

---------------------------------------------------------
*/
