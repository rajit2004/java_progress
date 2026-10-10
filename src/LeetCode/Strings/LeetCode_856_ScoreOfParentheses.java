package LeetCode.Strings;

import java.util.*;

public class LeetCode_856_ScoreOfParentheses {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "()" -> 1
        System.out.println("Sample 1 () -> BitShift: " + scoreOfParenthesesBitShift("()")
                + " | Stack: " + scoreOfParentheses("()")
                + " | Recursive: " + scoreOfParenthesesRecursive("()")
                + " (expected: 1)");

        // Sample 2 (LeetCode): "(())" -> 2 (double of inner "()" = 2*1)
        System.out.println("Sample 2 (()) -> BitShift: " + scoreOfParenthesesBitShift("(())")
                + " | Stack: " + scoreOfParentheses("(())")
                + " | Recursive: " + scoreOfParenthesesRecursive("(())")
                + " (expected: 2)");

        // Sample 3 (LeetCode): "()()" -> 1 + 1 = 2
        System.out.println("Sample 3 ()() -> BitShift: " + scoreOfParenthesesBitShift("()()")
                + " | Stack: " + scoreOfParentheses("()()")
                + " | Recursive: " + scoreOfParenthesesRecursive("()()")
                + " (expected: 2)");

        // Sample 4 (LeetCode): "(()(()))" -> 6 (inner: 1 + 2 = 3, doubled = 6)
        System.out.println("Sample 4 (()(())) -> BitShift: " + scoreOfParenthesesBitShift("(()(()))")
                + " | Stack: " + scoreOfParentheses("(()(()))")
                + " | Recursive: " + scoreOfParenthesesRecursive("(()(()))")
                + " (expected: 6)");

        // Edge case: empty string -> 0
        System.out.println("Edge (empty) -> BitShift: " + scoreOfParenthesesBitShift("")
                + " | Stack: " + scoreOfParentheses("")
                + " | Recursive: " + scoreOfParenthesesRecursive("")
                + " (expected: 0)");

        // Edge case: deeply nested 4 levels -> 2^3 = 8
        System.out.println("Edge (((()))) -> BitShift: " + scoreOfParenthesesBitShift("(((())))")
                + " | Stack: " + scoreOfParentheses("(((())))")
                + " | Recursive: " + scoreOfParenthesesRecursive("(((())))")
                + " (expected: 8)");

        // Edge case: many siblings
        //   "()()()()()" -> 5
        System.out.println("Edge ()()()()() -> BitShift: " + scoreOfParenthesesBitShift("()()()()()")
                + " | Stack: " + scoreOfParentheses("()()()()()")
                + " | Recursive: " + scoreOfParenthesesRecursive("()()()()()")
                + " (expected: 5)");

        // Edge case: nested with siblings inside
        //   "(()())" -> 2*(1+1) = 4
        System.out.println("Edge (()()) -> BitShift: " + scoreOfParenthesesBitShift("(()())")
                + " | Stack: " + scoreOfParentheses("(()())")
                + " | Recursive: " + scoreOfParenthesesRecursive("(()())")
                + " (expected: 4)");
    }


    /*
        Approach 1: Bit shift with depth tracking (optimal, O(1) space)

        Observation:
            The score of a primitive "()" at depth `d` is 2^d, where `d` is
            the number of unmatched '(' currently open (after popping the
            matching '(').

        Why:
            Each level of nesting doubles the score. A "()" at depth 0 (i.e.
            at the top level) contributes 1. Wrapping it in one pair gives
            2^1 = 2. Wrapping again gives 2^2 = 4, etc.

        So we scan once, maintaining `bal` = current depth. Whenever we see
        ')' preceded by '(', we've found a primitive leaf; add 2^bal to the
        answer. The `1 << bal` computes 2^bal using bit shift.

        Proof that only leaves contribute:
            Every score is built from leaves (base "()" units), and the
            total score is the sum over all leaves of (2 ^ depth of leaf).
            Inner "()" pairs are just wrappers that don't add score on
            their own — their score is entirely determined by their children.

        Example trace on "(()(()))":
            i=0 '(' -> bal=1
            i=1 '(' -> bal=2
            i=2 ')' with prev '(' -> ans += 1 << 1 = 2, bal=1
            i=3 '(' -> bal=2
            i=4 '(' -> bal=3
            i=5 ')' with prev '(' -> ans += 1 << 2 = 4, bal=2
            i=6 ')' -> bal=1
            i=7 ')' -> bal=0
            ans = 2 + 4 = 6 ✓

        Time:  O(n)
        Space: O(1)
    */
    static int scoreOfParenthesesBitShift(String S) {
        int ans = 0;
        int bal = 0;

        for (int i = 0; i < S.length(); ++i) {
            if (S.charAt(i) == '(') {
                bal++;
            } else {
                bal--;
                // A leaf "()" at depth `bal` contributes 2^bal
                if (S.charAt(i - 1) == '(') {
                    ans += 1 << bal;
                }
            }
        }

        return ans;
    }


    /*
        Approach 2: Stack of frame scores

        Maintain a stack where each entry is the accumulated score of the
        current nesting frame. Push 0 when entering a new '('; on ')':

            v = stack.pop()      // score of the just-closed frame
            w = stack.pop()      // score of its parent frame (on top of stack)
            stack.push(w + max(2 * v, 1))

        The `max(2*v, 1)` handles two cases:
            - v == 0 means this was a leaf "()" -> contributes 1.
            - v > 0 means we closed a nested group -> contributes 2*v.

        Stack starts with [0] representing the outermost frame.

        Example trace on "(()(()))":
            '('  -> stack = [0, 0]
            '('  -> stack = [0, 0, 0]
            ')'  -> v=0, w=0 -> push 0 + 1 = 1 -> stack = [0, 1]
            '('  -> stack = [0, 1, 0]
            '('  -> stack = [0, 1, 0, 0]
            ')'  -> v=0, w=0 -> push 1 -> stack = [0, 1, 1]
            ')'  -> v=1, w=1 -> push 1 + 2 = 3 -> stack = [0, 3]
            ')'  -> v=3, w=0 -> push 0 + 6 = 6 -> stack = [6]

        Time:  O(n)
        Space: O(n) — stack depth up to n/2
    */
    static int scoreOfParentheses(String S) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);      // outermost frame has score 0

        for (char c : S.toCharArray()) {
            if (c == '(') {
                stack.push(0);              // start a new frame
            } else {
                int v = stack.pop();        // score of just-closed frame
                int w = stack.pop();        // score of parent frame
                stack.push(w + Math.max(2 * v, 1));
            }
        }

        return stack.pop();
    }


    /*
        Approach 3: Recursive divide-and-conquer

        F(S, i, j) computes the score of S[i..j-1].

        Split the range into top-level primitives by tracking balance:
            - balance returns to 0 -> a primitive ends at index k.
            - If the primitive is exactly "()" (length 2), it contributes 1.
            - Otherwise it's "( ... )" wrapping an inner expression, which
              contributes 2 * F(inner).

        After processing a primitive, move the start pointer i to k + 1 and
        continue looking for the next primitive.

        Example trace on "(()(()))":
            Top-level primitives: just one: "(()(()))"
                Not length 2, so score = 2 * F("()(())")   [inner content]
                F("()(())") splits into "()" and "(())":
                    "()"   -> 1
                    "(())" -> 2 * F("()") = 2 * 1 = 2
                Total inner = 1 + 2 = 3, so outer score = 2 * 3 = 6 ✓

        Time:  O(n) — each character visited once across all recursive calls
        Space: O(n) — recursion depth up to n/2 in worst case
    */
    static int scoreOfParenthesesRecursive(String S) {
        return F(S, 0, S.length());
    }

    private static int F(String S, int i, int j) {
        int ans = 0;
        int bal = 0;

        // Split S[i..j-1] into primitives by tracking balance
        for (int k = i; k < j; ++k) {
            bal += S.charAt(k) == '(' ? 1 : -1;

            if (bal == 0) {
                // Found a top-level primitive S[i..k]
                if (k - i == 1) {
                    ans++;              // it's "()", contributes 1
                } else {
                    ans += 2 * F(S, i + 1, k);   // "( ... )", double inner
                }
                // Move start pointer for the next primitive
                i = k + 1;
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let n = S.length().

Approach 1: Bit shift with depth tracking

Time Complexity: O(n)
- Single pass, O(1) work per character.

Space Complexity: O(1)
- Two integer counters. Best in class.


Approach 2: Stack of frame scores

Time Complexity: O(n)
- Single pass, O(1) amortized work per character.

Space Complexity: O(n)
- Stack depth up to n/2 in the worst case (fully nested input).


Approach 3: Recursive divide-and-conquer

Time Complexity: O(n)
- Each character is processed exactly once across all recursive calls
  (the splitting pass consumes each level's characters once).

Space Complexity: O(n)
- Recursion depth up to n/2 for fully nested input; each call's stack frame
  is O(1).

Key Observation:

The problem reduces to summing 2^d over every leaf "()" in the parse tree,
where d is the depth of that leaf. All three approaches compute this same
value:
    - BitShift directly computes 2^d for each leaf via `1 << d`.
    - Stack accumulates the sum bottom-up frame by frame.
    - Recursive splits the parse tree top-down.

BitShift is the cleanest and most efficient; Stack is the most intuitive
for those who prefer explicit state; Recursive mirrors the grammar most
directly but has the same complexity as Stack.

Prefer BitShift in interviews — short, provably correct, O(1) space.

---------------------------------------------------------
*/