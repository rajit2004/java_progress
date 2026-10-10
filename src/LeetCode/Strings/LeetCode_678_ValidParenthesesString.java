package LeetCode.Strings;

import java.util.*;

public class LeetCode_678_ValidParenthesesString {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "()" -> true
        System.out.println("Sample 1 () -> TwoPass: " + checkValidStringTwoPass("()")
                + " | TwoStacks: " + checkValidString("()")
                + " | DP: " + checkValidStringDP("()")
                + " | Memo: " + checkValidStringMemo("()")
                + " (expected: true)");

        // Sample 2 (LeetCode): "(*)" -> true (* can be empty, '(' or ')')
        System.out.println("Sample 2 (*) -> TwoPass: " + checkValidStringTwoPass("(*)")
                + " | TwoStacks: " + checkValidString("(*)")
                + " | DP: " + checkValidStringDP("(*)")
                + " | Memo: " + checkValidStringMemo("(*)")
                + " (expected: true)");

        // Sample 3 (LeetCode): "(*))" -> true (* can be '(' → "(())")
        System.out.println("Sample 3 (*)) -> TwoPass: " + checkValidStringTwoPass("(*))")
                + " | TwoStacks: " + checkValidString("(*))")
                + " | DP: " + checkValidStringDP("(*))")
                + " | Memo: " + checkValidStringMemo("(*))")
                + " (expected: true)");

        // Edge case: empty string -> trivially valid
        System.out.println("Edge (empty) -> TwoPass: " + checkValidStringTwoPass("")
                + " | TwoStacks: " + checkValidString("")
                + " | DP: " + checkValidStringDP("")
                + " | Memo: " + checkValidStringMemo("")
                + " (expected: true)");

        // Edge case: only open bracket -> cannot be closed
        System.out.println("Edge (single '(') -> TwoPass: " + checkValidStringTwoPass("(")
                + " | TwoStacks: " + checkValidString("(")
                + " | DP: " + checkValidStringDP("(")
                + " | Memo: " + checkValidStringMemo("(")
                + " (expected: false)");

        // Edge case: only close bracket -> cannot be opened
        System.out.println("Edge (single ')') -> TwoPass: " + checkValidStringTwoPass(")")
                + " | TwoStacks: " + checkValidString(")")
                + " | DP: " + checkValidStringDP(")")
                + " | Memo: " + checkValidStringMemo(")")
                + " (expected: false)");

        // Edge case: single asterisk -> treat as empty
        System.out.println("Edge (single '*') -> TwoPass: " + checkValidStringTwoPass("*")
                + " | TwoStacks: " + checkValidString("*")
                + " | DP: " + checkValidStringDP("*")
                + " | Memo: " + checkValidStringMemo("*")
                + " (expected: true)");

        // Edge case: all asterisks -> all empty
        System.out.println("Edge (***) -> TwoPass: " + checkValidStringTwoPass("***")
                + " | TwoStacks: " + checkValidString("***")
                + " | DP: " + checkValidStringDP("***")
                + " | Memo: " + checkValidStringMemo("***")
                + " (expected: true)");

        // Edge case: too many opens even with '*' as ')'
        System.out.println("Edge (((*) -> TwoPass: " + checkValidStringTwoPass("(((*")
                + " | TwoStacks: " + checkValidString("(((*")
                + " | DP: " + checkValidStringDP("(((*")
                + " | Memo: " + checkValidStringMemo("(((*")
                + " (expected: false)");

        // Edge case: asterisk must fill the gap -> "(**)" = "(())" or "()()"
        System.out.println("Edge ((**)) -> TwoPass: " + checkValidStringTwoPass("((**))")
                + " | TwoStacks: " + checkValidString("((**))")
                + " | DP: " + checkValidStringDP("((**))")
                + " | Memo: " + checkValidStringMemo("((**))")
                + " (expected: true)");
    }


    /*
        Approach 1: Two-pass counter (greedy, O(1) space)

        A string is valid iff BOTH of these hold:
            (a) Scanning left-to-right, treating '(' and '*' as +1 and ')' as -1,
                the running count never goes negative.
                → guarantees: at every prefix, #')' <= #'(' + #'*'
            (b) Scanning right-to-left, treating ')' and '*' as +1 and '(' as -1,
                the running count never goes negative.
                → guarantees: at every suffix, #'(' <= #')' + #'*'

        Why these two together imply validity:
            (a) ensures close brackets can always be paired with an earlier
                open or wildcard.
            (b) ensures open brackets can always be paired with a later
                close or wildcard.
            Together, they mean no bracket is "stranded", so a valid
            assignment of '*' to '(', ')', or '' exists.

        Time:  O(n)
        Space: O(1)
    */
    static boolean checkValidStringTwoPass(String s) {
        int openCount = 0;
        int closeCount = 0;
        int last = s.length() - 1;

        // Traverse from both ends simultaneously
        for (int i = 0; i <= last; i++) {
            // Forward pass: '(' or '*' increases, ')' decreases
            if (s.charAt(i) == '(' || s.charAt(i) == '*') {
                openCount++;
            } else {
                openCount--;
            }

            // Backward pass: ')' or '*' increases, '(' decreases
            if (s.charAt(last - i) == ')' || s.charAt(last - i) == '*') {
                closeCount++;
            } else {
                closeCount--;
            }

            // If either pass goes negative, some bracket is stranded
            if (openCount < 0 || closeCount < 0) {
                return false;
            }
        }

        return true;
    }


    /*
        Approach 2: Two stacks (indices of '(' and '*')

        We track positions of unmatched '(' and '*' with two stacks.

        Scan left to right:
            - '(' : push index onto openBrackets
            - '*' : push index onto asterisks
            - ')' :
                  * If openBrackets is non-empty, pop from it (prefer '('
                    over '*' because '(' must pair with a LATER ')' anyway).
                  * Else if asterisks is non-empty, pop from it (treat '*' as '(').
                  * Else return false (nothing to close with).

        After scanning, some '(' and '*' may remain unmatched. We must try
        to pair them: an '*' can close an earlier '('. So pop both stacks
        in tandem:
            - If the '(' index is GREATER than the '*' index, the '*' comes
              before the '(' and cannot close it → return false.
            - Otherwise, the '*' closes the '(' → continue.

        Finally, return true only if no '(' remain unmatched.

        Time:  O(n)
        Space: O(n) — two stacks up to size n
    */
    static boolean checkValidString(String s) {
        Deque<Integer> openBrackets = new ArrayDeque<>();
        Deque<Integer> asterisks = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                openBrackets.push(i);
            } else if (ch == '*') {
                asterisks.push(i);
            } else {
                // ')' : prefer pairing with a real '(' over a '*'
                if (!openBrackets.isEmpty()) {
                    openBrackets.pop();
                } else if (!asterisks.isEmpty()) {
                    asterisks.pop();    // treat '*' as '('
                } else {
                    return false;       // nothing to close with
                }
            }
        }

        // Try to close remaining '(' with remaining '*'
        while (!openBrackets.isEmpty() && !asterisks.isEmpty()) {
            // '*' must come AFTER '(' to act as a closing bracket
            if (openBrackets.pop() > asterisks.pop()) {
                return false;
            }
        }

        // Any unmatched '(' means invalid
        return openBrackets.isEmpty();
    }


    /*
        Approach 3: Bottom-up DP

        dp[i][j] = true iff the substring s[i..n-1] is valid when we start
                   with j currently-unmatched open brackets.

        Base case: dp[n][0] = true  — empty suffix + 0 open brackets.
                   (dp[n][j>0] = false — leftover opens can't be resolved.)

        Transitions for index i and openBracket j:
            s[i] == '*' :  dp[i][j] = dp[i+1][j+1]         // '*' as '('
                                    | (j>0 && dp[i+1][j-1]) // '*' as ')'
                                    | dp[i+1][j]           // '*' as empty
            s[i] == '(' :  dp[i][j] = dp[i+1][j+1]
            s[i] == ')' :  dp[i][j] = (j>0) && dp[i+1][j-1]

        Answer: dp[0][0].

        Time:  O(n^2)
        Space: O(n^2)
    */
    static boolean checkValidStringDP(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n + 1][n + 1];

        // Base: empty suffix with 0 open brackets is valid
        dp[n][0] = true;

        for (int index = n - 1; index >= 0; index--) {
            for (int openBracket = 0; openBracket < n; openBracket++) {
                boolean isValid = false;

                if (s.charAt(index) == '*') {
                    // Try '*' as '('
                    isValid |= dp[index + 1][openBracket + 1];
                    // Try '*' as ')' (only if we have an open to close)
                    if (openBracket > 0) {
                        isValid |= dp[index + 1][openBracket - 1];
                    }
                    // Try '*' as empty
                    isValid |= dp[index + 1][openBracket];
                } else if (s.charAt(index) == '(') {
                    isValid |= dp[index + 1][openBracket + 1];
                } else if (openBracket > 0) {
                    isValid |= dp[index + 1][openBracket - 1];
                }

                dp[index][openBracket] = isValid;
            }
        }

        return dp[0][0];
    }


    /*
        Approach 4: Top-down memoization

        Same DP recurrence as Approach 3, but computed recursively with a
        memo table. Often easier to reason about because the recursion
        mirrors the three choices for '*'.

        memo[i][j] stores:
             1 -> known valid
             0 -> known invalid
            -1 -> not computed yet

        Time:  O(n^2)
        Space: O(n^2) for memo + O(n) recursion stack
    */
    static boolean checkValidStringMemo(String s) {
        int n = s.length();
        int[][] memo = new int[n + 1][n + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return memoDfs(0, 0, s, memo);
    }

    private static boolean memoDfs(int index, int openCount, String str, int[][] memo) {
        // Base case: reached end — valid iff no unmatched open brackets
        if (index == str.length()) {
            return openCount == 0;
        }

        // Memoized result
        if (memo[index][openCount] != -1) {
            return memo[index][openCount] == 1;
        }

        boolean isValid = false;

        if (str.charAt(index) == '*') {
            // Treat '*' as '('
            isValid |= memoDfs(index + 1, openCount + 1, str, memo);
            // Treat '*' as ')' (only if there's something to close)
            if (openCount > 0) {
                isValid |= memoDfs(index + 1, openCount - 1, str, memo);
            }
            // Treat '*' as empty
            isValid |= memoDfs(index + 1, openCount, str, memo);
        } else if (str.charAt(index) == '(') {
            isValid = memoDfs(index + 1, openCount + 1, str, memo);
        } else if (openCount > 0) {
            isValid = memoDfs(index + 1, openCount - 1, str, memo);
        }

        // Memoize and return
        memo[index][openCount] = isValid ? 1 : 0;
        return isValid;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let n = s.length().

Approach 1: Two-pass counter (greedy)

Time Complexity: O(n)

- Two simultaneous scans (front and back) in a single loop.

Space Complexity: O(1)

- Only two counter variables. This is the optimal-space solution.


Approach 2: Two stacks

Time Complexity: O(n)

- One linear scan plus a final pairing loop over the stacks.

Space Complexity: O(n)

- Two stacks holding up to n indices total.


Approach 3: Bottom-up DP

Time Complexity: O(n^2)

- States: (index, openBracket) pairs → O(n^2) states, O(1) per state.

Space Complexity: O(n^2)

- dp table of size (n+1) x (n+1).


Approach 4: Top-down memoization

Time Complexity: O(n^2)

- Same state space as Approach 3, memoized.

Space Complexity: O(n^2)

- memo table plus O(n) recursion depth.

Key Observation:

'*' has three possible meanings ('(', ')', or ''), so validity is a
reachability question over a state (index, openBracket). The DP solutions
model this explicitly. The stack approach greedily matches at each step
and then verifies leftover opens against leftover wildcards. The two-pass
counter compresses the same reasoning into two linear scans by exploiting
two necessary-and-sufficient conditions:
    (a) no prefix has more ')' than '(' + '*',
    (b) no suffix has more '(' than ')' + '*'.
Together (a) and (b) guarantee a valid '*' assignment exists.

Prefer Approach 1 (O(1) space, simplest) in interviews unless asked for
alternative formulations.

---------------------------------------------------------
*/