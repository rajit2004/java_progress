package LeetCode.SlidingWindow;

import java.util.HashMap;

public class LeetCode_2958_LengthofLongestSubarrayWithMostKFrequency {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 1, 2, 1};
        int k = 2;

        System.out.println(maxSubarrayLength(nums, k));
    }

    /*
        Sliding Window + HashMap Approach :

        Maintain a window [left...right] where every element appears at most k times.

        For every new element:
            1. Add it to the frequency map.
            2. If its frequency becomes greater than k, move the left pointer until the frequency becomes valid again.
            3. Track the maximum valid window length.

        Only the newly added element can violate the frequency condition, so we only need to shrink the window until that element becomes valid again.
     */
    static int maxSubarrayLength(int[] nums, int k) {

        // Stores the frequency of each element inside the current window.
        HashMap<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        // Expand the window using the right pointer.
        for (int right = 0; right < nums.length; right++) {

            int current = nums[right];

            // Add the current element to the window.
            freq.put(current,freq.getOrDefault(current, 0) + 1 );

//                If the current element appears more than k times, shrink the window from the left until the condition is valid.
            while (freq.get(current) > k) {
                int removed = nums[left];
                freq.put(removed,freq.get(removed) - 1);
                left++;
            }

            // Update the maximum valid window length.
            maxLength = Math.max(maxLength,right - left + 1);
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

Time Complexity: O(n)

Reason: The right pointer moves from left to right exactly once.
The left pointer also moves from left to right at most once across the entire algorithm.
Therefore, although there is a while loop, both pointers together perform O(n) operations.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: The HashMap can store up to n distinct elements in the worst case.

Overall: O(n)

---------------------------------------------------------

Key Observation:

The sliding window is valid when every element inside it appears at most k times.
When adding nums[right] violates the condition, only that element can be responsible for the violation.
Therefore, we shrink the window from the left until its frequency becomes at most k again.

---------------------------------------------------------
*/