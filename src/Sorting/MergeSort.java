package Sorting;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {9, 3, 5, 8, 4, 6, 1};
        System.out.println(Arrays.toString(sort(arr)));
    }

    static int[] sort(int[] arr) {

        // If the array has only one element,
        // it is already sorted.
        if (arr.length == 1)
            return arr;

        // Find the middle index so we can split the array.
        int mid = arr.length / 2;

        // Recursively sort the left half.
        int[] left = sort(Arrays.copyOfRange(arr, 0, mid));

        // Recursively sort the right half.
        int[] right = sort(Arrays.copyOfRange(arr, mid, arr.length));

        // Merge the two sorted halves into one sorted array.
        return merge(left, right);
    }

    static int[] merge(int[] first, int[] last) {


        /*
  Compare current elements
        ↓
    Take smaller one
        ↓
    Move that pointer
        ↓
      Repeat
        ↓
    Copy leftovers
*/



        // Final array that will store the merged result.
        int[] ans = new int[first.length + last.length];

        // i -> points to current element in first array
        // j -> points to current element in second array
        // n -> points to current position in answer array
        int i = 0;
        int j = 0;
        int n = 0;

        // Keep comparing elements until one array is exhausted.
        while (i < first.length && j < last.length) {

            // Put the smaller element into the answer array.
            if (first[i] <= last[j]) {
                ans[n] = first[i];
                i++; // move to next element in first array
            } else {
                ans[n] = last[j];
                j++; // move to next element in second array
            }

            // Move to next position in answer array.
            n++;
        }

        // If the second array is completely used,
        // copy all remaining elements from the first array.
        if (j == last.length) {
            for (int ele = i; ele < first.length; ele++) {
                ans[n] = first[ele];
                n++;
            }
        }

        // Otherwise, the first array is exhausted,
        // so copy all remaining elements from the second array.
        else {
            for (int ele = j; ele < last.length; ele++) {
                ans[n] = last[ele];
                n++;
            }
        }

        return ans;
    }
}