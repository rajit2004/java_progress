package sorting;

import java.util.Arrays;

public class CycleSort_0toN {
    public static void main(String[] args) {
        int[] nums = {3,5,0,2,1,4};
        System.out.println(Arrays.toString(cyclicSort(nums)));
    }
    static int[] cyclicSort(int[] arr) {
        int i = 0;

        while (i < arr.length) {
            int correct = arr[i];

            if (arr[i] != arr[correct]) {
                swap(arr, i, correct);
            } else {
                i++;
            }
        }
        return arr;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
