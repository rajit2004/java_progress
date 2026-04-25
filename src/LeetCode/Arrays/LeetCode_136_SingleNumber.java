package LeetCode.Arrays;

//Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.

public class LeetCode_136_SingleNumber {
    public static void main(String[] args) {
        int[] nums = {2,2,1};
        System.out.println(singleNumber(nums));
    }
    static int singleNumber(int[] nums) {
        int result = 0;

        for (int num : nums) {
            result ^= num;
        }

        return result;
    }
}
