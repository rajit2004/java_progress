package sorting;

import java.util.Arrays;

// cycle sort doesn't fully sort the array if there are duplicate entries
// there is no other specific technique for completely sorting array with duplicate entries using cycle sort
public class CycleSortDuplicateElements {
    public static void main(String[] args) {
        int[] arr = {4,3,2,7,8,2,3,1};
        System.out.println(Arrays.toString(cyclicSort(arr)));
    }

     static int[] cyclicSort(int[] arr) {
        int i = 0;

        while (i < arr.length) {
            int correct = arr[i] - 1;

            // check range + avoid infinite loop with duplicates
            if (arr[i] > 0 && arr[i] <= arr.length && arr[i] != arr[correct])
                swap(arr, i, correct);
                else
                    i++;
        }
//         for (int j = 0; j < arr.length; j++) {
//             if (arr[j] != j + 1) {
//                 System.out.println("Duplicate: " + arr[j]);
//                 System.out.println("Missing: " + (j + 1));
//             }
//         }
        return arr;
    }

     static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

