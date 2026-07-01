package LeetCode.Arrays;

import java.util.*;

/*
You are given an n x n grid containing thieves.

The safeness factor of a path is defined as the minimum distance from any cell on that path to the nearest thief.

Return the maximum safeness factor possible from (0,0) to (n-1,n-1).
*/

public class LeetCode_2812_SafestPathInGrid {
    public static void main(String[] args) {

        List<List<Integer>> grid = new ArrayList<>();

        grid.add(Arrays.asList(1, 0, 0));
        grid.add(Arrays.asList(0, 0, 0));
        grid.add(Arrays.asList(0, 0, 1));

        System.out.println(maximumSafenessFactor(grid));
    }

    /*
        Approach :

        Step 1: Use Multi-Source BFS from all thief cells to calculate the distance of every cell from its nearest thief.

        Step 2: Use a Max Heap (Priority Queue) to find the path from source to destination that maximizes the minimum safeness factor.

        This is similar to Dijkstra's algorithm where: Path Cost = minimum safeness along the path and we want to maximize this value.
     */

    static int maximumSafenessFactor(List<List<Integer>> grid) {

        int n = grid.size();

        // Stores distance of each cell from nearest thief.
        int[][] dist = new int[n][n];

        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new LinkedList<>();


//            Add all thief cells into the queue. Multi-source BFS starts simultaneously from every thief.

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (grid.get(i).get(j) == 1) {
                    queue.offer(new int[]{i, j});
                    dist[i][j] = 0;
                }
            }
        }

        // Directions : up, down, left, right.
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

//            Multi-Source BFS : Computes minimum distance from every cell to its nearest thief.
        while (!queue.isEmpty()) {

            int[] curr = queue.poll();

            int r = curr[0];
            int c = curr[1];

            for (int k = 0; k < 4; k++) {

                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr >= 0 && nr < n &&
                        nc >= 0 && nc < n &&
                        dist[nr][nc] == -1) {

                    dist[nr][nc] = dist[r][c] + 1;

                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        /*
            Max Heap : Stores: {row, col, safeness}
            Cells with larger safeness are processed first.
         */
        PriorityQueue<int[]> pq =
                new PriorityQueue<>((a, b) -> b[2] - a[2]);

        boolean[][] visited = new boolean[n][n];

        // Start from top-left cell.
        pq.offer(new int[]{0, 0, dist[0][0]});

        visited[0][0] = true;

//            Modified Dijkstra's Algorithm : Always explore the path having the highest current safeness factor.
        while (!pq.isEmpty()) {

            int[] curr = pq.poll();

            int r = curr[0];
            int c = curr[1];
            int safe = curr[2];

            // Destination reached.
            if (r == n - 1 && c == n - 1) {
                return safe;
            }

            for (int k = 0; k < 4; k++) {

                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr >= 0 && nr < n &&
                        nc >= 0 && nc < n &&
                        !visited[nr][nc]) {

                    visited[nr][nc] = true;

//                        Safeness of a path is determined by the minimum safeness value encountered along that path.
                    int newSafe =
                            Math.min(safe, dist[nr][nc]);

                    pq.offer(new int[]{nr, nc, newSafe});
                }
            }
        }

        return 0;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let : n = grid size

---------------------------------------------------------

Time Complexity: O(n² log n)

Reason:

1. Multi-Source BFS visits every cell once. - O(n²)

2. Modified Dijkstra using Max Heap: Each cell may be inserted into the heap once. Heap operations take: O(log(n²)) = O(log n)
    Total: O(n² log n)

Overall: O(n² log n)

---------------------------------------------------------

Space Complexity: O(n²)

Reason:

We store:

1. Distance matrix      -> O(n²)
2. Visited matrix       -> O(n²)
3. Queue for BFS        -> O(n²)
4. Priority Queue       -> O(n²)

Overall: O(n²)

---------------------------------------------------------

Key Observation:

The problem asks to maximize the minimum distance from any cell on the path to a thief.

This is a classic: "maximize the minimum value along a path" problem.

Steps:

1. Precompute cell safeness using Multi-Source BFS.
2. Use a Max Heap + Dijkstra to greedily explore
   paths with the highest current safeness first.

---------------------------------------------------------
*/