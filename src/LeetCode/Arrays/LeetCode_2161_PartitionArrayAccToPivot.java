package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_2161_PartitionArrayAccToPivot {
    public static void main(String[] args) {
        int[] arr = {9,12,5,10,14,3,10};
        int pivot = 10;

        System.out.println(Arrays.toString(pivotArray(arr, pivot)));
    }

    /*
        Approach:

        Partition the array into three groups:
            1. Elements smaller than pivot
            2. Elements equal to pivot
            3. Elements greater than pivot

        The relative order of elements inside each group must remain unchanged.

        To preserve order, we traverse the original array three times and place elements into the result array.
     */

    static int[] pivotArray(int[] nums, int pivot) {

        int n = nums.length;

        // Stores the final partitioned array.
        int[] result = new int[n];

        // Tracks the next insertion position.
        int index = 0;

//    Pass 1:  Place all elements smaller than pivot.

        for (int num : nums) {
            if (num < pivot) {
                result[index++] = num;
            }
        }

//    Pass 2: Place all elements equal to pivot.

        for (int num : nums) {
            if (num == pivot) {
                result[index++] = num;
            }
        }

//    Pass 3: Place all elements greater than pivot.

        for (int num : nums) {
            if (num > pivot) {
                result[index++] = num;
            }
        }

        return result;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason: The array is traversed three times.

O(n) + O(n) + O(n) = O(3n)

Ignoring constants: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: A new result array of size n is created to store the partitioned elements.

---------------------------------------------------------

Key Observation:

The problem requires maintaining the relative order of elements.

Instead of swapping elements in-place, we build a new array in three passes:
            < pivot
            = pivot
            > pivot

This naturally preserves the original order within each partition.

---------------------------------------------------------
*/