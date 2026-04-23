package LeetCode;

/*
Given a sorted array of distinct integers and a target value,
 return the index if the target is found.
If not, return the index where it would be if it were inserted in order.
*/

public class LeetCode_35_SearchInsertPosition {
    public static void main(String[] args) {
        int[] nums = {1,3,5,6};
        int target = 7;
        System.out.println(searchInsert(nums,target));
    }

    static int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] >= target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }
}
