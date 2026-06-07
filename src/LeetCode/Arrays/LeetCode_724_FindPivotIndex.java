package LeetCode.Arrays;

public class LeetCode_724_FindPivotIndex {
    public static void main(String[] args) {
        int[] nums = {1,7,3,6,5,6};
        System.out.println(pivotIndex(nums));
    }

    /*
        Approach:

        A pivot index is an index where:sum of elements on the left = sum of elements on the right

        Instead of calculating left and right sums for every index separately, we:
            1. Calculate the total sum of the array.
            2. Maintain a running leftSum.
            3. Derive rightSum using:

           rightSum = totalSum - leftSum - currentElement

        This allows us to check every index in O(1).
     */

    static int pivotIndex(int[] nums) {

        // Sum of all elements in the array.
        int totalSum = 0;

        for (int num : nums)
            totalSum += num;

        // Running sum of elements to the left
        // of the current index.
        int leftSum = 0;

        for (int i = 0; i < nums.length; i++) {

            /*
                totalSum = leftSum + nums[i] + rightSum

                Therefore:

                rightSum = totalSum - leftSum - nums[i]
             */
            int rightSum = totalSum - leftSum - nums[i];

            // Found a pivot index.
            if (leftSum == rightSum)
                return i;

            // Include current element in leftSum for the next iteration.
            leftSum += nums[i];
        }

        // No pivot index exists.
        return -1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason:
1. First traversal calculates totalSum.
2. Second traversal checks each index.

O(n) + O(n) = O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:
Only a few variables are used.
No extra arrays or data structures.

---------------------------------------------------------

Key Observation:

Instead of computing left and right sums for every index separately, maintain a running leftSum and derive:

rightSum = totalSum - leftSum - nums[i]

This reduces the solution from O(n²) to O(n).
---------------------------------------------------------
*/