package LeetCode.DynamicProgramming;

public class LeetCode_1563_StoneGame_V {
    public static void main(String[] args) {

        int[] stoneValue = {6, 2, 3, 4, 5, 5};

        System.out.println(stoneGameV(stoneValue));
    }

    /*
        Dynamic Programming + Prefix-Sum Optimization : Alice splits the current array into two non-empty parts.

        Let:
            leftSum  = sum of left part
            rightSum = sum of right part

        Rules:
            1. If leftSum < rightSum: Alice keeps the left part.
            2. If leftSum > rightSum: Alice keeps the right part.
            3. If leftSum == rightSum: Alice can choose either part.

        The goal is to maximize the score Alice can obtain.

        f[left][right] stores the maximum score Alice can obtain from the subarray [left...right].

        maxl[left][right] and maxr[left][right] are auxiliary DP tables used to quickly obtain the best result for prefixes and suffixes of the current range.

        The variable 'i' finds the largest split position where: 2 * leftSum <= totalSum

        This allows the valid split positions to be processed efficiently instead of checking every split independently.
     */

    static int[][] f;
    static int[][] maxl;
    static int[][] maxr;

    static int stoneGameV(int[] stoneValue) {

        int n = stoneValue.length;

        // f[left][right] stores the best score obtainable from stoneValue[left...right].
        f = new int[n][n];

        /*
            maxl[left][right] stores the maximum value of: sum(left...k) + f[left][k] for k within [left...right].

            This allows quick transitions when Alice keeps the left portion.
         */
        maxl = new int[n][n];

        /*
            maxr[left][right] stores the maximum value of: sum(k...right) + f[k][right] for k within [left...right].

            This allows quick transitions when Alice keeps the right portion.
         */
        maxr = new int[n][n];

        /*
            Process intervals from shorter ranges to longer ranges.
            Since f[left][right] depends on smaller subarrays, iterate left from right to left.
         */
        for (int left = n - 1; left >= 0; left--) {

            // Base case: a single stone.
            maxl[left][left] = stoneValue[left];
            maxr[left][left] = stoneValue[left];

            // Total sum of the current interval.
            int sum = stoneValue[left];

            /*
                Sum of the left portion of the current split.
                'i' represents the rightmost position of the left portion satisfying: 2 * leftSum <= totalSum
             */
            int sumLeft = 0;

//  Expand the interval from left + 1 towards the right.
            for (int right = left + 1, i = left - 1;right < n;right++ ) {
                // Add the newly included stone.
                sum += stoneValue[right];

//  Move the split pointer while the left portion does not exceed half of the total sum.
                while (i + 1 < right&& (sumLeft + stoneValue[i + 1]) * 2 <= sum) {
                    sumLeft += stoneValue[i + 1];
                    i++;
                }

                /*
                    Case 1: leftSum < rightSum

                    Alice keeps the left portion.
                    maxl[left][i] gives the best score obtainable from that left portion.
                 */
                if (left <= i)
                    f[left][right] = Math.max(f[left][right],maxl[left][i]);

                /*
                    Case 2:leftSum < rightSum
                    Alice can keep the right portion.
                    The right portion begins at i + 2.
                 */
                if (i + 1 < right)
                    f[left][right] = Math.max(f[left][right],maxr[i + 2][right]);

                /*
                    Case 3: leftSum == rightSum
                    Alice can choose either side.
                    Here the right portion starts at i + 1.
                 */
                if (sumLeft * 2 == sum)
                    f[left][right] = Math.max(f[left][right],maxr[i + 1][right]);


                /*
                    Update prefix-based auxiliary DP.
                    Current interval contributes: sum + f[left][right]
                 */
                maxl[left][right] = Math.max(maxl[left][right - 1],sum + f[left][right]);

//  Update suffix-based auxiliary DP.
                maxr[left][right] = Math.max(maxr[left + 1][right],sum + f[left][right]);
            }
        }

        return f[0][n - 1];
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:n = stoneValue.length

---------------------------------------------------------

Time Complexity: O(n²)

Reason:

1. There are O(n²) possible intervals.
2. For each left index, the right pointer moves from left + 1 to n - 1.
3. The split pointer 'i' only moves forward and never moves backward for a fixed left.
4. All DP transitions and auxiliary table updates take O(1).

Therefore, the total complexity is:O(n²)

---------------------------------------------------------

Space Complexity: O(n²)

Reason: Three n × n DP tables are maintained:
            1. f
            2. maxl
            3. maxr

Therefore: O(n²) + O(n²) + O(n²) = O(n²)

---------------------------------------------------------

Key Observation:

The main optimization is avoiding an O(n) scan over every possible split for every interval.
For a fixed left boundary, the split pointer 'i' moves only forward as the right boundary expands.
The auxiliary tables maxl and maxr allow us to retrieve the best score from the selected left or right portion in O(1).
This reduces the overall solution from the straightforward O(n³) DP to O(n²).

---------------------------------------------------------
*/