package conditionals;

import java.util.Scanner;

public class upper_or_lower {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Character : ");
        char x = input.next().trim().charAt(0);
        System.out.println(x);
        if (x >= 'a' && x <= 'z') {
            System.out.println("lower case");
        } else if (x >= 'A' && x <= 'Z') {
            System.out.println("upper case");
        }
        else if(x >= '0' && x <= '9') {
            System.out.println("digit");
        }
        else{
            System.out.println("invalid");
        }
    }
}