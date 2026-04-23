package LeetCode;

public class LeetCode_258_SumOfDigitsInNumber_BruteForce {
    public static void main(String[] args) {
        int num = 38;
        System.out.println(addDigits(num));
    }
    static int addDigits(int num) {
        while (num >= 10) {
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }
}

