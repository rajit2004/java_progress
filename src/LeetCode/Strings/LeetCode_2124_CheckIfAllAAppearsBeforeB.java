package LeetCode.Strings;

public class LeetCode_2124_CheckIfAllAAppearsBeforeB {

    public static void main(String[] args) {
        System.out.println(checkStringTwoPointers("aaabbb"));
        System.out.println(checkStringSubstring("aaabbb"));
    }

    /* Approach 1: Track last 'a' and first 'b'
       Scan once, record the index of the last 'a' and the first 'b'.
       The string is valid iff lasta < firstb (defaults -1 and n cover the
       all-'a' and all-'b' cases).
       Example: "aaabbb" -> lasta = 2, firstb = 3 -> 2 < 3 -> true.
    */
    static boolean checkStringTwoPointers(String s) {
        int n = s.length();
        int lasta = -1;   // index of the last occurrence of 'a'
        int firstb = n;   // index of the first occurrence of 'b'

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'a') lasta = Math.max(lasta, i);
            else                    firstb = Math.min(firstb, i);
        }

        return lasta < firstb;
    }

    /* Approach 2: Look for the forbidden pattern "ba"
       If any 'b' is followed by an 'a', the string is invalid; otherwise all
       'a's appear before all 'b's. Built-in substring search does the work.
       Example: "aaabbb" has no "ba" -> true.
    */
    static boolean checkStringSubstring(String s) {
        return !s.contains("ba");
    }
}

/* Complexity Analysis
   Let n = s.length().

   Approach 1: Track last 'a' and first 'b'
     Time : O(n)  - single left-to-right pass.
     Space: O(1)  - two integer indices.
     Key Observation: the condition "all 'a' before all 'b'" is equivalent to
     last occurrence of 'a' < first occurrence of 'b'.

   Approach 2: Look for the forbidden pattern "ba"
     Time : O(n)  - String.contains runs a linear substring scan.
     Space: O(1)  - no extra structures.
     Key Observation: the ordering is violated exactly when some 'b' is
     immediately followed by an 'a', i.e. the substring "ba" exists.
*/
