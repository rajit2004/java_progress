package Recursion.Questions;

public class ReverseOfNum {
    public static void main(String[] args) {
        System.out.println(reverses(1824));
    }

//    using stringbuilder :
    static StringBuilder reverse(int n){
        StringBuilder ans = new StringBuilder();
        while(n > 0){
            ans.append(n%10);
            n /= 10;
        }
        return ans;
    }

//    using maths :
    static int rev(int n){
        int ans = 0;
        while(n > 0){
            ans = ans * 10 + (n % 10);
            n /= 10;
        }
        return ans;
    }

//    using recursion :
    static int ans(int n) {
        return helper(n, 0);
    }

    static int helper(int n, int rev) {
        if (n == 0)
            return rev;

        return helper(n / 10, rev * 10 + n % 10);
    }

//    alternate recursion method :

    static int reverses(int n) {
        int digits = (int) Math.log10(n) + 1;
        return helpFn(n, digits);
    }

    static int helpFn(int n, int currDig) {
        if (n % 10 == n)
            return n;

        int remainder = n % 10;
        return remainder * (int) Math.pow(10, currDig - 1) + helpFn(n / 10, currDig - 1);
    }
}
