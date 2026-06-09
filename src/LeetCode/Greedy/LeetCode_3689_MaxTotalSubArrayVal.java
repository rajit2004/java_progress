package LeetCode.Greedy;

/*
You are given an integer array nums of length n and an integer k.
You need to choose exactly k non-empty subarrays nums[l..r] of nums.
Subarrays may overlap, and the exact same subarray (same l and r) can be chosen more than once.
The value of a subarray nums[l..r] is defined as: max(nums[l..r]) - min(nums[l..r]).
The total value is the sum of the values of all chosen subarrays.
Return the maximum possible total value you can achieve.
*/

public class LeetCode_3689_MaxTotalSubArrayVal {
    public static void main(String[] args) {
        int[] arr = {1,3,2};
        System.out.println(maxTotalValue(arr, 2));
    }

    /*
        Greedy Observation:

        Since:

            1. Subarrays may overlap.
            2. The same subarray can be chosen multiple times.

        We only need to find the single subarray having the maximum value.

        The maximum possible subarray value is obtainedby taking the subarray containing both the
        global minimum and global maximum elements.

        Therefore: maxSubarrayValue = globalMax - globalMin

        Since the same subarray can be selected repeatedly, choosing it exactly k times gives the maximum answer.

        Answer: k * (globalMax - globalMin)
     */

    static long maxTotalValue(int[] nums, int k) {

        // Track the smallest and largest elements present in the array.
        int min = nums[0];
        int max = nums[0];

        for (int num : nums) {

            // Update global minimum.
            min = Math.min(min, num);

            // Update global maximum.
            max = Math.max(max, num);
        }

        // Choose the best subarray k times.
        return (long) (max - min) * k;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason: Single traversal of the array is performed to find the global minimum and maximum.

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few variables are used. No extra arrays or data structures are created.

---------------------------------------------------------

Key Observation:

Because:

1. Overlapping subarrays are allowed.
2. The same subarray can be chosen multiple times.

There is no need to search for k different subarrays. Simply find the maximum possible subarray value: globalMax - globalMin
and select that subarray k times.

---------------------------------------------------------
*/