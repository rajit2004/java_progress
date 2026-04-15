package Sorting;

import java.util.Arrays;

public class InsertionSortOptimized {
    public static void main(String[] args) {
        int[] array = {12, -5, 7, -18, 3, 25, -2, 9, -11, 6, 0, -7, 14, -20, 8, -1, 19, -3, 4, -9};
        System.out.println(Arrays.toString(sort(array)));
    }
    static int[] sort(int[] arr){
        for(int i = 1; i < arr.length; i++){
            int key = arr[i];
            int j = i - 1;

            while(j >= 0 && arr[j] > key){
                arr[j + 1] = arr[j];  // shift right
                j--;
            }
            arr[j + 1] = key;  // insert at correct position
        }
        return arr;
    }
}
