package LeetCode.PrefixSum;

public class LeetCode_1991_MiddleIndexInArray {
    public static void main(String[] args) {
        int[] arr = {2,3,-1,8,4};
        System.out.println(findMiddleIndex(arr));
    }

    /*
        Approach:

        A middle index is an index where: sum of elements on the left = sum of elements on the right

        Instead of calculating left and right sums separately for every index, we:
            1. Calculate the total sum of the array.
            2. Maintain a running leftSum.
            3. Derive rightSum using:

           rightSum = totalSum - leftSum - currentElement

        This allows us to check every index in O(1).
     */

    static int findMiddleIndex(int[] nums) {

        // Stores the sum of all elements in the array.
        int totalSum = 0;

        for (int num : nums)
            totalSum += num;

        // Running sum of elements to the left of the current index.
        int leftSum = 0;

        for (int i = 0; i < nums.length; i++) {

            /*
                totalSum = leftSum + nums[i] + rightSum

                Therefore:

                rightSum = totalSum - leftSum - nums[i]
             */
            int rightSum = totalSum - leftSum - nums[i];

            // Found a valid middle index.
            if (leftSum == rightSum)
                return i;

            // Include the current element in leftSum for the next iteration.
            leftSum += nums[i];
        }

        // No middle index exists.
        return -1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason:
1. First traversal computes totalSum.
2. Second traversal checks every index.

O(n) + O(n) = O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:
Only a few variables are used.
No extra arrays or data structures are required.

---------------------------------------------------------

Key Observation:

Instead of recomputing left and right sums for every index, maintain a running leftSum and derive:

rightSum = totalSum - leftSum - nums[i]

This reduces the brute force O(n²) solution to O(n).
---------------------------------------------------------
*/