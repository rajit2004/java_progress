package LeetCode.Strings;

public class LeetCode_3783_MirrorDistance {
    public static void main(String[] args) {
        int digit = 25;
        System.out.println(mirrorDistance(digit));
    }
    static int mirrorDistance(int n) {
        int original = n;
        int reversed = 0;

        // Reverse the number using basic math
        while (n > 0) {
            int digit = n % 10;          // get last digit
            reversed = reversed * 10 + digit; // build reversed number
            n = n / 10;                 // remove last digit
        }
        return Math.abs(original - reversed);
    }
}
