package LeetCode;

/*
The intuition behind this approach is that if an element occurs more than n/2 times in the array
(where n is the size of the array),
it will always occupy the middle position when the array is sorted.
Therefore, we can sort the array and return the element at index n/2.
*/

import java.util.Arrays;

public class LeetCode_169_MajorityElement {
    public static void main(String[] args) {
        int[] arr = {2,2,1,1,1,2,2};
        System.out.println(majorityElement(arr));
    }
    static int majorityElement(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return nums[n/2];
    }
}

