package Strings;

public class PrettyPrinting {
    public static void main(String[] args) {
        float n = 761.126456789f;
        System.out.printf("The formatted number is : %.2f" , n);
        System.out.println();
//        printf is used to print in formatted manner
//        % is used to add placeholder
//        .nf represents the num of decimal values we wanna print after the .
//        let us assume %.3f => this prints 3 digits after the decimal
//        it also rounds off the number to n places like in the above example
        System.out.println(Math.PI);
        System.out.printf("pi till 3 decimal places = %.3f" , Math.PI);
        System.out.println();

//        for strings
        String name = "Ranesh";
        String hobby = "coding";
        System.out.printf("Hello I'm %s and my hobby is %s" , name , hobby);



        // Right-aligned (default) with width 10
        System.out.printf("%10d%n", 42);     // "        42"

// Left-aligned with width 10
        System.out.printf("%-10d%n", 42);    // "42        "

// Zero-padded (right-aligned)
        System.out.printf("%010d%n", 42);    // "0000000042"

// Floating-point with decimals
        System.out.printf("%.2f%n", 3.14159);    // "3.14"

// String alignment
        System.out.printf("%10s%n", "hello");    // "     hello"
        System.out.printf("%-10s%n", "hello");   // "hello     "
    }
}
