package LeetCode.Sorting;

/*
Given an array nums with n objects colored red, white, or blue, sort them in-place
so that objects of the same color are adjacent, with the colors in the order red, white, and blue.
We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
*/

import java.util.Arrays;

public class LeetCode_75_SortColors {
    public static void main(String[] args) {
        int[] nums = {2,0,2,1,1,0};
        System.out.println(Arrays.toString(sortColors(nums)));
    }
    static int[] sortColors(int[] arr) {
        int low = 0, mid = 0, high = arr.length - 1;

        while (mid <= high) {
            if (arr[mid] == 0) {
                // swap arr[low] and arr[mid]
                int temp = arr[low];
                arr[low] = arr[mid];
                arr[mid] = temp;

                low++;
                mid++;
            }
            else if (arr[mid] == 1) {
                mid++;
            }
            else { // arr[mid] == 2
                // swap arr[mid] and arr[high]
                int temp = arr[mid];
                arr[mid] = arr[high];
                arr[high] = temp;

                high--;
            }
        }
        return arr;
    }
}
