package loops;

import java.util.Scanner;

public class loops{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the value of j : ");
        int j = in.nextInt();
//      using for loop:
        for (int i=0 ; i<j ; i++){
            System.out.println("Value of i = " + i );
        }
//      using while loop:
        int i = 0;
        while(i<j){
            System.out.println("Value of i = " + i);
            i++;
        }
    }
}