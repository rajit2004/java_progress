package LeetCode;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_448_FindAllNumsDisappearedFromArray_UsingARRAYLIST {
    public static void main(String[] args) {
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println(findDisappearedNumbers(nums));
    }

    static void swap(int[] arr , int i , int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static List<Integer> findDisappearedNumbers(int[] arr) {

        // Cyclic sort
        int i = 0;
        while (i < arr.length) {
            int correct = arr[i] - 1;

            if (arr[i] != arr[correct]) {
                swap(arr, i, correct);
            } else {
                i++;
            }
        }

        // find and store missing elements using ArrayList
        List<Integer> ans = new ArrayList<>();

        for (int j = 0; j < arr.length; j++) {      // traverse over the array
            if (arr[j] != j + 1) {                  // finding missing elements
                ans.add(j + 1);                     // storing missing elements
            }
        }

        return ans;
    }
}