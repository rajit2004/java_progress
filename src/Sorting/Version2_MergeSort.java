package Sorting;

import java.util.Arrays;

// version two still uses an extra array to merge but reduces the smaller array created while sorting

/*
BREAK → SORT → MERGE

        BREAK the array into halves until one element remains.
        SORT happens automatically because a single element is already sorted.
        MERGE the small sorted pieces back together.

And for the merge step:

        COMPARE → PICK SMALLER → MOVE POINTER

        Repeat until one side finishes, then:

        COPY THE LEFTOVERS
*/



public class Version2_MergeSort {

    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};

        // Sort the entire array.
        mergeSortInPlace(arr, 0, arr.length);

        System.out.println(Arrays.toString(arr));
    }

    static void mergeSortInPlace(int[] arr, int s, int e) {

        // If only one element is present,
        // it is already sorted.
        if (e - s == 1)
            return;

        // Split the current portion into two halves.
        int mid = (s + e) / 2;

        // Sort the left half.
        mergeSortInPlace(arr, s, mid);

        // Sort the right half.
        mergeSortInPlace(arr, mid, e);

        // Both halves are now sorted.
        // Merge them into one sorted portion.
        mergeInPlace(arr, s, mid, e);
    }

    static void mergeInPlace(int[] arr, int s, int m, int e) {

        // Temporary array used to store the merged result.
        // Size = total elements between s and e.
        int[] mix = new int[e - s];

        // i -> current element in left half
        // j -> current element in right half
        // k -> current position in mix array
        int i = s;
        int j = m;
        int k = 0;

        // Keep comparing until one half gets exhausted.
        while (i < m && j < e) {

            // Pick the smaller element and put it into mix.
            if (arr[i] < arr[j]) {
                mix[k] = arr[i];
                i++; // move left pointer
            } else {
                mix[k] = arr[j];
                j++; // move right pointer
            }

            // Move to next position in mix.
            k++;
        }

        // If elements are still left in the left half,
        // copy them directly.
        while (i < m) {
            mix[k] = arr[i];
            i++;
            k++;
        }

        // If elements are still left in the right half,
        // copy them directly.
        while (j < e) {
            mix[k] = arr[j];
            j++;
            k++;
        }

        // Copy the sorted data back into the original array.
        // Start writing from index 's'.
        for (int l = 0; l < mix.length; l++) {
            arr[s + l] = mix[l];
        }
    }
}