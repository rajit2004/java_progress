package LeetCode.SlidingWindow;

/*
We only care about elements within k positions.

Instead of checking the entire remaining array,
check only the next k positions because only those
can satisfy abs(i-j) <= k.
*/


public class LeetCode_219_ContainsDuplicate2 {
    public static void main(String[] args) {
        int[] arr = {1,2,3,1};
        System.out.println(containsNearbyDuplicate(arr, 3));
    }
    static boolean containsNearbyDuplicate(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {

            int end = Math.min(i + k, nums.length - 1);

            for (int j = i + 1; j <= end; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }

        return false;
    }
}
