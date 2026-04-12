package LeetCode;

import java.util.Arrays;

public class LeetCode_66_PlusOne {
    public static void main(String[] args) {
        int[] num = {9};
        System.out.println(Arrays.toString(increment(num)));
    }
    static int[] increment(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] < 9) {
                arr[i]++;      // no carry needed
                return arr;
            }
            arr[i] = 0;        // set to 0 and carry continues
        }

        // if all digits were 9 → need new array
        int[] result = new int[arr.length + 1];
        result[0] = 1;
        return result;
    }
}
