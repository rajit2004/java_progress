package LeetCode.Maths;

public class LeetCode_3723_MaxSumOfSqOfDigits {
    public static void main(String[] args) {
        System.out.println(maxSumOfSquares(2, 10));   // 91
        System.out.println(maxSumOfSquares(3, 20));   // 992
    }


    /*
        Approach: Greedy concentration into 9s

        To maximize the sum of squares of digits for a fixed digit sum,
        concentrate as much value as possible into the largest digit (9).
        This works because x^2 is convex: 9^2 + 1^2 > 5^2 + 5^2.

        Steps:
            1. If sum > 9 * num, it is impossible -> return "".
            2. Put as many 9s as possible: nines = sum / 9.
            3. If there is a remainder r = sum % 9, put it next.
            4. Fill the remaining positions with zeros.

        This also produces the lexicographically largest valid string
        among all maximum-square-sum candidates because larger digits
        come first.

        Time:  O(num)
        Space: O(num) for the result
    */
    static String maxSumOfSquares(int num, int sum) {
        // Impossible: digit sum exceeds what num digits can hold
        if (sum > 9 * num) {
            return "";
        }

        int r = sum % 9;
        StringBuilder ans = new StringBuilder();

        // Add as many 9s as possible
        int nines = sum / 9;
        for (int i = 0; i < nines; i++) {
            ans.append('9');
        }

        // Add the remaining digit (if any)
        if (r > 0) {
            ans.append((char) ('0' + r));
        }

        // Pad with zeros to reach exactly `num` digits
        int zeros = num - ans.length();
        for (int i = 0; i < zeros; i++) {
            ans.append('0');
        }

        return ans.toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Greedy concentration into 9s

Time Complexity: O(num)

- Building the result string takes O(num) total appends.

Space Complexity: O(num)

- StringBuilder holds exactly `num` characters.

Key Observation:
For a fixed digit sum, the sum of squares is maximized by making digits
as extreme as possible (9s first, then one remainder, then zeros), because
x^2 is convex. This also yields the lexicographically largest string
among all optimal choices since larger digits are placed first.

---------------------------------------------------------
*/