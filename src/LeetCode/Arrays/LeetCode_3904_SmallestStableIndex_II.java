package LeetCode.Arrays;

public class LeetCode_3904_SmallestStableIndex_II {
    public static void main(String[] args) {
        int[] nums = {10, 5, 7, 6, 8};
        int k = 2;
        System.out.println(firstStableIndex(nums, k));
    }

    /*
       Approach: Suffix Minimum + Prefix Maximum
       For every index i, we need: max(nums[0...i]) - min(nums[i...n-1]) <= k
           1. Build a suffix minimum array where minValue[i] stores the minimum value from index i to the end.
           2. Traverse from left to right while maintaining the maximum value seen so far.
           3. At each index, check whether the difference between the prefix maximum and suffix minimum is <= k.
           4. The first valid index is the smallest stable index.
   */
    static int firstStableIndex(int[] nums, int k) {
        int n = nums.length;

        // Store minimum value from each index to the end.
        int[] minValue = new int[n];
        minValue[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            minValue[i] = Math.min(minValue[i + 1], nums[i]);
        }

        // Track maximum value from the beginning up to index i.
        int maxValue = 0;
        for (int i = 0; i < n; i++) {
            maxValue = Math.max(maxValue, nums[i]);
            if (maxValue - minValue[i] <= k) {
                return i;
            }
        }
        return -1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

- Building the suffix minimum array takes O(n).
- Finding the first stable index takes O(n).

Space Complexity: O(n)

- The suffix minimum array requires O(n) extra space.

Key Observation: For index i, the stability condition can be checked as: max(nums[0...i]) - min(nums[i...n-1]) <= k

By maintaining the prefix maximum and precomputing suffix minimums, every index can be checked in O(1).
---------------------------------------------------------
*/