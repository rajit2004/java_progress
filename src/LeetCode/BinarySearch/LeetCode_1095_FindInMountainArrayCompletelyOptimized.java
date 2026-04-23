package LeetCode;

//https://leetcode.com/problems/find-in-mountain-array/description/

/*
Given a mountain array mountainArr,
        return the minimum index such that mountainArr.get(index) == target.
        If such an index does not exist, return -1.
*/

public class LeetCode_1095_FindInMountainArrayCompletelyOptimized {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,3,1};
        int target = 3;
        System.out.println(findInMountainArray(nums,target));
    }
    static int peak(int[] arr){
        int st = 0;
        int end = arr.length-1;

        while (st<end){
            int mid = st + (end-st)/2;

            if(arr[mid]>arr[mid+1])         //descending part of the array
                end = mid;
            else                            //ascending part of the array
                st = mid+1;
        }
        if(st==end)
            return st;          // peak found
        return -1;              // not found
    }
    static int BinarySearch(int[] arr, int target, int st, int end, boolean isAsc){
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
