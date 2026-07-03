package LeetCode.Strings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LeetCode_17_LetterCombinationOfPhoneNumber {
    public static void main(String[] args) {

        String digits = "23";

        System.out.println(letterCombinations(digits));
    }

    /*
        Breadth First Search (Queue) Approach : Start with an empty string.

        For every digit:
            1. Take all existing combinations.
            2. Append every possible letter for the current digit.
            3. Push the new combinations back into the queue.

        After processing every digit, the queue contains all possible letter combinations.
     */

    static List<String> letterCombinations(String digits) {

        List<String> result = new ArrayList<>();

        // No digits means no possible combinations.
        if (digits == null || digits.isEmpty()) {
            return result;
        }

        // Digit to character mapping.
        String[] map = {
                "", "",
                "abc", "def",
                "ghi", "jkl",
                "mno", "pqrs",
                "tuv", "wxyz"
        };

        // Stores combinations generated so far.
        Queue<String> queue = new LinkedList<>();

        // Start with an empty combination.
        queue.offer("");

        // Process every digit one by one.
        for (char digit : digits.toCharArray()) {

            // Characters corresponding to the current digit.
            String letters = map[digit - '0'];

//  Number of combinations already present. Only these combinations should be expanded for the current digit.
            int size = queue.size();

            // Expand every existing combination.
            for (int i = 0; i < size; i++) {

                String current = queue.poll();

                /*
                    Append every possible letter for the current digit.

                    Example:

                    Current = "a"

                    Digit = '3'

                    Letters = "def"

                    New combinations:

                    "ad"
                    "ae"
                    "af"
                 */
                for (char ch : letters.toCharArray()) {
                    queue.offer(current + ch);
                }
            }
        }

        // Queue now contains every possible combination.
        result.addAll(queue);

        return result;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = number of digits

Each digit maps to at most 4 letters.

---------------------------------------------------------

Time Complexity: O(4ⁿ × n)

Reason: In the worst case: Every digit has 4 choices.
Total combinations: 4ⁿ

Each generated string has length n.
Overall: O(4ⁿ × n)

---------------------------------------------------------

Space Complexity: O(4ⁿ × n)

Reason: The queue stores every generated combination. Worst-case number of combinations: 4ⁿ

Each combination has length n.
Overall: O(4ⁿ × n)

---------------------------------------------------------

Key Observation:

Instead of using recursion (Backtracking), we can generate combinations level by level using a Queue.

Each digit represents one level of expansion.

After processing all digits, the queue contains every valid letter combination.

---------------------------------------------------------
*/