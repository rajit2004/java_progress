import java.util.Scanner;
public class oddEven {
    static void main() {
        System.out.println(check());
    }
    static String check(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number : ");
        int a = input.nextInt();
        if(a%2==0)
            return "EVEN";
        else
            return "ODD";
    }
}
