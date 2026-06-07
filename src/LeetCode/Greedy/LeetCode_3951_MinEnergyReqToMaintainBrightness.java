package LeetCode.Greedy;

import java.util.Arrays;

public class LeetCode_3951_MinEnergyReqToMaintainBrightness {
    public static void main(String[] args) {
        int n = 5;
        int brightness = 5;
        int[][] intervals = {{6,12}};

        System.out.println(minEnergy(n, brightness, intervals));
    }

    /*
        Approach:

        A bulb illuminates on :
            current position
            left neighbor
            right neighbor

        Therefore, one bulb can illuminate at most 3 positions.

        To achieve a required brightness: minimum bulbs needed = ceil(brightness / 3) which can be computed as: (brightness + 2) / 3

        ------------------------------------------------

        The lighting requirement only needs to be satisfied during active time units.

        Since intervals may overlap, first merge all overlapping intervals and calculate the total number of active time units.

        Final Energy: total active time units × minimum bulbs needed per time unit
     */

    static long minEnergy(int n, int brightness, int[][] intervals) {

        // Sort intervals by starting time.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Total number of time units that require lighting.
        long timeAct = 0;

        // Current merged interval.
        int st = intervals[0][0];
        int end = intervals[0][1];

        for (int i = 0; i < intervals.length; i++) {

            /*
                Overlapping or adjacent interval.

                Example:

                [1,5]
                [4,8]

                becomes:  [1,8]
             */
            if (intervals[i][0] <= end + 1) {
                end = Math.max(end, intervals[i][1]);
            }

            /*
                Non-overlapping interval.

                Store the length of the current merged interval and start a new one.
             */
            else {
                timeAct += (long) end - st + 1;

                st = intervals[i][0];
                end = intervals[i][1];
            }
        }

        // Add the final merged interval length.
        timeAct += (long) end - st + 1;

        /*
            One bulb covers at most 3 positions.

            Ceiling division: ceil(brightness / 3)
         */
        long bulb = (brightness + 2L) / 3L;

        // Total energy consumed.
        return timeAct * bulb;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(m log m)

where:

m = number of intervals

Reason:

1. Sorting intervals:
   O(m log m)

2. Merging intervals:
   O(m)

Overall: O(m log m)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Only a few variables are used. Ignoring the sorting algorithm's internal space: O(1)

---------------------------------------------------------

Key Observation:

1 bulb illuminates at most 3 positions.

Therefore:

minimum bulbs required  = ceil(brightness / 3)

The problem then reduces to:

1. Merge overlapping intervals.
2. Count active time units.
3. Multiply by bulbs required per unit time.

---------------------------------------------------------
*/