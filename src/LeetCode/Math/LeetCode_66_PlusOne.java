package LeetCode;

import java.util.Arrays;

public class LeetCode_66_PlusOne {
    public static void main(String[] args) {
        int[] num = {9,9,9};
        System.out.println(Arrays.toString(increment(num)));
    }
    static int[] increment(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] < 9) {
                arr[i]++;      // no carry needed
                return arr;
            }
//            else part:
            arr[i] = 0;        // if last digit is more than 0 then set it to 0 and carry continues
        }

        // If loop exits without returning → all digits were 9 → increase size
        int[] result = new int[arr.length + 1];
        result[0] = 1;
        return result;
    }
}
