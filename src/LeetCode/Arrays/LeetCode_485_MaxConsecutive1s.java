package LeetCode.Arrays;

// Given a binary array nums,
// return the maximum number of consecutive 1's in the array.

public class LeetCode_485_MaxConsecutive1s {
    public static void main(String[] args) {
        int[] arr = {1,1,0,1,1,1};

        System.out.println(findMaxConsecutiveOnes(arr));
    }

    /*
        Approach: Traverse the array while keeping track of:
            1. currentCount -> current streak of consecutive 1s
            2. maxCount     -> largest streak seen so far

        If we encounter:
            1 -> extend the current streak
            0 -> streak breaks, reset currentCount

        Continuously update maxCount whenever a longer streak is found.
     */

    static int findMaxConsecutiveOnes(int[] nums) {

        // Current streak of consecutive 1s.
        int currentCount = 0;

        // Maximum streak found so far.
        int maxCount = 0;

        for (int num : nums) {

            if (num == 1) {

                // Extend the current streak.
                currentCount++;

                // Update the maximum streak.
                maxCount = Math.max(maxCount, currentCount);

            } else {

                // Streak breaks when a 0 is encountered.
                currentCount = 0;
            }
        }

        return maxCount;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of the array

---------------------------------------------------------

Time Complexity: O(n)

Reason: The array is traversed exactly once. Each iteration performs constant-time work.

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only two integer variables are used: currentCount and maxCount
No extra arrays or data structures are created.

---------------------------------------------------------

Key Observation:

A consecutive sequence of 1s ends immediately when a 0 is encountered.

Therefore, we only need to track:

- current streak
- maximum streak

while scanning the array once.

---------------------------------------------------------
*/