package LeetCode.Arrays;

/*
You are given an integer array nums.

You replace each element in nums with the sum of its digits.

Return the minimum element in nums after all replacements.
*/


public class LeetCode_3300_MinEleAfterReplacementWithDigSum {
    public static void main(String[] args) {
        int[] arr = {999,19,199};
        System.out.println(minElement(arr));
    }
    static int minElement(int[] nums){
        int minSum = Integer.MAX_VALUE;

        for(int num : nums) {
            int sum = 0;

            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }

            minSum = Math.min(minSum, sum);
        }

        return minSum;
    }
}
