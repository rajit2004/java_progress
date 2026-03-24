package methods;

import java.util.Scanner;

public class sumOfFirstNnums {
    static void main() {
//        sum(10);
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the value of n : ");
        int a = in.nextInt();
        sum(a);
    }
    static void sum(int n){
        int sum = 0;
        while(n>0){
            sum += n;
            n--;
        }
//        System.out.println("Sum of first "+ n +" natural nums : " + sum);
        System.out.println("Sum : " + sum);
    }
}
