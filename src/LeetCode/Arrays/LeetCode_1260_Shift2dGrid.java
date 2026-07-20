package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_1260_Shift2dGrid {
    public static void main(String[] args) {

        int[][] grid = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int k = 1;

        System.out.println(shiftGrid(grid, k));
    }

    /*
        Simulation Approach : Treat the 2D grid as a single 1D array.

        For every element:

        1. Convert its 2D position into a 1D index.
        2. Shift the index by k positions.
        3. Convert the new 1D index back into a 2D position.
        4. Place the element in its new location.

        Finally, convert the resulting 2D array into a List<List<Integer>>.
     */
    static List<List<Integer>> shiftGrid(int[][] grid, int k) {

        int rows = grid.length;
        int cols = grid[0].length;

        // Total number of elements in the grid.
        int totalElements = rows * cols;

        // Only the effective number of shifts matters.
        k %= totalElements;

        // Stores the shifted grid.
        int[][] shiftedGrid = new int[rows][cols];

        // Move every element to its shifted position.
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                // Convert the current cell into a 1D index.
                int currentIndex = i * cols + j;

                // Compute the shifted index.
                int shiftedIndex = (currentIndex + k) % totalElements;

                // Convert the shifted index back into row and column.
                int newRow = shiftedIndex / cols;
                int newCol = shiftedIndex % cols;

                shiftedGrid[newRow][newCol] = grid[i][j];
            }
        }

        // Convert the shifted grid into the required format.
        List<List<Integer>> answer = new ArrayList<>();

        for (int i = 0; i < rows; i++) {

            List<Integer> currentRow = new ArrayList<>();

            for (int j = 0; j < cols; j++) {
                currentRow.add(shiftedGrid[i][j]);
            }

            answer.add(currentRow);
        }

        return answer;
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

1. Traverse every cell once to compute its new position.
2. Traverse the shifted grid once to convert it into List<List<Integer>>.

Overall: O(m × n)

---------------------------------------------------------

Space Complexity: O(m × n)

Reason: An additional grid of the same size is used to store the shifted elements. The output list also stores all elements.

Overall: O(m × n)

---------------------------------------------------------

Key Observation:

A 2D grid can be treated as a linear array.

Using the mappings: index = row × cols + col { row = index / cols and col = index % cols }

allows every element to be shifted in constant time without performing repeated single-step shifts.

---------------------------------------------------------
*/