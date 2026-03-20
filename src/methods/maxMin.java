import java.util.Scanner;
public class maxMin {
    static void main() {
    max();
    min();
    }
    static void max(){
        System.out.print("Enter two nums :");
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();
        int y = input.nextInt();
        if (x>y)
            System.out.println("Max element : " + x);

        else
            System.out.println("Max element : " + y);

    }
    static void min() {
        System.out.print("Enter two nums : ");
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();
        int y = input.nextInt();
        if (x < y)
            System.out.println("Min element : " + x);

        else
            System.out.println("Min element : " + y);
    }
}
