package LeetCode.Arrays;

public class LeetCode_3942_MinOperationsToSortAPermutation {

    public static void main(String[] args) {
        System.out.println(minOperations(new int[]{0, 1, 2, 3}));
        System.out.println(minOperations(new int[]{2, 3, 0, 1}));
        System.out.println(minOperations(new int[]{0, 3, 2, 1}));
    }

    /* Approach 1: Locate 0 and check cyclic ascending / descending order
       A sortable permutation must be a cyclic rotation of either
         [0, 1, 2, ..., n-1]      (ascending)
         [0, n-1, n-2, ..., 1]    (descending)
       Find the index of 0 (`start`). Walk the permutation circularly from
       `start` and compare each value against both target patterns.

       If neither pattern matches, sorting is impossible -> -1.

       If ascending matches:
         - 0 can move directly by rotating the array left by `start` moves, or
         - by rotating right by `n - start` moves, or
         - by reversing the direction first (cost 2 extra operations), giving
           the two candidates `start` and `n - start + 2`.
         Answer is the smaller of those two.

       If descending matches:
         - the mirror candidates are `n - start` and `start + 2`.
         Answer is the smaller of those two.

       Example: [2,3,0,1] -> 0 at index 2, ascending pattern matches ->
                min(2, 4-2+2) = min(2, 4) = 2.
                [0,3,2,1] -> 0 at index 0, descending pattern matches ->
                min(4-0, 0+2) = min(4, 2) = 2.
    */
    static int minOperations(int[] nums) {
        final int n = nums.length;
        if (n == 1) return 0;

        int start = 0;
        while (nums[start] != 0) start++;

        boolean asc = true;
        boolean desc = true;

        for (int i = 1; i < n; i++) {
            int value = nums[(start + i) % n];

            if (value != i)       asc = false;
            if (value != n - i)   desc = false;

            if (!asc && !desc) return -1;
        }

        if (asc) {
            return Math.min(start, n - start + 2);
        }

        return Math.min(n - start, start + 2);
    }
}

/* Complexity Analysis
   Let n = nums.length.

   Approach 1: Locate 0 and check cyclic ascending / descending order
     Time : O(n)   - one pass to find 0, one circular pass to verify both
            patterns.
     Space: O(1)   - only the start index and two booleans.
     Key Observation: after fixing the position of 0, the only valid layouts
     are cyclic ascending or cyclic descending; once that is confirmed, the
     cheapest way to sort is the smaller of rotating directly or reversing
     direction first (cost 2).
*/

