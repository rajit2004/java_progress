package LeetCode;

public class LeetCode_33_SearchInRotatedSortedArray_MoreSelfOptimized {
    public static void main(String[] args) {
        int[] nums = {4,5,6,7,0,1,2};
        int target = 0;
        System.out.println(search(nums ,target));
    }
    static int FindPivot(int[] arr){
        int st = 0 ;
        int end = arr.length-1;
        while(st<end){
            int mid = st + (end-st)/2;

            if(arr[mid]<arr[mid+1])
                st = mid+1;
            else
                end = mid;
        }
        return st;
    }

    static int BinarySearch(int[] arr , int target , int start , int end){
        while(start<=end){
            int mid = start + (end-start)/2;
            if (arr[mid] == target)
                return mid;
            else
            if(arr[mid]>target)
                end = mid -1;
            else
                start = mid+1;
        }
        return -1;
    }

    static int search(int[] arr, int target){
        int pivot = FindPivot(arr);
        int ans = BinarySearch(arr , target, 0 , pivot);
        if(ans == -1)
            ans = BinarySearch(arr , target , pivot+1 ,arr.length-1);
        return ans;
    }
}


/*
there are many loopholes in this.
handles no edge case
logic breaks if the array isn't rotated
*/