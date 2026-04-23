package LeetCode;

public class LeetCode_33_SearchInRotatedSortedArraySelfOptimized {
    public static void main(String[] args) {
        int[] nums = {1};
        int target = 0;
        System.out.println(search(nums , target));
    }
    static int FindPivot(int[] arr){
        int max = arr[0];
        int index = 0;
        int counter = 0;
        while (counter < arr.length){
            if(arr[counter] > max) {
                max = arr[counter];
                index = counter;
            }
            counter++;
        }
        return index;
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
