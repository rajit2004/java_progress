package LeetCode.SlidingWindow;

import java.util.HashMap;

public class BRUTE_LeetCode_2958_LengthOfLongestSubarrayWithMostKFrequency {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 1, 2, 1};
        int k = 2;

        System.out.println(maxSubarrayLength(nums, k));
    }

    /*
        Brute Force Approach : Try every possible starting index.

        For each starting index:
            1. Expand the subarray towards the right.
            2. Maintain the frequency of every element.
            3. If any element appears more than k times, stop extending the current subarray.
            4. Track the maximum valid subarray length.

        Since adding a new element can only increase its own frequency, only the newly added element needs to be checked.
     */
    static int maxSubarrayLength(int[] nums, int k) {

        int n = nums.length;

        // Stores the maximum valid subarray length.
        int maxLength = 0;

        // Try every possible starting index.
        for (int left = 0; left < n; left++) {

            // Stores frequencies for the current subarray.
            HashMap<Integer, Integer> freq = new HashMap<>();

            // Expand the subarray towards the right.
            for (int right = left; right < n; right++) {

                int current = nums[right];

                // Update frequency of the current element.
                int count = freq.getOrDefault(current, 0) + 1;

                freq.put(current, count);

                /*
                    If the current element appears more than k times, the current subarray is invalid.
                    Since extending it further cannot make the frequency smaller, we can stop.
                 */
                if (count > k) {
                    break;
                }

                // Update the longest valid subarray length.
                maxLength = Math.max(maxLength,right - left + 1);
            }
        }

        return maxLength;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n²)

Reason:

1. The outer loop tries every starting index.
2. The inner loop expands the subarray.

In the worst case, no frequency exceeds k, so we examine: n + (n - 1) + (n - 2) + ... + 1 = n(n + 1) / 2 = O(n²)

HashMap operations take O(1) average time.

Overall: O(n²)

---------------------------------------------------------

Space Complexity: O(n)

Reason: For each starting index, the HashMap can contain up to n distinct elements.

Overall: O(n)

---------------------------------------------------------

Key Observation:

For a fixed starting index, once an element's frequency becomes greater than k, any larger subarray will also violate the condition.
Therefore, we can immediately stop expanding that particular subarray.

This avoids checking unnecessary extensions.

---------------------------------------------------------
*/