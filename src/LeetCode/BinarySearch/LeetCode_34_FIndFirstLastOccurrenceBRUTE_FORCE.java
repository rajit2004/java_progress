package LeetCode;

import java.util.Arrays;

public class LeetCode_34_FIndFirstLastOccurrenceBRUTE_FORCE {
    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int target = 81;
        System.out.println(Arrays.toString(searchRange(nums, target)));
    }

    public static int[] searchRange(int[] nums, int target) {
        int first = -1;
        int last = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (first == -1) {
                    first = i;      // first occurrence
                }
                last = i;           // keep updating last occurrence
            }
        }

        return new int[]{first, last};
    }
}
