package methods;

import java.util.Scanner;
public class functions {
    public static void main(String[] args) {
        sum();
    }

    static void sum(){
        int a,b,sum;
        Scanner ip = new Scanner(System.in);
        System.out.print("Enter the basics.basics.basics.first number : ");
        a = ip.nextInt();
        System.out.print("Enter the second number : ");
        b = ip.nextInt();
        sum = a+b;
        System.out.println("The basics.sum is : "+sum);
    }
}
