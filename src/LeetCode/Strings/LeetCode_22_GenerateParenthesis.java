package LeetCode.Strings;

import java.util.*;

public class LeetCode_22_GenerateParenthesis {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): n = 3 -> ["((()))","(()())","(())()","()(())","()()()"]
        System.out.println("n = 1 -> " + generateParenthesis(1));
        System.out.println("n = 2 -> " + generateParenthesis(2));
        System.out.println("n = 3 -> " + generateParenthesis(3));

        // Cross-check all three approaches on small n
        for (int n = 1; n <= 5; n++) {
            Set<String> a = new HashSet<>(generateParenthesis(n));
            Set<String> b = new HashSet<>(generateParenthesisBitset(n));
            Set<String> c = new HashSet<>(generateParenthesisDFS(n));
            System.out.println("n = " + n
                    + " | Backtrack size: " + a.size()
                    + " | Bitmask size: " + b.size()
                    + " | DFS size: " + c.size()
                    + " | All agree: " + (a.equals(b) && b.equals(c)));
        }

        // Edge case: n = 0 -> only the empty string is valid
        System.out.println("Edge (n = 0) -> " + generateParenthesis(0)
                + " (expected: [])");
    }


    /*
        Approach 1: Backtracking (canonical)

        At every step we have two choices:
            1. Add '('
            2. Add ')'

        Rules:
            1. We can add '(' only if we have not used all n opening brackets.
            2. We can add ')' only if there are more opening brackets than
               closing brackets (close < open).

        Continue building until the string reaches length 2 * n. Every valid
        string built this way is added to the result.

        Why it works:
            Rule 1 prevents exceeding n pairs.
            Rule 2 ensures we never close more than we open — i.e. the
            prefix-balance is never negative at any point.
            Together, they force the final string (length 2n) to be balanced.

        current -> current parenthesis string being built
        open    -> number of '(' used so far
        close   -> number of ')' used so far

        Time:  O(4^n / sqrt(n)) — proportional to the nth Catalan number
        Space: O(n) recursion depth + O(4^n / sqrt(n)) for the output
    */
    static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        // Start with an empty string.
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    static void backtrack(List<String> result,
                          StringBuilder current,
                          int open,
                          int close,
                          int n) {

        // Base case: string has 2n characters -> a valid combination is formed
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }

        // Add '(' if we still have opening brackets remaining (max = n)
        if (open < n) {
            current.append('(');
            backtrack(result, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1);   // undo
        }

        // Add ')' only if it keeps the string valid (close < open)
        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1);   // undo
        }
    }


    /*
        Approach 2: Bitmask enumeration (Gosper's hack)

        Enumerate all n-bit masks using Gosper's hack (next combination of
        k bits in lexicographic order). Each mask selects which positions
        receive '(' (1) and which receive ')' (0).

        For each mask, walk through positions and simulate the balance:
            bal += 1 if bit is 1 (open), bal -= 1 if bit is 0 (close)
            If bal ever goes negative, discard the mask.
            If bal ends at 0 (and length matches), the string is valid.

        This is an iterative alternative to backtracking. It's a bit clever
        but harder to read, and the constant factor is usually worse than
        backtracking. Still O(Catalan(n)) output size.

        Time:  O(C(2n, n) * n) — enumerates binomial(2n, n) candidates,
               filters each in O(n)
        Space: O(4^n / sqrt(n)) for the output
    */
    static final char OP = '(';

    static List<String> generateParenthesisBitset(int n) {
        if (n == 0) return new ArrayList<>();
        if (n == 1) return List.of("()");

        List<String> res = new ArrayList<>();
        int half = n - 1;                   // number of bits to choose (excluding first '(')
        int sz = half << 1;                 // remaining slots after first '('
        int mask = (1 << half) - 1;         // smallest mask with `half` bits set

        while (mask < (1 << sz)) {
            StringBuilder sb = new StringBuilder();
            sb.append('(');
            int bal = 1;

            for (int i = 0; i < sz; i++) {
                int b = (mask >> i) & 1;
                bal += 1 - (b << 1);        // b=1 -> +1, b=0 -> -1

                if (bal < 0) break;
                sb.append((char) (OP | b)); // b=1 -> '(', b=0 -> ')'
            }

            if (sb.length() == sz + 1 && bal == 0)
                res.add(sb.toString());

            // Gosper's hack: next integer with the same popcount
            int c = mask & -mask;
            int r = mask + c;
            mask = (((r ^ mask) >> 2) / c) | r;
        }

        return res;
    }


    /*
        Approach 3: DFS with open/close countdown

        Alternative formulation: pass remaining counts of open and close
        brackets, starting with n of each. The first '(' is pre-placed
        (since every valid string must start with '(').

        Rules at each step:
            - If open remaining > 0, place '(' and recurse.
            - If close remaining >= open remaining (i.e. we have at least
              as many ')' as '(' left), place ')' and recurse.

        When both counts hit 0, append the closing ')' and record the string.

        Equivalent to Approach 1, just a different state representation
        (remaining instead of used). Pre-placing the first '(' saves one
        level of branching and enforces the "must start with '('" invariant.

        Time:  O(4^n / sqrt(n))
        Space: O(n) recursion depth + O(4^n / sqrt(n)) for the output
    */
    static List<String> generateParenthesisDFS(int n) {
        List<String> res = new ArrayList<>();
        if (n == 0) return res;

        // Pre-place the mandatory first '(' and start with n-1 of each left
        dfs(res, n - 1, n - 1, new StringBuilder("("));
        return res;
    }

    private static void dfs(List<String> res, int O, int C, StringBuilder s) {
        // Base case: no more brackets to place -> close the final ')'
        if (O == 0 && C == 0) {
            res.add(s.toString() + ")");
            return;
        }

        if (O > 0) {
            s.append('(');
            dfs(res, O - 1, C, s);
            s.deleteCharAt(s.length() - 1);
        }

        // Only place ')' if at least as many ')' remain as '(' remain
        if (C >= O) {
            s.append(')');
            dfs(res, O, C - 1, s);
            s.deleteCharAt(s.length() - 1);
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let n = number of parenthesis pairs.

Time Complexity: O(4^n / sqrt(n))

- The number of valid parenthesis combinations is the nth Catalan number.
- Catalan(n) ≈ 4^n / (n^(3/2)).
- Generating each valid combination requires building a string of length 2n.
- Overall complexity is commonly written as: O(4^n / sqrt(n)).

Space Complexity: O(4^n / sqrt(n))

- The result list stores all valid combinations.
- The recursion depth is at most 2n, which is O(n).
- The output itself dominates the space usage.

Key Observation:

A parenthesis string is valid only if:
    1. Opening brackets never exceed n.
    2. Closing brackets never exceed opening brackets.

By enforcing these rules during construction, invalid strings are never
generated. This is the strength of backtracking — build only valid
candidates instead of generating every possible string.

The bitmask (Gosper's hack) variant enumerates all binomial(2n, n)
candidates and filters them, so it does asymptotically more work per
valid answer, but is still bounded by the Catalan number of outputs.

The DFS variant is a reformulation of Approach 1 using remaining counts
instead of used counts, with the first '(' pre-placed. It produces the
same result set with the same complexity but a slightly smaller
recursion tree.

---------------------------------------------------------
*/