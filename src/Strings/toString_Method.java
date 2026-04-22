package Strings;

import java.util.Arrays;

public class toString_Method {
    public static void main(String[] args) {
        int num = 56;                   // we cant use methods on this coz it is a primitive
//        in order to be e able to use method on primitives we create the wrapper classes of the primitives
        Integer x = 56;
// or
        Integer q = Integer.valueOf(56);
//        now we have created the wrapper class of the primitive (int) and can apply various methods on them
        System.out.println(q.equals(num));

        int[] array = {1,2,3,4,5,6,7,8,9,0};
        System.out.println(array);          // will return random values
        System.out.println(Arrays.toString(array));
    }
}
