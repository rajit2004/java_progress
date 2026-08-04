package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode_3731_MissingNum {
    public static void main(String[] args) {

        int[] nums = {2, 4, 7, 8};

        System.out.println(findMissingElements(nums));
    }

    /*
        Sorting + Simulation Approach :

        Step 1: Sort the array.

        Step 2: Start from the smallest number.

        Step 3: Compare the expected number with the current array element.

            If both are equal, move to the next array element.
            Otherwise, the expected number is missing, so add it to the answer.

        Continue until all array elements have been processed.
     */
    static List<Integer> findMissingElements(int[] nums) {

        // Stores all missing numbers.
        List<Integer> result = new ArrayList<>();

        // Sort the array.
        Arrays.sort(nums);

        // Current expected number.
        int current = nums[0];

//            Compare the expected number with the current array element.
        for (int i = 0; i < nums.length; current++, i++) {

            // Missing number found.
            if (current < nums[i]) {

                result.add(current);

                // Stay on the same array element.
                i--;
            }
        }

        return result;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n log n)

Reason:

1. Sorting the array: O(n log n)

2. Linear traversal to find missing numbers: O(n + k)

where k is the number of missing values.

Overall: O(n log n)

---------------------------------------------------------

Space Complexity: O(k)

Reason:

The result list stores all missing numbers.

Ignoring the output list, the algorithm uses only constant extra space.

Overall: O(k)

---------------------------------------------------------

Key Observation:

After sorting, every number should appear in increasing order without gaps.

Whenever the expected value is smaller than the current array element, it must be a missing number and is added to the answer.

---------------------------------------------------------
*/