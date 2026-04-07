package LeetCode;

public class LeetCode_258_SumOfDigitsInNumber {

    public static int addDigits(int num) {
        if (num == 0) return 0;
        return 1 + (num - 1) % 9;
    }

    public static void main(String[] args) {
        int num = 38; // test input
        System.out.println(addDigits(num)); // expected output: 2
    }
}