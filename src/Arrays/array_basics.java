package Arrays;

import java.util.Arrays;
import java.util.Collections;

public class array_basics {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        Integer[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Arrays.sort(arr2 , Collections.reverseOrder());
//        System.out.println(arr);    //can't print arrays like this we need to use the "Arrays.toString(parameter)" fn.
        System.out.println(Arrays.toString(arr2));

    }
}
