package LeetCode;

/*
Given an array nums containing n distinct numbers in the range [0, n],
return the only number in the range that is missing from the array.
*/

/* Intuition = since we are given in the question that we have a array of nums in range 0 to n
0 to n hints cyclic sort , we have continuous nums in array
*/

/*
 this version fails a test case of input array = {1};

Input :
        nums = [1]
Use Testcase :
    Output = 2
    Expected = 0

*/

public class LeetCode_268_MissingNumber_FAILED_TESTCASE {
    public static void main(String[] args) {
        int[] arr = {1};
        System.out.println(missingNumber(arr));
    }
    static int missingNumber(int[] nums) {
//        apply cyclic sort :
        int i = 0;

        while (i < nums.length) {
            int correct = nums[i] - 1;

            if (nums[i] > 0 && nums[i] <= nums.length
                    && nums[i] != nums[correct]) {
                swap(nums, i, correct);
            } else {
                i++;
            }
        }

        // for missing num
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != j + 1) {
                return j + 1;
            }
        }

        return nums.length + 1;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
