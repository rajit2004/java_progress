package LeetCode.Strings;

public class LeetCode_2904_ShortestAndLexicographicallySmallestBeautifulString {
    public static void main(String[] args) {

        String s = "100011001";
        int k = 3;

        System.out.println(new Solution().shortestBeautifulSubstring(s, k));

        System.out.println(new Solution2().shortestBeautifulSubstring(s, k));
    }
}

/*
    Approach 1: Sliding Window

    A beautiful substring must contain exactly k ones.

    We maintain a sliding window and keep track of the number of ones inside it.
    Once the window contains exactly k ones, we remove unnecessary leading zeroes because they only make the substring longer.

    Among all valid substrings, choose:
        1. Shorter length
        2. Lexicographically smaller if lengths are equal
 */
class Solution {

    public String shortestBeautifulSubstring(String s, int k) {

        // Count the total number of ones in the string.
        int total = 0;

        for (int i = 0; i < s.length(); i++)
            total += s.charAt(i) - '0';


        // If the string does not contain k ones, no beautiful substring can exist.
        if (total < k)
            return "";

        String ans = s;

        int cnt = 0;
        int left = 0;

        // Expand the window from the right.
        for (int right = 0; right < s.length(); right++) {

            // Add the current character to the window.
            cnt += s.charAt(right) - '0';

            /*
                Remove characters while:
                    1. The window contains more than k ones.
                    2. The leftmost character is zero.

                Leading zeroes are unnecessary because removing them does not change the number of ones.
             */
            while (cnt > k || s.charAt(left) == '0')
                cnt -= s.charAt(left++) - '0';

            // Check the current window if it contains k ones.
            if (cnt == k) {
                String t = s.substring(left, right + 1);

//  Update the answer if the current substring is shorter or lexicographically smaller when both have the same length.

                if (t.length() < ans.length()|| (t.length() == ans.length() && t.compareTo(ans) < 0))
                    ans = t;
            }
        }

        return ans;
    }
}

/*
    Approach 2: Brute Force

    Try every possible substring length starting from the smallest possible length.

    For each length:
        1. Check every substring of that length.
        2. Count its number of ones.
        3. Keep the lexicographically smallest valid substring.

    As soon as a valid substring is found for a given length, it is guaranteed to be the shortest one.
 */
class Solution2 {

    public String shortestBeautifulSubstring(String s, int k) {

        int n = s.length();

//            A substring containing k ones must have a length of at least k.
        for (int m = k; m <= n; m++) {

            String ans = "";

            // Check every substring having length m.
            for (int i = m; i <= n; i++) {

                String t = s.substring(i - m, i);

                int cnt = 0;

                // Count the number of ones in the substring.
                for (int j = 0; j < t.length(); j++)
                    cnt += t.charAt(j) - '0';


//                    Keep the lexicographically smallest substring containing exactly k ones.
                if ((ans.isEmpty() || t.compareTo(ans) < 0)
                        && cnt == k) {

                    ans = t;
                }
            }

//                Since lengths are checked from smallest to largest, the first valid answer is automatically the shortest one.
            if (!ans.isEmpty())
                return ans;
        }

        return "";
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Sliding Window

Time Complexity: O(n)

Reason: The right pointer moves from left to right once, and the left pointer also moves only forward.

Overall: O(n)
Space Complexity: O(n)

The implementation creates substring objects for candidate answers.

Overall: O(n)

---------------------------------------------------------

Approach 2: Brute Force

Time Complexity: O(n^3)

Reason:

1. There are O(n) possible substring lengths.
2. For each length, there are O(n) substrings.
3. Counting the ones in each substring takes O(n).

Therefore: O(n^3)

Space Complexity: O(n)

Due to the substring objects created while checking candidate substrings.

---------------------------------------------------------

Key Observation:

A beautiful substring must contain exactly k ones.
For the sliding-window solution, once the window contains k ones, leading zeroes can always be removed because they do not affect the number of ones.
Therefore, every candidate can be reduced to its shortest valid form.
For equal-length candidates, String.compareTo() selects the lexicographically smallest substring.

---------------------------------------------------------
*/