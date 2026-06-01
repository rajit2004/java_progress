package Sorting.UsingRecursion;

import java.util.Arrays;

public class ALT_BubbleSortUsingRecursion {
    public static void main(String[] args) {
        int[] arr = {4,3,2,1};
        System.out.println(Arrays.toString(sort(arr,0,arr.length-1)));
    }
    static int[] sort(int[] arr, int index, int end) {
        // Outer recursion ends when no unsorted range is left
        if (end == 0) {
            return arr;
        }

        // Inner pass finished for current 'end'
        if (index == end) {
            return sort(arr, 0, end - 1);
        }

        if (arr[index] > arr[index + 1]) {
            int temp = arr[index];
            arr[index] = arr[index + 1];
            arr[index + 1] = temp;
        }

        return sort(arr, index + 1, end);
    }
}
