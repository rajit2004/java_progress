package LeetCode;

/*
Given an array of integers nums containing n + 1 integers
where each integer is in the range [1, n] inclusive.
There is only one repeated number in nums, return this repeated number.
*/

import java.util.Arrays;

public class LeetCode_287_DuplicateElement {
    public static void main(String[] args) {
        int[] nums = {1,4,3,4,2};
        System.out.println(findDuplicate(nums));
    }

    static void swap(int[] arr , int index1 , int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    static int findDuplicate(int[] arr) {

//        first simple cyclic sort :
        int i = 0;

        while (i < arr.length) {

            // here we check for the duplicates

            if(arr[i] != i+1){                  // arr[i] is not index + 1
                int correct = arr[i] - 1;       // right index
                if (arr[i] != arr[correct])     // now we check if we have the value at the current index
                    swap(arr, i, correct);      // if no then swap
                else
                    return arr[i];              // if yes => duplicate
            }
            else
                i++;
        }
/*
        now we find duplicates :
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] != j + 1) {
                return arr[j];
            }
        }
*/

        return -1;
    }
}
