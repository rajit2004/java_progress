//works on both ascending and descending order
//enhanced version of binary search
package searching_sorting;

public class OrderAgnosticBinarySearchSelf {
    public static void main(String[] args) {
        int[] arr1 = {2, 5, 8, 12, 16, 23, 38, 45, 56, 72};     // target = 23
        int[] arr2 = {90, 75, 60, 50, 42, 31, 20, 15, 10, 3};   // target = 15


        if(type(arr1))
            System.out.println(ascending(arr1 , 23));
        else
            System.out.println(descending(arr1 , 23));

        if(type(arr2))
            System.out.println(ascending(arr2 , 15));
        else
            System.out.println(descending(arr2 , 15));



    }
    static boolean type(int[] arr){
        if(arr[0]<arr[arr.length-1])
            return true;
        return false;
    }
    static int ascending(int[] arr , int target){
        int st =0;
        int end = arr.length-1;
        while(st<=end){
            int mid = st + (end-st)/2;
            if(target<arr[mid])
                end = mid-1;
            else if(target>arr[mid])
                st = mid+1;
            else
                return mid;
        }
        return -1;
    }
    static int descending(int[] arr , int target){
        int st = 0 ;
        int end = arr.length-1;
        while(st<=end){
            int mid = st+(end-st)/2;
            if(target<arr[mid])
                st = mid+1;
            else if(target>arr[mid])
                end = mid -1;
            else
                return mid;
        }
        return -1;
    }
}
