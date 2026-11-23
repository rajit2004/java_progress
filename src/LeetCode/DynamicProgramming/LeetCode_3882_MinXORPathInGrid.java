package LeetCode.DynamicProgramming;

import java.util.*;

public class LeetCode_3882_MinXORPathInGrid {

    public static void main(String[] args) {
        int[][] grid = {{1, 2}, {3, 4}};
        System.out.println(minCostBrute(grid));
        System.out.println(minCostDp(grid));
    }

    /* Approach 1: Brute force DFS
       Enumerate every monotone path from (0,0) to (m-1, n-1) (only right and
       down moves are allowed), keep the running XOR of the path values, and
       track the minimum XOR seen at the destination. Exponential in m+n,
       only practical for tiny grids but serves as an oracle.
       Example: grid = [[1,2],[3,4]]
                paths: (1^2^4)=7 and (1^3^4)=6 -> answer 6.
    */
    static int minCostBrute(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] best = {Integer.MAX_VALUE};
        dfs(grid, 0, 0, 0, m, n, best);
        return best[0];
    }

    static void dfs(int[][] grid, int i, int j, int xor, int m, int n, int[] best) {
        xor ^= grid[i][j];

        if (i == m - 1 && j == n - 1) {
            best[0] = Math.min(best[0], xor);
            return;
        }

        if (i + 1 < m) dfs(grid, i + 1, j, xor, m, n, best);
        if (j + 1 < n) dfs(grid, i, j + 1, xor, m, n, best);
    }

    /* Approach 2: Row-by-row DP on (column, XOR value) boolean states (optimal)
       Let dp[j][k] = true iff some path reaches the CURRENT cell (i, j) with
       XOR = k. Rolling over the grid row by row, the state dp[j] before
       processing (i, j) holds the paths reaching (i-1, j) (from above), and
       dp[j-1] just finished processing has the paths reaching (i, j-1) (from
       left). So for each cell:
         next[k ^ grid[i][j]] = true  for any k with dp[j][k] or dp[j-1][k]
       then dp[j] is replaced with next. After the full grid, the answer is the
       smallest k with dp[n-1][k] = true.
       Example: grid = [[1,2],[3,4]] -> dp[n-1] has bits 7 and 6 set
                -> smallest set bit is 6 -> answer 6.
    */
    static int minCostDp(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        boolean[][] dp = new boolean[n][1024];
        boolean[] next = new boolean[1024];

        dp[0][grid[0][0]] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;

                int value = grid[i][j];
                Arrays.fill(next, false);

                for (int k = 0; k < 1024; k++) {
                    if (dp[j][k] || (j > 0 && dp[j - 1][k])) {
                        next[k ^ value] = true;
                    }
                }

                System.arraycopy(next, 0, dp[j], 0, 1024);
            }
        }

        for (int k = 0; k < 1024; k++) {
            if (dp[n - 1][k]) return k;
        }

        return -1;
    }
}

/* Complexity Analysis
   Let m = grid.length, n = grid[0].length, V = 1024 (XOR value space).

   Approach 1: Brute force DFS
     Time : O(2^(m + n))  - every monotone path from (0,0) to (m-1, n-1) is
            enumerated once.
     Space: O(m + n)      - recursion depth equals path length.
     Key Observation: the path XOR is order-independent under XOR, but the
     set of values available on a path is constrained by monotone moves, so
     only brute-force enumeration works without state compression.

   Approach 2: Row-by-row DP on (column, XOR value) (recommended)
     Time : O(m * n * V)  - each cell does a V-sized pass over the boolean
            state of column j and column j-1.
     Space: O(n * V)      - dp[j][k] is rolled by row; only the previous row's
            contribution per column is kept.
     Key Observation: XOR is closed under 10 bits (0..1023), so a boolean
     table over the value space suffices; processing cells left-to-right in
     each row means dp[j] and dp[j-1] together give exactly the two possible
     predecessors (top and left) of cell (i, j).
*/

