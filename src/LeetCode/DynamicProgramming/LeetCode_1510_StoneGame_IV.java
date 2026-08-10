package LeetCode.DynamicProgramming;

public class LeetCode_1510_StoneGame_IV {
    public static void main(String[] args) {

        int n = 7;

        System.out.println(winnerSquareGame(n));
    }

    /*
        Dynamic Programming + Game Theory Approach :

        Let:

        dp[i] = true  -> Current player can win
        dp[i] = false -> Current player loses

        For every number of stones i, try removing every possible perfect square.

        If there exists a square such that the remaining state is losing for the opponent, the current player can force a win.

        Therefore: dp[i] = true if any dp[i - square] == false
     */
    static boolean winnerSquareGame(int n) {

        // dp[i] tells whether the current player can win when i stones remain.
        boolean[] dp = new boolean[n + 1];

        // dp[0] = false because no move is possible when there are zero stones.

        // Calculate the result for every number of stones.
        for (int i = 1; i <= n; i++) {

//  Try removing every perfect square that does not exceed i.
            for (int j = 1; j * j <= i; j++) {

                int square = j * j;
                int remaining = i - square;

//  If the opponent loses from the remaining state, the current player can win by choosing this square.
                if (!dp[remaining]) {

                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = number of stones

---------------------------------------------------------

Time Complexity: O(n√n)

Reason:

For every value from 1 to n, we try all perfect squares up to that value.

The number of possible squares for i is approximately √i.

Therefore: O(√1 + √2 + ... + √n) = O(n√n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: A boolean DP array of size n + 1 is maintained.

Overall: O(n)

---------------------------------------------------------

Key Observation:

This is a winning-state / losing-state game. A state is winning if we can make at least one move that sends the opponent to a losing state.

Therefore: dp[i] = true , if there exists a perfect square x² such that: dp[i - x²] = false . Otherwise, dp[i] remains false.

---------------------------------------------------------
*/