package LeetCode;

public class LeetCode_263_ALTERNATIVE {
    public static void main(String[] args) {
        int n = 12;
        System.out.println(isUgly(n));
    }
    static boolean isUgly(int n) {
        while(true){
            if(n==0)
                return false;
            else if(n%2==0)
                n/=2;
            else if(n%3==0)
                n/=3;
            else if(n%5==0)
                n/=5;
            else
                break;
        }
        return n==1;
    }
}
