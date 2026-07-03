package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3286_SafeWalkThroughGrid {
    public static void main(String[] args) {

        List<List<Integer>> grid = new ArrayList<>();

        grid.add(Arrays.asList(0, 1, 0));
        grid.add(Arrays.asList(1, 0, 1));
        grid.add(Arrays.asList(0, 0, 0));

        int health = 3;

        System.out.println(findSafeWalk(grid, health));
    }

    /*
        Breadth First Search (BFS) Approach :

        We start from the top-left cell.

        Every time we move into a cell, its value is deducted from our current health.

        A move is valid only if health remains positive.

        Since the same cell can be reached with different remaining health values, we store the maximum health seen so far for every cell.

        A cell is explored again only if we reach it with more remaining health than before.
     */

    static boolean findSafeWalk(List<List<Integer>> grid, int health) {

        int m = grid.size();
        int n = grid.get(0).size();

        // Deduct the cost of the starting cell.
        int startHealth = health - grid.get(0).get(0);

        // Cannot even stand on the starting cell.
        if (startHealth <= 0)
            return false;

//            Queue stores: {row, column, remainingHealth}
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{0, 0, startHealth});

//            best[r][c] stores the maximum remaining health with which this cell has been reached.
        int[][] best = new int[m][n];

        for (int i = 0; i < m; i++) {
            Arrays.fill(best[i], -1);
        }

        best[0][0] = startHealth;

        // Four possible directions.
        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        // Perform BFS.
        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int row = current[0];
            int col = current[1];
            int currentHealth = current[2];

            // Destination reached safely.
            if (row == m - 1 && col == n - 1) {
                return true;
            }

            // Explore all neighbouring cells.
            for (int[] dir : directions) {

                int newRow = row + dir[0];
                int newCol = col + dir[1];

                // Ignore cells outside the grid.
                if (newRow < 0 || newRow >= m ||
                        newCol < 0 || newCol >= n) {
                    continue;
                }

                // Health remaining after entering the next cell.
                int newHealth =
                        currentHealth - grid.get(newRow).get(newCol);

                // Health must always remain positive.
                if (newHealth <= 0)
                    continue;

//  Visit this cell only if the current path leaves us with more health than any previous path.
                if (newHealth > best[newRow][newCol]) {

                    best[newRow][newCol] = newHealth;

                    queue.offer(new int[]{
                            newRow,
                            newCol,
                            newHealth
                    });
                }
            }
        }

        // No valid path exists.
        return false;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

m = number of rows
n = number of columns

---------------------------------------------------------

Time Complexity: O(m × n)

Reason:

Each cell is processed only when we find a better remaining health value.
Each exploration checks only four neighbours.

Overall: O(m × n)

---------------------------------------------------------

Space Complexity: O(m × n)

Reason:

The following data structures are used:

1. best[][] array
2. BFS queue

Both can store at most m × n cells.

Overall: O(m × n)

---------------------------------------------------------

Key Observation:

A normal visited array is not sufficient.

The same cell may be reached through different paths with different remaining health values.

We should continue exploring a cell only if the new path leaves us with more health than any previously discovered path.

---------------------------------------------------------
*/