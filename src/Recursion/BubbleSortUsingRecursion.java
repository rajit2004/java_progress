package Recursion;

public class BubbleSortUsingRecursion {
    public static void main(String[] args) {
        int[] arr = {5, 1, 3, 2, 4};
        sort(arr, arr.length - 1);

        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    static void sort(int[] arr, int end) {
        if (end == 0) {
            return;
        }

        helper(arr, 0, end);
        sort(arr, end - 1);
    }

    static void helper(int[] arr, int index, int end) {
        if (index == end) {
            return;
        }

        if (arr[index] > arr[index + 1]) {
            swap(arr, index, index + 1);
        }

        helper(arr, index + 1, end);
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}