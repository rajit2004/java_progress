package sorting;

import java.util.Arrays;

public class cycleSort {
    public static void main(String[] args) {
        int[] nums = {3,5,2,1,4};
        System.out.println(Arrays.toString(sort(nums)));
    }

    static int[] sort(int[] arr){
        int i = 0;

        while (i < arr.length){
            int correct = arr[i] - 1;

            if (arr[i] != arr[correct]) {
                swap(arr, i, correct);
            } else {
                i++;
            }
        }
        return arr;
    }

    static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}