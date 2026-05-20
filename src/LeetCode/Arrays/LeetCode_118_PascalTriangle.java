package LeetCode.Arrays;

/*
Given an integer numRows, return the first numRows of Pascal's triangle.
In Pascal's triangle, each number is the sum of the two numbers directly above it as shown
*/

import java.util.ArrayList;
import java.util.List;

public class LeetCode_118_PascalTriangle {
    public static void main(String[] args) {
        int rows = 5;
        System.out.println(generate(rows));
    }
    static List<List<Integer>> generate(int numRows) {

        List<List<Integer>> result = new ArrayList<>();

        // Create each row
        for (int i = 0; i < numRows; i++) {

            List<Integer> row = new ArrayList<>();

            // Every row starts and ends with 1
            for (int j = 0; j <= i; j++) {

                // First or last position
                if (j == 0 || j == i) {
                    row.add(1);
                } else {

                    // Get previous row
                    List<Integer> prevRow = result.get(i - 1);

                    // Add two numbers from previous row
                    int value = prevRow.get(j - 1) + prevRow.get(j);

                    row.add(value);
                }
            }

            // Add completed row to result
            result.add(row);
        }

        return result;
    }
}
