package LeetCode;

public class LeetCode_33_SearchInRotatedSortedArray_ActuallyOptimized {

    public static void main(String[] args) {
        int[] nums = {4,5,6,7,0,1,2};
        int target = 0;
        System.out.println(search(nums, target));
    }

    // Find pivot (index of largest element)
    static int findPivot(int[] arr) {
        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            // Case 1: mid is pivot
            if (mid < end && arr[mid] > arr[mid + 1])
                return mid;

            // Case 2: mid-1 is pivot
            if (mid > start && arr[mid] < arr[mid - 1])
                return mid - 1;

            // Decide direction
//            figure out which half contains the answer and move there

            if (arr[mid] >= arr[start])         //answer in right half
                start = mid + 1;
            else                                //answer in right half
                end = mid - 1;
        }
        return -1; // no rotation
    }

    // Standard binary search
    static int binarySearch(int[] arr, int target, int start, int end) {
        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (arr[mid] == target)
                return mid;
            else if (arr[mid] < target)
                start = mid + 1;
            else
                end = mid - 1;
        }
        return -1;
    }

    // Main search function
    static int search(int[] arr, int target) {
        int pivot = findPivot(arr);

        // Case: no rotation
        if (pivot == -1)
//            just do normal binary search
            return binarySearch(arr, target, 0, arr.length - 1);

//        if pivot is found we have two ascending sorted arrays

        // Case: pivot is target
        if (arr[pivot] == target)
            return pivot;

        // Decide which side to search
        if (target >= arr[0])
            return binarySearch(arr, target, 0, pivot - 1);

        return binarySearch(arr, target, pivot + 1, arr.length - 1);
    }
}