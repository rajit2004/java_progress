package LeetCode.Arrays;

/*
Given two integer arrays nums1 and nums2, return an array of their intersection.
Each element in the result must appear as many times as it shows in both arrays and
you may return the result in any order.
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class LeetCode_350_IntersectionOfArrays {
    public static void main(String[] args) {
        int[] num1 = {1,2,2,1};
        int[] num2 = {2,2};
        System.out.println(Arrays.toString(intersect(num1, num2)));
    }
    static int[] intersect(int[] nums1, int[] nums2) {

        // Sort both arrays
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int i = 0;
        int j = 0;

        List<Integer> list = new ArrayList<>();

        // Two pointer traversal
        while (i < nums1.length && j < nums2.length) {

            if (nums1[i] == nums2[j]) {
                list.add(nums1[i]);
                i++;
                j++;
            }
            else if (nums1[i] < nums2[j]) {
                i++;
            }
            else {
                j++;
            }
        }

        // Convert list to array
        int[] result = new int[list.size()];

        for (int k = 0; k < list.size(); k++) {
            result[k] = list.get(k);
        }

        return result;
    }
}
