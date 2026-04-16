package Strings;

public class Comparison {
    public static void main(String[] args) {
        /*
        == (Reference comparison) => Checks if both variables point to the same memory location
        .equals() (Content comparison) => Checks if the actual string values are equal
        */

        String a = "Apple";
        String b = "Apple";
        System.out.println(a == b);         // checks whether a & b points to the same reference

        String c = new String("Apple");         // we explicitly create a new reference variable with the same value of object
        System.out.println(a == c);         // now when we check whether a & c points to the same reference it returns false even if they have same value

        System.out.println(a.equals(b));        // .equals() check the value at the reference variable if it is same no matter the memory address
        System.out.println(a.equals(c));        // .equals() check the value at the reference variable if it is same no matter the memory address

    }
}
