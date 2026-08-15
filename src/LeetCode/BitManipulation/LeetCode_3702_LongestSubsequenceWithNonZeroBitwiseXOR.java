package LeetCode.BitManipulation;

public class LeetCode_3702_LongestSubsequenceWithNonZeroBitwiseXOR {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3};

        System.out.println(longestSubsequence(nums));
    }

    /*
        Greedy + Bit Manipulation Approach :

        We need the longest subsequence whose bitwise XOR is non-zero.

        Consider the XOR of the entire array.

        Case 1: Total XOR is non-zero.
                    The entire array itself is a valid subsequence, so the answer is n.

        Case 2: Total XOR is zero.
                    Since at least one element is non-zero, removing one non-zero element changes the XOR to: 0 ^ x = x which is non-zero.
                    Therefore, we can always take n - 1 elements.

        Case 3:
            All elements are zero.
                    XOR of every possible subsequence will also be zero.
                    Therefore, no valid subsequence exists.
     */
    static int longestSubsequence(int[] nums) {

        int totalXor = 0;

        // Tracks whether the array contains at least one non-zero element.
        boolean hasNonZero = false;

        // Calculate XOR of all elements.
        for (int num : nums) {
            totalXor ^= num;
            if (num != 0)
                hasNonZero = true;
        }

        // If every element is zero, no valid  subsequence can have non-zero XOR.
        if (!hasNonZero)
            return 0;

//  If the XOR of the complete array is already non-zero, the entire array is the longest valid subsequence.
        if (totalXor != 0)
            return nums.length;


//  Total XOR is zero, but at least one element is non-zero.
//  Remove one non-zero element to make the resulting XOR non-zero.
        return nums.length - 1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason: The array is traversed exactly once to calculate the total XOR and check whether a non-zero element exists.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only two variables are used:
            1. totalXor
            2. hasNonZero

No additional data structures are required.

Overall: O(1)

---------------------------------------------------------

Key Observation:

XOR has the property: x ^ x = 0

If the XOR of the complete array is non-zero, the entire array is the answer.

If the XOR is zero and at least one element is non-zero, removing any non-zero element makes the resulting XOR non-zero.

If every element is zero, no valid subsequence exists.

---------------------------------------------------------
*/