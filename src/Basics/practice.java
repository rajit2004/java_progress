package basics;

import java.util.Scanner;

public class practice {
    public static void main(String[] args){
       Scanner input = new Scanner(System.in);
//        System.out.print("Enter your roll number: ");
//        int x = input.nextInt();
//        System.out.println("Your roll number is : " + x);

//        System.out.print("Enter your name: ");
//        String name = input.next();
//        String full_name = input.nextLine();
//        System.out.println("Your name is : " + name);
//        System.out.println("Your full name is : " + full_name);

//        float sgpa = 7.75f;
//        System.out.printf("Your SGPA is : %.2f", sgpa); // .2f means 2 digits after decimal point

        System.out.print("Enter your cgpa : ");
        float cgpa = input.nextFloat();
        System.out.println("your cgpa is : " + cgpa);
    }
}
