package conditionals;

import java.util.Scanner;

public class if_cndtn {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the amount : ");
        float amt = input.nextFloat();
        if (amt >= 2000){
            System.out.println("You are eligible for free delivery");
        }
        else{
            System.out.println("Delivery charge of $9.99 will be applicable");
        }
    }
}
