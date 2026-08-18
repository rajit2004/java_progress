package LeetCode.Arrays;

public class LeetCode_3471_FindLargestAlmostMissingInt {
    public static void main(String[] args) {

        int[] nums = {3, 9, 2, 1, 7};
        int k = 3;

        System.out.println(largestInteger(nums, k));
    }

    /*
        Frequency + Boundary Observation Approach : An integer is "almost missing" if it appears in exactly one subarray of size k.

        Instead of generating every subarray, we use the frequency of each value and observe the possible positions where a unique element can appear in only one window.

        Cases:
            1. k == n: There is only one subarray, so every distinct element appears in exactly one subarray.
            2. k == 1: Every element belongs to exactly one subarray, so a value is valid if it appears only once.
            3. 1 < k < n: A value can belong to only one window when it occurs exactly once and is positioned at either end of the array.

        Among all valid values, return the largest.
     */
    static int largestInteger(int[] nums, int k) {

        // Store the frequency of every value.
        int[] frequency = new int[51];

        for (int num : nums)
            frequency[num]++;

        int answer = -1;
        int n = nums.length;

        // Check every element as a candidate.
        for (int i = 0; i < n; i++) {

            /*
                If k == n, there is only one subarray, so every value is almost missing.
                Otherwise, the value must occur exactly once and must be positioned where it can belong to only one window.
             */
            if (k == n|| (frequency[nums[i]] == 1 && (k == 1|| i == 0|| i == n - 1)))
                // Keep the largest valid value.
                answer = Math.max(answer, nums[i]);
        }
        return answer;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Build the frequency array: O(n)
2. Traverse the array to find the largest valid element: O(n)

The frequency array has fixed size 51, so its initialization is O(1).

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: A fixed-size frequency array of size 51 is used. No additional data structure depends on n.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The important part is determining when a value can occur in exactly one subarray of size k.
If k == n, there is only one possible window.
If k == 1, every position forms its own window, so only values occurring once are valid.
For 1 < k < n, a value occurring exactly once can belong to only one window when it is located at the beginning or end of the array.
Therefore, we only need frequency counting and a single scan to find the largest candidate.

---------------------------------------------------------
*/