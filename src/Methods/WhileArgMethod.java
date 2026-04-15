package Methods;

import java.util.Scanner;

public class WhileArgMethod {
    public static void main(String[] args) {
        calc();
    }

    static void calc() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nBasic conditionals.Calculator");

            System.out.print("Enter basics.basics.basics.first number: ");
            int a = input.nextInt();

            System.out.print("Enter second number: ");
            int b = input.nextInt();

            System.out.println("Choose the operation:");
            System.out.println("1.) Addition");
            System.out.println("2.) Subtraction");
            System.out.println("3.) Multiplication");
            System.out.println("4.) Division");
            System.out.println("5.) Exit");

            System.out.print("Enter your choice: ");
            int ch = input.nextInt();

            if (ch == 1) System.out.println("Result = " + (a + b));
            else if (ch == 2) System.out.println("Result = " + (a - b));
            else if (ch == 3) System.out.println("Result = " + (a * b));
            else if (ch == 4) {
                if (b != 0) System.out.println("Result = " + (a / b));
                else System.out.println("Division by zero not allowed.");
            }
            else if (ch == 5) {
                System.out.println("Exiting...");
                break;
            }
            else System.out.println("Invalid choice.");

            System.out.print("\nDo you want to continue? (y/n): ");
            char cont = input.next().charAt(0);

            if (cont == 'n' || cont == 'N') {
                System.out.println("Program ended.");
                break;
            }
        }
    }
}