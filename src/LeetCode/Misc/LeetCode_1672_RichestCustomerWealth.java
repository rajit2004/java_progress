package LeetCode;

public class LeetCode_1672_RichestCustomerWealth {
    public static void main(String[] args) {
        int[][] account = {
                {3, 7, 1},
                {10, 25},
                {60, 2, 9, 4},
                {8},
                {11, 14, 5}
        };
        balance(account);
    }

    static void balance(int[][] arr) {
        int max = 0;
        int Index = 0;

        for (int i = 0; i < arr.length; i++) {
            int sum = 0;

            for (int ele : arr[i]) {
                sum += ele;
            }

            if (sum > max) {
                max = sum;
                Index = i;
            }
        }

        System.out.println("Max wealth : " + max);
        System.out.println("By person : " + (Index+1));
    }
}