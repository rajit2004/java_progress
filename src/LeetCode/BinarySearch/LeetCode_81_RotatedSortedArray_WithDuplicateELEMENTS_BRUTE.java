package LeetCode;

/*
Given the array nums after the rotation and an integer target
return true if target is in nums, or false if it is not in nums.
 */

public class LeetCode_81_RotatedSortedArray_WithDuplicateELEMENTS_BRUTE {
    public static void main(String[] args) {
        int[] nums = {2,5,6,0,0,1,2};
        int target = 0;
        System.out.println(search(nums,target));
    }
    static boolean search(int[] arr , int target){
        for(int element : arr){
            if(element == target)
                return true;
        }
        return false;
    }
}
