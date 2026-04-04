package LeetCode;

public class LeetCode_33_SearchInRotatedSortedArrayBruteForce {
    public static void main(String[] args) {
        int[] nums = {4,5,6,1,2,3};
        int target = 3;
        System.out.println(search(nums,target));
    }
    static int search(int[] nums, int target){
        int i = 0;
        while (i < nums.length) {
            if(nums[i] == target){
                return i;
            }
            i++;
        }
        return -1;
    }
}
