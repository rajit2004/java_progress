package sorting;

import java.util.Arrays;

public class InsertionSortSwappingMethod {
    public static void main(String[] args) {
        int[] array = {12, -5, 7, -18, 3, 25, -2, 9, -11, 6, 0, -7, 14, -20, 8, -1, 19, -3, 4, -9};
        System.out.println(Arrays.toString(sort(array)));
    }
    static void swap(int[] arr , int first , int second){
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
    static int[] sort(int[] arr){
        for(int i = 0;i<arr.length-1;i++){
            for(int j = i+1; j>0 ; j--){
                if(arr[j]<arr[j-1])
                    swap(arr , j , j-1);
                else
                    break;
            }
        }
        return arr;
    }
}
