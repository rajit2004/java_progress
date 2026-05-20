package LeetCode.Arrays;

import java.util.Arrays;

/*
Given two integer arrays nums1 and nums2, return an array of their intersection.
Each element in the result must be unique and you may return the result in any order.
*/

public class LeetCode_349_IntersectionOfArrays {
    public static void main(String[] args) {
        int[] num1 = {1,2,2,1};
        int[] num2 = {2,2};
        System.out.println(Arrays.toString(intersection(num1, num2)));
    }
    static int[] intersection(int[] nums1, int[] nums2) {

        int[] temp = new int[nums1.length];
        int k = 0;

        for (int i = 0; i < nums1.length; i++) {

            for (int j = 0; j < nums2.length; j++) {

                if (nums1[i] == nums2[j]) {

                    // Check duplicate
                    boolean alreadyPresent = false;

                    for (int x = 0; x < k; x++) {
                        if (temp[x] == nums1[i]) {
                            alreadyPresent = true;
                            break;
                        }
                    }

                    // Add if not already present
                    if (!alreadyPresent) {
                        temp[k] = nums1[i];
                        k++;
                    }

                    break;
                }
            }
        }

        // Final answer array
        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = temp[i];
        }

        return result;
    }
}
