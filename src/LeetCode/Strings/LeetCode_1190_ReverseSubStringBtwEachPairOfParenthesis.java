package LeetCode.Strings;

import java.util.*;

public class LeetCode_1190_ReverseSubStringBtwEachPairOfParenthesis {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "(abcd)" -> "dcba"
        System.out.println("Sample 1 -> Stack: " + reverseParenthesesStack("(abcd)")
                + " | PairJump: " + reverseParentheses("(abcd)")
                + " (expected: dcba)");

        // Sample 2 (LeetCode): "(u(love)i)" -> "iloveu"
        System.out.println("Sample 2 -> Stack: " + reverseParenthesesStack("(u(love)i)")
                + " | PairJump: " + reverseParentheses("(u(love)i)")
                + " (expected: iloveu)");

        // Sample 3 (LeetCode): "(ed(et(oc))el)" -> "leetcode"
        System.out.println("Sample 3 -> Stack: " + reverseParenthesesStack("(ed(et(oc))el)")
                + " | PairJump: " + reverseParentheses("(ed(et(oc))el)")
                + " (expected: leetcode)");

        // Edge case: no parentheses -> return unchanged
        System.out.println("Edge (no parens) -> Stack: " + reverseParenthesesStack("abc")
                + " | PairJump: " + reverseParentheses("abc")
                + " (expected: abc)");

        // Edge case: empty string
        System.out.println("Edge (empty) -> Stack: " + reverseParenthesesStack("")
                + " | PairJump: " + reverseParentheses("")
                + " (expected: )");

        // Edge case: single pair with one char -> same char
        System.out.println("Edge (single char) -> Stack: " + reverseParenthesesStack("(a)")
                + " | PairJump: " + reverseParentheses("(a)")
                + " (expected: a)");

        // Edge case: empty parentheses
        System.out.println("Edge (empty parens) -> Stack: " + reverseParenthesesStack("()")
                + " | PairJump: " + reverseParentheses("()")
                + " (expected: )");

        // Edge case: adjacent pairs -> "ab" reversed twice = "ab"
        System.out.println("Edge (adjacent pairs) -> Stack: " + reverseParenthesesStack("(ab)(cd)")
                + " | PairJump: " + reverseParentheses("(ab)(cd)")
                + " (expected: badc)");
    }


    /*
        Approach 1: Stack of open-parenthesis positions + in-place reversal

        Scan s left to right:
            - On '(', remember the current length of the result buffer.
              That position is where the matching ')' reversal should begin.
            - On ')', pop the saved start index and reverse result[start .. end]
              in place.
            - On any other char, append it to the result buffer.

        Because inner brackets are processed first and their result is already
        embedded in `result` before the outer ')' is encountered, each
        reversal correctly operates on the fully-expanded inner content.

        Example: "(u(love)i)"
            - open positions pushed: 0, then 2 (after 'u')
            - on ')' at "(love)": reverse result[2..] = "u evol" -> "u love"? Actually:
              after pushing 0, appending 'u', pushing 2, appending l,o,v,e
              result = "ulove"; on ')': reverse [2..4] -> "u evo l" -> "uevol"
            - continue: append 'i' -> "uevoli"
            - on final ')': reverse [0..5] -> "iloveu" ✓

        Time:  O(n^2) worst case (each ')' may reverse an O(n) segment)
        Space: O(n) for the result buffer + O(depth) for the stack
    */
    static String reverseParenthesesStack(String s) {
        Deque<Integer> openParenthesesIndices = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();

        for (char currentChar : s.toCharArray()) {
            if (currentChar == '(') {
                // Store the current length as the start index for future reversal
                openParenthesesIndices.push(result.length());
            } else if (currentChar == ')') {
                int start = openParenthesesIndices.pop();
                // Reverse the substring between the matching parentheses
                reverse(result, start, result.length() - 1);
            } else {
                // Append non-parenthesis characters to the processed string
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    /*
        In-place reversal of sb[start..end] (inclusive) using two pointers.
    */
    static void reverse(StringBuilder sb, int start, int end) {
        while (start < end) {
            char temp = sb.charAt(start);
            sb.setCharAt(start++, sb.charAt(end));
            sb.setCharAt(end--, temp);
        }
    }


    /*
        Approach 2: Pair map + single directional walk (O(n))

        Step 1 (pairing pass):
            Use a stack to match each '(' with its ')' and store the positions
            in a `pair` array. pair[i] gives the index of the matching
            counterpart for every bracket position.

        Step 2 (walk pass):
            Walk the string with a signed `direction` (+1 or -1):
                - Letter: append it to the result.
                - Bracket: jump to its pair (currIndex = pair[currIndex]),
                           then flip direction.

        Why this works:
            Reversing a substring inside brackets is equivalent to walking
            through it in the opposite direction. Each bracket pair acts as
            a "direction flip" — entering '(' from either side means we're
            about to traverse the enclosed content in reverse; hitting the
            matching ')' flips us back.

            The net effect of nested pairs is that each character ends up
            visited exactly once, in its correct final position.

        This yields O(n) time — each index is visited once.

        Time:  O(n)
        Space: O(n) for the pair array + stack
    */
    static String reverseParentheses(String s) {
        int n = s.length();
        Deque<Integer> openParenthesesIndices = new ArrayDeque<>();
        int[] pair = new int[n];

        // Pass 1: match every '(' with its corresponding ')'
        for (int i = 0; i < n; ++i) {
            if (s.charAt(i) == '(') {
                openParenthesesIndices.push(i);
            }
            if (s.charAt(i) == ')') {
                int j = openParenthesesIndices.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }

        // Pass 2: walk the string, flipping direction at each bracket pair
        StringBuilder result = new StringBuilder();
        for (int currIndex = 0, direction = 1;
             currIndex < n;
             currIndex += direction) {

            char c = s.charAt(currIndex);
            if (c == '(' || c == ')') {
                currIndex = pair[currIndex];   // jump to the matching bracket
                direction = -direction;        // flip traversal direction
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Stack + in-place reversal

Time Complexity: O(n^2)

- Worst case: deeply nested brackets like "((((...))))" cause O(n)
  reversals of size O(n) each.

Space Complexity: O(n)

- StringBuilder of size n plus stack up to depth n.


Approach 2: Pair map + directional walk

Time Complexity: O(n)

- First pass: O(n) to build the pair table.
- Second pass: each index is visited at most once (direction flips
  cancel out), so the walk is O(n).

Space Complexity: O(n)

- int[] pair of size n; stack up to depth n during pairing.

Key Observation:
Reversing a bracketed substring is equivalent to traversing it in the
opposite direction. Precomputing matching bracket pairs with a stack
lets us simulate this by literally flipping a direction variable while
walking through s — every character is emitted exactly once, giving
an elegant O(n) solution that avoids the repeated reversals of the
stack approach.

---------------------------------------------------------
*/