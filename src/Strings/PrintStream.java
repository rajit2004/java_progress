package Strings;

import java.util.Arrays;

public class PrintStream {
    public static void main(String[] args) {
        System.out.println(56);
        System.out.println("RANESH");
        System.out.println('A');
        System.out.println(new int[]{1,2,3,4,5});                         // will print random value instead of the array
        System.out.println(Arrays.toString(new int[]{1,2,3,4,5}));        // this prints the array
        System.out.println(true);
        System.out.println(0.2345);

    }
}
