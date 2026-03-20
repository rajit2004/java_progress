import java.util.Scanner;

public class vote {
    static void main() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your age : ");
        int a = input.nextInt();
        System.out.println(eligibility(a));
    }
    static String eligibility(int age){
        if (age >= 18)
            return "Eligible to vote";
        else
            return "Not eligible to vote";
    }
}
