package LeetCode.Strings;

public class LeetCode_1541_MinInsertionToBalanceParenthesesString {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "(()))" -> 1 (the first '(' needs an extra ')')
        System.out.println("Sample 1 (()))) -> " + minInsertions("(()))")
                + " (expected: 1)");

        // Sample 2 (LeetCode): "())" -> 0 (already balanced: one '(' + '))')
        System.out.println("Sample 2 ()) -> " + minInsertions("())")
                + " (expected: 0)");

        // Sample 3 (LeetCode): "))())(" -> 3
        System.out.println("Sample 3 ))())( -> " + minInsertions("))())(")
                + " (expected: 3)");

        // Sample 4 (LeetCode): "((((((" -> 12 (each '(' needs two ')')
        System.out.println("Sample 4 (((((( -> " + minInsertions("((((((")
                + " (expected: 12)");

        // Sample 5 (LeetCode): ")))))))" -> 5
        System.out.println("Sample 5 ))))))) -> " + minInsertions(")))))))")
                + " (expected: 5)");

        // Edge case: empty string -> nothing to insert
        System.out.println("Edge (empty) -> " + minInsertions("")
                + " (expected: 0)");

        // Edge case: minimal balanced unit
        System.out.println("Edge (()) -> " + minInsertions("())")
                + " (expected: 0)");

        // Edge case: two opens with two closes -> each '(' needs two ')', so 2 insertions
        System.out.println("Edge (()) -> " + minInsertions("(())")
                + " (expected: 2)");

        // Edge case: perfect nested 2x2 (balanced after additions)
        //   "((()))" has 3 opens, 3 closes -> 3 opens need 6 closes, have 3 -> need 3 more
        System.out.println("Edge ((())) -> " + minInsertions("((()))")
                + " (expected: 3)");

        // Edge case: single ')' -> need one '(' and one extra ')' -> 2 insertions
        System.out.println("Edge ) -> " + minInsertions(")")
                + " (expected: 2)");

        // Edge case: single '(' -> need two ')' -> 2 insertions
        System.out.println("Edge ( -> " + minInsertions("(")
                + " (expected: 2)");

        // Edge case: string of only close brackets
        System.out.println("Edge ))) -> " + minInsertions(")))")
                + " (expected: 3)");
    }


    /*
        Approach: Single-pass greedy with leftCount

        Problem restated (LeetCode 1541):
        A balanced string is one where every '(' is followed by TWO
        consecutive ')' (i.e. "()" is NOT balanced here; "())" is).
        Each '(' effectively needs a matching "))" pair.

        Greedy scan left to right, tracking:
            leftCount  = number of unmatched '(' seen so far
            insertions = total number of characters we've had to insert

        Rules per index:
            - If s[index] == '(' :
                  leftCount++
                  index++

            - If s[index] == ')' :
                  1. Match this ')' against an open bracket:
                       - If leftCount > 0, consume one open  (leftCount--)
                       - Otherwise, we must insert a '(' beforehand
                         (insertions++), and now this ')' has a partner.
                  2. Every open needs TWO consecutive ')'. Try to consume
                     the next ')' as the second one:
                       - If s[index + 1] == ')', consume both (index += 2)
                       - Otherwise, insert a ')' (insertions++), index++

        After the scan, any remaining unmatched '(' each needs "))"
        appended at the end -> insertions += leftCount * 2.

        Why greedy is optimal:
            - Each unmatched ')' forces an inserted '(' immediately before it.
              There is no cheaper repair, and delaying the insertion only
              makes things worse.
            - Each '(' whose following character is not ')' forces an
              inserted ')' right after it. No alternative placement is
              cheaper.
            - Trailing '(' must each be closed with "))" — again forced.
            So every insertion decision is forced; the greedy count equals
            the minimum.

        Example trace on "))())(":
            idx 0: ')' -> leftCount=0, insert '(' (ins=1). Next is ')' -> idx=2.
            idx 2: '(' -> leftCount=1, idx=3.
            idx 3: ')' -> leftCount-- (0). Next is ')' -> idx=5.
            idx 5: '(' -> leftCount=1, idx=6 (end).
            insertions += 1 * 2 = 2. Total = 1 + 2 = 3. ✓

        Time:  O(n) — single pass, each character visited once
        Space: O(1) — two integer counters
    */
    static int minInsertions(String s) {
        int insertions = 0;
        int leftCount = 0;
        int length = s.length();
        int index = 0;

        while (index < length) {
            char c = s.charAt(index);

            if (c == '(') {
                // Start of a new open bracket; it will need two ')' to close
                leftCount++;
                index++;
            } else {
                // Current character is ')'

                // Step 1: match this ')' with an open (real or inserted)
                if (leftCount > 0) {
                    leftCount--;        // consume one unmatched '('
                } else {
                    insertions++;       // must insert a '(' before this ')'
                }

                // Step 2: an open bracket needs TWO consecutive ')'.
                // Check whether the next character provides the second ')'
                if (index < length - 1 && s.charAt(index + 1) == ')') {
                    index += 2;         // both ')' consumed
                } else {
                    insertions++;       // insert the missing second ')'
                    index++;
                }
            }
        }

        // Each remaining unmatched '(' needs "))" appended → 2 insertions each
        insertions += leftCount * 2;
        return insertions;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Single-pass greedy with leftCount

Time Complexity: O(n)

- One linear scan. Each character is examined at most once because when
  a '))' pair is consumed, the index jumps by 2.

Space Complexity: O(1)

- Only two integer counters; no auxiliary data structures.

Key Observation:
The "balanced" definition here (each '(' matched with two consecutive ')')
means the greedy decision at every step is FORCED:
    - A ')' with no available '(' forces an inserted '(' right before it.
    - An '(' whose next character is not ')' forces an inserted ')' right after it.
    - Any unmatched '(' at the end forces an appended "))".

Because there is no flexibility in where these insertions go, the greedy
count is not just a heuristic — it is exactly the minimum.

---------------------------------------------------------
*/