package Recursion;

public class ProductOfDigits {
    public static void main(String[] args) {
        System.out.println(product(505));
    }
    static int product(int n){
//        base condition:
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;

        return (n % 10) * product(n/10);
    }
}
