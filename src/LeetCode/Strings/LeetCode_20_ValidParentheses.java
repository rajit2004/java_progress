package LeetCode.Strings;

import java.util.*;

public class LeetCode_20_ValidParentheses {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "()" -> true
        System.out.println("Sample 1 () -> StringBuilder: " + isValid("()")
                + " | CharArray: " + isValidCharArray("()")
                + " (expected: true)");

        // Sample 2 (LeetCode): "()[]{}" -> true
        System.out.println("Sample 2 ()[]{} -> StringBuilder: " + isValid("()[]{}")
                + " | CharArray: " + isValidCharArray("()[]{}")
                + " (expected: true)");

        // Sample 3 (LeetCode): "(]" -> false
        System.out.println("Sample 3 (] -> StringBuilder: " + isValid("(]")
                + " | CharArray: " + isValidCharArray("(]")
                + " (expected: false)");

        // Sample 4 (LeetCode): "([])" -> true
        System.out.println("Sample 4 ([]) -> StringBuilder: " + isValid("([])")
                + " | CharArray: " + isValidCharArray("([])")
                + " (expected: true)");

        // Edge case: odd length -> cannot be balanced
        System.out.println("Edge (odd length) -> StringBuilder: " + isValid("(")
                + " | CharArray: " + isValidCharArray("(")
                + " (expected: false)");

        // Edge case: empty string -> trivially valid
        System.out.println("Edge (empty) -> StringBuilder: " + isValid("")
                + " | CharArray: " + isValidCharArray("")
                + " (expected: true)");

        // Edge case: closing bracket first -> invalid
        System.out.println("Edge (starts with ')') -> StringBuilder: " + isValid(")")
                + " | CharArray: " + isValidCharArray(")")
                + " (expected: false)");

        // Edge case: mismatched nesting "([)]" -> invalid
        System.out.println("Edge (mismatched nesting) -> StringBuilder: " + isValid("([)]")
                + " | CharArray: " + isValidCharArray("([)]")
                + " (expected: false)");

        // Edge case: unclosed opening at end "(()"
        System.out.println("Edge (unclosed open) -> StringBuilder: " + isValid("(()")
                + " | CharArray: " + isValidCharArray("(()")
                + " (expected: false)");

        // Edge case: deeply nested balanced
        System.out.println("Edge (deeply nested) -> StringBuilder: " + isValid("({[()]})")
                + " | CharArray: " + isValidCharArray("({[()]})")
                + " (expected: true)");
    }


    /*
        Approach 1: StringBuilder as a stack

        Use a StringBuilder as an ad-hoc stack of opening brackets.

        Scan each character:
            - On an opening bracket '(', '{', '[' -> append to the stack.
            - On a closing bracket ')', '}', ']':
                  * If the stack is empty -> nothing to match -> invalid.
                  * If the top of the stack matches the expected opener
                    -> pop it (delete last char).
                  * Otherwise -> mismatched type -> invalid.

        After scanning, the string is valid iff the stack is empty
        (every opening bracket was properly closed).

        Time:  O(n) — amortized; StringBuilder append and deleteCharAt at the
                     end are O(1).
        Space: O(n) worst case — all opening brackets stored.
    */
    static boolean isValid(String s) {
        StringBuilder store = new StringBuilder();

        for (char ch : s.toCharArray()) {

            // Opening bracket -> push onto the stack
            if (ch == '(' || ch == '{' || ch == '[') {
                store.append(ch);
            } else {
                // Closing bracket with nothing to match
                if (store.isEmpty()) return false;

                char last = store.charAt(store.length() - 1);

                // Check that the closing bracket matches the last opening one
                if ((ch == ')' && last == '(') ||
                        (ch == '}' && last == '{') ||
                        (ch == ']' && last == '[')) {
                    // Pop the matched opener
                    store.deleteCharAt(store.length() - 1);
                } else {
                    return false;
                }
            }
        }

        // Valid only if every opener was matched and closed
        return store.isEmpty();
    }


    /*
        Approach 2: Precomputed char[] stack (faster, no boxing / no library overhead)

        Instead of storing the OPENING bracket on the stack, we store the
        EXPECTED closing bracket. This turns the "does it match?" check into
        a single character equality comparison — no switch/branch needed.

        Scan each character:
            - On '(' push ')'; on '{' push '}'; on '[' push ']'.
            - On any closer:
                  * head == 0       -> stack empty, invalid.
                  * stack[--head]   -> pop and compare with the current char.
                    If they differ -> invalid.

        Quick reject: strings with odd length can never be balanced.
        Final check: head == 0 (nothing left on the stack).

        Time:  O(n)
        Space: O(n) worst case (char[] of size n).

        This approach is faster than Approach 1 in practice because:
            - No StringBuilder overhead (direct array access).
            - Comparison is a single char != char, no multi-clause if.
            - Uses char[] which is a primitive array (cache-friendly).
    */
    static boolean isValidCharArray(String s) {
        // Odd-length strings cannot be balanced
        if (s.length() % 2 != 0) return false;

        char[] stack = new char[s.length()];
        int head = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack[head++] = ')';       // push the EXPECTED closer
            } else if (c == '{') {
                stack[head++] = '}';
            } else if (c == '[') {
                stack[head++] = ']';
            } else {
                // Closer: pop and compare in one step
                if (head == 0 || stack[--head] != c) {
                    return false;
                }
            }
        }

        return head == 0;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: StringBuilder as a stack

Time Complexity: O(n)

- One pass over the string, O(1) amortized work per character.
- StringBuilder append / deleteCharAt at the end are O(1).

Space Complexity: O(n)

- StringBuilder holds up to n/2 opening brackets in the worst case.


Approach 2: Precomputed char[] stack

Time Complexity: O(n)

- One pass over the string, O(1) work per character.
- Early exit on the first mismatch.

Space Complexity: O(n)

- char[] of size n (only the first n/2 slots used at most).

Key Observation:
The two solutions use the same underlying algorithm (stack-based matching),
but differ in representation:
    - Approach 1 stores OPENING brackets and checks the match with an if.
    - Approach 2 stores EXPECTED CLOSING brackets, turning the match check
      into a single `!=` comparison — simpler code, faster in practice.

The odd-length rejection in Approach 2 is a cheap early-out that catches
many invalid inputs before any stack work.

---------------------------------------------------------
*/