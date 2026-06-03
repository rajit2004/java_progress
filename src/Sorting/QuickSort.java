package Sorting;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {5,3,2,1,4};

        // Sort the complete array
        sort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }

    static void sort(int[] arr, int low, int high) {

        // Base Condition:
        // If the subarray has 0 or 1 element,
        // it is already sorted.
        if (low >= high)
            return;

        // Choose the middle element as pivot.
        // Pivot is used to divide the array into
        // smaller and larger elements.
        int pivot = arr[low + (high - low) / 2];

        // Temporary pointers used for partitioning.
        // We do not modify low/high because we need
        // them later for recursive calls.
        int st = low;
        int end = high;

        // Partition the array around the pivot.
        while (st <= end) {

            // Move st until we find an element that
            // should be on the right side of the pivot.
            while (arr[st] < pivot)
                st++;

            // Move end until we find an element that
            // should be on the left side of the pivot.
            while (arr[end] > pivot)
                end--;

            // If pointers have not crossed,
            // swap the misplaced elements.
            if (st <= end) {
                swap(arr, st, end);

                // Move both pointers inward and continue searching.
                st++;
                end--;
            }
        }

        /*
           After partition:

           low .... end | st .... high

           Left side  -> elements <= pivot
           Right side -> elements >= pivot

           Now sort both halves separately.
        */

        sort(arr, low, end);   // Sort left half
        sort(arr, st, high);   // Sort right half
    }

    static void swap(int[] array, int a, int b) {

        // Standard swap logic
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
/*

Best / Average Case:

When the pivot divides the array into roughly equal halves,
the recursion tree has log n levels.

At every level, partitioning processes all n elements once.

Therefore:

Time = O(n) × O(log n)
     = O(n log n)

Worst Case:

When the pivot repeatedly becomes the smallest or largest element,
the partition creates:

        0 elements | n-1 elements

The recursion tree becomes skewed and has n levels.

At each level, partitioning still scans the remaining elements.

Therefore:

Time = O(n²)

Quick Sort becomes O(n²) when the pivot repeatedly creates highly unbalanced partitions.
*/
