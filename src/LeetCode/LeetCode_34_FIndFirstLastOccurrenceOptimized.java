// can be done by using binary search twice
//one to find the index of first occurrence and the other to find the index of the last occurrence

package LeetCode;

import java.util.Arrays;

public class LeetCode_34_FIndFirstLastOccurrenceOptimized {
    public static void main(String[] args) {
        int[] nums = {6,6,7,7,7,7,7,7, 7, 7, 8, 8, 9};
        int target = 7;
        System.out.println(Arrays.toString(searchRange(nums, target)));
    }
    static int occurrence(int[] arr , int target , boolean StartEnd){

//        bool StartEnd = true for searching from start and false for searching from end

        int st = 0;
        int end = arr.length-1;
        int ans = -1;

        while(st<=end){
            int mid = st + (end-st)/2;
            if(target == arr[mid]){
                ans = mid;
                if(StartEnd)
                    end = mid - 1;
                else
                    st = mid + 1;
            }
            else{
                if (target > arr[mid])
                    st = mid + 1;
                else
                    end = mid - 1;
            }
        }
        return ans;
    }

    static int[] searchRange(int[] nums, int target){
        int startIndex = occurrence(nums , target , true);
        int endIndex = occurrence(nums , target , false);
        return new int[]{startIndex,endIndex};
    }
}