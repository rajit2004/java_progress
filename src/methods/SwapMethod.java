package methods;

import java.util.Scanner;
public class SwapMethod {
    public static void main(String[] args) {
//       int a = 12;
//       int b = 33;
//       methods.swap(a,b);

        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the numbers: ");
        int num1 = input.nextInt();
        int num2 = input.nextInt();
        swap(num1, num2);
    }
    static void swap(int a, int b){
        System.out.println("Before methods.swap : a = " + a + " b = " + b);
        int temp = a;
        a = b;
        b = temp;
        System.out.println("After methods.swap : a = " + a + " b = " + b);
    }
}
