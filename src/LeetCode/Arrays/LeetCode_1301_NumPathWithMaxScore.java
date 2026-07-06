package LeetCode.Arrays;

import java.util.Arrays;
import java.util.List;

public class LeetCode_1301_NumPathWithMaxScore {
    public static void main(String[] args) {

        List<String> board = List.of(
                "E23",
                "2X2",
                "12S"
        );

        System.out.println(Arrays.toString(pathsWithMaxScore(board)));
    }

    /*
        Dynamic Programming Approach :

        Work backwards from the destination (S).

        For every cell, calculate:

            1. Maximum score obtainable from this cell to S.
            2. Number of paths that achieve that maximum score.

        From each cell we can move:

            1. Down
            2. Right
            3. Diagonally Down-Right

        Choose the neighbour with the highest score.
        If multiple neighbours have the same best score,
        add all their path counts.
     */

    static int[] pathsWithMaxScore(List<String> board) {

        int n = board.size();
        int MOD = 1_000_000_007;

        // Stores the maximum score from each cell to S.
        int[][] score = new int[n][n];

        // Stores the number of maximum-score paths.
        int[][] ways = new int[n][n];

        // -1 indicates that the cell is unreachable.
        for (int i = 0; i < n; i++) {
            Arrays.fill(score[i], -1);
        }

        // Base Case : Destination.
        score[n - 1][n - 1] = 0;
        ways[n - 1][n - 1] = 1;

        // Fill DP tables from bottom-right to top-left.
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                char ch = board.get(i).charAt(j);

                // Cannot pass through obstacles.
                if (ch == 'X')
                    continue;

                // Destination already initialized.
                if (i == n - 1 && j == n - 1)
                    continue;

                int best = -1;
                long count = 0;

                // Check Down cell.
                if (i + 1 < n && score[i + 1][j] != -1) {

                    if (score[i + 1][j] > best) {
                        best = score[i + 1][j];
                        count = ways[i + 1][j];
                    }

                    else if (score[i + 1][j] == best) {
                        count += ways[i + 1][j];
                    }
                }

                // Check Right cell.
                if (j + 1 < n && score[i][j + 1] != -1) {

                    if (score[i][j + 1] > best) {
                        best = score[i][j + 1];
                        count = ways[i][j + 1];
                    }

                    else if (score[i][j + 1] == best) {
                        count += ways[i][j + 1];
                    }
                }

                // Check Diagonal cell.
                if (i + 1 < n &&
                        j + 1 < n &&
                        score[i + 1][j + 1] != -1) {

                    if (score[i + 1][j + 1] > best) {
                        best = score[i + 1][j + 1];
                        count = ways[i + 1][j + 1];
                    }

                    else if (score[i + 1][j + 1] == best) {
                        count += ways[i + 1][j + 1];
                    }
                }

                // No valid path from this cell.
                if (best == -1)
                    continue;

                // Add current cell value if it is a digit.
                int value = 0;

                if (ch >= '0' && ch <= '9') {
                    value = ch - '0';
                }

                score[i][j] = best + value;
                ways[i][j] = (int) (count % MOD);
            }
        }

        // Starting cell is unreachable.
        if (ways[0][0] == 0)
            return new int[]{0, 0};

        return new int[]{score[0][0], ways[0][0]};
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = board size

---------------------------------------------------------

Time Complexity: O(n²)

Reason:

Every cell is processed exactly once.

For each cell, we examine only:

1. Down
2. Right
3. Diagonal

Each takes constant time.

Overall:

O(n²)

---------------------------------------------------------

Space Complexity: O(n²)

Reason:

Two DP tables are maintained:

1. score[][] -> Maximum score
2. ways[][]  -> Number of maximum-score paths

Overall:

O(n²)

---------------------------------------------------------

Key Observation:

Instead of exploring every possible path,
store two pieces of information for each cell:

1. Best score achievable.
2. Number of ways to achieve that score.

Whenever multiple neighbouring cells produce
the same maximum score, simply add their path counts.

This avoids recomputing overlapping subproblems,
making Dynamic Programming the ideal solution.

---------------------------------------------------------
*/