package LeetCode.Arrays;

public class LeetCode_3105_LongestStrictlyIncreasingOrStrictlyDecreasingSubarray {

    public static void main(String[] args) {
        int[] nums = {1, 4, 3, 3, 2};
        System.out.println(longestMonotonicSubarrayBrute(nums));
        System.out.println(longestMonotonicSubarrayOnePass(nums));
    }

    /* Approach 1: Brute force -- try every start for both directions
       Pass 1: for each start, extend forward while nums[pos] > nums[pos-1];
       break on the first non-increasing step. Pass 2: same idea with the
       comparison flipped for strictly decreasing. Answer is the larger of the
       two best runs.
       Example: [1,4,3,3,2] -> longest increasing = [1,4] (2),
                longest decreasing = [4,3] or [3,2] (2) -> answer 2.
    */
    static int longestMonotonicSubarrayBrute(int[] nums) {
        int n = nums.length;
        int maxLength = 0;

        // Longest strictly increasing
        for (int start = 0; start < n; start++) {
            int len = 1;
            for (int pos = start + 1; pos < n; pos++) {
                if (nums[pos] > nums[pos - 1]) len++;
                else break;
            }
            maxLength = Math.max(maxLength, len);
        }

        // Longest strictly decreasing
        for (int start = 0; start < n; start++) {
            int len = 1;
            for (int pos = start + 1; pos < n; pos++) {
                if (nums[pos] < nums[pos - 1]) len++;
                else break;
            }
            maxLength = Math.max(maxLength, len);
        }

        return maxLength;
    }

    /* Approach 2: One pass tracking both runs (optimal)
       Walk adjacent pairs. If nums[pos+1] > nums[pos], the current increasing
       run grows and the decreasing run resets to 1 (and vice versa). On
       equality both reset to 1. After each step update the running max with
       max(incLength, decLength).
       Example: [1,4,3,3,2] -> inc run: 1,2,1,1,1  dec run: 1,1,2,1,2
                running max = 2 -> answer 2.
    */
    static int longestMonotonicSubarrayOnePass(int[] nums) {
        int incLength = 1, decLength = 1, maxLength = 1;

        for (int pos = 0; pos < nums.length - 1; pos++) {
            if (nums[pos + 1] > nums[pos]) {
                incLength++;
                decLength = 1;
            } else if (nums[pos + 1] < nums[pos]) {
                decLength++;
                incLength = 1;
            } else {
                incLength = 1;
                decLength = 1;
            }

            maxLength = Math.max(maxLength, Math.max(incLength, decLength));
        }

        return maxLength;
    }
}

/* Complexity Analysis
   Let n = nums.length.

   Approach 1: Brute force
     Time : O(n^2)  - every start scans forward until the first break; worst
            case (already monotone) each start reaches the end.
     Space: O(1)    - running length per start only.
     Key Observation: the longest increasing / decreasing subarray has to start
     somewhere, and once a comparison fails the run cannot extend further from
     that start, so breaking is safe.

   Approach 2: One pass tracking both runs (recommended)
     Time : O(n)    - single left-to-right pass over adjacent pairs.
     Space: O(1)    - two run lengths and one running max.
     Key Observation: increasing and decreasing runs are mutually exclusive at
     each step, so a single comparison decides which run extends and which
     resets -- no need to restart from every index.
*/