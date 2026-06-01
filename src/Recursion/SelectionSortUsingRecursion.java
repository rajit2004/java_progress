package Recursion;

/*
Selection sort = take the max ele put it at the last index then take the next max and put it in the second last index and so on
*/

import java.util.Arrays;

public class SelectionSortUsingRecursion {
    public static void main(String[] args) {
        int[] arr = {5,4,3,2,1};
        System.out.println(Arrays.toString(sort(arr,arr.length-1)));
    }
    static int[] sort(int[] arr , int end){

//        base condition:
        if(end == 0)
            return arr;

//        for each index:
        swap(arr,maxIndex(arr,end), end);
        return sort(arr , end - 1);



    }
    static int maxIndex(int[] arr , int end){
        int index = 0;
        int max = Integer.MIN_VALUE;
        for(int i = 0 ; i <= end ; i++) {
            if (arr[i] > max) {
                max = arr[i];
                index = i;
            }
        }
        return index;
    }
    static void swap(int[] arr , int st , int end){
        int temp = arr[st];
        arr[st] = arr[end];
        arr[end]= temp;
    }
}
