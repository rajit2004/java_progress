package LeetCode.Graphs;

import java.util.Arrays;

public class LeetCode_3534_PathExistenceQuery_II {
    public static void main(String[] args) {

        int n = 5;
        int[] nums = {1, 3, 5, 7, 9};
        int maxDiff = 2;

        int[][] queries = {
                {0, 2},
                {1, 4},
                {2, 2}
        };

        System.out.println(Arrays.toString(pathExistenceQueries(n, nums, maxDiff, queries)));
    }

    /*
        Sorting + Binary Lifting Approach :

        1. Sort the values while remembering their original indices.
        2. For every index, precompute the farthest reachable index
           within maxDiff.
        3. Build a Binary Lifting table to jump multiple steps at once.
        4. For every query, greedily use the largest jumps possible
           to reach the destination in minimum moves.
     */

    static int[] pathExistenceQueries(int n,
                                      int[] nums,
                                      int maxDiff,
                                      int[][] queries) {

        /*
            arr[i][0] -> value
            arr[i][1] -> original index
         */
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        // Sort by values.
        Arrays.sort(arr, (a, b) -> a[0] - b[0]);

        // Maximum power for Binary Lifting.
        int LOG = 20;

        /*
            up[i][k] stores the node reached after
            making 2^k jumps starting from node i.
         */
        int[][] up = new int[n][LOG];

        int r = n - 1;

        /*
            Compute the farthest reachable node
            within maxDiff for every value.
         */
        for (int l = n - 1; l >= 0; l--) {

            while (arr[r][0] - arr[l][0] > maxDiff) {
                r--;
            }

            int from = arr[l][1];
            int to = arr[r][1];

            up[from][0] = to;

            // Build Binary Lifting table.
            for (int k = 1; k < LOG; k++) {
                up[from][k] =
                        up[up[from][k - 1]][k - 1];
            }
        }

        int[] ans = new int[queries.length];

        // Answer every query.
        for (int t = 0; t < queries.length; t++) {

            int u = queries[t][0];
            int v = queries[t][1];

            // Always move from smaller value to larger value.
            if (nums[u] > nums[v]) {
                int temp = u;
                u = v;
                v = temp;
            }

            // Same node.
            if (u == v) {
                ans[t] = 0;
                continue;
            }

            // Equal values require only one move.
            if (nums[u] == nums[v]) {
                ans[t] = 1;
                continue;
            }

            int steps = 0;

            /*
                Greedily take the largest jump that
                still keeps us before the destination.
             */
            for (int k = LOG - 1; k >= 0; k--) {

                if (nums[up[u][k]] < nums[v]) {

                    steps += 1 << k;

                    u = up[u][k];
                }
            }

            /*
                If even one final jump cannot reach
                the destination, no path exists.
             */
            if (nums[up[u][0]] < nums[v]) {

                ans[t] = -1;
            }

            else {

                ans[t] = steps + 1;
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of elements
q = number of queries

---------------------------------------------------------

Time Complexity: O(n log n + q log n)

Reason:

1. Sorting the values:
   O(n log n)

2. Building the Binary Lifting table:
   O(n log n)

3. Each query performs at most LOG jumps:
   O(log n)

Overall:

O(n log n + q log n)

---------------------------------------------------------

Space Complexity: O(n log n)

Reason:

Binary Lifting table:

up[n][LOG]

dominates the memory usage.

Overall:

O(n log n)

---------------------------------------------------------

Key Observation:

Instead of moving one step at a time, Binary
Lifting allows jumping 2^k steps in one move.

This reduces every query from linear traversal
to logarithmic time while still producing the
minimum number of jumps.

---------------------------------------------------------
*/