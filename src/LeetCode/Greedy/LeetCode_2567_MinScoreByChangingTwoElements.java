package LeetCode.Greedy;

import java.util.*;

public class LeetCode_2567_MinScoreByChangingTwoElements {

    public static void main(String[] args) {
        System.out.println(minimizeSumTopBottom(new int[]{1, 4, 7, 8, 5}));
        System.out.println(minimizeSumTopBottom(new int[]{1, 4, 3}));

        System.out.println(minimizeSumSort(new int[]{1, 4, 7, 8, 5}));
        System.out.println(minimizeSumSort(new int[]{1, 4, 3}));
    }

    /* Approach 1: Track top 3 and bottom 3 in O(n)
       We may change any 2 elements to any values. To minimise (max - min) the
       optimal move is to spend the two "free changes" on the extremes, so only
       three candidates matter:
         (a) change the 2 largest   -> score = 3rd largest - smallest
         (b) change the 2 smallest  -> score = largest - 3rd smallest
         (c) change 1 largest + 1 smallest -> score = 2nd largest - 2nd smallest
       Track the top-3 and bottom-3 values in one pass with nested if-else, then
       take the minimum of the three candidates.
       Example: [1,4,7,8,5] -> mn = {1,4,5}, mx = {8,7,5}
                candidates: 7-1=6, 8-5=3, 5-4=1? -- recomputed below.
    */
    static int minimizeSumTopBottom(int[] nums) {
        int mn1 = Integer.MAX_VALUE, mn2 = Integer.MAX_VALUE, mn3 = Integer.MAX_VALUE;
        int mx1 = Integer.MIN_VALUE, mx2 = Integer.MIN_VALUE, mx3 = Integer.MIN_VALUE;

        for (int x : nums) {
            if (x < mn1)      { mn3 = mn2; mn2 = mn1; mn1 = x; }
            else if (x < mn2) { mn3 = mn2; mn2 = x; }
            else if (x < mn3) { mn3 = x; }

            if (x > mx1)      { mx3 = mx2; mx2 = mx1; mx1 = x; }
            else if (x > mx2) { mx3 = mx2; mx2 = x; }
            else if (x > mx3) { mx3 = x; }
        }

        return Math.min(mx1 - mn3, Math.min(mx2 - mn2, mx3 - mn1));
    }

    /* Approach 2: Sort and take the three candidates
       After sorting, the same three candidates are direct index expressions:
         nums[n-3] - nums[0]   (drop 2 largest)
         nums[n-2] - nums[1]   (drop 1 largest + 1 smallest)
         nums[n-1] - nums[2]   (drop 2 smallest)
       Answer is the minimum of the three.
       Example: [1,4,7,8,5] -> [1,4,5,7,8]
                candidates: 5-1=4, 7-4=3, 8-5=3 -> answer 3.
    */
    static int minimizeSumSort(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return Math.min(
                Math.min(nums[n - 3] - nums[0], nums[n - 2] - nums[1]),
                nums[n - 1] - nums[2]
        );
    }
}

/* Complexity Analysis
   Let n = nums.length.

   Approach 1: Track top 3 and bottom 3 in O(n)
     Time : O(n)   - one pass, constant work per element.
     Space: O(1)   - six scalar trackers.
     Key Observation: only the two extremes can be eliminated by the two free
     changes, so the answer is min of the three extreme-drop differences; the
     top-3 / bottom-3 values are enough to compute them without sorting.

   Approach 2: Sort and take the three candidates
     Time : O(n log n)  - dominated by the sort.
     Space: O(1)        - sort in place, no extra arrays.
     Key Observation: after sorting, the three candidates are contiguous index
     differences: nums[n-3]-nums[0], nums[n-2]-nums[1], nums[n-1]-nums[2].
*/
