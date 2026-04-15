package conditionals;

import java.util.Scanner;
public class no_of_occurance {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number : ");
        int num = input.nextInt();
        int count = 0;
        System.out.print("Enter the number to check : ");
        int check = input.nextInt();
        while(num > 0){
            int remainder = num % 10;
            if (remainder == check){
                count++;
            }
            num = num / 10;         //we do this to make the number shorter
        }

        System.out.println("Number of occurance of "+ check + " is : " + count);
    }
}
//import java.util.Scanner;

//public class conditionals.no_of_occurance {
//    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//
//        System.out.print("Enter the number: ");
//        String num = input.nextLine();
//
//        System.out.print("Enter the digit to check: ");
//        char check = input.next().charAt(0);
//
//        int count = 0;
//
//        for (int i = 0; i < num.length(); i++) {
//            if (num.charAt(i) == check) {
//                count++;
//            }
//        }
//
//        System.out.println("Number of occurrence of " + check + " is: " + count);
//    }
//}
