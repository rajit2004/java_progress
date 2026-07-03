package LeetCode.Strings;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_22_GenerateParenthesis {
    public static void main(String[] args) {

        int n = 3;

        System.out.println(generateParenthesis(n));
    }

    /*
        Backtracking Approach :

        At every step we have two choices:

            1. Add '('
            2. Add ')'

        Rules:

            1. We can add '(' only if we have not used all n opening brackets.
            2. We can add ')' only if there are more opening brackets than closing brackets.

        Continue building the string until its length becomes 2 * n.

        Every valid string is added to the answer.
     */

    static List<String> generateParenthesis(int n) {

        List<String> result = new ArrayList<>();

        // Start with an empty string.
        backtrack(result, "", 0, 0, n);

        return result;
    }

    /*
        current -> Current parenthesis string being built.

        open -> Number of '(' used so far.

        close -> Number of ')' used so far.
     */

    static void backtrack(List<String> result,
                          String current,
                          int open,
                          int close,
                          int n) {

//            Base Case : If the string contains 2 * n characters, a valid combination has been formed.
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }

        /*
            Add '(' if we still have opening brackets remaining.
            Maximum allowed = n.
         */
        if (open < n) {
            backtrack(result,
                    current + "(",
                    open + 1,
                    close,
                    n);
        }

//            Add ')' only if it keeps the string valid. Closing brackets can never exceed opening brackets.
        if (close < open) {
            backtrack(result,
                    current + ")",
                    open,
                    close + 1,
                    n);
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = number of parenthesis pairs

---------------------------------------------------------

Time Complexity: O(4ⁿ / √n)

Reason:

The number of valid parenthesis combinations is the nth Catalan Number.

Catalan(n) ≈ 4ⁿ / (n^(3/2))

Generating each valid combination requires building a string of length 2n.

Overall complexity is commonly written as: O(4ⁿ / √n)

---------------------------------------------------------

Space Complexity: O(4ⁿ / √n)

Reason: The result list stores all valid combinations.

The recursion depth is at most: 2n , which is O(n).

The output itself dominates the space usage.

---------------------------------------------------------

Key Observation:

A parenthesis string is valid only if:

1. Opening brackets never exceed n.
2. Closing brackets never exceed opening brackets.

By enforcing these rules during construction, invalid strings are never generated.

This is the strength of Backtracking build only valid candidates instead of generating every possible string.

---------------------------------------------------------
*/