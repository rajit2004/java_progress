package Methods;

import java.util.Scanner;
public class ReturnString {
    public static void main(String[] args){
        String name = retstr();
        System.out.println("Your answer is : "+name);
    }
    static String retstr(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name : ");
//        String name = input.nextLine();
//        return name;
        return input.nextLine();
    }
}
