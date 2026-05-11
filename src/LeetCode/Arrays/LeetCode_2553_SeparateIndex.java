package LeetCode.Arrays;

/*
Given an array of positive integers nums,
return an array answer that consists of the digits of each integer in nums
after separating them in the same order they appear in nums.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode_2553_SeparateIndex {
    public static void main(String[] args) {
        int[] arr = {13,25,83,77};
        System.out.println(Arrays.toString(separateDigits(arr)));
    }
    static int[] separateDigits(int[] nums) {

        // List to store all digits
        List<Integer> result = new ArrayList<>();

        // Traverse each number
        for (int num : nums) {

            // Convert number to string
            String str = String.valueOf(num);

            // Traverse each character (digit)
            for (char ch : str.toCharArray()) {

                // Convert character to integer digit
                result.add(ch - '0');
            }
        }

        // Convert List<Integer> to int[]
        int[] ans = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }

        return ans;
    }
}