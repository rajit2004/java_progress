package LeetCode.Enum;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_1291_SequentialDigits {
    public static void main(String[] args) {

        int low = 100;
        int high = 5000;

        System.out.println(sequentialDigits(low, high));
    }

    /*
        Enumeration Approach :

        Every sequential digit number is a substring of:

            "123456789"

        Generate every possible substring of length:

            2 to 9

        Convert each substring into an integer.

        If the number lies within the given range,
        add it to the answer.
     */

    static List<Integer> sequentialDigits(int low, int high) {

        List<Integer> ans = new ArrayList<>();

        // String containing all sequential digits.
        String digits = "123456789";

        // Choose every possible length.
        for (int len = 2; len <= 9; len++) {

            /*
                Generate every substring of the
                current length.
             */
            for (int start = 0; start + len <= 9; start++) {

                // Convert the substring into a number.
                int num = Integer.parseInt(
                        digits.substring(start, start + len)
                );

                // Include only numbers inside the given range.
                if (num >= low && num <= high) {
                    ans.add(num);
                }
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Only 36 sequential numbers are possible.

Reason:

Length 2 : 8 numbers

Length 3 : 7 numbers

Length 4 : 6 numbers

Length 5 : 5 numbers

Length 6 : 4 numbers

Length 7 : 3 numbers

Length 8 : 2 numbers

Length 9 : 1 number

Total = 36

---------------------------------------------------------

Time Complexity: O(1)

Reason:

The algorithm always generates at most
36 numbers regardless of the input range.

Overall:

O(1)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Ignoring the output list, only a few
variables are used.

The maximum number of sequential numbers
that can be stored is also fixed (36).

Overall:

O(1)

---------------------------------------------------------

Key Observation:

Every valid sequential digit number already
exists as a substring of:

"123456789"

Instead of constructing numbers digit by digit,
simply enumerate all possible substrings and
keep those lying within the required range.

---------------------------------------------------------
*/