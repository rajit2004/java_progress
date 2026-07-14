package LeetCode.Graphs;

import java.util.Arrays;

public class LeetCode_3532_PathExistenceQuery_I {
    public static void main(String[] args) {

        int n = 5;
        int[] nums = {1, 2, 3, 8, 9};
        int maxDiff = 2;

        int[][] queries = {
                {0, 2},
                {1, 3},
                {3, 4}
        };

        System.out.println(Arrays.toString(pathExistenceQueries(n, nums, maxDiff, queries)));
    }

    /*
        Greedy Approach :

        Consecutive indices belong to the same connected component
        as long as the difference between adjacent values is
        less than or equal to maxDiff.

        Whenever the difference becomes greater than maxDiff,
        start a new connected component.

        For every query, simply check whether both indices
        belong to the same component.
     */

    static boolean[] pathExistenceQueries(int n,
                                          int[] nums,
                                          int maxDiff,
                                          int[][] queries) {

        // component[i] stores the connected component of index i.
        int[] component = new int[n];

        // Current component number.
        int group = 0;

        component[0] = 0;

        // Assign a component number to every index.
        for (int i = 1; i < n; i++) {

            /*
                If the gap between consecutive numbers
                exceeds maxDiff, a new component begins.
             */
            if (nums[i] - nums[i - 1] > maxDiff) {
                group++;
            }

            component[i] = group;
        }

        // Stores answers for all queries.
        boolean[] answer = new boolean[queries.length];

        // Process every query.
        for (int i = 0; i < queries.length; i++) {

            int u = queries[i][0];
            int v = queries[i][1];

            // A path exists only if both indices belong
            // to the same connected component.
            answer[i] = (component[u] == component[v]);
        }

        return answer;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = nums.length
q = queries.length

---------------------------------------------------------

Time Complexity: O(n + q)

Reason:

1. Traverse nums once to assign components:
   O(n)

2. Answer each query in O(1):
   O(q)

Overall:

O(n + q)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

An additional component array of size n
is maintained.

Overall:

O(n)

---------------------------------------------------------

Key Observation:

Whenever the difference between two consecutive
numbers exceeds maxDiff, they can never belong
to the same connected component.

By assigning a component number to every index,
each query reduces to a simple component equality
check, giving O(1) query time.

---------------------------------------------------------
*/