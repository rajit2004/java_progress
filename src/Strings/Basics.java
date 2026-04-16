package Strings;

public class Basics {
    public static void main(String[] args) {
//        creation of String
        String fruit = "Apple";         // initial creation of a string reference variable pointing to an object "Apple" & "Apple" is stored in string pool
        System.out.println(fruit);
        fruit = "Banana";               // A new string "Banana" is created in the pool & fruit now points to "Banana"
        System.out.println(fruit);

//        "Apple still exists in the pool only the reference changed, not the string."

    /*
    Strings are immutable → "Apple" cannot become "Banana"
    Variable fruit is mutable → it can point to a different string
    */

    }
}
