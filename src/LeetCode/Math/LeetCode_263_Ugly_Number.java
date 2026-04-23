package LeetCode;

public class LeetCode_263_Ugly_Number {
    public static void main(String[] args) {
        int n = 18;
        System.out.println(isUgly(n));
    }
    static boolean isUgly(int n) {
        // Step 1: Ugly numbers must be positive
        if (n <= 0)
            return false;

        // Step 2: Divide by 2 as long as possible
        while (n % 2 == 0)
            n = n / 2;

        // Step 3: Divide by 3 as long as possible
        while (n % 3 == 0)
            n = n / 3;

        // Step 4: Divide by 5 as long as possible
        while (n % 5 == 0)
            n = n / 5;

        // Step 5: Check if reduced to 1
        return n == 1;
    }
}
