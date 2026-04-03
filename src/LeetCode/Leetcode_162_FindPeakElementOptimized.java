package LeetCode;

//https://leetcode.com/problems/find-peak-element/description/

public class Leetcode_162_FindPeakElementOptimized {
    public static void main(String[] args) {
        int[] arr = {1,2,3,1};
        System.out.println(findPeakElement(arr));
    }
    static int findPeakElement(int[] nums){
        int st = 0 ;
        int end = nums.length-1;
        while(st<end){
            int mid = st + (end-st)/2;

            if(nums[mid]<nums[mid+1])
                st = mid+1;
            else
                end = mid;
        }
        return st;
    }
}
