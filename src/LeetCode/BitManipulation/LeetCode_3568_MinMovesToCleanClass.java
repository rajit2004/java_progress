package LeetCode.BitManipulation;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class LeetCode_3568_MinMovesToCleanClass {
    public static void main(String[] args) {

        LeetCode_3568_MinMovesToCleanClass solution =
                new LeetCode_3568_MinMovesToCleanClass();

        // Test Case 1: Reach both pieces of litter before the energy runs out.
        String[] classroom1 = {"S..",".L.","..L"};
        System.out.println("Test Case 1: " + solution.minMoves(classroom1, 3));

        // Test Case 2: Recharge at R before continuing toward the litter.
        String[] classroom2 = {"S.R",".XL","..L"};
        System.out.println("Test Case 2: " + solution.minMoves(classroom2, 2));

        // Test Case 3: The litter cannot be reached because of obstacles.
        String[] classroom3 = {"SXL","XXX","..L"};
        System.out.println("Test Case 3: " + solution.minMoves(classroom3, 5));
    }

    static final int[] dx = {0, 1, 0, -1};
    static final int[] dy = {1, 0, -1, 0};

    /*
        BFS + Bitmask Dynamic Programming Approach:

        Each state is represented by:
            1. The current cell.
            2. The set of litter pieces already cleaned.
            3. The remaining energy.
            4. The number of moves taken so far.

        A bitmask is used to represent cleaned litter:
            bit i = 1  -> the i-th litter piece has been cleaned.
            bit i = 0  -> the i-th litter piece has not been cleaned.

        Since every movement costs one move, BFS guarantees that the first state that cleans all litter uses the minimum possible number of moves.
        For every cell and litter mask, we store only the maximum energy reached so far. A state with more remaining energy is always at least as useful as a state with less energy at the same cell and mask.
     */
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int[][] id = new int[m][n];
        int sx = 0;
        int sy = 0;
        int litterCount = 0;

        // Assign one bit to every litter cell and find the starting cell.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = classroom[i].charAt(j);

                if (cell == 'S') {
                    sx = i;
                    sy = j;
                } else if (cell == 'L') {
                    id[i][j] = 1 << litterCount;
                    litterCount++;
                }
            }
        }

        // When all bits are set, every litter cell has been cleaned.
        int fullMask = (1 << litterCount) - 1;

//            bestEnergy[x][y][mask] stores the greatest amount of energy with which we have reached (x, y) after cleaning the litter represented by mask.
        int[][][] bestEnergy = new int[m][n][1 << litterCount];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(bestEnergy[i][j], -1);
            }
        }

        // The initial state starts at S with no litter cleaned.
        bestEnergy[sx][sy][0] = energy;

        Deque<State> queue = new ArrayDeque<>();
        queue.addLast(new State(sx, sy, 0, energy, 0));

        while (!queue.isEmpty()) {
            State current = queue.removeFirst();

            // BFS reaches this state in the minimum number of moves.
            if (current.mask == fullMask)
                return current.steps;

            // No movement is possible when the remaining energy is zero.
            if (current.remainingEnergy == 0)
                continue;

            // Try moving in all four directions.
            for (int direction = 0; direction < 4; direction++) {
                int nextX = current.x + dx[direction];
                int nextY = current.y + dy[direction];

                // Ignore positions outside the classroom or blocked by X.
                if (nextX < 0 ||nextX >= m ||nextY < 0 ||nextY >= n ||classroom[nextX].charAt(nextY) == 'X')
                    continue;


                char nextCell = classroom[nextX].charAt(nextY);

                // R restores the energy to its original maximum value.
                int nextEnergy = nextCell == 'R'? energy: current.remainingEnergy - 1;

                // Mark the litter as cleaned if the next cell contains L.
                int nextMask = current.mask | id[nextX][nextY];

//                    Visit the next state only when it provides more energy than any previously known state with the same position and cleaned-litter mask.
                if (nextEnergy > bestEnergy[nextX][nextY][nextMask]) {
                    bestEnergy[nextX][nextY][nextMask] = nextEnergy;

                    queue.addLast(new State(nextX,nextY,nextMask,nextEnergy,current.steps + 1)
                    );
                }
            }
        }

        // Return -1 when it is impossible to clean all litter.
        return -1;
    }

    // Stores one BFS state.
    private static class State {
        int x;
        int y;
        int mask;
        int remainingEnergy;
        int steps;

        State(int x, int y, int mask, int remainingEnergy, int steps) {
            this.x = x;
            this.y = y;
            this.mask = mask;
            this.remainingEnergy = remainingEnergy;
            this.steps = steps;
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

m = number of rows
n = number of columns
L = number of litter cells
E = maximum energy

---------------------------------------------------------

Time Complexity: O(m * n * 2^L * E)

There are m * n possible positions and 2^L possible litter masks.
A state can be improved for different energy values, and each state checks four possible movements.

Therefore, the worst-case time complexity is: O(m * n * 2^L * E)

---------------------------------------------------------

Space Complexity: O(m * n * 2^L)

The bestEnergy array stores one maximum-energy value for every position and litter mask.
The BFS queue can also contain states from this state space.

Overall: O(m * n * 2^L)

---------------------------------------------------------

Key Observation:

The current position alone is not enough to describe a state.
The cleaned litter and remaining energy also affect future moves.
Therefore, the complete state is: (row, column, cleaned-litter mask, remaining energy)
BFS finds the minimum number of moves, while bestEnergy removes states that reach the same position and mask with less energy.

---------------------------------------------------------
*/
