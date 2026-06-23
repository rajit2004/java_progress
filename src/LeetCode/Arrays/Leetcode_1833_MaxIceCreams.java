package LeetCode.Arrays;

import java.util.Arrays;

public class Leetcode_1833_MaxIceCreams {
    public static void main(String[] args) {
        int[] cost = {1,3,2,4,1};
        int coins = 7;

        System.out.println(maxIceCream(cost, coins));
    }

    /*
        Greedy Approach: To maximize the number of ice creams bought, always buy the cheapest available ice cream first.

        Steps:
            1. Sort the costs array in ascending order.
            2. Traverse the sorted array.
            3. Buy an ice cream if enough coins remain.
            4. Stop as soon as we cannot afford the next one.

        This greedy strategy guarantees the maximum number of ice creams.
     */

    static int maxIceCream(int[] costs, int coins) {

        // Sort costs so that cheaper ice creams are bought first.
        Arrays.sort(costs);

        // Stores the number of ice creams purchased.
        int count = 0;

        for (int cost : costs) {

//            Buy the current ice cream only if enough coins are available.

            if (coins >= cost) {

                // Spend coins.
                coins -= cost;

                // Increase purchased count.
                count++;
            }

//  Since the array is sorted, if we cannot afford the current ice cream, we cannot afford any of the remaining ones either.

            else {
                break;
            }
        }

        return count;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = costs.length

---------------------------------------------------------

Time Complexity: O(n log n)

Reason:

1. Sorting the costs array: O(n log n)

2. Traversing the sorted array: O(n)

Overall: O(n log n)

Sorting dominates the runtime.

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Ignoring the internal space used by the sorting algorithm, only a few variables are used. => count and coins

Therefore: O(1)

Note: Java's Arrays.sort() for primitive arrays uses Dual-Pivot Quicksort with O(log n) recursion stack.

---------------------------------------------------------

Key Observation:

To maximize the number of items purchased under a fixed budget, always buy the cheapest items first.

This is a classic Greedy pattern.

---------------------------------------------------------
*/