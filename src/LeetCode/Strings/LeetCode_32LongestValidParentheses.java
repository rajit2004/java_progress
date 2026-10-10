package LeetCode.Strings;

import java.util.*;

public class LeetCode_32LongestValidParentheses {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "(()" -> 2 (substring "()")
        System.out.println("Sample 1 (() -> Stack: " + longestValidParenthesesStack("(()")
                + " | TwoPass: " + longestValidParentheses("(()")
                + " (expected: 2)");

        // Sample 2 (LeetCode): ")()())" -> 4 (substring "()()")
        System.out.println("Sample 2 )()()) -> Stack: " + longestValidParenthesesStack(")()())")
                + " | TwoPass: " + longestValidParentheses(")()())")
                + " (expected: 4)");

        // Sample 3 (LeetCode): "" -> 0
        System.out.println("Sample 3 (empty) -> Stack: " + longestValidParenthesesStack("")
                + " | TwoPass: " + longestValidParentheses("")
                + " (expected: 0)");

        // Edge case: only open brackets -> 0
        System.out.println("Edge ((( -> Stack: " + longestValidParenthesesStack("(((")
                + " | TwoPass: " + longestValidParentheses("(((")
                + " (expected: 0)");

        // Edge case: only close brackets -> 0
        System.out.println("Edge ))) -> Stack: " + longestValidParenthesesStack(")))")
                + " | TwoPass: " + longestValidParentheses(")))")
                + " (expected: 0)");

        // Edge case: fully balanced whole string
        System.out.println("Edge (()()) -> Stack: " + longestValidParenthesesStack("()()()")
                + " | TwoPass: " + longestValidParentheses("()()()")
                + " (expected: 6)");

        // Edge case: nested valid substring
        System.out.println("Edge ((())) -> Stack: " + longestValidParenthesesStack("((()))")
                + " | TwoPass: " + longestValidParentheses("((()))")
                + " (expected: 6)");

        // Edge case: valid substring in the middle
        System.out.println("Edge ()(()()) -> Stack: " + longestValidParenthesesStack(")(()())")
                + " | TwoPass: " + longestValidParentheses(")(()())")
                + " (expected: 6)");

        // Edge case: single character
        System.out.println("Edge (single () -> Stack: " + longestValidParenthesesStack("(")
                + " | TwoPass: " + longestValidParentheses("(")
                + " (expected: 0)");
    }


    /*
        Approach 1: Stack of indices

        Idea: A valid parentheses substring corresponds to a range whose
        endpoints are matched, and any unmatched bracket breaks the run.

        Algorithm:
            - Push -1 as a "base index" (the index just before position 0).
            - On '(': push its index onto the stack.
            - On ')':
                  * Pop the top. If the stack becomes empty, the popped item
                    was the base; push the current index as the new base.
                  * Otherwise, the current top is the index just before the
                    start of a valid run — the current run length is
                    (i - stack.peek()). Update the answer.

        Why the -1 base works:
            It gives a sentinel so that when the first character is '(' and
            then matches with ')', the length i - (-1) = i + 1 is computed
            correctly. Every later "unmatched )" resets the base to its own
            index, effectively discarding everything before it.

        Time:  O(n)
        Space: O(n) — worst case, the stack holds all indices.
    */
    static int longestValidParenthesesStack(String s) {
        int res = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);                         // base index sentinel

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);                  // remember position of '('
            } else {
                stack.pop();                    // consume matching '(' or base

                if (stack.isEmpty()) {
                    // This ')' had no match → becomes the new base
                    stack.push(i);
                } else {
                    // Current top is the index before the valid run
                    res = Math.max(res, i - stack.peek());
                }
            }
        }

        return res;
    }


    /*
        Approach 2: Two-pass scan with O(1) space

        Idea: Without a stack, we can find the longest valid run by counting
        '(' and ')' while scanning. A run is valid only when:
            - open == close (balanced), and
            - at no prefix does close exceed open.

        Left-to-right pass catches runs where the number of '(' is >= number
        of ')' at every prefix. That misses runs where there are extra '('
        at the START (e.g. "(()" — the valid substring "()" at indices 1-2
        is never detected because the leading '(' skews the count).

        Right-to-left pass catches those. In reverse, a valid run has
        close >= open at every prefix, and we reset when open > close.

        Scanning both directions with a reset condition on opposite
        inequalities covers all cases in O(1) space.

        `s.charAt(i) & 1` trick:
            '(' has ASCII 40 (even) → bit is 0
            ')' has ASCII 41 (odd)  → bit is 1
            So f[0] counts '(', f[1] counts ')'.

        Time:  O(n) — two linear scans
        Space: O(1) — only four counters
    */
    static int longestValidParentheses(String s) {
        int[] f = new int[2];   // forward counters:  f[0] = '(', f[1] = ')'
        int[] b = new int[2];   // backward counters: b[0] = '(', b[1] = ')'
        int res = 0, n = s.length();

        for (int i = 0; i < n; i++) {
            // ----- Forward scan -----
            f[s.charAt(i) & 1]++;                   // increment '(' or ')' counter
            if (f[0] == f[1]) {
                res = Math.max(res, f[1] << 1);     // balanced → candidate length
            }
            if (f[0] < f[1]) {
                f[0] = f[1] = 0;                    // too many ')' → invalid run, reset
            }

            // ----- Backward scan -----
            b[s.charAt(n - 1 - i) & 1]++;
            if (b[0] == b[1]) {
                res = Math.max(res, b[1] << 1);
            }
            if (b[0] > b[1]) {
                b[0] = b[1] = 0;                    // too many '(' (i.e. extra ')' when read forward) → reset
            }
        }

        return res;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Stack of indices

Time Complexity: O(n)

- Single pass over the string. Each index is pushed/popped at most once.

Space Complexity: O(n)

- Stack holds up to n indices in the worst case (e.g. "(((((").


Approach 2: Two-pass scan with O(1) space

Time Complexity: O(n)

- Two passes over the string, O(1) work per character.

Space Complexity: O(1)

- Only four integer counters and the result.

Key Observation:

A valid parentheses substring is maximal in two senses:
   (a) It is balanced (equal counts of '(' and ')').
   (b) No prefix of it has more ')' than '('.

The stack approach directly identifies maximal valid runs by tracking the
nearest unmatched index. The two-pass approach detects runs by counting
from both directions and resetting on the first violation of the balance
inequality — the forward pass handles over-closed runs, and the backward
pass handles over-opened runs.

The bit trick `char & 1` is a fast way to index the counter array:
'(' (ASCII 40, even) → index 0, ')' (ASCII 41, odd) → index 1.

---------------------------------------------------------
*/