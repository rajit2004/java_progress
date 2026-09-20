package LeetCode.Strings;

public class LeetCode_3948_ReverseDegreeOfString {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "abc"
        //   a -> 26, b -> 25, c -> 24
        //   26*1 + 25*2 + 24*3 = 26 + 50 + 72 = 148
        System.out.println("Sample 1 (abc) -> " + reverseDegree("abc") + " (expected: 148)");

        // Sample 2 (LeetCode): "z" -> reverse degree of 'z' is 1, position 1
        //   1*1 = 1
        System.out.println("Sample 2 (z) -> " + reverseDegree("z") + " (expected: 1)");

        // Sample 3 (LeetCode): "za" -> 1*1 + 26*2 = 53
        System.out.println("Sample 3 (za) -> " + reverseDegree("za") + " (expected: 53)");

        // Edge case: single 'a' -> 26*1 = 26
        System.out.println("Edge (a) -> " + reverseDegree("a") + " (expected: 26)");

        // Edge case: all same letter -> arithmetic series
        //   "aaa" -> 26*(1+2+3) = 26*6 = 156
        System.out.println("Edge (aaa) -> " + reverseDegree("aaa") + " (expected: 156)");

        // Edge case: empty string -> no contribution -> 0
        System.out.println("Edge (empty) -> " + reverseDegree("") + " (expected: 0)");

        // Edge case: full reverse order — letters z..a each at their natural position
        //   Each char c has reverse degree 1..26 and position 1..26 → 1*1 + 2*2 + ... + 26*26
        //   = sum(i^2 for i in 1..26) = 26*27*53/6 = 6201
        System.out.println("Edge (zyx...a) -> " + reverseDegree("zyxwvutsrqponmlkjihgfedcba")
                + " (expected: 6201)");
    }


    /*
        Approach: Single-pass accumulation

        Problem restated:
        For each character c at 1-based position i in s, compute its
        "reverse degree" = 26 - (c - 'a'), i.e.
            'a' -> 26, 'b' -> 25, ..., 'z' -> 1.
        Then sum reverseDegree(c) * i over all positions.

        Implementation:
        - Loop i from 1 to n (1-based position).
        - For each character s.charAt(i - 1), compute its reverse degree as
              26 - (s.charAt(i - 1) - 'a')
        - Multiply by position i, add to answer.

        Using the 1-based index directly avoids an extra (i + 1) adjustment
        inside the loop body — a small but clean readability win.

        Time:  O(n)
        Space: O(1)
    */
    static int reverseDegree(String s) {
        int ans = 0;

        // i is the 1-based position of s.charAt(i - 1)
        for (int i = 1; i <= s.length(); i++) {
            // Reverse degree of s[i-1]: 'a' -> 26, 'b' -> 25, ..., 'z' -> 1
            ans += (26 - (s.charAt(i - 1) - 'a')) * i;
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Single-pass accumulation

Time Complexity: O(n)

- One pass over the string, constant work per character.

Space Complexity: O(1)

- Only an int accumulator; no auxiliary data structures.

Key Observation:
The reverse degree of a character is just (26 - its 0-based alphabet index),
so the whole computation reduces to a single weighted-sum pass over the
string, with the position acting as the weight.

---------------------------------------------------------
*/