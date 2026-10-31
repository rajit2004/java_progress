package LeetCode.Arrays;

public class LeetCode_3665_TwistedMirrorPathCount {
    public static void main(String[] args) {
        System.out.println(uniquePaths(new int[][]{{0, 0}, {0, 0}}));   // 2
        System.out.println(uniquePaths(new int[][]{{1, 0}, {0, 0}}));   // 1
    }


    /*
        Approach 1: Direction-state DP with rolling 1D array (O(n) space)

        State: for each cell (i, j), track two counts:
            f[j][0] = ways to ENTER (i, j) moving DOWN  (from (i-1, j))
            f[j][1] = ways to ENTER (i, j) moving RIGHT (from (i, j-1))

        Transition rules (based on the PREDECESSOR's grid value):
            Moving down into (i, j):
                - If grid[i-1][j] == 0 (normal): predecessor exits in its
                  entry direction -> both entry directions of (i-1, j) work.
                      f[j][0] = f[j][0] + f[j][1]
                - If grid[i-1][j] == 1 (mirror): predecessor flips direction
                  (right -> down) -> only entries that came in going right.
                      f[j][0] = f[j][1]
            Moving right into (i, j):
                - Analogous, using grid[i][j-1].

        Answer at (m-1, n-1) = f[n-1][0] + f[n-1][1] (either entry direction).

        Initial state: f[0][0][0] = 1 (we "arrive" at start moving down).
        This is an artificial sentinel that the grid[0][0] rule then interprets:
            - grid[0][0] == 0 -> both exits allowed from start
            - grid[0][0] == 1 -> only exit right (mirror flips down->right)

        Time:  O(m * n)
        Space: O(n) — only the current row's state is kept
    */
    static int uniquePaths(int[][] grid) {
        final int MOD = 1_000_000_007;
        int m = grid.length;
        int n = grid[0].length;

        // f[j][0]: enter (i, j) by moving down
        // f[j][1]: enter (i, j) by moving right
        long[][] f = new long[n][2];
        f[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Coming from above (i-1, j) -> entering (i, j) going down
                if (i > 0) {
                    if (grid[i - 1][j] == 0) {
                        // Predecessor is normal -> both entry directions can exit down
                        f[j][0] = (f[j][0] + f[j][1]) % MOD;
                    } else {
                        // Predecessor is mirror -> only "entered right, exits down"
                        f[j][0] = f[j][1];
                    }
                }

                // Coming from left (i, j-1) -> entering (i, j) going right
                if (j > 0) {
                    if (grid[i][j - 1] == 0) {
                        // Predecessor is normal -> both entry directions can exit right
                        f[j][1] = (f[j - 1][0] + f[j - 1][1]) % MOD;
                    } else {
                        // Predecessor is mirror -> only "entered down, exits right"
                        f[j][1] = f[j - 1][0];
                    }
                }
            }
        }

        return (int) ((f[n - 1][0] + f[n - 1][1]) % MOD);
    }


    /*
        Approach 2: Full 3D DP table (O(m * n) space)

        Identical transitions to Approach 1, but stores the full table
        f[i][j][dir] instead of rolling over rows. Useful for debugging or
        if you need to answer queries about intermediate cells.

        Time:  O(m * n)
        Space: O(m * n)
    */
    static int uniquePaths3D(int[][] grid) {
        final int MOD = 1_000_000_007;
        int m = grid.length;
        int n = grid[0].length;

        // f[i][j][0]: enter (i, j) by moving down
        // f[i][j][1]: enter (i, j) by moving right
        int[][][] f = new int[m][n][2];
        f[0][0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i > 0) {
                    if (grid[i - 1][j] == 0) {
                        f[i][j][0] = (f[i - 1][j][0] + f[i - 1][j][1]) % MOD;
                    } else {
                        f[i][j][0] = f[i - 1][j][1];
                    }
                }

                if (j > 0) {
                    if (grid[i][j - 1] == 0) {
                        f[i][j][1] = (f[i][j - 1][0] + f[i][j - 1][1]) % MOD;
                    } else {
                        f[i][j][1] = f[i][j - 1][0];
                    }
                }
            }
        }

        return (f[m - 1][n - 1][0] + f[m - 1][n - 1][1]) % MOD;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Direction-state DP with rolling 1D array

Time Complexity: O(m * n)
- Visit every cell once, O(1) work per cell.

Space Complexity: O(n)
- Only the current row's two-direction state is kept.

Approach 2: Full 3D DP table

Time Complexity: O(m * n)
- Same recurrence, same visitation order.

Space Complexity: O(m * n)
- Full f[m][n][2] table.

Key Observation:
The grid value at cell (i, j) determines how the path EXITS that cell,
not how it enters. So when computing transitions INTO (i, j), the relevant
grid value is at the PREDECESSOR. A normal cell (0) preserves direction,
a mirror cell (1) flips it (right <-> down). This gives a clean 2-state
DP per cell — the "direction of entry" is the only additional state
needed beyond position.

---------------------------------------------------------
*/