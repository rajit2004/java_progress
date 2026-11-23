package LeetCode.Strings;

public class LeetCode_3863_MinOperationsToSortAString {

    public static void main(String[] args) {
        System.out.println(minOperationsCaseAnalysis("acb") + " " + minOperationsIndexBased("acb"));
        System.out.println(minOperationsCaseAnalysis("bca") + " " + minOperationsIndexBased("bca"));
        System.out.println(minOperationsCaseAnalysis("cba") + " " + minOperationsIndexBased("cba"));
    }

    /* Approach 1: Single-pass case analysis
       Walk the string once to track the smallest character (mn), the largest
       character (mx), and whether the string is already non-decreasing. Then:
         - already sorted            -> 0
         - length 2 and unsorted     -> -1 (no interior character to move)
         - first char is mn OR last char is mx -> 1
         - mn or mx appears anywhere strictly inside (index 1..n-2) -> 2
         - otherwise                 -> 3
       Example: "acb" -> first == mn -> 1
                "bca" -> mx 'c' inside -> 2
                "cba" -> neither endpoint matches, neither mn/mx inside -> 3
    */
    static int minOperationsCaseAnalysis(String s) {
        int n = s.length();
        char mn = 'z', mx = 'a';
        boolean sorted = true;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            mn = (char) Math.min(mn, c);
            mx = (char) Math.max(mx, c);

            if (i > 0 && s.charAt(i - 1) > c) sorted = false;
        }

        if (sorted) return 0;
        if (n == 2) return -1;
        if (s.charAt(0) == mn || s.charAt(n - 1) == mx) return 1;

        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) == mn || s.charAt(i) == mx) return 2;
        }

        return 3;
    }

    /* Approach 2: Same case analysis with an explicit interior check
       Identical decision tree to Approach 1, but the "mn or mx appears in the
       interior" condition is factored into a helper `appearsInInterior`,
       which makes the main method read as a sequence of clearly separated
       cases. The min/max/sorted state is still gathered in one pass.
       Example: "acb" -> 1, "bca" -> 2, "cba" -> 3 (same as Approach 1).
    */
    static int minOperationsIndexBased(String s) {
        int n = s.length();
        if (n == 1) return 0;

        char mn = 'z', mx = 'a';
        boolean sorted = true;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            mn = (char) Math.min(mn, c);
            mx = (char) Math.max(mx, c);
            if (i > 0 && s.charAt(i - 1) > c) sorted = false;
        }

        if (sorted) return 0;
        if (n == 2) return -1;

        if (s.charAt(0) == mn || s.charAt(n - 1) == mx) return 1;
        if (appearsInInterior(s, mn) || appearsInInterior(s, mx)) return 2;

        return 3;
    }

    static boolean appearsInInterior(String s, char c) {
        for (int i = 1; i < s.length() - 1; i++) {
            if (s.charAt(i) == c) return true;
        }
        return false;
    }
}

/* Complexity Analysis
   Let n = s.length().

   Approach 1: Single-pass case analysis
     Time : O(n)   - one pass to compute mn, mx, and sortedness, plus at most
            one more pass to check for mn/mx in the interior.
     Space: O(1)   - a handful of characters and a boolean.
     Key Observation: the answer only depends on (a) whether the string is
     sorted, (b) the length being 2, and (c) the positions of the smallest
     and largest characters - no simulation of moves is required.

   Approach 2: Same case analysis with an explicit interior check
     Time : O(n)   - same asymptotic cost; the interior check is a scan over
            indices 1..n-2.
     Space: O(1)   - identical storage to Approach 1.
     Key Observation: factoring `appearsInInterior` out of the main method
     makes the four return values (0, 1, 2, 3) and the -1 case visually
     separate, at the cost of one extra function call per check.
*/

