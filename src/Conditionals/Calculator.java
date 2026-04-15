package conditionals;

import java.util.Scanner;
public class Calculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the operation to be performed {x/X to quit}: ");
            char op = input.next().trim().charAt(0);
            if (op == '+' || op == '-' || op == '*' || op == '/' || op == '%') {
                System.out.print("Enter the num1: ");
                int n1 = input.nextInt();
                System.out.print("Enter the num2 :");
                int n2 = input.nextInt();

                if (op == '+') {
                    System.out.println("SUM : " + (n1 + n2));
                } else if (op == '-') {
                    System.out.println("DIFF : " + (n1 - n2));
                } else if (op == '*') {
                    System.out.println("PRODUCT : " + (n1 * n2));
                } else if (op == '/') {
                    System.out.println("DIVISION : " + (n1 / n2));
                } else {
                    System.out.println("REMAINDER : " + (n1 % n2));
                }
            } else if(op == 'x' || op == 'X'){
                System.out.println("QUIT!");
                break;
            }
            else {
            System.out.println("INVALID OPERATION !");
            }
        }
    }
}
