package basics;

import java.util.Scanner;

public class data_types_inputs{
    public static void main(String[] args){
        // we have to take basics.inputs so we use scanner now and the basics.inputs are gonna be from the user by keyboard so we use System.in here
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your roll number: ");
        int roll_numm = input.nextInt();
        System.out.println("Your roll number is: " + roll_numm);
    }
}