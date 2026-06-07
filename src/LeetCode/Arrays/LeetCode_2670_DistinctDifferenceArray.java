package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_2670_DistinctDifferenceArray {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        System.out.println(Arrays.toString(distinctDifferenceArray(arr)));
    }

    /*
        Brute Force Approach:

        For every index i:

        leftDistinct  = number of distinct elements from index 0 to i.

        rightDistinct = number of distinct elements from index i+1 to n-1.

        answer[i] = leftDistinct - rightDistinct

        Since we are only using arrays, we manually check whether an element has already appeared in the current portion of the array.
     */

    static int[] distinctDifferenceArray(int[] nums) {
        int n = nums.length;

        // Stores the final distinct difference values.
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {

            // Count distinct elements in prefix [0...i].
            int leftDistinct = 0;

            for (int j = 0; j <= i; j++) {

                boolean found = false;

                // Check whether nums[j] has already appeared before index j.
                for (int k = 0; k < j; k++) {
                    if (nums[k] == nums[j]) {
                        found = true;
                        break;
                    }
                }

                // First occurrence => distinct element.
                if (!found)
                    leftDistinct++;
            }

            // Count distinct elements in suffix [i+1...n-1].
            int rightDistinct = 0;

            for (int j = i + 1; j < n; j++) {

                boolean found = false;

                // Check whether nums[j] has already appeared in the suffix before index j.
                for (int k = i + 1; k < j; k++) {
                    if (nums[k] == nums[j]) {
                        found = true;
                        break;
                    }
                }

                // First occurrence => distinct element.
                if (!found)
                    rightDistinct++;
            }

            // Store the difference between
            // prefix distinct count and suffix distinct count.
            ans[i] = leftDistinct - rightDistinct;
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n³)

Reason:

For every index i:

1. We traverse the left portion.
2. For every element in the left portion, we may scan all previous elements. => O(n²)

Similarly, we do the same for the right portion.

Since this work is repeated for every index: O(n × n²) = O(n³)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

- ans[] of size n is required for the output.
- No additional data structures are used.

Auxiliary Space: O(1)
Output Space: O(n)

---------------------------------------------------------

Key Observation:

To determine whether an element is distinct, we repeatedly scan previous elements manually.

This causes many repeated comparisons, making the brute force solution O(n³).

Using HashSet/HashMap later can reduce the complexity significantly.
---------------------------------------------------------
*/