package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_1288_RemovedCoveredIntervals {
    public static void main(String[] args) {

        int[][] intervals = {
                {1, 4},
                {3, 6},
                {2, 8}
        };

        System.out.println(removeCoveredIntervals(intervals));
    }

    /*
        Greedy + Sorting Approach :

        Sort intervals by:

            1. Start in ascending order.
            2. If starts are equal, end in descending order.

        This ensures larger intervals come first when
        the starting points are the same.

        Traverse the sorted intervals while maintaining
        the maximum ending point seen so far.

        If the current interval extends beyond maxEnd,
        it is not covered and should be counted.

        Otherwise, it is completely covered by a
        previously processed interval.
     */

    static int removeCoveredIntervals(int[][] intervals) {

        /*
            Sort intervals by:

            1. Start ascending.
            2. End descending (if starts are equal).
         */
        Arrays.sort(intervals, (a, b) -> {

            if (a[0] == b[0]) {
                return b[1] - a[1];
            }

            return a[0] - b[0];
        });

        // Stores the number of remaining intervals.
        int count = 0;

        // Largest ending point seen so far.
        int maxEnd = -1;

        // Traverse all intervals.
        for (int[] interval : intervals) {

            /*
                If the current interval extends beyond
                maxEnd, it cannot be covered.
             */
            if (interval[1] > maxEnd) {

                count++;

                maxEnd = interval[1];
            }

            // Otherwise, the interval is covered
            // by a previously processed interval.
        }

        return count;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of intervals

---------------------------------------------------------

Time Complexity: O(n log n)

Reason:

1. Sorting the intervals:
   O(n log n)

2. Single traversal:
   O(n)

Overall:

O(n log n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Sorting is performed in-place (ignoring the
internal recursion stack used by the sorting algorithm).

Only two variables are maintained:

count
maxEnd

Overall:

O(1)

---------------------------------------------------------

Key Observation:

After sorting,

if an interval's ending point is less than or
equal to the maximum ending point seen so far,
it is completely covered by an earlier interval.

Thus, a single linear scan after sorting is
enough to identify all covered intervals.

---------------------------------------------------------
*/