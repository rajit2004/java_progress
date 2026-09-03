package LeetCode.Maths;

public class LeetCode_3875_ConstructUniformParityArray_I {
    public static void main(String[] args) {

        LeetCode_3875_ConstructUniformParityArray_I solution = new LeetCode_3875_ConstructUniformParityArray_I();

        // Test Case 1: Use subtraction to make the first value odd.
        int[] nums1 = {2, 3};
        System.out.println("Test Case 1: " + solution.uniformArray(nums1));

        // Test Case 2: The array is already uniformly even.
        int[] nums2 = {4, 6};
        System.out.println("Test Case 2: " + solution.uniformArray(nums2));

        // Test Case 3: The array is already uniformly odd.
        int[] nums3 = {1, 5, 9};
        System.out.println("Test Case 3: " + solution.uniformArray(nums3));

        // Test Case 4: A mixture of even and odd values.
        int[] nums4 = {2, 4, 7, 10};
        System.out.println("Test Case 4: " + solution.uniformArray(nums4));
    }

    /*
        Parity Observation:

        For every element nums1[i], we can choose either:
            1. nums1[i]
            2. nums1[i] - nums1[j], where j != i

        The parity of a difference follows this rule:
            even - even = even
            odd  - odd  = even
            even - odd  = odd
            odd  - even = odd

        If the array contains both an even and an odd number:
            1. Keep every odd number unchanged.
            2. For every even number, subtract any odd number.

        The resulting values are all odd.

        If all values already have the same parity, we can keep every value unchanged. Therefore, the resulting array is already uniform.
        Hence, constructing a uniform parity array is always possible. => Simply return true;
     */
    public boolean uniformArray(int[] nums1) {

        // A single element is already uniformly odd or uniformly even.
        if (nums1.length <= 1)
            return true;

        boolean hasEven = false;
        boolean hasOdd = false;

        // Check whether the array contains both parity types.
        for (int num : nums1) {
            if (num % 2 == 0)
                hasEven = true;
            else
                hasOdd = true;
        }

//            If both parity types exist, every even number can be converted to an odd number by subtracting any odd number. Existing odd numbers can be kept unchanged.
        if (hasEven && hasOdd)
            return true;

        // If all values have the same parity, keep them unchanged.
        return true;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

We scan the array once to check whether even and odd values exist.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Only two boolean variables are used apart from the input array.

Overall: O(1)

---------------------------------------------------------

Key Observation:

If the array contains both an even and an odd number, keep every odd number unchanged and subtract an odd number from every even number. Every resulting value is odd.
If all values already have the same parity, no operation is needed.
Therefore, the answer is always true under the given constraints.

---------------------------------------------------------
*/
