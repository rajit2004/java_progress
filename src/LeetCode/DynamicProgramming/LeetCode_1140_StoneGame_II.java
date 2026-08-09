package LeetCode.DynamicProgramming;

import java.util.Arrays;

public class LeetCode_1140_StoneGame_II {
    public static void main(String[] args) {

        int[] piles = {2, 7, 9, 4, 4};

        System.out.println(stoneGameII(piles));
    }

    static int n;
    static int[] suffix;
    static int[][] dp;

    /*
        Dynamic Programming + Memoization Approach :

        At every turn, the player can take between 1 and 2 * M piles.

        After taking X piles: M = max(M, X)

        We use suffix sums to quickly calculate the total number of stones remaining.

        dp[i][m] represents the maximum number of stones the current player can collect when:

            i = current pile index
            m = current value of M

        The current player takes some number of piles and leaves the remaining piles to the opponent.

        Therefore: current score = remaining stones - opponent's best score
     */

//        Returns the maximum stones the current player can collect starting from index i with the current value of M.
    static int solve(int i, int m) {

        // No piles remain.
        if (i == n)
            return 0;

        // Return the already computed state.
        if (dp[i][m] != -1)
            return dp[i][m];

        int best = 0;

//            Try taking every possible number of piles from 1 to 2 * M.
        for (int x = 1; x <= 2 * m && i + x <= n; x++) {

            // Update M for the next turn.
            int nextM = Math.max(m, x);

            /*
                suffix[i] = total stones remaining.

                solve(i + x, nextM) = maximum stones the opponent can collect.

                Therefore, the current player's maximum score is: remaining stones - opponent's score
             */
            int current = suffix[i] - solve(i + x, nextM);

            best = Math.max(best, current);
        }

        // Store and return the best result for this state.
        return dp[i][m] = best;
    }

    static int stoneGameII(int[] piles) {

        n = piles.length;

        /*
            suffix[i] stores the sum of all piles from index i to the end.

            This allows us to calculate the remaining total in O(1).
         */
        suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--)
            suffix[i] = suffix[i + 1] + piles[i];

        // dp[i][m] stores the result of each state.
        dp = new int[n][n + 1];

        // -1 means the state has not been calculated.
        for (int i = 0; i < n; i++)
            Arrays.fill(dp[i], -1);

        // Initially, we start from index 0 with M = 1.
        return solve(0, 1);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = piles.length

---------------------------------------------------------

Time Complexity: O(n³)

Reason:

There are O(n²) possible states: dp[i][m]

For every state, we may try up to O(n) possible values of X.

Therefore: O(n²) × O(n) = O(n³)

---------------------------------------------------------

Space Complexity: O(n²)

Reason:

1. DP table: O(n²)

2. Suffix sum array: O(n)

3. Recursion stack: O(n)

Overall: O(n²)

---------------------------------------------------------

Key Observation:

The important state is not only the current pile index.

The value of M determines how many piles can be taken on the next turn.

Therefore, the state is: dp[i][M]

Using suffix sums allows us to calculate the total remaining stones in O(1), while memoization prevents the same game state from being solved repeatedly.

---------------------------------------------------------
*/