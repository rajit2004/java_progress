package Sorting.UsingRecursion;

import java.util.Arrays;

public class BubbleSortUsingRecursion {
    public static void main(String[] args) {

        // our unsorted box of numbers
        int[] arr = {5, 1, 3, 2, 4};

        // start sorting — tell it to work on the full array
        // arr.length - 1 is the last index (index 4 for a 5-element array)
        sort(arr, arr.length - 1);

        System.out.println(Arrays.toString(arr));
        }

/*
     sort() — the boss function
     it runs one full pass, then shrinks the problem by 1
     think of it after each pass, the biggest number has BUBBLED to its correct position at the end so next time we don't need to touch that last position anymore
*/

    static void sort(int[] arr, int end) {

        // if end has reached 0, the whole array is sorted -> stop = > (no element need sorting)
        if (end == 0) {
            return;
        }

        // does one full pass from index 0 to end -> this will push the biggest unsorted number to position 'end'
        helper(arr, 0, end);

        // the last position is now correctly placed -> ignore it next time
        // shrink the problem by 1 and sort the remaining part

        sort(arr, end - 1);
    }

/*
     helper() — does ONE full pass through the array
     it walks from left to right comparing two neighbors at a time - >if left neighbor is bigger than right neighbor -> swap them
     the biggest number slowly "bubbles up" to the right end
*/

    static void helper(int[] arr, int index, int end) {

        // we've reached the end of this pass -> stop
        if (index == end) {
            return;
        }

/*
        look at current number and the number RIGHT next to it -> if current number is bigger than its right neighbor -> they are in wrong order
        swap them so the bigger one moves one step to the right
*/
        if (arr[index] > arr[index + 1]) {
            swap(arr, index, index + 1);
        }

        // move one step to the right and repeat the same check
        helper(arr, index + 1, end);
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}