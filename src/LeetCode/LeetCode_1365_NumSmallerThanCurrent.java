package LeetCode;

import java.util.Arrays;

public class LeetCode_1365_NumSmallerThanCurrent {
    public static void main(String[] args) {
        int[] arr = {8,1,2,2,3};
        System.out.println(Arrays.toString(smallerNumbersThanCurrent(arr)));
    }
    static int[] smallerNumbersThanCurrent(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        for(int i = 0; i < n; i++){
            int count = 0;

            for(int j = 0; j < n; j++){
                if(nums[j] < nums[i]){
                    count++;
                }
            }

            result[i] = count;
        }

        return result;
    }
}
