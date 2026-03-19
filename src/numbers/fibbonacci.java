package numbers;

import java.util.Scanner;

public class fibbonacci {
    public static void main(String[] args) {
        long a = 0;
        long b = 1;
        long sum = 0;
        Scanner input = new Scanner(System.in);
        System.out.print("enter a number : ");
        int n = input.nextInt();
        for (int i = 1; i < n; i++) {
            sum = a + b;
            a = b;
            b = sum;
        }
        System.out.println("The " + n +"th term of the fibonacci is : " + sum);
    }
}


