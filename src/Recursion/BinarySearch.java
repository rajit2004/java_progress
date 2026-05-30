package Recursion;

import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) {
        int[] nums = {9,4,3,1};
        int target = 9;
        System.out.println(search(nums,target));
        System.out.println(bs(nums , target , 0 , nums.length));
    }
    static int search(int[] arr , int n){

        Arrays.sort(arr);
        int st = 0;
        int end = arr.length-1;

        while(st <= end){

            int mid = st  + (end-st) / 2;

            if (arr[mid] == n)
                return mid;
            if (n > arr[mid])
                st = mid + 1;
            else
                end = mid - 1;
        }
        return -1;
    }

//    binary search using recursion

    static int bs(int[] arr , int target , int st , int end){

        if (st > end)
            return -1;

        int mid = st + (end - st) / 2;

        if(arr[mid] == target)
            return mid;
        if (arr[mid] < target)
            return bs(arr , target , mid + 1, end);
        else
            return bs(arr , target , st ,  mid - 1);
    }
}
