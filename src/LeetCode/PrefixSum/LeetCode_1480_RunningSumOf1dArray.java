package LeetCode.PrefixSum;

import java.util.Arrays;

public class LeetCode_1480_RunningSumOf1dArray {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 4};
        System.out.println(Arrays.toString(runningSum(nums)));
    }

    /*
        Prefix Sum Approach : The running sum at index i is: runningSum[i] = nums[0] + nums[1] + ... + nums[i]
        We can modify the input array itself. For every index: nums[i] += nums[i - 1]
        This makes nums[i] store the running sum up to that position.
     */
    static int[] runningSum(int[] nums) {

//            Start from index 1 because the first element is already its own running sum.
        for (int i = 1; i < nums.length; i++)
            // Add the previous running sum to the current element.
            nums[i] += nums[i - 1];

        return nums;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason: The array is traversed once from index 1 to n - 1.

Each operation takes O(1).

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

The input array is modified in-place.
No additional array or data structure is created.

Overall: O(1)

---------------------------------------------------------

Key Observation: The previous element already contains the running sum up to that position.
Therefore: nums[i] = nums[i] + nums[i - 1] allows us to calculate every prefix sum in-place without using an additional array.

---------------------------------------------------------
*/