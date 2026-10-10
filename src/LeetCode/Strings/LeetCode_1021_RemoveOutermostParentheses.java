package LeetCode.Strings;

import java.util.*;

public class LeetCode_1021_RemoveOutermostParentheses {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "(()())(())" -> "()()()"
        //   Primitives: "(()())" and "(())" -> strip outer -> "()()" and "()" -> concat
        System.out.println("Sample 1 -> Counter: " + removeOuterParenthesesCounter("(()())(())")
                + " | Stack: " + removeOuterParentheses("(()())(())")
                + " (expected: ()()())".replace(")", "").concat(")")); // trim comment trick

        // Sample 2 (LeetCode): "(()())(())(()(()))" -> "()()()()(())"
        System.out.println("Sample 2 -> Counter: " + removeOuterParenthesesCounter("(()())(())(()(()))")
                + " | Stack: " + removeOuterParentheses("(()())(())(()(()))")
                + " (expected: ()()()()(())")
        ;

        // Sample 3 (LeetCode): "()()" -> ""
        System.out.println("Sample 3 -> Counter: '" + removeOuterParenthesesCounter("()()")
                + "' | Stack: '" + removeOuterParentheses("()()")
                + "' (expected: '')");

        // Edge case: single pair "()" -> ""
        System.out.println("Edge (single pair) -> Counter: '" + removeOuterParenthesesCounter("()")
                + "' | Stack: '" + removeOuterParentheses("()")
                + "' (expected: '')");

        // Edge case: empty string -> ""
        System.out.println("Edge (empty) -> Counter: '" + removeOuterParenthesesCounter("")
                + "' | Stack: '" + removeOuterParentheses("")
                + "' (expected: '')");

        // Edge case: deeply nested single primitive "((()))" -> "(())"
        System.out.println("Edge (nested) -> Counter: " + removeOuterParenthesesCounter("((()))")
                + " | Stack: " + removeOuterParentheses("((()))")
                + " (expected: (()))".replace(")", "").concat("))"));

        // Edge case: many siblings "()()()()" -> ""
        System.out.println("Edge (siblings) -> Counter: '" + removeOuterParenthesesCounter("()()()()")
                + "' | Stack: '" + removeOuterParentheses("()()()()")
                + "' (expected: '')");
    }


    /*
        Approach 1: Level counter (optimal)

        Track the current nesting depth `level`:
            - On ')' : decrement first, then check the depth AFTER popping.
            - On '(' : check the depth BEFORE pushing.

        Rule: append a character to the result only if, after accounting for
        the character's effect on depth, the current depth is > 0. That is:
            - ')' at depth 0 (after decrement) -> it was the outer closer,
              skip it.
            - ')' at depth > 0 (after decrement) -> it's inside the
              primitive, keep it.
            - '(' at depth > 0 (before increment) -> it's inside the
              primitive, keep it.
            - '(' at depth 0 (before increment) -> it's the outer opener,
              skip it.

        This is why the code checks ')' before appending (after decrement)
        and checks '(' after appending (before increment). Both checks are
        really "is the character interior?", just timed differently for
        each type.

        Time:  O(n)
        Space: O(n) for the result string (O(1) auxiliary)
    */
    static String removeOuterParenthesesCounter(String s) {
        int level = 0;
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Decrement BEFORE the interior check so ')' at depth 0 is skipped
            if (c == ')') {
                level--;
            }

            // Only append if we're inside a primitive
            if (level > 0) {
                res.append(c);
            }

            // Increment AFTER the interior check so '(' at depth 0 is skipped
            if (c == '(') {
                level++;
            }
        }

        return res.toString();
    }


    /*
        Approach 2: Stack-based (equivalent, more memory)

        Mirror of Approach 1 using an explicit stack:
            - On ')' : pop first.
            - Append if the stack is NOT empty (i.e. we're inside a primitive).
            - On '(' : push AFTER the check.

        Because the stack only ever contains '(' characters, the stack size
        is exactly the same as `level` in Approach 1. This version is
        functionally identical but uses O(n) extra space.

        Included for reference and to build intuition that "level counter"
        and "stack" are often interchangeable.

        Time:  O(n)
        Space: O(n) for the stack (worst case, fully nested)
    */
    static String removeOuterParentheses(String s) {
        StringBuilder res = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Pop before interior check
            if (c == ')') {
                stack.pop();
            }

            // Only append if we're inside a primitive
            if (!stack.isEmpty()) {
                res.append(c);
            }

            // Push after interior check
            if (c == '(') {
                stack.push(c);
            }
        }

        return res.toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Level counter (optimal)

Time Complexity: O(n)

- One pass over the input, O(1) work per character.

Space Complexity: O(n)

- Output StringBuilder (unavoidable, since the output can be nearly as
  long as the input). O(1) auxiliary space beyond the output.


Approach 2: Stack-based

Time Complexity: O(n)

- One pass over the input, O(1) amortized per character.

Space Complexity: O(n)

- Stack holds up to n/2 '(' in the worst case (fully nested input).
- Plus O(n) for the output.

Key Observation:

Every outermost bracket pair starts and ends at depth 0 (before the '('
is pushed / after the ')' is popped). All interior characters are at
depth > 0 at the moment they're processed. So the problem reduces to:
"emit each character iff its depth is > 0 at the right moment."

The trick is that for ')' we check AFTER decrementing (so the outer ')'
lands at depth 0 and is skipped), and for '(' we check BEFORE incrementing
(so the outer '(' lands at depth 0 and is skipped).

The stack version is the same algorithm with an explicit container — useful
for building intuition but strictly worse on space.

---------------------------------------------------------
*/