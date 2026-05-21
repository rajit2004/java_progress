package Strings;

import java.util.Scanner;

public class PalindromeUsingREVERSE {
    public static void main(String[] args) {
        System.out.print("Enter a string : ");
        Scanner in = new Scanner(System.in);
        String original = in.next();
        String reversed = new StringBuilder(original).reverse().toString();

        if(original.equals(reversed))
            System.out.println("valid");
        else
            System.out.println("invalid");
    }
}
