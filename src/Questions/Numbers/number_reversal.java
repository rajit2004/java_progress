package numbers;

import java.util.Scanner;
//public class numbers.number_reversal {
//    public static void main(String[] args){
//        Scanner input = new Scanner(System.in);
//        System.out.print("Enter the number : ");
//        int n = input.nextInt();
//        System.out.println("The number you entered is : "+n);
//        System.out.print("The reversed number is : ");
//        while(n>0){
//            int num = n%10;
//            System.out.print(num);
//            n = n/10;
//        }
//    }
//}
//alternative method :
public class number_reversal{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number : ");
        int num = input.nextInt();
        int ans = 0 ;
        while(num>0){
            int rem = num%10;
            num /= 10;
            ans = ans*10+rem;

        }
        System.out.print("Reversed number : "+ans);
    }
}