package LeetCode.Strings;

public class LeetCode_1111_MaximumNestingDepthOfTwoValidParenthesesStrings {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): seq = "(()())"
        //   Output assigns chars to two groups so each group's max depth <= 1.
        //   One valid answer: [0,1,1,1,1,0]
        System.out.println("Sample 1 -> IndexParity: " + java.util.Arrays.toString(maxDepthAfterSplitIndexParity("(()())"))
                + " | DepthParity: " + java.util.Arrays.toString(maxDepthAfterSplit("(()())")));

        // Sample 2 (LeetCode): seq = "()(())()"
        //   One valid answer: [0,0,0,1,1,0,0,0]
        System.out.println("Sample 2 -> IndexParity: " + java.util.Arrays.toString(maxDepthAfterSplitIndexParity("()(())()"))
                + " | DepthParity: " + java.util.Arrays.toString(maxDepthAfterSplit("()(())()")));

        // Sample 3 (LeetCode): seq = "(((())))"
        //   Original depth = 4, split puts alternating brackets into groups 0/1 -> max depth 2 each.
        System.out.println("Sample 3 -> IndexParity: " + java.util.Arrays.toString(maxDepthAfterSplitIndexParity("(((())))"))
                + " | DepthParity: " + java.util.Arrays.toString(maxDepthAfterSplit("(((())))")));

        // Edge case: empty string -> empty array
        System.out.println("Edge (empty) -> IndexParity: " + java.util.Arrays.toString(maxDepthAfterSplitIndexParity(""))
                + " | DepthParity: " + java.util.Arrays.toString(maxDepthAfterSplit("")));

        // Edge case: single pair "()"
        System.out.println("Edge (single pair) -> IndexParity: " + java.util.Arrays.toString(maxDepthAfterSplitIndexParity("()"))
                + " | DepthParity: " + java.util.Arrays.toString(maxDepthAfterSplit("()")));

        // Edge case: fully nested short "((( )))" — should still produce max depth 2 per group
        System.out.println("Edge (nested) -> IndexParity: " + java.util.Arrays.toString(maxDepthAfterSplitIndexParity("((()))"))
                + " | DepthParity: " + java.util.Arrays.toString(maxDepthAfterSplit("((()))")));

        // Edge case: alternating non-nested "()()()"
        System.out.println("Edge (flat) -> IndexParity: " + java.util.Arrays.toString(maxDepthAfterSplitIndexParity("()()()"))
                + " | DepthParity: " + java.util.Arrays.toString(maxDepthAfterSplit("()()()")));
    }


    /*
        Approach 1: Index-parity based assignment

        Observation: In any valid parentheses string, characters at ODD indices
        are always ')' and characters at EVEN indices are always '('.
        (Proof sketch: valid strings have even length; scanning left to right,
        each '(' advances position by 1 and flips the parity context, so
        nesting structure is rigid with respect to index parity.)

        So we can assign group = (i & 1) XOR (whether char is '(').

        Effect:
            - '(' at even index -> group 0
            - '(' at odd index  -> group 1
            - ')' at even index -> group 1
            - ')' at odd index  -> group 0

        Each group ends up with balanced parentheses, and its maximum depth
        is at most ceil(original_max_depth / 2). This satisfies the problem
        requirement (minimize the maximum depth across the two subsequences).

        Time:  O(n)
        Space: O(n) for the output array

        Compact form: ans[i] = (i & 1) ^ (seq.charAt(i) == '(' ? 1 : 0);
    */
    static int[] maxDepthAfterSplitIndexParity(String seq) {
        int length = seq.length();
        int[] ans = new int[length];

        for (int i = 0; i < length; ++i) {
            // XOR: '( ' flips group by index parity, ')' keeps index parity
            ans[i] = (i & 1) ^ (seq.charAt(i) == '(' ? 1 : 0);
        }

        return ans;
    }


    /*
        Approach 2: Running-depth parity assignment (cleaner)

        Track the current nesting depth `d` while scanning.
            - On '(': increment d, assign group = d % 2.
            - On ')': assign group = d % 2, then decrement d.

        Why this works:
            Each group receives every other level of nesting. When depth is
            odd, we're in group 1; when even, in group 0. So consecutive
            nested brackets alternate between the two groups — meaning each
            group only contains nesting depths 1, 3, 5, ... OR 2, 4, 6, ...
            After halving (as required by the problem), both groups have
            maximum depth <= ceil(maxDepth / 2), which is optimal.

        Both approaches produce a valid (and optimal in terms of the maximum
        depth) answer. The problem accepts ANY valid split, so exact arrays
        may differ between the two approaches while both being correct.

        Time:  O(n)
        Space: O(n) for the output array
    */
    static int[] maxDepthAfterSplit(String seq) {
        int d = 0;                          // current nesting depth
        int length = seq.length();
        int[] ans = new int[length];

        for (int i = 0; i < length; i++) {
            if (seq.charAt(i) == '(') {
                ++d;
                ans[i] = d % 2;             // assign group based on new depth
            } else {
                ans[i] = d % 2;             // assign group based on current depth
                --d;
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Both approaches

Time Complexity: O(n)

- Single pass over the input string, O(1) work per character.

Space Complexity: O(n)

- Output array `ans` of length n.
- All other state is O(1) (index, depth counter).

Key Observation:
Because every valid parentheses string has a rigid left-to-right structure,
we can decouple nested brackets into two independent valid subsequences by
assigning them alternately. Two natural "alternation" signals work:
    - Index parity (Approach 1) — exploits the structure of valid strings.
    - Depth parity (Approach 2) — directly targets the "every other nesting
      level" split, which is easier to reason about correctness for.

Both achieve the theoretical optimum: max depth per group is
ceil(original_max_depth / 2).

---------------------------------------------------------
*/