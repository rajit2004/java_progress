package methods;

import java.util.Scanner;
public class VariableASargumanet {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name : ");
        String name = input.nextLine();
        System.out.println(greet(name));
    }
    static String greet(String name){
        return "Hello, " + name + "!";
    }
}
