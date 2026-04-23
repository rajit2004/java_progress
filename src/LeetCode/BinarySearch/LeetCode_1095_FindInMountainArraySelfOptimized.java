package LeetCode;

//https://leetcode.com/problems/find-in-mountain-array/description/

/*
Given a mountain array mountainArr,
        return the minimum index such that mountainArr.get(index) == target.
        If such an index does not exist, return -1.
*/

public class LeetCode_1095_FindInMountainArraySelfOptimized {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,3,1};
        int target = 3;
        System.out.println(findInMountainArray(nums,target));
    }
    static int BinarySearch(int[] arr, int target, int starting, int ending, boolean isAsc){
        int st = starting;
        int end = ending;

        while(st <= end){
            int mid = st + (end - st) / 2;

            if(arr[mid] == target)
                return mid;

            if(isAsc){
                if(arr[mid] < target)
                    st = mid + 1;
                else
                    end = mid - 1;
            } else {
                if(arr[mid] < target)
                    end = mid - 1;
                else
                    st = mid + 1;
            }
        }
        return -1;
    }
    static int peak(int[] arr){
        int max = arr[0];
        int index = 0;
        for(int i = 0 ; i< arr.length;i++){
            if(arr[i]>max) {
                max = arr[i];
                index = i;
            }
        }
        return index;           // returns the index of the max item
    }
    static int findInMountainArray(int[] arr , int target){
        int index = peak(arr);
        // check peak
        if(arr[index] == target)
            return index;

        // search left (ascending)
        int left = BinarySearch(arr, target, 0, index - 1, true);
        if(left != -1)
            return left;

        // search right (descending)
        return BinarySearch(arr, target, index + 1, arr.length - 1, false);
    }
}