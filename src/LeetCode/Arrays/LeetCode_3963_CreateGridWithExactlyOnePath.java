package LeetCode.Arrays;

public class LeetCode_3963_CreateGridWithExactlyOnePath {

    public static void main(String[] args) {
        printGrid(createGridTopThenRight(3, 4));
        printGrid(createGridLeftThenBottom(3, 4));
    }

    /* Approach 1: Top row open, last column open
       Fill the first row entirely with '.'. For every other row, put '#' in
       columns 0..n-2 and '.' only in column n-1. The only path from (0,0) to
       (m-1, n-1) is: walk right along row 0 to (0, n-1), then walk down the
       last column. Every other route hits a '#'.
       Example: m = 3, n = 4
                "...."
                "###."
                "###."
    */
    static String[] createGridTopThenRight(int m, int n) {
        String[] grid = new String[m];
        grid[0] = ".".repeat(n);

        String walledRow = "#".repeat(n - 1) + ".";
        for (int i = 1; i < m; i++) {
            grid[i] = walledRow;
        }

        return grid;
    }

    /* Approach 2: Left column open, first row open (mirror construction)
       Fill the first row entirely with '.'. For every other row, put '.' only
       in column 0 and '#' everywhere else. The only path from (0,0) to
       (m-1, n-1) is: walk right along row 0 to (0, n-1)... wait -- the left
       column is the corridor here, so the path is: walk down column 0 to
       (m-1, 0), then walk right along the bottom row to (m-1, n-1). Also
       unique.
       Example: m = 3, n = 4
                "...."
                ".###"
                ".###"
    */
    static String[] createGridLeftThenBottom(int m, int n) {
        String[] grid = new String[m];
        grid[0] = ".".repeat(n);

        String walledRow = "." + "#".repeat(n - 1);
        for (int i = 1; i < m; i++) {
            grid[i] = walledRow;
        }

        return grid;
    }

    static void printGrid(String[] grid) {
        for (String row : grid) System.out.println(row);
        System.out.println();
    }
}

/* Complexity Analysis
   Let m = number of rows, n = number of columns.

   Approach 1: Top row open, last column open
     Time : O(m * n)  - '.".repeat(n)' and '#'.repeat(n-1) each cost O(n);
            the pattern row is built once and reused m-1 times by reference.
     Space: O(m * n)  - the output grid itself; the walled row is a single
            shared reference (String is immutable, so this is safe).
     Key Observation: making column n-1 the only vertical corridor and row 0
     the only horizontal one forces the unique path top-row -> last-column;
     any other move immediately hits a '#'.

   Approach 2: Left column open (mirror construction)
     Time : O(m * n)  - identical string building costs.
     Space: O(m * n)  - same output size, same shared-row trick.
     Key Observation: symmetric to Approach 1, forcing the unique path
     left-column -> bottom-row; both constructions are interchangeable, so
     either may be returned.
*/

