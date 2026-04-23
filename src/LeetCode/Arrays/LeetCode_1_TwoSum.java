package LeetCode;

/*
 Given an array of integers nums and an integer target,
 return indices of the two numbers such that they add up to target.

 You may assume that each input would have exactly one solution,
 and you may not use the same element twice.
*/

import java.util.Arrays;

public class LeetCode_1_TwoSum {
    public static void main(String[] args) {
        int[] nums = {2,3,4,5,6,7,11,15};
        int target = 11;
        System.out.println(Arrays.toString(twoSum(nums,target)));
    }
    static int[] twoSum(int[] nums, int target){
        for(int i = 0; i < nums.length; i++){
            for(int j = i + 1; j < nums.length; j++){
                if(nums[i] + nums[j] == target){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }
}
