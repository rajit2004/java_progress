package LeetCode.Strings;

import java.util.*;

public class LeetCode_301_RemoveInvalidParentheses {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "()())()" -> ["(())()", "()()()"]
        System.out.println("Sample 1 ()())( -> MinRemoved: " + removeInvalidParenthesesMinRemoved("()())()")
                + " | PrunedCount: " + removeInvalidParentheses("()())()")
                + " | Reverse: " + removeInvalidParenthesesReverse("()())()"));

        // Sample 2 (LeetCode): "(a)())()" -> ["(a)()()", "(a())()"]
        System.out.println("Sample 2 (a)())( -> MinRemoved: " + removeInvalidParenthesesMinRemoved("(a)())()")
                + " | PrunedCount: " + removeInvalidParentheses("(a)())()")
                + " | Reverse: " + removeInvalidParenthesesReverse("(a)())()"));

        // Sample 3 (LeetCode): ")(" -> [""]
        System.out.println("Sample 3 )( -> MinRemoved: " + removeInvalidParenthesesMinRemoved(")(")
                + " | PrunedCount: " + removeInvalidParentheses(")(")
                + " | Reverse: " + removeInvalidParenthesesReverse(")("));

        // Edge case: empty string -> [""]
        System.out.println("Edge (empty) -> MinRemoved: " + removeInvalidParenthesesMinRemoved("")
                + " | PrunedCount: " + removeInvalidParentheses("")
                + " | Reverse: " + removeInvalidParenthesesReverse(""));

        // Edge case: already valid -> return as-is
        System.out.println("Edge (valid) -> MinRemoved: " + removeInvalidParenthesesMinRemoved("(a)(b)")
                + " | PrunedCount: " + removeInvalidParentheses("(a)(b)")
                + " | Reverse: " + removeInvalidParenthesesReverse("(a)(b)"));

        // Edge case: no parentheses -> return original
        System.out.println("Edge (no parens) -> MinRemoved: " + removeInvalidParenthesesMinRemoved("abc")
                + " | PrunedCount: " + removeInvalidParentheses("abc")
                + " | Reverse: " + removeInvalidParenthesesReverse("abc"));

        // Edge case: all opening -> remove all
        System.out.println("Edge ((( -> MinRemoved: " + removeInvalidParenthesesMinRemoved("(((")
                + " | PrunedCount: " + removeInvalidParentheses("(((")
                + " | Reverse: " + removeInvalidParenthesesReverse("((("));

        // Edge case: all closing -> remove all
        System.out.println("Edge ))) -> MinRemoved: " + removeInvalidParenthesesMinRemoved(")))")
                + " | PrunedCount: " + removeInvalidParentheses(")))")
                + " | Reverse: " + removeInvalidParenthesesReverse(")))"));

        // Edge case: single valid pair
        System.out.println("Edge () -> MinRemoved: " + removeInvalidParenthesesMinRemoved("()")
                + " | PrunedCount: " + removeInvalidParentheses("()")
                + " | Reverse: " + removeInvalidParenthesesReverse("()"));
    }


    /*
        Approach 1: Backtracking with running minimum removals

        Explore all 2^n subsets of (keep, drop) decisions for each bracket
        in s. Whenever we reach the end of the string AND the built
        expression is balanced (leftCount == rightCount), update the result:

            - If this removal count is strictly less than the current best,
              clear previous results and record this one.
            - If equal, add to the result set (multiple optimal answers).

        Pruning:
            - Only recurse on ')' if rightCount < leftCount (keeps prefix valid).
            - Skip state updates once removedCount exceeds minimumRemoved.

        Uses a StringBuilder for the current expression to avoid O(n) string
        copies at every node.

        Time:  O(2^n) worst case (all brackets)
        Space: O(n) recursion depth + O(2^n) output worst case
    */
    static Set<String> validExpressions1 = new HashSet<>();
    static int minimumRemoved1;

    static List<String> removeInvalidParenthesesMinRemoved(String s) {
        validExpressions1.clear();
        minimumRemoved1 = Integer.MAX_VALUE;
        recurseMinRemoved(s, 0, 0, 0, new StringBuilder(), 0);
        return new ArrayList<>(validExpressions1);
    }

    private static void recurseMinRemoved(String s, int index, int leftCount, int rightCount,
                                          StringBuilder expression, int removedCount) {

        if (index == s.length()) {
            // Valid iff balanced
            if (leftCount == rightCount) {
                if (removedCount <= minimumRemoved1) {
                    String possibleAnswer = expression.toString();

                    // New best -> clear previous answers
                    if (removedCount < minimumRemoved1) {
                        validExpressions1.clear();
                        minimumRemoved1 = removedCount;
                    }
                    validExpressions1.add(possibleAnswer);
                }
            }
            return;
        }

        char currentCharacter = s.charAt(index);
        int length = expression.length();

        // Non-bracket characters are always kept
        if (currentCharacter != '(' && currentCharacter != ')') {
            expression.append(currentCharacter);
            recurseMinRemoved(s, index + 1, leftCount, rightCount, expression, removedCount);
            expression.deleteCharAt(length);
        } else {
            // Branch 1: drop the bracket
            recurseMinRemoved(s, index + 1, leftCount, rightCount, expression, removedCount + 1);

            // Branch 2: keep the bracket (only if it doesn't break prefix balance)
            expression.append(currentCharacter);
            if (currentCharacter == '(') {
                recurseMinRemoved(s, index + 1, leftCount + 1, rightCount, expression, removedCount);
            } else if (rightCount < leftCount) {
                recurseMinRemoved(s, index + 1, leftCount, rightCount + 1, expression, removedCount);
            }
            expression.deleteCharAt(length);
        }
    }


    /*
        Approach 2: Count misplaced brackets first, then DFS with exact removal budget

        Pass 1 (counting):
            Scan left to right tracking `left` = unmatched '('.
            For ')' with left == 0, it's a misplaced close -> increment `right`.
            Otherwise decrement `left`.
            At the end:
                left  = number of unmatched '(' that must be removed.
                right = number of unmatched ')' that must be removed.

        Pass 2 (DFS):
            Explore keep/drop for each bracket, but with a budget:
                - Drop '(' only if leftRem > 0.
                - Drop ')' only if rightRem > 0.
                - Keep ')' only if rightCount < leftCount (prefix balance).
            When we reach the end with leftRem == 0 && rightRem == 0, the
            expression is a valid minimum-removal answer.

        Why this is more efficient than Approach 1:
            Approach 1 explores all subsets and picks the minimum at the end.
            Approach 2 precomputes the exact minimum removal count, so the
            DFS prunes aggressively: no branch is explored that can't hit
            the budget. In practice this is dramatically faster.

        Time:  O(2^n) worst case, but heavily pruned in practice
        Space: O(n) recursion depth + output size
    */
    static Set<String> validExpressions2 = new HashSet<>();

    static List<String> removeInvalidParentheses(String s) {
        validExpressions2.clear();

        int left = 0, right = 0;
        // Pass 1: compute misplaced counts
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else if (s.charAt(i) == ')') {
                if (left == 0) {
                    right++;            // unmatched close -> must remove
                } else {
                    left--;             // matched with earlier '('
                }
            }
        }

        // Pass 2: DFS with the exact budget
        recurseWithBudget(s, 0, 0, 0, left, right, new StringBuilder());
        return new ArrayList<>(validExpressions2);
    }

    private static void recurseWithBudget(String s, int index, int leftCount, int rightCount,
                                          int leftRem, int rightRem, StringBuilder expression) {

        if (index == s.length()) {
            // Only valid if both budgets are exhausted
            if (leftRem == 0 && rightRem == 0) {
                validExpressions2.add(expression.toString());
            }
            return;
        }

        char character = s.charAt(index);
        int length = expression.length();

        // Drop branch (only if we still have budget for this bracket type)
        if ((character == '(' && leftRem > 0) || (character == ')' && rightRem > 0)) {
            recurseWithBudget(s, index + 1, leftCount, rightCount,
                    leftRem - (character == '(' ? 1 : 0),
                    rightRem - (character == ')' ? 1 : 0),
                    expression);
        }

        // Keep branch
        expression.append(character);
        if (character != '(' && character != ')') {
            recurseWithBudget(s, index + 1, leftCount, rightCount, leftRem, rightRem, expression);
        } else if (character == '(') {
            recurseWithBudget(s, index + 1, leftCount + 1, rightCount, leftRem, rightRem, expression);
        } else if (rightCount < leftCount) {
            recurseWithBudget(s, index + 1, leftCount, rightCount + 1, leftRem, rightRem, expression);
        }
        expression.deleteCharAt(length);
    }


    /*
        Approach 3: "Remove first invalid close then recurse on suffix" (recursive prune)

        Elegant recursive formulation. Works in two directions:

        Left-to-right pass (p = ['(', ')']):
            Scan with a balance counter. If balance goes negative, we found
            a misplaced ')' at position k. For every unique ')' at positions
            j in [start, k] (skipping consecutive duplicates to avoid repeated
            work), remove that ')' and recurse on the resulting string
            starting from index k.

            After processing all such ')' choices, return.

            If the whole string is balanced, we then need to also remove any
            misplaced '(' — so we reverse the string and run the same routine
            with p = [')', '('].

        Right-to-left pass (p = [')', '(']):
            Symmetric handling for misplaced '(' — after processing, if the
            string is balanced (no more flips needed), add to the answer.

        The duplicate skipping `(x == j || s.charAt(x - 1) != p[1])` avoids
        exploring the same removal twice when consecutive ')' (or '(') exist.

        Time:  O(2^n) worst case, typically much faster due to pruning
        Space: O(n) recursion depth + output size

        This is the classic "prune and recurse" solution popular in LeetCode
        discussions. It's compact but subtle — read the flow carefully.
    */
    static List<String> removeInvalidParenthesesReverse(String s) {
        List<String> ans = new ArrayList<>();
        remove(s, ans, 0, 0, new char[]{'(', ')'});
        return ans;
    }

    private static void remove(String s, List<String> ans, int i, int j, char[] p) {
        int count = 0;

        for (int k = i; k < s.length(); k++) {
            if (s.charAt(k) == p[0]) count++;
            if (s.charAt(k) == p[1]) count--;

            // Found a misplaced p[1] at position k -> try removing each candidate
            if (count < 0) {
                for (int x = j; x <= k; x++) {
                    if (s.charAt(x) == p[1] &&
                            (x == j || s.charAt(x - 1) != p[1])) {     // skip duplicates

                        remove(s.substring(0, x) + s.substring(x + 1),
                                ans, k, x, p);
                    }
                }
                return;     // stop after handling the first mismatch
            }
        }

        // No misplaced p[1] found -> now check the mirrored direction
        String rev = new StringBuilder(s).reverse().toString();

        if (p[0] == '(') {
            remove(rev, ans, 0, 0, new char[]{')', '('});    // handle misplaced '('
        } else {
            ans.add(rev);   // both passes clean -> valid answer
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let n = s.length().

Approach 1: Backtracking with running minimum removals

Time Complexity: O(2^n)

- Explores keep/drop for every bracket position.
- Pruned only by the "don't break prefix balance" rule.
- Not bounded by the minimum-removal count.

Space Complexity: O(n) recursion depth + O(2^n) worst-case output set


Approach 2: Count misplaced brackets, then DFS with exact budget

Time Complexity: O(2^n) worst case, but heavily pruned in practice

- First pass is O(n) to count misplaced brackets.
- DFS explores at most C(n, leftRem + rightRem) branches, often far less.
- Effectively exponential only in the number of brackets to remove, not
  in the length of the string.

Space Complexity: O(n) recursion depth + output size


Approach 3: Recursive prune (reverse-and-recurse)

Time Complexity: O(2^n) worst case

- Each recursive call scans a substring and, on mismatch, branches over
  candidate removals. Duplicate skipping prevents redundant branches.

Space Complexity: O(n^2) worst case — substring copies at each recursion level.

Key Observation:

The key to efficiency is the DFS budget: instead of exploring all subsets
of keep/drop and finding the minimum at the end, we first compute exactly
how many '(' and ')' MUST be removed (a simple O(n) scan). Then the DFS
only explores branches that can exhaust that budget — this prunes the
search tree dramatically.

Approach 1 is the "clean" backtracking baseline.
Approach 2 is the practical choice for interviews — same idea, better pruning.
Approach 3 is the compact "prune on first mismatch" variant, useful as an
alternative formulation but harder to reason about.

---------------------------------------------------------
*/