package Recursion.Questions;

public class ReverseOfNum {
    public static void main(String[] args) {
        System.out.println(ans(1824));
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
}
