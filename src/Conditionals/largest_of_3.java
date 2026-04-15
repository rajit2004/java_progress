package conditionals;

import java.util.Scanner;

public class largest_of_3 {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
//        method 1
        System.out.print("Enter three numbers separated by spaces : ");
        float b = in.nextFloat();
        float c = in.nextFloat();
        float a = in.nextFloat();

        if (a>b && a>c){
            System.out.println("The largest number is " + a);
        }
        else if(b>a && b>c){
            System.out.println("The largest number is " + b);
        }
        else{
            System.out.println("The largest number is " + c);
        }

//method 2

//        System.out.print("Enter three numbers : ");
//        float x = in.nextFloat();
//        float y = in.nextFloat();
//        float z = in.nextFloat();

        float max = a;
        if (b>max){
            max = b;
        }
        if(c>max){
            max = c;
        }

        System.out.println("The maximum number is " + max);

//        method 3

        float p = Math.max(a,b);
        float q = Math.max(a,c);
        System.out.println("The max is "+ Math.max(p,q));

//        method 4
        System.out.println("The max is :" + Math.max(c , Math.max(a,b)));
    }
}
