package LeetCode.Arrays;

public class LeetCode_1267_CountServersThatCommunicate {

    public static void main(String[] args) {
        int[][] grid = {{1, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
        System.out.println(countServersBrute(grid));
        System.out.println(countServersRowColCounts(grid));
    }

    /* Approach 1: Brute force -- scan row and column per server
       For every cell with a server, walk its entire row looking for another
       server; if none, walk its entire column. If either finds a partner,
       the server can communicate and is counted.
       Example: [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
                -> (0,0),(0,1) share row, (1,2),(2,2) share column,
                   (3,3) is alone -> answer 4.
    */
    static int countServersBrute(int[][] grid) {
        int rows = grid.length;
        int cols = rows > 0 ? grid[0].length : 0;
        int count = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != 1) continue;

                boolean canCommunicate = false;

                // same row?
                for (int otherCol = 0; otherCol < cols; otherCol++) {
                    if (otherCol != c && grid[r][otherCol] == 1) {
                        canCommunicate = true;
                        break;
                    }
                }

                // same column?
                if (!canCommunicate) {
                    for (int otherRow = 0; otherRow < rows; otherRow++) {
                        if (otherRow != r && grid[otherRow][c] == 1) {
                            canCommunicate = true;
                            break;
                        }
                    }
                }

                if (canCommunicate) count++;
            }
        }

        return count;
    }

    /* Approach 2: Precompute row and column counts (optimal)
       rowCount[r] = number of servers in row r, colCount[c] = number in
       column c. A server at (r, c) can communicate iff rowCount[r] > 1 or
       colCount[c] > 1. Count all such servers in a second pass.
       Example: [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
                rowCount = [2,1,1,1], colCount = [1,1,2,1]
                -> (0,0),(0,1),(1,2),(2,2) qualify -> answer 4.
    */
    static int countServersRowColCounts(int[][] grid) {
        int rows = grid.length;
        int cols = rows > 0 ? grid[0].length : 0;

        int[] rowCount = new int[rows];
        int[] colCount = new int[cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    rowCount[r]++;
                    colCount[c]++;
                }
            }
        }

        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1 && (rowCount[r] > 1 || colCount[c] > 1)) {
                    count++;
                }
            }
        }

        return count;
    }
}

/* Complexity Analysis
   Let m = grid.length (rows) and n = grid[0].length (columns).

   Approach 1: Brute force -- scan row and column per server
     Time : O(m * n * (m + n))  - each server scans its row (n) and possibly
            its column (m).
     Space: O(1)                - only a counter and a flag.
     Key Observation: a server communicates iff some other server shares its
     row or column; the scan can stop as soon as one partner is found.

   Approach 2: Precompute row and column counts (recommended)
     Time : O(m * n)  - one pass to fill row/col counts, one pass to tally.
     Space: O(m + n)  - rowCount and colCount arrays.
     Key Observation: a server is communicable iff its row has > 1 server or
     its column has > 1 server, which depends only on the per-row and
     per-column totals, not on any pairwise comparison.
*/