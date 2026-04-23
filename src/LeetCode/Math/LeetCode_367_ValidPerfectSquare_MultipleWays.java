package LeetCode;

public class LeetCode_367_ValidPerfectSquare_MultipleWays {
    public static void main(String[] args) {
        int num = 81;
        System.out.println(isPerfectSquare(num));
    }
    static boolean isPerfectSquare(int num) {
        for (long i = 1; i * i <= num; i++) {
            if (i * i == num) return true;
        }
        return false;
    }
    static boolean isPerfectSquare2(int num) {
        int i = 1;

        while (num > 0) {
            num -= i;
            i += 2;
        }

        return num == 0;
    }
    static boolean isPerfectSquare3(int num) {
        long x = num;

        while (x * x > num) {
            x = (x + num / x) / 2;
        }

        return x * x == num;
    }
}




