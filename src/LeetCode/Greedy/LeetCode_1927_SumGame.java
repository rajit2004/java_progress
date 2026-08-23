package LeetCode.Greedy;

public class LeetCode_1927_SumGame {
    public static void main(String[] args) {

        String num = "5023";

        System.out.println(sumGame(num));
    }

    /*
        Greedy + Mathematical Observation : The string is divided into two equal halves.
        For each half, we calculate:
                nn = sum of known digits
                qq = number of '?' characters

        Let:
            n0 = sum of digits in the left half
            q0 = number of '?' in the left half
            n1 = sum of digits in the right half
            q1 = number of '?' in the right half

        Alice and Bob take turns replacing '?' with digits from 0 to 9.

        The game can only result in a tie when:
            1. The total number of '?' is even.
            2. The existing sum difference can be exactly balanced by the '?' values.

        The condition: n0 - n1 == ((q1 - q0) * 9) / 2 represents the exact balance required for the two halves to have equal sums.

        Therefore, Alice wins whenever either:
            - The number of '?' is odd, or
            - The required balance is impossible.
     */
    static boolean sumGame(String num) {

        int n = num.length();

        // Process the left half of the string.
        int[] left = get(num.substring(0, n / 2));

        // Process the right half of the string.
        int[] right = get(num.substring(n / 2, n));

        // Sum of known digits in the left half.
        int n0 = left[0];

        // Number of '?' characters in the left half.
        int q0 = left[1];

        // Sum of known digits in the right half.
        int n1 = right[0];

        // Number of '?' characters in the right half.
        int q1 = right[1];

        /*
            If the total number of '?' characters is odd, Alice always has a winning strategy.
            Otherwise, check whether the current sum difference can be exactly balanced.
         */
        return (q0 + q1) % 2 == 1
                || n0 - n1 != ((q1 - q0) * 9) / 2;
    }

    /*
        Extract information from one half of the string.

        Returns:
            [0] -> sum of all known digits
            [1] -> number of '?' characters
     */
    private static int[] get(String s) {

        int digitSum = 0;
        int questionMarks = 0;

        // Process every character in the half.
        for (char ch : s.toCharArray()) {

            if (ch == '?')
                // Count unknown digits.
                questionMarks++;

            else
                // Add the known digit to the sum.
                digitSum += ch - '0';
        }

        return new int[]{digitSum, questionMarks};
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = num.length()

---------------------------------------------------------

Time Complexity: O(n)

Reason:

The string is divided into two halves and every character is processed exactly once.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: substring() creates strings representing the two halves of the input.
Apart from these strings, only a constant amount of additional information is maintained.

Overall: O(n)

---------------------------------------------------------

Key Observation:

Only two pieces of information are required from  each half:
    1. Sum of known digits
    2. Number of '?' characters

If the total number of '?' is odd, Alice can always force a win.
If it is even, the game can end in a tie only when the existing digit-sum difference exactly matches the maximum possible contribution difference from the unknown digits.

---------------------------------------------------------
*/