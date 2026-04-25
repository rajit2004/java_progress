package Strings;

/*
the operator has simply two function :
    1. Add if inputs are all primitives
    2. But is overloaded in case of strings to concatenate the strings
*/

import java.util.ArrayList;
import java.util.Arrays;

public class Operators {
    public static void main(String[] args) {

//        Concatenation Operator : +    -> only applicable for primitives or when either one of the operands is a string

        System.out.println('a' + 'b');              // prints the sum of ASCII value of both char
        System.out.println('a' + 16);               // prints the ASCII value of 16th alphabet after a
        System.out.println((char)('a' + 16));       // prints the char of the ASCII value of nth alphabet after char {Type Casting}
        System.out.println("A" + "B");              // merges both the strings
        System.out.println("a" + 1);                // integer will be converted to Integer that will call toString() -> this is same as after a few steps: "a" + "1"
        System.out.println("Apple" + new ArrayList<Integer>(5));
        System.out.println("Apple" + Arrays.toString(new String[]{"Banana" , "Peach"}));
        System.out.println(new ArrayList<Integer>() + Arrays.toString(new int[]{1,2,3,4,5,6,7,8,9,0}));
        System.out.println(new Integer(56) + "banana");
//      System.out.println(new ArrayList<Integer>() + new Integer(25);    -> error as there should be all primitives or at least one string
//      But we can type cast the above result to a string and print
        String ans = new ArrayList<Integer>() + " " + new Integer(25);           // adding " " is important or else the + operator won't work
        System.out.println(ans);
    }
}