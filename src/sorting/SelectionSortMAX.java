package sorting;

import java.util.Arrays;

public class SelectionSortMAX {
    public static void main(String[] args) {
        int[] array  = {12, -5, 0, 23, -17, 8, -1, 34, -9, 2, 19, -12, 7, -3, 25, -20, 4, -7, 15, -2};
        System.out.println(Arrays.toString(sort(array)));
    }
    static void swap(int[] arr , int first , int second){
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
    static int getMaxIndex(int[] arr , int start , int end){
        int max = start;
        for (int i = start; i <= end ; i++) {
            if(arr[max]  < arr[i])
                max = i;
        }
        return max;
    }
    static int[] sort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int last = arr.length-i-1;
            int maxIndex = getMaxIndex(arr , 0 , last);
            swap(arr ,maxIndex , last);
        }
        return arr;
    }
}
