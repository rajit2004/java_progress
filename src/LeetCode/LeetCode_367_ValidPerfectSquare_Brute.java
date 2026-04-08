package LeetCode;

public class LeetCode_367_ValidPerfectSquare_Brute {
    public static void main(String[] args) {
        int num = 81;
        System.out.println(isPerfectSquare(num~));
    }
    static boolean isPerfectSquare(int num) {
        for (long i = 1; i * i <= num; i++) {
            if (i * i == num) return true;
        }
        return false;
    }
}
