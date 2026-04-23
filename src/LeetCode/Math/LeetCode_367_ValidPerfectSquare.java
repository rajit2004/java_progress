package LeetCode;

// Given a positive integer num, return true if num is a perfect square or false otherwise.

public class LeetCode_367_ValidPerfectSquare {
    public static void main(String[] args) {
        int num = 16;
        System.out.println(isPerfectSquare(num));
    }
    static boolean isPerfectSquare(int num) {
        if (num < 2) return true;

        long start = 1;
        long end = num / 2;

        while (start <= end) {
            long mid = start + (end - start) / 2;
            long square = mid * mid;

            if (square == num) return true;
            else if (square < num) start = mid + 1;
            else end = mid - 1;
        }

        return false;
    }
}
