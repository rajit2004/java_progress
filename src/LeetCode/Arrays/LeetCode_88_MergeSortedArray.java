package LeetCode.Arrays;

/*
You are given two integer arrays nums1 and nums2, sorted in non-decreasing order,
and two integers m and n, representing the number of elements in nums1 and nums2 respectively.

Merge nums1 and nums2 into a single array sorted in non-decreasing order.

The final sorted array should not be returned by the function,
but instead be stored inside the array nums1.
To accommodate this, nums1 has a length of m + n,
where the first m elements denote the elements that should be merged,
and the last n elements are set to 0 and should be ignored. nums2 has a length of n.
*/

import java.util.Arrays;

public class LeetCode_88_MergeSortedArray {
    public static void main(String[] args) {
        int [] nums1 = {1,2,3,0,0,0};
        int m = 3;
        int[] nums2 = {2,5,6};
        int n = 3;
        System.out.println(Arrays.toString(merge(nums1, m, nums2, n)));
    }
    static int[] merge(int[] nums1, int m, int[] nums2, int n) {

        int i = m - 1;          // last valid element in nums1
        int j = n - 1;          // last element in nums2
        int k = m + n - 1;      // last position in nums1

        // Compare elements from back
        while (i >= 0 && j >= 0) {

            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }

            k--;
        }

        // Copy remaining nums2 elements
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }
        return nums1;
    }
}
