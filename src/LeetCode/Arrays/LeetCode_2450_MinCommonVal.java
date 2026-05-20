package LeetCode.Arrays;

/*
Given two integer arrays nums1 and nums2, sorted in non-decreasing order,
return the minimum integer common to both arrays. If there is no common integer amongst nums1 and nums2, return -1.

Note that an integer is said to be common to nums1 and nums2 if both arrays have at least one occurrence
of that integer.
*/

public class LeetCode_2450_MinCommonVal {
    public static void main(String[] args) {
        int[] num1 = {1,2,3};
        int[] num2 = {2,4};
    }
    static int getCommon(int[] nums1, int[] nums2) {
        int i = 0;
        int j = 0;

        while (i < nums1.length && j < nums2.length) {

            // Common value found
            if (nums1[i] == nums2[j]) {
                return nums1[i];
            }

            // Move smaller element pointer
            if (nums1[i] < nums2[j]) {
                i++;
            } else {
                j++;
            }
        }

        // No common value
        return -1;
    }
}
