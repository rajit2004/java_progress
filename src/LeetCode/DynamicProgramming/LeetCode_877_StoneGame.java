package LeetCode.DynamicProgramming;

public class LeetCode_877_StoneGame {
    public static void main(String[] args) {

        int[] piles = {5, 3, 4, 5};

        System.out.println(stoneGame(piles));
    }

    /*
        Dynamic Programming Approach :

        Let: dp[i][j] = Maximum score difference the current player can achieve over the opponent using piles from index i to j.

        At every turn, the player has two choices:

        1. Take the leftmost pile.
        2. Take the rightmost pile.

        The opponent also plays optimally, so their best score difference is subtracted.
     */
    static boolean stoneGame(int[] piles) {

        int n = piles.length;

        // dp[i][j] stores the maximum score difference for the subarray [i...j].
        int[][] dp = new int[n][n];

        // Base case: only one pile is available.
        for (int i = 0; i < n; i++) {

            dp[i][i] = piles[i];
        }

//            Build solutions for larger subarrays.
        for (int len = 2; len <= n; len++) {

            for (int i = 0; i <= n - len; i++) {

                int j = i + len - 1;

//                    Pick the left pile. Subtract the opponent's best possible score difference.
                int takeLeft =
                        piles[i] - dp[i + 1][j];

//                    Pick the right pile. Again subtract the opponent's optimal score difference.
                int takeRight =
                        piles[j] - dp[i][j - 1];

                // Choose the better move.
                dp[i][j] = Math.max(takeLeft, takeRight);
            }
        }

        // Positive score difference means => Alice wins.
        return dp[0][n - 1] > 0;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = piles.length

---------------------------------------------------------

Time Complexity: O(n²)

Reason:

1. There are O(n²) subarrays.

2. Each DP state is computed in O(1).

Overall: O(n²)

---------------------------------------------------------

Space Complexity: O(n²)

Reason: A DP table of size n × n is maintained.

Overall: O(n²)

---------------------------------------------------------

Key Observation: Instead of storing the actual scores of both players, store only the maximum score difference the current player can achieve.

The recurrence becomes:

dp[i][j] = max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1])

This naturally models optimal play by both players.

---------------------------------------------------------
*/