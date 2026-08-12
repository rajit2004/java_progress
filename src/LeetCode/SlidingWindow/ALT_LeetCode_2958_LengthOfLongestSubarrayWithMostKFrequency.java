package LeetCode.SlidingWindow;

import java.util.HashMap;

public class ALT_LeetCode_2958_LengthOfLongestSubarrayWithMostKFrequency {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 1, 2, 1};
        int k = 2;

        System.out.println(maxSubarrayLength(nums, k));
    }

    /*
        Optimized Sliding Window Approach : The window size never decreases.

        Instead of repeatedly shrinking the window until it becomes valid, track how many elements currently have a frequency > k.

        'bad' stores the number of elements whose frequency exceeds k.
        When bad > 0, remove exactly one element from the left.
        This keeps the window size constant or increases it by one, so the final window represents the longest valid subarray.
     */
    static int maxSubarrayLength(int[] nums, int k) {

        int n = nums.length;

        // Left boundary of the sliding window.
        int left = 0;

//            Number of distinct values whose frequency is currently greater than k.
        int bad = 0;

        // Stores frequencies inside the window.
        HashMap<Integer, Integer> freq = new HashMap<>();

        // Expand the window using the right pointer.
        for (int right = 0; right < n; right++) {

            int current = nums[right];

            // Increase frequency of the current element.
            int count = freq.getOrDefault(current, 0) + 1;
            freq.put(current, count);

            /*
                The frequency becomes invalid exactly when it changes from k to k + 1.
                Increment bad only once for each value.
             */
            if (count == k + 1) {
                bad++;
            }

//                If the window is already valid, continue expanding it.
            if (bad == 0) {
                continue;
            }

            /*
                The window became invalid.
                Remove exactly one element from the left. This is enough because the window size is maintained as a non-decreasing value.
             */
            int removed = nums[left];

            int newCount = freq.get(removed) - 1;
            freq.put(removed, newCount);

//                If the frequency drops from k + 1 to k, this value is valid again.
            if (newCount == k) {
                bad--;
            }

            left++;
        }

        /*
            The window size never decreases.
            Therefore, after processing the entire array, the final window length is the maximum valid length.
         */
        return n - left;
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

The left pointer also moves at most n times.
Each HashMap operation takes O(1) average time.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: The HashMap can contain up to n distinct elements in the worst case.

Overall: O(n)

---------------------------------------------------------

Key Observation:

The normal sliding-window solution may shrink the window multiple times while fixing a frequency violation.

Here, we observe that the optimal window size never needs to decrease.
Whenever adding an element makes the window invalid, remove exactly one element from the left and continue.
The variable 'bad' tells us whether the window currently contains any value appearing more than k times.

Therefore, the final window length is the maximum valid length.

---------------------------------------------------------
*/