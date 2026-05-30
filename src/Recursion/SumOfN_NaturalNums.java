package Recursion;

public class SumOfN_NaturalNums {
    public static void main(String[] args) {
        System.out.println(sum(10));
        System.out.println(sum(50));
    }
    static int sum(int n){
        if(n == 0)
            return 0;
        return n + sum(n-1);
    }
}
