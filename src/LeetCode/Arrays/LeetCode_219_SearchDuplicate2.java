package LeetCode.Arrays;

/*
Given an integer array nums and an integer k,
return true if there are two distinct indices i and j in the array
such that nums[i] == nums[j] and abs(i - j) <= k.
*/

public class LeetCode_219_SearchDuplicate2 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,1,2,3};
        int k = 2;
        System.out.println(containsNearbyDuplicate(nums , k));
    }
    static boolean containsNearbyDuplicate(int[] arr, int k) {
        for(int i = 0; i < arr.length; i++){
            for(int j = i + 1; j < arr.length; j++){
                if(arr[i] == arr[j] && Math.abs(i - j) <= k){
                    return true;
                }
            }
        }
        return false;
    }
}
