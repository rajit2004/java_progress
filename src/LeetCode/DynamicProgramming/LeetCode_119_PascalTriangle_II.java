package LeetCode.DynamicProgramming;

//Given an integer rowIndex, return the rowIndexth (0-indexed) row of the Pascal's triangle.

import java.util.ArrayList;
import java.util.List;

public class LeetCode_119_PascalTriangle_II {
    public static void main(String[] args) {
        System.out.println(getRow(4));
        System.out.println(getRow(3));
    }

    /*
        Dynamic Programming Approach

        Pascal's Triangle Property: row[i] = previousRow[i-1] + previousRow[i]

        Instead of building the entire triangle, we build only the required row.

        Observation:
            While constructing a new row, each element depends on values from the previous row.
            To avoid overwriting values too early,we update the row from right to left.
     */

    static List<Integer> getRow(int rowIndex) {

        // Stores the current row being built.
        List<Integer> row = new ArrayList<>();

        for (int i = 0; i <= rowIndex; i++) {

            // Every row in Pascal's Triangle starts and ends with 1.
            row.add(1);

            /*
                Update interior elements from right to left.

                Example:

                Current Row:
                [1,3,3,1]

                Next Row:

                [1,4,6,4,1]

                Each element becomes: row[j] = row[j] + row[j-1]
             */
            for (int j = i - 1; j > 0; j--) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
        }

        return row;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(rowIndex²)

Reason: For each row i: Inner loop runs approximately i times.

Total work: 1 + 2 + 3 + ... + rowIndex = rowIndex(rowIndex + 1) / 2 = O(rowIndex²)

---------------------------------------------------------

Space Complexity: O(rowIndex)

Reason:

Only the required row is stored. Maximum row size: rowIndex + 1 , Therefore: O(rowIndex)

---------------------------------------------------------

Key Observation:

Instead of generating the entire Pascal's Triangle, we reuse a single list and update it in-place.

Updating from right to left ensures that previous row values are not overwritten before they are used.

This reduces space from O(rowIndex²) to O(rowIndex).

---------------------------------------------------------
*/