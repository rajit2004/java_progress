package Searching;

//binary search for ascending order elements in array

public class BinarySearchAscending {
    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9, 12, 15, 18, 21, 25, 30, 34, 39, 42, 47, 53, 58, 64, 71, 80};
        System.out.println(binarySearch(array , 30));
    }

//    return the index at which th element is found!
//    return -1 if not found!
    static int binarySearch(int[] arr , int target ){
        int start = 0;
        int end = arr.length-1;
       while(start<= end){
//           why not (start + end)/2 = st + end may exceed the size limit of int type in java
           int mid = start + (end-start)/2;
           if(target<arr[mid])
               end = mid-1;
           else if(target>arr[mid])
               start= mid +1;
           else         // found
               return mid;
       }
       return -1;
    }
}