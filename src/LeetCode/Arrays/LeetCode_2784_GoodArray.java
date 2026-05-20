package LeetCode.Arrays;

/*
You are given an integer array nums. We consider an array good if it is a permutation of an array base[n].

base[n] = [1, 2, ..., n - 1, n, n] (in other words, it is an array of length n + 1 which contains 1 to n - 1 exactly
once, plus two occurrences of n). For example, base[1] = [1, 1] and base[3] = [1, 2, 3, 3].

Return true if the given array is good, otherwise return false.
*/

public class LeetCode_2784_GoodArray {
    public static void main(String[] args) {
        int[] arr= {2,1,3};
        System.out.println(isGood(arr));
    }
    static  boolean isGood(int[] nums) {

        int n = nums.length - 1;

        // frequency array
        int[] freq = new int[201];

        // count occurrences
        for (int num : nums) {
            freq[num]++;
        }

        // n should appear exactly twice
        if (freq[n] != 2) {
            return false;
        }

        // numbers 1 to n-1 should appear once
        for (int i = 1; i < n; i++) {
            if (freq[i] != 1) {
                return false;
            }
        }

        return true;
    }
}
