package LeetCode;

public class LeetCode_852_PeakIndexInMountainArrayOptimized {
    public static void main(String[] args) {
        int[] mountain_array = {1, 3, 7, 12, 18, 25, 21, 16, 10, 5, 2};
        System.out.println(ansIndex(mountain_array));
    }
    static int ansIndex(int[] arr){
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
}
