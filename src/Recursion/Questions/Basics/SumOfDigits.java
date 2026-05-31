package Recursion.Questions.Basics;

public class SumOfDigits {
    public static void main(String[] args) {
        System.out.println(recSum(1342));
    }
    static int sum(int n){
        int ans = 0;
        while(n != 0){
            ans += n % 10;
            n /= 10;
        }
        return ans;
    }

//    using recursion :

    static int recSum(int n){
        if (n == 0)
            return 0;
        return (n % 10) + recSum(n/10);
    }
}
