package LeetCode.Arrays;

/*
You are given a 0-indexed integer array nums of size n.
Define two arrays leftSum and rightSum where:
leftSum[i] is the sum of elements to the left of the index i in the array nums. If there is no such element, leftSum[i] = 0.
rightSum[i] is the sum of elements to the right of the index i in the array nums. If there is no such element, rightSum[i] = 0.
Return an integer array answer of size n where answer[i] = |leftSum[i] - rightSum[i]|.

Example 1:

Input: nums = [10,4,8,3]
Output: [15,1,11,22]
Explanation: The array leftSum is [0,10,14,22] and the array rightSum is [15,11,3,0].
The array answer is [|0 - 15|,|10 - 11|,|14 - 3|,|22 - 0|] = [15,1,11,22].

*/

import java.util.Arrays;

public class LeetCode_2754_LeftRightSumDifference {
    public static void main(String[] args) {
        int[] arr = {10,4,8,3};
        System.out.println(Arrays.toString(leftRightDifference(arr)));
    }

    /*
        Approach:

        Instead of creating separate leftSum[] and rightSum[] arrays,
        we compute their values on the fly.

        Observation:

        rightSum = totalSum - leftSum - currentElement

        because:

        totalSum = leftSum + currentElement + rightSum

        Rearranging gives:

        rightSum = totalSum - leftSum - currentElement
     */

    static int[] leftRightDifference(int[] nums) {
        int n = nums.length;

        // Stores the final answer.
        int[] answer = new int[n];

        // Sum of all elements in the array.
        int totalSum = 0;

        for (int num : nums)
            totalSum += num;

        // Running sum of elements to the left of the current index.
        int leftSum = 0;

        for (int i = 0; i < n; i++) {

            /*
                Calculate the sum of elements strictly to the right of the current index.

                totalSum = leftSum + nums[i] + rightSum

                => rightSum = totalSum - leftSum - nums[i]
             */
            int rightSum = totalSum - leftSum - nums[i];

            // Store the absolute difference.
            answer[i] = Math.abs(leftSum - rightSum);

            // Include the current element in leftSum for the next iteration.

            leftSum += nums[i];
        }

        return answer;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason:
1. First traversal computes totalSum.
2. Second traversal computes answer[].

O(n) + O(n) = O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason:
- answer[] of size n is required for the result.
- Apart from that, only a few variables are used.

Auxiliary Space: O(1)
Output Space: O(n)

---------------------------------------------------------

Key Observation:

Instead of explicitly building:

leftSum[]
rightSum[]

we maintain: leftSum (running prefix sum)

and derive: rightSum = totalSum - leftSum - currentElement

This reduces extra space while keeping the solution linear.
---------------------------------------------------------
*/