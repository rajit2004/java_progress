package Strings;

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


    }
}