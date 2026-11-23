package LeetCode.Arrays;

import java.util.*;

public class LeetCode_1254_NumOfClosedIslands {

    public static void main(String[] args) {
        int[][] grid = {
                {1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1}
        };
        System.out.println(closedIslandUnionFind(cloneGrid(grid)));
        System.out.println(closedIslandDfs(cloneGrid(grid)));
        System.out.println(closedIslandBfs(cloneGrid(grid)));
    }

    /* Approach 1: Union-Find over all water cells
       Count every water (0) cell as a separate component. Pre-attach all
       water cells touching the border to a sentinel (parent 0) and decrement
       the component count for each. Then union each water cell with its top
       and left neighbours. Any component that ends up merged with a border
       cell is disqualified; the remaining component count is the number of
       closed islands.
       Example: the standard 5x8 example returns 2.
    */
    static int closedIslandUnionFind(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        int water = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) water++;
            }
        }

        UnionFind uf = new UnionFind(m * n, water);

        // Attach border water cells to the sentinel.
        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 0)        uf.initEdge(i * n);
            if (grid[i][n - 1] == 0)    uf.initEdge(i * n + n - 1);
        }
        for (int j = 1; j < n - 1; j++) {
            if (grid[0][j] == 0)        uf.initEdge(j);
            if (grid[m - 1][j] == 0)    uf.initEdge((m - 1) * n + j);
        }

        // Union each water cell with its top and left neighbours.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) continue;

                if (i > 0 && grid[i - 1][j] == 0) uf.union(i * n + j, (i - 1) * n + j);
                if (j > 0 && grid[i][j - 1] == 0) uf.union(i * n + j, i * n + j - 1);
            }
        }

        return uf.getCount();
    }

    static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        private final boolean[] onEdge;
        private int count;

        UnionFind(int n, int count) {
            parent = new int[n];
            rank = new int[n];
            onEdge = new boolean[n];
            for (int i = 0; i < n; i++) parent[i] = i;
            this.count = count;
        }

        void initEdge(int x) {
            if (!onEdge[x]) {
                parent[x] = 0;       // sentinel
                onEdge[x] = true;
                count--;
            }
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int rx = find(x), ry = find(y);
            if (rx == ry) return;

            if (rank[rx] > rank[ry]) {
                parent[ry] = rx;
                onEdge[rx] |= onEdge[ry];
            } else if (rank[rx] < rank[ry]) {
                parent[rx] = ry;
                onEdge[ry] |= onEdge[rx];
            } else {
                parent[ry] = rx;
                onEdge[rx] |= onEdge[ry];
                rank[rx]++;
            }
            count--;
        }

        int getCount() {
            return count;
        }
    }

    /* Approach 2: DFS flood-fill
       Scan every cell; when a water cell is unvisited, DFS its component and
       return true iff no cell in the component escapes the grid. If the DFS
       ever steps outside the boundary, the component touches the border and
       is not closed; the entire DFS returns false up the call chain.
       Example: the standard 5x8 example returns 2.
    */
    static int closedIslandDfs(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && !visited[i][j] && dfs(grid, i, j, m, n, visited)) {
                    count++;
                }
            }
        }
        return count;
    }

    static boolean dfs(int[][] grid, int x, int y, int m, int n, boolean[][] visited) {
        if (x < 0 || x >= m || y < 0 || y >= n) return false;
        if (grid[x][y] == 1 || visited[x][y])    return true;

        visited[x][y] = true;
        boolean closed = true;

        int[] dr = {0, 1, 0, -1};
        int[] dc = {-1, 0, 1, 0};
        for (int d = 0; d < 4; d++) {
            if (!dfs(grid, x + dr[d], y + dc[d], m, n, visited)) closed = false;
        }
        return closed;
    }

    /* Approach 3: BFS flood-fill
       Same logic as Approach 2 but iterative. Start a BFS from each
       unvisited water cell, marking neighbours as visited. Whenever a
       neighbour would step outside the grid, flag the component as not
       closed; count it only if the flag stays true at the end of the BFS.
       Example: the standard 5x8 example returns 2.
    */
    static int closedIslandBfs(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && !visited[i][j] && bfs(grid, i, j, m, n, visited)) {
                    count++;
                }
            }
        }
        return count;
    }

    static boolean bfs(int[][] grid, int x, int y, int m, int n, boolean[][] visited) {
        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{x, y});
        visited[x][y] = true;
        boolean closed = true;

        int[] dr = {0, 1, 0, -1};
        int[] dc = {-1, 0, 1, 0};

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int r = cur[0] + dr[d];
                int c = cur[1] + dc[d];

                if (r < 0 || r >= m || c < 0 || c >= n) {
                    closed = false;
                } else if (grid[r][c] == 0 && !visited[r][c]) {
                    visited[r][c] = true;
                    q.offer(new int[]{r, c});
                }
            }
        }
        return closed;
    }

    static int[][] cloneGrid(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) copy[i] = grid[i].clone();
        return copy;
    }
}

/* Complexity Analysis
   Let R = grid.length, C = grid[0].length, and N = R * C.

   Approach 1: Union-Find
     Time : O(N * alpha(N))  - one union per water cell (top + left checks),
            amortised inverse-Ackermann per operation with path compression +
            union by rank; border pass is O(R + C).
     Space: O(N)             - parent, rank, onEdge arrays of size R * C.
     Key Observation: closed islands are exactly the water components that
     never touch the grid border; a sentinel (parent 0) merges every border-
     touching component so they can be excluded by simply decrementing the
     component count when they form.

   Approach 2: DFS flood-fill
     Time : O(N)             - every cell is visited at most once.
     Space: O(N)             - worst-case recursion depth equals the largest
            component (up to N), plus visited[][].
     Key Observation: a component is closed iff DFS never exits the grid; the
            boolean return value propagates that fact up the call chain, so
            one DFS handles the whole island in a single pass.

   Approach 3: BFS flood-fill (recommended)
     Time : O(N)             - each cell is enqueued at most once.
     Space: O(N)             - queue + visited[][]; queue size bounded by the
            component's boundary, no recursion depth risk.
     Key Observation: same invariant as DFS, but the check "did any neighbour
     step outside the grid?" is evaluated directly while polling, so a single
     boolean flag at the end of the BFS fully decides whether the island is
     closed.
*/

