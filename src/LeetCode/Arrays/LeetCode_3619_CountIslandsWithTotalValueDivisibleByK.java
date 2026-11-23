package LeetCode.Arrays;

public class LeetCode_3619_CountIslandsWithTotalValueDivisibleByK {

    public static void main(String[] args) {
        int[][] grid1 = {{0, 2, 1}, {0, 2, 1}, {1, 1, 0}};
        int k1 = 3;
        System.out.println(countIslandsDfsReturnsSum(grid1, k1));
        System.out.println(countIslandsDfsInstanceState(cloneGrid(grid1), k1));

        int[][] grid2 = {{1, 1, 0}, {0, 1, 0}, {0, 0, 1}};
        int k2 = 2;
        System.out.println(countIslandsDfsReturnsSum(grid2, k2));
        System.out.println(countIslandsDfsInstanceState(cloneGrid(grid2), k2));
    }

    /* Approach 1: DFS that returns the island's total sum
       Walk every cell. When a non-zero cell is found, run a DFS that
       accumulates its component's total value and zeroes out visited cells
       (in-place visited marking). The returned sum is tested against k; if
       divisible, count the island.
       Example: grid = [[0,2,1],[0,2,1],[1,1,0]], k = 3
                island {2,2,1,1,1} sum 7 -> not divisible
                island {1} sum 1 -> not divisible -> answer 0.
    */
    static int countIslandsDfsReturnsSum(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0 && dfsSum(grid, i, j, m, n) % k == 0) {
                    count++;
                }
            }
        }

        return count;
    }

    // Returns the total value of the island containing (i, j); marks cells as 0.
    static long dfsSum(int[][] grid, int i, int j, int m, int n) {
        long sum = grid[i][j];
        grid[i][j] = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int d = 0; d < 4; d++) {
            int x = i + dr[d], y = j + dc[d];
            if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] > 0) {
                sum += dfsSum(grid, x, y, m, n);
            }
        }

        return sum;
    }

    /* Approach 2: DFS that accumulates into an instance field
       Same traversal, but the running sum lives in a field instead of being
       returned. For each new island, reset `sum`, run the DFS over the whole
       component, then test `sum % k`. Also uses in-place zeroing as the
       visited marker.
       Example: same grids as Approach 1 -> identical counts.
    */
    static int countIslandsDfsInstanceState(int[][] grid, int k) {
        int rows = grid.length, cols = grid[0].length;
        long[] sum = {0};
        int count = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != 0) {
                    sum[0] = 0;
                    dfsAccumulate(grid, r, c, rows, cols, sum);
                    if (sum[0] % k == 0) count++;
                }
            }
        }

        return count;
    }

    static void dfsAccumulate(int[][] grid, int r, int c, int rows, int cols, long[] sum) {
        if (r < 0 || c < 0 || r >= rows || c >= cols) return;
        if (grid[r][c] == 0) return;

        sum[0] += grid[r][c];
        grid[r][c] = 0;

        dfsAccumulate(grid, r + 1, c, rows, cols, sum);
        dfsAccumulate(grid, r - 1, c, rows, cols, sum);
        dfsAccumulate(grid, r, c + 1, rows, cols, sum);
        dfsAccumulate(grid, r, c - 1, rows, cols, sum);
    }

    // Small helper so the two approaches do not see each other's mutations.
    static int[][] cloneGrid(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) copy[i] = grid[i].clone();
        return copy;
    }
}

/* Complexity Analysis
   Let R = grid.length, C = grid[0].length, and V = total cells visited.

   Approach 1: DFS returning the island's total sum
     Time : O(R * C)  - every cell is visited at most once; DFS work across
            all components totals O(R * C).
     Space: O(R * C)  - worst-case recursion depth equals the largest
            component (which can be the whole grid).
     Key Observation: marking cells as 0 during DFS doubles as the visited
     flag, so no separate visited array is needed; the return value carries
     the island sum back up the recursion.

   Approach 2: DFS accumulating into an instance field (recommended)
     Time : O(R * C)  - same traversal cost as Approach 1.
     Space: O(R * C)  - same worst-case recursion depth; no return values
            carried.
     Key Observation: the running sum lives in a shared variable, so the DFS
     can stay void and the caller resets the sum before each island; useful
     when the island must be processed inline (e.g. prune early on some
     condition) rather than only summed at the end.
*/

