package LeetCode.Arrays;

public class LeetCode_3903_SmallestStableIndex_I {
    public static void main(String[] args) {

        LeetCode_3903_SmallestStableIndex_I solution = new LeetCode_3903_SmallestStableIndex_I();

        // Test Case 1: Index 1 is the first stable index.
        int[] nums1 = {1, 3, 2, 5};
        System.out.println("Test Case 1: " + solution.firstStableIndex(nums1, 2));

        // Test Case 2: Index 0 is stable because the full array range is within k.
        int[] nums2 = {4, 5, 6};
        System.out.println("Test Case 2: " + solution.firstStableIndex(nums2, 2));

        // Test Case 3: No index satisfies the stability condition.
        int[] nums3 = {1, 10, 20};
        System.out.println("Test Case 3: " + solution.firstStableIndex(nums3, 3));

        // Test Case 4: The last index is stable because only nums[i] is considered.
        int[] nums4 = {1, 10, 20};
        System.out.println("Test Case 4: " + solution.firstStableIndex(nums4, 0));
    }

    /*
        Brute Force Approach:

        An index i is stable when the difference between:
            1. The maximum value in nums[0 ... i].
            2. The minimum value in nums[i ... n - 1].
        is at most k.

        For every index:
            1. Start with nums[i] as both the maximum and minimum.
            2. Scan the left side, including nums[0 ... i - 1], to find the maximum value.
            3. Scan the right side, including nums[i + 1 ... n - 1], to find the minimum value.
            4. Return the first index where maxValue - minValue <= k.

        If no index satisfies the condition, return -1.
     */
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;

        // Check every index from left to right.
        for (int i = 0; i < n; i++) {
            // nums[i] belongs to both the left prefix and right suffix.
            int maxValue = nums[i];
            int minValue = nums[i];

            // Find the maximum value in nums[0 ... i].
            for (int j = 0; j < i; j++)
                maxValue = Math.max(maxValue, nums[j]);

            // Find the minimum value in nums[i ... n - 1].
            for (int j = i + 1; j < n; j++)
                minValue = Math.min(minValue, nums[j]);

            // Return the first index whose range is at most k.
            if (maxValue - minValue <= k)
                return i;
        }

        // No stable index exists.
        return -1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n^2)

For every index, we may scan the elements on its left and right.
Across all indices, the total number of comparisons is O(n^2).

Overall : O(n^2)

---------------------------------------------------------

Space Complexity: O(1)

Only a few integer variables are used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The current value nums[i] is included in both ranges. Therefore, maxValue and minValue are initialized with nums[i], then the left side is used to find the maximum and the right side is used to find the minimum.
The first index satisfying: maximum value in nums[0 ... i] - minimum value in nums[i ... n - 1] <= k is the smallest stable index.

---------------------------------------------------------
*/
