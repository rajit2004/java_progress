package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_1840_MaxBuildingHeight {
    public static void main(String[] args) {
        int n = 5;
        int[][] restrictions = {{2,1},{4,1}};

        System.out.println(maxBuilding(n, restrictions));
    }

    /*
        Greedy + Constraint Propagation

        Observation: Adjacent buildings can differ in height by at most 1. Therefore, every restriction affects nearby buildings as well.

        Steps:

        1. Add building 1 with height 0.
        2. Add building n with maximum possible height n - 1.
        3. Sort all restrictions by building index.
        4. Propagate constraints left → right.
        5. Propagate constraints right → left.
        6. Compute the tallest height achievable between every pair of restrictions.
     */

    static int maxBuilding(int n, int[][] restrictions) {

        List<int[]> list = new ArrayList<>();

        // Building 1 always has height 0.
        list.add(new int[]{1, 0});

        // Add all given restrictions.
        for (int[] r : restrictions) {
            list.add(r);
        }

//  Building n can initially have height n-1. This comes from the rule that adjacent buildings differ by at most 1.

        list.add(new int[]{n, n - 1});

        // Sort restrictions by building index.
        list.sort((a, b) -> a[0] - b[0]);

        /*
            Left → Right Pass

            Ensure every restriction can be reached from the previous restriction.

            maxPossibleHeight = previousHeight + distance
         */
        for (int i = 1; i < list.size(); i++) {

            int distance =
                    list.get(i)[0] - list.get(i - 1)[0];

            list.get(i)[1] = Math.min(
                    list.get(i)[1],
                    list.get(i - 1)[1] + distance
            );
        }

        /*
            Right → Left Pass

            Apply the same logic in the reverse direction to satisfy constraints coming from the right side.
         */
        for (int i = list.size() - 2; i >= 0; i--) {

            int distance =
                    list.get(i + 1)[0] - list.get(i)[0];

            list.get(i)[1] = Math.min(
                    list.get(i)[1],
                    list.get(i + 1)[1] + distance
            );
        }

        int answer = 0;

        /*
            Find the tallest possible peak between every pair of consecutive restrictions.

            The maximum height is achieved when climbing up from one restriction and descending toward the next restriction.
         */
        for (int i = 1; i < list.size(); i++) {

            int leftBuilding = list.get(i - 1)[0];
            int leftHeight = list.get(i - 1)[1];

            int rightBuilding = list.get(i)[0];
            int rightHeight = list.get(i)[1];

            int distance = rightBuilding - leftBuilding;

//  Maximum peak possible between: leftHeight and rightHeight => Formula: (leftHeight + rightHeight + distance) / 2

            int tallestPossible =
                    (leftHeight + rightHeight + distance) / 2;

            answer = Math.max(answer, tallestPossible);
        }

        return answer;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: m = number of restrictions

---------------------------------------------------------

Time Complexity: O(m log m)

Reason:

1. Add restrictions: O(m)

2. Sort restrictions: O(m log m)

3. Left → Right pass: O(m)

4. Right → Left pass: O(m)

5. Find tallest peak: O(m)

Overall: O(m log m)

Sorting dominates the runtime.

---------------------------------------------------------

Space Complexity: O(m)

Reason:

The list stores: m restrictions + building 1 + building n

Total: O(m)

---------------------------------------------------------

Key Observation:

A restriction does not only affect one building.

Because neighboring buildings may differ by at most 1, every restriction influences an entire range of buildings.

The two propagation passes ensure all restrictions become mutually consistent.

After that, the tallest achievable building between two restrictions can be computed directly using: (leftHeight + rightHeight + distance) / 2

---------------------------------------------------------
*/