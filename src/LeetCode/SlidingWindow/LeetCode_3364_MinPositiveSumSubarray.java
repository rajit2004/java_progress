package LeetCode.SlidingWindow;

import java.util.*;

public class LeetCode_3364_MinPositiveSumSubarray {

    public static void main(String[] args) {
        List<Integer> nums = List.of(3, -2, 1, 4);
        System.out.println(minimumSumSubarrayBrute(nums, 2, 3));
        System.out.println(minimumSumSubarraySlidingWindow(nums, 2, 3));
    }

    /* Approach 1: Brute force -- try every valid-length window
       For every start index i, extend j while the window length stays <= r,
       accumulating the running sum; whenever the length is >= l and the sum
       is positive, update the minimum. Return -1 if nothing qualifies.
       Example: nums = [3,-2,1,4], l=2, r=3
                len 2: [3,-2]=1, [-2,1]=-1, [1,4]=5 -> best 1
                len 3: [3,-2,1]=2, [-2,1,4]=3        -> best 2
                answer = 1.
    */
    static int minimumSumSubarrayBrute(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int minSum = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n && j - i + 1 <= r; j++) {
                sum += nums.get(j);
                if (j - i + 1 >= l && sum > 0) {
                    minSum = Math.min(minSum, sum);
                }
            }
        }

        return minSum == Integer.MAX_VALUE ? -1 : minSum;
    }

    /* Approach 2: Sliding window per fixed window size
       Iterate window size w from l to r. For each w:
         - build the sum of nums[0..w-1] and, if positive, update minSum.
         - slide the window by one: subtract the leaving element, add the
           entering element, re-check if the new sum is positive.
       Equivalent to trying every valid window, but reuses the running sum
       across slides instead of recomputing it.
       Example: nums = [3,-2,1,4], l=2, r=3 -> same answer 1.
    */
    static int minimumSumSubarraySlidingWindow(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int minSum = Integer.MAX_VALUE;

        for (int w = l; w <= r; w++) {
            int sum = 0;
            for (int j = 0; j < w; j++) sum += nums.get(j);

            if (sum > 0) minSum = Math.min(minSum, sum);

            int low = 0, high = w;
            while (high < n) {
                sum -= nums.get(low++);
                sum += nums.get(high++);
                if (sum > 0) minSum = Math.min(minSum, sum);
            }
        }

        return minSum == Integer.MAX_VALUE ? -1 : minSum;
    }
}

/* Complexity Analysis
   Let n = nums.size() and m = r - l + 1.

   Approach 1: Brute force
     Time : O(n^2 * r)  - for each start i the inner loop runs at most r
            steps, so O(n * r) per start times n starts.
     Space: O(1)        - running sum and answer only.
     Key Observation: every window sum is recomputed from scratch by extending
     j from each start i; correct but does redundant work across starts.

   Approach 2: Sliding window per fixed window size (recommended)
     Time : O((r - l + 1) * n)  - one O(n) pass per window size.
     Space: O(1)                - running sum and two pointers.
     Key Observation: with a fixed window size, sliding by one position is an
     O(1) update (subtract leaving, add entering), so each pass touches every
     window once.
*/

