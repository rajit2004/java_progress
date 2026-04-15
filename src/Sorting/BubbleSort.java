package Sorting;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] array = {17, -4, 23, 0, -15, 8, 3, -9, 12, -1, 6, -20, 14, 5, -7, 19, -2, 11, -13, 2};
        System.out.println(Arrays.toString(bubble(array)));
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static int[] bubble(int [] arr){
        for (int i = 0; i < arr.length-1; i++) {
            for(int j = 0 ; j < arr.length-i-1 ; j++){
                if(arr[j]>arr[j+1])
                    swap(arr, j , j+1);
            }
        }
        return arr;
    }
}
