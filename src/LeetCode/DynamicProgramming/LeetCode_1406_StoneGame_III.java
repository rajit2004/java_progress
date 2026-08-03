package LeetCode.DynamicProgramming;

public class LeetCode_1406_StoneGame_III {
    public static void main(String[] args) {

        int[] stoneValue = {1, 2, 3, 7};

        System.out.println(stoneGameIII(stoneValue));
    }

    /*
        Dynamic Programming Approach :

        Let: dp[i] = Maximum score difference the current player can achieve starting from index i.

        At every turn, the player can take:

        1 stone
        2 stones
        3 stones

        Choose the option that maximizes: currentSum - opponentBestDifference
     */
    static String stoneGameIII(int[] stoneValue) {

        int n = stoneValue.length;

//            dp[i] stores the maximum score difference starting from index i.
        int[] dp = new int[n + 1];

        // Process states from right to left.
        for (int i = n - 1; i >= 0; i--) {

            int currentSum = 0;

            dp[i] = Integer.MIN_VALUE;

            // Try taking 1, 2 or 3 stones.
            for (int k = 0; k < 3 && i + k < n; k++) {

                currentSum += stoneValue[i + k];

                /*
                    Current player gains currentSum. Opponent then plays optimally, contributing dp[i + k + 1].

                    Hence: currentSum - opponentDifference
                 */
                dp[i] = Math.max(
                        dp[i],
                        currentSum - dp[i + k + 1]
                );
            }
        }

        // Positive difference means Alice wins.
        if (dp[0] > 0) {
            return "Alice";
        }

        // Negative difference means Bob wins.
        if (dp[0] < 0) {
            return "Bob";
        }

        // Equal scores.
        return "Tie";
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = stoneValue.length

---------------------------------------------------------

Time Complexity: O(n)

Reason: For every index, we try at most three possible moves.

Overall: O(3 × n) = O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: The DP array stores one value for every index.

Overall: O(n)

---------------------------------------------------------

Key Observation: Instead of storing each player's score, store only the maximum score difference the current player can achieve.

For every position:

dp[i] = max(take1 - dp[next], take2 - dp[next], take3 - dp[next])

The sign of dp[0] directly determines
the winner:

Positive -> Alice

Negative -> Bob

Zero -> Tie

---------------------------------------------------------
*/