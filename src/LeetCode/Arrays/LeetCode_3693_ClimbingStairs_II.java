package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3693_ClimbingStairs_II {

    public static void main(String[] args) {
        int n = 4;
        int[] costs = {1, 2, 3, 4};
        System.out.println(climbStairsFullDP(n, costs));
        System.out.println(climbStairsRolling(n, costs));
    }

    /* Approach 1: Full 1D DP array
       dp[i] = minimum total cost to reach step i (1-indexed); dp[0] = 0 is the
       floor before the first step. To stand on step i, arrive from one of the
       previous three steps:
         - from i-1 with a 1-step move, cost dp[i-1] + 1
         - from i-2 with a 2-step move, cost dp[i-2] + 4
         - from i-3 with a 3-step move, cost dp[i-3] + 9
       Then add costs[i-1] because landing on step i incurs its cost. Take the
       minimum of the valid transitions.
       Example: n = 4, costs = [1,2,3,4] -> answer 6.
    */
    static int climbStairsFullDP(int n, int[] costs) {
        final int INF = Integer.MAX_VALUE / 2;
        int[] dp = new int[n + 1];   // dp[0] = 0

        for (int i = 1; i <= n; i++) {
            int oneStep   = dp[i - 1] + 1;
            int twoStep   = i >= 2 ? dp[i - 2] + 4 : INF;
            int threeStep = i >= 3 ? dp[i - 3] + 9 : INF;

            dp[i] = costs[i - 1] + Math.min(oneStep, Math.min(twoStep, threeStep));
        }

        return dp[n];
    }

    /* Approach 2: Rolling three variables (optimal)
       Same recurrence as Approach 1, but each dp[i] only depends on the last
       three entries, so we keep them in a, b, c:
         a = dp[i-3], b = dp[i-2], c = dp[i-1]
       For each step i:
         next = min(a + 9, b + 4, c + 1) + costs[i]
       then shift a <- b, b <- c, c <- next.
       Example: n = 4, costs = [1,2,3,4] -> answer 6 (same as Approach 1).
    */
    static int climbStairsRolling(int n, int[] costs) {
        int a = 0, b = 0, c = 0;

        for (int i = 0; i < n; i++) {
            int next = Math.min(Math.min(a + 9, b + 4), c + 1) + costs[i];
            a = b;
            b = c;
            c = next;
        }

        return c;
    }
}

/* Complexity Analysis
   Let n be the number of steps.

   Approach 1: Full 1D DP array
     Time : O(n)  - one constant-time transition per step.
     Space: O(n)  - dp array of size n + 1.
     Key Observation: each step is reachable only from the previous three
     steps; the +1/+4/+9 costs model 1-, 2- and 3-step moves respectively,
     and costs[i-1] is always paid on landing.

   Approach 2: Rolling three variables (recommended)
     Time : O(n)  - same transitions, same work per step.
     Space: O(1)  - only a, b, c.
     Key Observation: dp[i] depends only on dp[i-1], dp[i-2], dp[i-3], so
     keeping three rolling variables is enough and drops the array entirely.
*/

