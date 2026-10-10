package LeetCode.DynamicProgramming;

public class LeetCode_2267_CheckIfValidParenthesesStringPath {
    public static void main(String[] args) {

        // Sample 1 (2x3): all three paths yield "(())" -> true
        char[][] grid1 = {
                {'(', '(', ')'},
                {'(', ')', ')'}
        };
        System.out.println("Sample 1 -> " + hasValidPath(grid1) + " (expected: true)");

        // Sample 2 (2x3): path "(())" exists -> true
        char[][] grid2 = {
                {'(', ')', '('},
                {'(', ')', ')'}
        };
        System.out.println("Sample 2 -> " + hasValidPath(grid2) + " (expected: true)");

        // Sample 3 (1x4): single path "(())" -> true
        char[][] grid3 = {{'(', '(', ')', ')'}};
        System.out.println("Sample 3 -> " + hasValidPath(grid3) + " (expected: true)");

        // Edge case: odd path length (3x3 -> 5 chars) -> cannot be balanced
        char[][] grid4 = {
                {'(', '(', '('},
                {'(', ')', '('},
                {'(', '(', ')'}
        };
        System.out.println("Edge (odd path length) -> " + hasValidPath(grid4) + " (expected: false)");

        // Edge case: start is ')' -> immediately invalid
        char[][] grid5 = {
                {')', '('},
                {'(', ')'}
        };
        System.out.println("Edge (start is ')') -> " + hasValidPath(grid5) + " (expected: false)");

        // Edge case: end is '(' -> immediately invalid
        char[][] grid6 = {
                {'(', '('},
                {')', '('}
        };
        System.out.println("Edge (end is '(') -> " + hasValidPath(grid6) + " (expected: false)");

        // Edge case: no valid path even though start/end look fine
        //   1x4: single path "()((" -> never closes -> false
        char[][] grid7 = {{'(', ')', '(', '('}};
        System.out.println("Edge (unbalanced path) -> " + hasValidPath(grid7) + " (expected: false)");

        // Edge case: 2x2 -> path length 3 (odd) -> false
        char[][] grid8 = {
                {'(', '('},
                {')', ')'}
        };
        System.out.println("Edge (2x2 odd length) -> " + hasValidPath(grid8) + " (expected: false)");
    }


    /*
        Approach: Grid DP on (row, col, balance)

        A path from (0, 0) to (n-1, m-1) has exactly n + m - 1 characters.

        Quick rejections:
            1. If (n + m - 1) is odd, no balanced parentheses string can exist.
            2. If grid[0][0] != '(' or grid[n-1][m-1] != ')', it's impossible.

        DP definition:
            dp[i][j][b] = true iff there exists a path from (0,0) to (i,j)
                          whose character string has balance b
                          (balance = # of '(' minus # of ')').

        A valid path requires:
            - balance >= 0 at every prefix (never closed more than opened), and
            - final balance at (n-1, m-1) equals 0.

        The "balance >= 0" constraint is enforced implicitly: when computing
        transitions we only set dp[i][j][next] = true if next >= 0.

        Initialization:
            dp[0][0][1] = true (we've consumed the first character '(' → balance 1).

        Transition for cell (i, j) with c = grid[i][j]:
            change = (c == '(') ? +1 : -1
            For each predecessor p ∈ { (i-1, j), (i, j-1) } and each balance b:
                if dp[p][b] is true:
                    next = b + change
                    if next >= 0: dp[i][j][next] = true

        Answer:
            dp[n-1][m-1][0]  (balance 0 at the bottom-right cell).

        Time:  O(n * m * (n + m))
        Space: O(n * m * (n + m))
    */
    static boolean hasValidPath(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int pathLen = n + m - 1;

        // Quick rejections
        if (pathLen % 2 == 1) {
            return false;                       // odd length -> cannot balance
        }
        if (grid[0][0] != '(' || grid[n - 1][m - 1] != ')') {
            return false;
        }

        // dp[i][j][balance] = reachable?
        boolean[][][] dp = new boolean[n][m][pathLen + 1];

        // Starting cell: we've just read '(' so balance is 1
        dp[0][0][1] = true;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                // Skip the start cell — already initialized
                if (i == 0 && j == 0) continue;

                int change = grid[i][j] == '(' ? 1 : -1;

                // Coming from above (i-1, j)
                if (i > 0) {
                    for (int balance = 0; balance <= pathLen; ++balance) {
                        if (!dp[i - 1][j][balance]) continue;

                        int next = balance + change;
                        if (next >= 0) {
                            dp[i][j][next] = true;
                        }
                    }
                }

                // Coming from the left (i, j-1)
                if (j > 0) {
                    for (int balance = 0; balance <= pathLen; ++balance) {
                        if (!dp[i][j - 1][balance]) continue;

                        int next = balance + change;
                        if (next >= 0) {
                            dp[i][j][next] = true;
                        }
                    }
                }
            }
        }

        return dp[n - 1][m - 1][0];
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Grid DP on (row, col, balance)

Let n = grid.length, m = grid[0].length.

Time Complexity: O(n * m * (n + m))

- We visit every cell (n * m) and, for each, iterate over all possible
  balances up to pathLen = n + m - 1.
- Transitions are O(1) per (cell, balance) pair.

Space Complexity: O(n * m * (n + m))

- The DP table dp[n][m][pathLen + 1] dominates.

Key Observation:
A valid parentheses path is characterized by two properties:
   (a) balance never goes negative (no prefix has more ')' than '('), and
   (b) final balance is 0.
The DP state (i, j, b) captures exactly "can we reach (i, j) with balance b
while satisfying (a)". Checking dp[n-1][m-1][0] then answers the question.
The balance bound is pathLen because each step changes balance by at most 1.

Possible optimization (not required for this problem):
Since pathLen = n + m - 1 is at most 200 for constraints, O(n*m*pathLen)
is comfortably small. A memoized DFS would use the same state space but
avoid pre-allocating the full 3D table.

---------------------------------------------------------
*/