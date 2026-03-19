package methods;

import java.util.Scanner;
public class SumMethodReturn {
    public static void main(String[] args){
        int ans = sum();
        System.out.println("Your answer is : "+ans);
    }
    static int sum(){
        Scanner ip = new Scanner(System.in);
        int a,b,sum;
        System.out.print("Enter your number 1 : ");
        a = ip.nextInt();
        System.out.print("Enter your number 2 : ");
        b = ip.nextInt();
        sum = a+b;
        return sum;
    }
}
