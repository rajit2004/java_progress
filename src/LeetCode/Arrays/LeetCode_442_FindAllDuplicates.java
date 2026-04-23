package LeetCode;

/*
Given an integer array nums of length n where all the integers of nums are in the range [1, n]
and each integer appears at most twice, return an array of all the integers that appears twice.

return type of teh fn is arraylist
*/

import java.util.ArrayList;
import java.util.List;

public class LeetCode_442_FindAllDuplicates{
    public static void main(String[] args) {
        int[] nums = {5,4,6,7,9,3,10,9,5,6};
        System.out.println(findDuplicates(nums));
    }

    static List<Integer> findDuplicates(int[] arr) {
        List<Integer> ans = new ArrayList<>();
//        first we perform basic cycle sort :
        int i = 0;
        while (i < arr.length) {
            int correctIndex = arr[i] - 1;

            if (arr[i] != arr[correctIndex])
                swap(arr, i, correctIndex);

            else
                i++;
        }
//        now we find all the duplicate ones :

        for (int j = 0; j < arr.length; j++) {
            if(arr[j]!=j+1)
                ans.add(arr[j]);
        }
        return ans;
    }

    static void swap(int[] arr , int index1 , int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}