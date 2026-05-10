package LeetCode.TwoPointers;

/*
Given an integer array nums, move all 0's to the end of it while
maintaining the relative order of the non-zero elements.
*/

import java.util.Arrays;

public class LeetCode_283_MoveZeros {
    public static void main(String[] args) {
        int[] arr = {1,0,2,12,3};
        System.out.println(Arrays.toString(moveZeros(arr)));
    }
    static int[] moveZeros(int[] nums){
        int insPos = 0;
//        we move all the non-zero elements forward
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]!=0){
                nums[insPos] = nums[i];
                insPos++;
            }
        }
//        Now we fill the remaining positions with 0s
        while(insPos < nums.length){
            nums[insPos] = 0;
            insPos++;
        }
        return nums;
    }
}
