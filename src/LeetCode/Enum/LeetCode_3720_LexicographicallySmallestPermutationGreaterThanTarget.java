package LeetCode.Enum;

import java.util.Arrays;

public class LeetCode_3720_LexicographicallySmallestPermutationGreaterThanTarget {
    public static void main(String[] args) {

        String s = "abc";
        String target = "abb";

        System.out.println(lexGreaterPermutation(s, target));
        System.out.println(lexGreaterPermutationALT(s, target));
    }

    /*
        Approach 1: Greedy + Backtracking

        Build the answer from left to right.
        At every position:
            1. Try to place the same character as target[i].
            2. Check whether the remaining characters can form a string greater than the remaining target suffix.
            3. If not possible, restore the character.
            4. Try the smallest character greater than target[i].
            5. Once a greater character is selected, fill the remaining positions in ascending order.

        This gives the lexicographically smallest permutation that is strictly greater than target.
     */
    static String lexGreaterPermutation(String s, String target) {

        // Store the frequency of every character in s.
        int[] cnt = new int[26];
        for (char c : s.toCharArray())
            cnt[c - 'a']++;

        StringBuilder res = new StringBuilder();
        int n = target.length();

        for (int i = 0; i < n; i++) {
            int targetChar = target.charAt(i) - 'a';

            /*
                Case 1: Try placing the same character as target[i].
                Keep this choice only if the remaining characters can still make the result greater than the target.
             */
            if (cnt[targetChar] > 0) {
                cnt[targetChar]--;

                // Check whether the remaining characters can form a string greater than the target suffix.
                if (canFormGreater(cnt, target, i + 1)) {
                    res.append(target.charAt(i));
                    continue;
                }

                // Restore the character if this choice fails.
                cnt[targetChar]++;
            }

            /*
                Case 2: Try a character greater than target[i].
                Characters are checked in ascending order so the first valid choice gives the smallest result.
             */
            for (int j = targetChar + 1; j < 26; j++) {
                if (cnt[j] > 0) {
                    cnt[j]--;

                    // Place the larger character at the current position.
                    res.append((char) ('a' + j));

                    // Fill the remaining positions with the smallest order.
                    res.append(getMinString(cnt));

                    return res.toString();
                }
            }

            // No character can make the permutation greater.
            return "";
        }

        return "";
    }

    /*
        Check whether the remaining characters can form a string greater than target[start...].
        The maximum possible remaining string is constructed in descending order.
        If even this maximum string is not greater than the target suffix, no valid arrangement is possible.
     */
    private static boolean canFormGreater(int[] cnt,String target,int start) {

        String maxStr = getMaxString(cnt);
        String suffix = target.substring(start);

        return maxStr.compareTo(suffix) > 0;
    }

    // Build the maximum possible string in descending order.
    private static String getMaxString(int[] cnt) {

        StringBuilder res = new StringBuilder();

        for (int i = 25; i >= 0; i--) {
            if (cnt[i] > 0)
                res.append(String.valueOf((char) ('a' + i)).repeat(cnt[i]));
        }
        return res.toString();
    }

    // Build the minimum possible string in ascending order.
    private static String getMinString(int[] cnt) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (cnt[i] > 0)
                res.append(String.valueOf((char) ('a' + i)).repeat(cnt[i]));
        }
        return res.toString();
    }

    /*
        Approach 2: Greedy from Right to Left

        Process target from right to left and find the rightmost position that can be increased.

        Once such a position is found:
            1. Keep the prefix unchanged.
            2. Replace the current character with the smallest available character greater than it.
            3. Fill the remaining positions in ascending order.

        Changing the rightmost possible position keeps the resulting permutation as small as possible.
     */
    static String lexGreaterPermutationALT(String s, String target) {

        // Store the difference between frequencies of s and target.
        int[] cnt = new int[26];

        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
            cnt[target.charAt(i) - 'a']--;
        }

        char[] t = target.toCharArray();

        /*
            Process positions from right to left.
            This allows us to modify the latest possible position while preserving the longest prefix.
         */
        for (int i = s.length() - 1; i >= 0; i--) {

            int b = t[i] - 'a';

            // Return target[i] to the available character pool.
            cnt[b]++;

//                If any frequency is negative, the target prefix cannot be formed using characters from s.
            if (Arrays.stream(cnt).min().getAsInt() < 0)
                continue;

            // Find the smallest available character greater than target[i].
            for (int j = b + 1; j < 26; j++) {
                if (cnt[j] > 0) {
                    cnt[j]--;

                    // Increase the current position.
                    t[i] = (char) ('a' + j);

//      Keep the prefix unchanged and fill the remaining positions with the smallest possible characters.
                    return new String(t, 0, i + 1) + getMinStringALT(cnt);
                }
            }
        }

        // No permutation of s is greater than target.
        return "";
    }

    // Build the smallest possible string in ascending order.
    private static String getMinStringALT(int[] cnt) {

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < 26; i++)
            res.append(String.valueOf((char) ('a' + i)).repeat(cnt[i]));

        return res.toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of the string

The character set contains only 26 characters.

---------------------------------------------------------

Approach 1: Greedy + Backtracking

Time Complexity: O(26 * n²)

For multiple positions, the algorithm may construct and compare strings of length O(n).
Since the alphabet size is fixed at 26: O(n²)

Space Complexity: O(n)

The StringBuilder and generated strings require O(n).

---------------------------------------------------------

Approach 2: Greedy from Right to Left

Time Complexity: O(26 * n)

Each position may scan up to 26 characters.

The frequency validation also works over a fixed alphabet of 26 characters.

Since 26 is constant: O(n)

Space Complexity: O(n)

The character array and generated result require O(n).

---------------------------------------------------------

Key Observation:

To obtain the lexicographically smallest permutation strictly greater than target:
    1. Keep the prefix equal to target for as long as possible.
    2. When equality can no longer continue, increase the rightmost possible character.
    3. Increase it by the smallest possible amount.
    4. Arrange all remaining characters in ascending order.

Approach 1 builds from left to right and checks whether the remaining characters can still produce a greater string.
Approach 2 directly searches from right to left for the position that should be increased.

---------------------------------------------------------
*/