package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_349_IntersectionOfArray {
    public static void main(String[] args) {
        int[] arr1 = {4, 9, 5};
        int[] arr2 = {9, 4, 9, 8, 4};

        System.out.println(Arrays.toString(intersection(arr1, arr2)));
    }

    static int[] intersection(int[] nums1, int[] nums2) {
        int[] ans = new int[Math.min(nums1.length, nums2.length)];
        int common = 0;

        for (int ele1 : nums1) {
            for (int ele2 : nums2) {
                if (ele1 == ele2) {

                    boolean exists = false;
                    for (int i = 0; i < common; i++) {
                        if (ans[i] == ele1) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        ans[common++] = ele1;
                    }
                    break;
                }
            }
        }

        return Arrays.copyOf(ans, common);
    }
}