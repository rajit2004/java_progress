package LeetCode.Arrays;

//Given an integer array nums, find three numbers whose product is maximum and return the maximum product.

import java.util.Arrays;

public class LeetCode_628_MaxProdOf3Nums {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        System.out.println(maximumProduct(nums));
    }
    static int maximumProduct(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;

        int product1 = nums[n - 1] * nums[n - 2] * nums[n - 3];
        int product2 = nums[0] * nums[1] * nums[n - 1];

        return Math.max(product1, product2);
    }
}
