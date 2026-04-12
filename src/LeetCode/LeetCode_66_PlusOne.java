package LeetCode;

import java.util.Arrays;

public class LeetCode_66_PlusOne {
    public static void main(String[] args) {
        int[] num = {1,2,3};
        System.out.println(Arrays.toString(increment(num)));
    }
    static int[] increment(int[] arr){
        int last = arr[arr.length-1] = arr[arr.length-1] + 1;
        if(last>9){
            int num1 = last%10;
        }
        return arr;
    }
}
