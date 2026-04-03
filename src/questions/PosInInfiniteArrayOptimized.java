package questions;

//https://www.geeksforgeeks.org/find-position-element-sorted-array-infinite-numbers/

public class PosInInfiniteArrayOptimized {
    public static void main(String[] args) {
        int[] nums = {-45, -30, -22, -15, -9, -3, -1, 0, 2, 5, 8, 11, 14, 18, 21, 25, 30, 35, 40, 50};
        int target = -1;
        System.out.println(ans(nums,target));
    }

    /*
    STEPS:
        1.) find range in which the target is present
            *> declare a single chunk of array -> find -> if it exists apply binary search on it -> if not double the range

        SIZE OF BOX = end - st + 1
    */
    static int BinarySearch(int[] arr , int target , int st , int end){
       while(st<=end){
           int mid = st + (end-st)/2;
           if(target==arr[mid])
               return mid;
           else
               if(target>arr[mid])
                   st = mid + 1;
               else
                   end = mid - 1;
       }
       return -1;
    }
    static int ans(int[] arr , int target){
//        we start with a chunk of size 2
        int start = 0;
        int end = 1;
//        condition for target to be in the range
        while(target > arr[end]){
            int newStart = end + 1;                             //new start
            end = end + (end - start + 1) * 2;                  //new end (current end + double the size of box)
            start = newStart;
        }
        return BinarySearch(arr , target , start , end);
    }
}