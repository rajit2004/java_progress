package methods;

import java.util.Scanner;
public class GreetMethod {
    public static void main(String[] args){
        greet();
    }

    static void greet(){
        String name;
        Scanner ip = new Scanner(System.in);
        System.out.print("Enter your name : ");
        name = ip.nextLine();
        System.out.println("Hey there! " +name);
    }
}
