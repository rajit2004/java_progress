package Arrays;

import java.util.Arrays;

public class array_basics {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
//        System.out.println(arr);    //can't print arrays like this we need to use the "Arrays.toString(parameter)" fn.
        System.out.println(Arrays.toString(arr));

    }
}
