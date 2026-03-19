package numbers;

import java.util.Scanner;
public class isPrime {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("Enter a number (enter 0 to exit) : ");
            int x = input.nextInt();
            if(x==0){
                System.out.println("exiting......");
                break;
            }
            else
                is_prime(x);
        }
    }
    static void is_prime(int a) {
        if (a <= 1) {
            System.out.println("Not a prime number!");
            return;
        }

        for (int i = 2; i < a; i++) {
            if (a % i == 0) {
                System.out.println("Not a prime number!");
                return;
            }
        }

        System.out.println("Is a prime number!");
    }
}
