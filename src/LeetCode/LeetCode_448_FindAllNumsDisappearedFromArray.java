package LeetCode;

/*
Given an array nums of n integers where nums[i] is in the range [1, n],
 return an array of all the integers in the range [1, n] that do not appear in nums.
*/

import java.util.Arrays;

public class LeetCode_448_FindAllNumsDisappearedFromArray {
    public static void main(String[] args) {
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println(Arrays.toString(findMissing(nums)));
    }
    static void swap(int[] arr , int index1 , int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
    static int[] findMissing(int[] arr) {
        // First apply cyclic sort
        int i = 0;
        while (i < arr.length) {
            int correct = arr[i] - 1;

            if (arr[i] > 0 && arr[i] <= arr.length && arr[i] != arr[correct]) {
                swap(arr, i, correct);
            } else {
                i++;
            }
        }

        // Count missing elements
        int count = 0;
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] != j + 1) {
                count++;
            }
        }

        // Store missing elements
        int[] ans = new int[count];
        int index = 0;

        for (int j = 0; j < arr.length; j++) {
            if (arr[j] != j + 1) {
                ans[index] = j + 1;
                index++;
            }
        }

        return ans;
    }
}
