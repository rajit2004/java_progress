package LeetCode.Arrays;

/*
Given an integer array nums sorted in non-decreasing order,
return an array of the squares of each number sorted in non-decreasing order.
*/

import java.util.Arrays;

public class LeetCode_977_SqOfSortedArray {
    public static void main(String[] args) {
        int[] nums = {-7,-3,2,3,11};
        System.out.println(Arrays.toString(sortedSquares(nums)));
    }
    static int[] sortedSquares(int[] nums){
        for(int i = 0 ; i < nums.length ; i ++){
            int val = nums[i];
            nums[i] = val*val;
        }
        Arrays.sort(nums);

        return nums;
    }
}
