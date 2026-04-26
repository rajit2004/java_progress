package LeetCode.Sorting;

/*
Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
Since the result may be very large, so you need to return a string instead of an integer.
*/

import java.util.Arrays;

public class LeetCode_179_Largest_Num {
    public static void main(String[] args) {
        int[] num = {3,30,34,5,9};
        System.out.println(largestNumber(num));
    }
    static String largestNumber(int[] nums) {
        // Convert int array to String array
        String[] arr = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = String.valueOf(nums[i]);
        }

        // Custom sort
        Arrays.sort(arr, (a, b) -> (b + a).compareTo(a + b));

        // Edge case: if largest is "0"
        if (arr[0].equals("0")) return "0";

        // Build result
        StringBuilder result = new StringBuilder();
        for (String s : arr) {
            result.append(s);
        }

        return result.toString();
    }
}
