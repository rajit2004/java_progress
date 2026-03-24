package methods;

import java.util.Scanner;
public class prime {
    static void main() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter starting num : ");
        int x = in.nextInt();
        System.out.print("Enter the ending num : ");
        int y = in.nextInt();
        for(int i = x; i<y ; i++) {
            isPrime(i);
        }
    }
    static void isPrime(int a){
        if (a<=1)
            return ;
        for (int i = 2; i <a ; i++) {
            if(a%i==0)
                return;
        }
        System.out.print(a + " ");
    }
}
