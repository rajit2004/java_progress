package LeetCode.Maths;

public class LeetCode_3876_ConstructUniformParityArray_II {
    public static void main(String[] args) {

        LeetCode_3876_ConstructUniformParityArray_II solution = new LeetCode_3876_ConstructUniformParityArray_II();

        // Test Case 1: The minimum value is odd, so the answer is true.
        int[] nums1 = {3, 8, 10};
        System.out.println("Test Case 1: " + solution.uniformArray(nums1));

        // Test Case 2: All values are even, so the answer is true.
        int[] nums2 = {2, 4, 8, 10};
        System.out.println("Test Case 2: " + solution.uniformArray(nums2));

        // Test Case 3: The minimum value is even and an odd value exists.
        int[] nums3 = {2, 5, 8};
        System.out.println("Test Case 3: " + solution.uniformArray(nums3));

        // Test Case 4: A single odd value is already uniform.
        int[] nums4 = {7};
        System.out.println("Test Case 4: " + solution.uniformArray(nums4));
    }

    /*
        Minimum-Value Parity Observation:

        Let mn be the smallest value in nums1.
        If mn is odd, every element can be made odd:
            1. Keep mn unchanged.
            2. For any other value nums1[i], subtract mn.

        Since subtracting an odd number changes the parity of a value, every even value becomes odd. Odd values can be kept unchanged or transformed when needed.

        If mn is even, the only possible uniform parity is even.
        In this case, every value must already be even. If any odd value exists, it cannot be converted to an even value using the allowed operation because subtracting the smallest even value preserves the odd parity.

        Therefore:
            1. If the minimum value is odd, return true.
            2. Otherwise, return true only when every value is even.
     */
    public boolean uniformArray(int[] nums1) {

        // Find the smallest value in the array.
        int minimum = nums1[0];

        for (int num : nums1) {
            if (num < minimum)
                minimum = num;
        }

        // An odd minimum can be used to make all values odd.
        if (minimum % 2 != 0)
            return true;

        /*
            When the minimum is even, all values must already be even.
            An odd value would make constructing a uniform array impossible.
        */
        for (int num : nums1) {
            if (num % 2 != 0)
                return false;
        }

        // Every value is even, so the original array is already uniform.
        return true;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

We scan the array once to find the minimum value and possibly scan it once more to check whether every value is even.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Only one integer variable is used apart from the input array.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The parity of the minimum element determines the answer.
If the minimum is odd, it can be used to change even values into odd values. If the minimum is even, every value must already be even.

---------------------------------------------------------
*/
