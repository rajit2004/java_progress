package methods;

import java.util.Scanner;
public class factorials {
    static void main() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number : ");
        int x = in.nextInt();
        System.out.println("Factorial of " + x + " : " + factorial(x));
    }
    static int factorial(int n) {
        if (n<0)
            return 0;
        if (n ==0 || n ==1)
            return 1;
        return n * factorial(n-1);
    }
}
