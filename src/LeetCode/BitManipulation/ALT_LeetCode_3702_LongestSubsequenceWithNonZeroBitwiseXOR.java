package LeetCode.BitManipulation;

import java.util.Arrays;

public class ALT_LeetCode_3702_LongestSubsequenceWithNonZeroBitwiseXOR {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3};

        System.out.println(longestSubsequence(nums));
    }

    /*
        Alternative Approach :

        First check whether all elements are zero.
        If every element is zero:
            XOR of every possible subsequence is 0,
            so no valid subsequence exists.

        Otherwise, calculate the XOR of the complete array.
        If total XOR is non-zero: The complete array is valid.
        If total XOR is zero: Remove one non-zero element. The remaining XOR becomes non-zero, so the answer is n - 1.
     */
    static int longestSubsequence(int[] nums) {

        int n = nums.length;

        /*
            Create an array containing only zeros and compare it with nums.
            If they are equal, every element is zero.
         */
        int[] zeros = new int[n];

        if (Arrays.equals(nums, zeros)) {
            return 0;
        }

        // Calculate XOR of all elements.
        int totalXor = 0;
        for (int num : nums)
            totalXor ^= num;


        /*
            If total XOR is non-zero, take the complete array.
            Otherwise, remove one non-zero element to make the XOR non-zero.
         */
        return totalXor != 0 ? n : n - 1;
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

1. Arrays.equals() checks at most n elements: O(n)
2. XOR calculation traverses the array once: O(n)

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: An additional array of size n is created to check whether all elements are zero.

Overall: O(n)

---------------------------------------------------------

Key Observation:

If all elements are zero, every possible subsequence has XOR = 0.

Otherwise:

1. Total XOR != 0: Take the complete array.

2. Total XOR == 0: Removing any non-zero element changes the XOR from 0 to that non-zero value.

Therefore, the answer is either n or n - 1.

---------------------------------------------------------
*/