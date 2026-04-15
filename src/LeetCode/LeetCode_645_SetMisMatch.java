package LeetCode;
/*
You have a set of integers s, which originally contains all the numbers from 1 to n.
Unfortunately, due to some error, one of the numbers in s got duplicated to another number in the set,
which results in repetition of one number and loss of another number.

You are given an integer array nums representing the data status of this set after the error.

Find the number that occurs twice and the number that is missing and return them in the form of an array.
*/

import java.util.Arrays;

public class LeetCode_645_SetMisMatch {
    public static void main(String[] args) {
        int[] nums = {1,1};
        System.out.println(Arrays.toString(findErrorNums(nums)));
    }

    static void swap(int[] arr , int index1 , int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    static int[] findErrorNums(int[] arr) {

        int i = 0;

        // cyclic sort
        while (i < arr.length) {
            int correctIndex = arr[i] - 1;

            if (arr[i] != arr[correctIndex])
                swap(arr, i, correctIndex);
            else
                i++;
        }

        // find duplicate and missing
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] != j + 1) {
                return new int[]{arr[j], j + 1}; // {duplicate, missing}
            }
        }

        return new int[]{-1, -1}; // fallback (won’t happen)
    }
}
