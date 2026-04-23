package LeetCode;

// Given an integer n, return true if it is a power of two. Otherwise, return false.
// An integer n is a power of two, if there exists an integer x such that n == 2x.

public class LeetCode_231_PowerOf2 {
    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(4));
    }
    static boolean isPowerOfTwo(int n) {
        if (n <= 0) return false;

        while (n % 2 == 0) {
            n = n / 2;
        }
        return n == 1;
    }
}
