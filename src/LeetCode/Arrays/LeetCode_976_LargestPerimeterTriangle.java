package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_976_LargestPerimeterTriangle {
    public static void main(String[] args) {
        int[] nums = {1,2,7,10};
        System.out.println(largestPerimeter(nums));
    }
    static int largestPerimeter(int[] nums){
        Arrays.sort(nums);

        for (int i = nums.length - 1; i >= 2; i--) {
            if (nums[i - 2] + nums[i - 1] > nums[i]) {
                return nums[i - 2] + nums[i - 1] + nums[i];
            }
        }

        return 0;
    }
}
