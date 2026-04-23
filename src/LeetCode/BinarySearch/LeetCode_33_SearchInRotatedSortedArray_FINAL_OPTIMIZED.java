package LeetCode;

public class LeetCode_33_SearchInRotatedSortedArray_FINAL_OPTIMIZED {
    public static void main(String[] args) {
        int[] nums = {4,5,6,7,0,1,2};
        int target = 0;
        System.out.println(search(nums, target)); // Output: 4
    }

    static int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] == target)
                return mid;

            // Left half is sorted
            if (nums[start] <= nums[mid]) {

                // Target lies in left half
                if (target >= nums[start] && target < nums[mid])
                    end = mid - 1;
                else
                    start = mid + 1;
            }

            // Right half is sorted
            else {

                // Target lies in right half
                if (target > nums[mid] && target <= nums[end])
                    start = mid + 1;
                else
                    end = mid - 1;
            }
        }
        return -1;
    }
}