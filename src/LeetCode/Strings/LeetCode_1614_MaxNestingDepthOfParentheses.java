package LeetCode.Strings;

import java.util.*;

public class LeetCode_1614_MaxNestingDepthOfParentheses {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "(1+(2*3)+((8)/4))+1" -> max depth 3
        System.out.println("Sample 1 -> Counter: " + maxDepthCounter("(1+(2*3)+((8)/4))+1")
                + " | Stack: " + maxDepth("(1+(2*3)+((8)/4))+1")
                + " (expected: 3)");

        // Sample 2 (LeetCode): "(1)+((2))+(((3)))" -> max depth 3
        System.out.println("Sample 2 -> Counter: " + maxDepthCounter("(1)+((2))+(((3)))")
                + " | Stack: " + maxDepth("(1)+((2))+(((3)))")
                + " (expected: 3)");

        // Sample 3 (LeetCode): "()(())((()()))" -> max depth 3
        System.out.println("Sample 3 -> Counter: " + maxDepthCounter("()(())((()()))")
                + " | Stack: " + maxDepth("()(())((()()))")
                + " (expected: 3)");

        // Edge case: no parentheses -> depth 0
        System.out.println("Edge (no parens) -> Counter: " + maxDepthCounter("abc")
                + " | Stack: " + maxDepth("abc")
                + " (expected: 0)");

        // Edge case: empty string -> depth 0
        System.out.println("Edge (empty) -> Counter: " + maxDepthCounter("")
                + " | Stack: " + maxDepth("")
                + " (expected: 0)");

        // Edge case: single pair -> depth 1
        System.out.println("Edge (single pair) -> Counter: " + maxDepthCounter("()")
                + " | Stack: " + maxDepth("()")
                + " (expected: 1)");

        // Edge case: fully nested -> depth = n
        System.out.println("Edge (fully nested) -> Counter: " + maxDepthCounter("(((())))")
                + " | Stack: " + maxDepth("(((())))")
                + " (expected: 4)");

        // Edge case: sequential non-nested pairs -> depth 1
        System.out.println("Edge (sequential) -> Counter: " + maxDepthCounter("()()()()")
                + " | Stack: " + maxDepth("()()()()")
                + " (expected: 1)");
    }


    /*
        Approach 1: Single counter (optimal)

        Maintain a running count `openBrackets`:
            - '(' : increment
            - ')' : decrement

        After every character, update `ans = max(ans, openBrackets)`.

        The invariant is that `openBrackets` equals the current nesting depth
        at every position, so the running maximum is the answer.

        Time:  O(n)
        Space: O(1)
    */
    static int maxDepthCounter(String s) {
        int ans = 0;
        int openBrackets = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                openBrackets++;
            } else if (c == ')') {
                openBrackets--;
            }
            // Running maximum equals max nesting depth seen so far
            ans = Math.max(ans, openBrackets);
        }

        return ans;
    }


    /*
        Approach 2: Stack (equivalent, more memory)

        Push '(' onto a stack; pop on ')'.
        The stack size at any point equals the current nesting depth, so the
        running maximum of `st.size()` is the answer.

        This is functionally identical to Approach 1 — the stack only ever
        contains '(' characters, so its size tracks the same counter. Use
        this form if you find the stack model easier to reason about, but
        prefer Approach 1 for the O(1) space.

        Note on parentheses validity:
            Both approaches assume the input is a valid parentheses sequence
            (LeetCode guarantee). On invalid input like ")(", the counter
            approach would go negative and the stack would throw.

        Time:  O(n)
        Space: O(n) worst case (fully nested input)
    */
    static int maxDepth(String s) {
        int ans = 0;
        Deque<Character> st = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(') {
                st.push(c);
            } else if (c == ')') {
                st.pop();
            }
            // Stack size equals current nesting depth
            ans = Math.max(ans, st.size());
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Single counter (optimal)

Time Complexity: O(n)

- One pass over the string, O(1) work per character.

Space Complexity: O(1)

- Only two integer counters; no auxiliary data structure.


Approach 2: Stack

Time Complexity: O(n)

- One pass over the string, O(1) amortized work per character.

Space Complexity: O(n)

- Stack depth up to the maximum nesting depth (worst case n).

Key Observation:
The nesting depth is exactly the count of currently-unmatched '(' at each
position. Tracking that count directly is O(1) space; the stack version
is a heavier way of doing the same thing, since the stack only ever holds
'(' characters.

---------------------------------------------------------
*/