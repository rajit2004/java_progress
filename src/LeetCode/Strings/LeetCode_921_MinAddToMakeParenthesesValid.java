package LeetCode.Strings;

public class LeetCode_921_MinAddToMakeParenthesesValid {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "())" -> 1 (add one '(' at the start)
        System.out.println("Sample 1 ()) -> " + minAddToMakeValid("())")
                + " (expected: 1)");

        // Sample 2 (LeetCode): "(((" -> 3 (add three ')')
        System.out.println("Sample 2 ((( -> " + minAddToMakeValid("(((")
                + " (expected: 3)");

        // Edge case: already valid -> 0 additions
        System.out.println("Edge (()) -> " + minAddToMakeValid("()()")
                + " (expected: 0)");

        // Edge case: fully nested balanced -> 0
        System.out.println("Edge (nested balanced) -> " + minAddToMakeValid("((()))")
                + " (expected: 0)");

        // Edge case: empty string -> nothing to add
        System.out.println("Edge (empty) -> " + minAddToMakeValid("")
                + " (expected: 0)");

        // Edge case: only closing brackets -> add one '(' per ')'
        System.out.println("Edge ))) -> " + minAddToMakeValid(")))")
                + " (expected: 3)");

        // Edge case: single open -> add one ')'
        System.out.println("Edge ( -> " + minAddToMakeValid("(")
                + " (expected: 1)");

        // Edge case: single close -> add one '('
        System.out.println("Edge ) -> " + minAddToMakeValid(")")
                + " (expected: 1)");

        // Edge case: interleaved mismatches
        //   "())((" -> one unmatched ')' at idx 2, two unmatched '(' at end -> total 3
        System.out.println("Edge ())(( -> " + minAddToMakeValid("())((")
                + " (expected: 3)");

        // Edge case: alternating unbalanced
        //   ")(()" -> leading ')' unmatched (1), trailing ')' wait:
        //   scan: ')' -> minAdds=1; '(' -> open=1; '(' -> open=2; ')' -> open=1
        //   total = 1 + 1 = 2
        System.out.println("Edge )(() -> " + minAddToMakeValid(")(()")
                + " (expected: 2)");
    }


    /*
        Approach: Single-pass greedy count

        Scan left to right while maintaining:
            openBrackets       = unmatched '(' seen so far
            minAddsRequired    = total ')' that had no matching '('

        For each character:
            - '(' : increment openBrackets.
            - ')' :
                  * If openBrackets > 0, match this ')' with an earlier '('
                    → decrement openBrackets.
                  * Otherwise, no unmatched '(' is available, so this ')'
                    cannot be matched by anything in s → we must add a '('
                    before it → increment minAddsRequired.

        At the end, any remaining openBrackets are unmatched '(' — each
        needs one extra ')' appended after the string. So the answer is
        minAddsRequired + openBrackets.

        Why greedy is optimal:
            Each ')' that finds no partner must be paired with an inserted
            '(' — there is no way to avoid this addition because no later
            character can match it (matching is left-to-right). Similarly,
            each trailing unmatched '(' must be paired with an inserted ')'
            after the string. Both additions are forced, so the greedy count
            equals the minimum.

        Example trace on "())":
            c = '(' -> openBrackets = 1
            c = ')' -> openBrackets = 0
            c = ')' -> no open to match -> minAddsRequired = 1
            end: 1 + 0 = 1 ✓

        Example trace on "(((":
            All three '(' -> openBrackets = 3
            end: 0 + 3 = 3 ✓

        Time:  O(n)
        Space: O(1)
    */
    static int minAddToMakeValid(String s) {
        int openBrackets = 0;
        int minAddsRequired = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                openBrackets++;
            } else {
                // If an unmatched '(' exists, match it with the current ')'
                // Otherwise, we need to insert an extra '(' before this ')'
                if (openBrackets > 0) {
                    openBrackets--;
                } else {
                    minAddsRequired++;
                }
            }
        }

        // Remaining unmatched '(' each need a matching ')' appended at the end
        return minAddsRequired + openBrackets;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Single-pass greedy count

Time Complexity: O(n)

- One linear scan over the string, O(1) work per character.

Space Complexity: O(1)

- Only two integer counters; no auxiliary data structures.

Key Observation:
Every unmatched ')' must be fixed by inserting a '(' before it, and every
unmatched '(' at the end must be fixed by inserting a ')' after it. Both
counts are forced, and the greedy left-to-right scan counts them in one
pass. Because the two repair sets are disjoint, the total is simply
(unmatched ')') + (unmatched '('), which is exactly the answer.

---------------------------------------------------------
*/