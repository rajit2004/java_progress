package LeetCode.Enum;

import java.util.Arrays;

public class LeetCode_3720_LexicographicallySmallestPermutationGreaterThanTarget {
    public static void main(String[] args) {

        String s = "abc";
        String target = "abb";

        System.out.println(new Solution().lexGreaterPermutation(s, target));

        System.out.println(new Solution2().lexGreaterPermutation(s, target));
    }
}

/*
    Approach 1: Greedy + Backtracking

    Build the answer from left to right.

    At every position:
        1. Try to place the same character as target[i].
        2. Check whether the remaining characters can form a string greater than the remaining target suffix.
        3. If not possible, restore the character.
        4. Try the smallest character greater than target[i].
        5. Once a greater character is selected, fill all remaining positions with the smallest order.

    This gives the lexicographically smallest permutation that is strictly greater than target.
 */
class Solution {

    public String lexGreaterPermutation(String s, String target) {

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
                We only keep this choice if the remaining characters can still make the result greater than the remaining target suffix.
             */
            if (cnt[targetChar] > 0) {

                cnt[targetChar]--;

                // Check whether the remaining characters can form a string greater than target[i + 1:].
                if (canFormGreater(cnt, target, i + 1)) {
                    res.append(target.charAt(i));
                    continue;
                }

                // Same character does not lead to a valid greater permutation, so restore it.
                cnt[targetChar]++;
            }

            /*
                Case 2: Try every character greater than target[i].
                We try them in ascending order so that the resulting permutation is lexicographically smallest.
             */
            for (int j = targetChar + 1; j < 26; j++) {

                if (cnt[j] > 0) {

                    cnt[j]--;

                    // Place the smallest possible remaining characters after choosing a larger one.
                    res.append((char) ('a' + j));
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
        The maximum possible remaining string is constructed in descending order. If even this maximum string is not greater than the target suffix, no valid arrangement is possible.
     */
    private boolean canFormGreater(int[] cnt, String target, int start) {

        String maxStr = getMaxString(cnt);
        String suffix = target.substring(start);

        return maxStr.compareTo(suffix) > 0;
    }

    // Build the maximum possible string in descending order.
    private String getMaxString(int[] cnt) {
        StringBuilder res = new StringBuilder();
        for (int i = 25; i >= 0; i--) {
            if (cnt[i] > 0)
                res.append(String.valueOf((char) ('a' + i)).repeat(cnt[i]));
        }
        return res.toString();
    }

    // Build the minimum possible string in ascending order.
    private String getMinString(int[] cnt) {

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            if (cnt[i] > 0)
                res.append(String.valueOf((char) ('a' + i)).repeat(cnt[i]));
        }
        return res.toString();
    }
}

/*
    Approach 2: Greedy from Right to Left

    Compare the permutation with target from the right.
    We try to find the rightmost position where the character can be increased.

    Once such a position is found:
        1. Keep the prefix unchanged.
        2. Replace the current character with the smallest available character greater than it.
        3. Fill the remaining positions in ascending order.

    Starting from the right ensures that the resulting permutation is as small as possible.
 */
class Solution2 {

    public String lexGreaterPermutation(String s, String target) {

        // Difference between character frequencies of s and the target prefix currently being considered.
        int[] cnt = new int[26];

        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
            cnt[target.charAt(i) - 'a']--;
        }

        char[] t = target.toCharArray();

        /*
            Process positions from right to left.
            This allows us to change the latest possible position while keeping the earlier prefix unchanged.
         */
        for (int i = s.length() - 1; i >= 0; i--) {

            int b = t[i] - 'a';

            // Return the character at position i to the pool.
            cnt[b]++;

//                If any frequency becomes negative, the target prefix cannot be formed using characters from s.
            if (Arrays.stream(cnt).min().getAsInt() < 0)
                continue;

//                Find the smallest available character greater than target[i].
            for (int j = b + 1; j < 26; j++) {

                if (cnt[j] > 0) {

                    cnt[j]--;

                    // Increase the current position.
                    t[i] = (char) ('a' + j);

                    /*
                        The prefix before i remains unchanged.
                        Fill everything after i with the smallest possible characters to minimize the result.
                     */
                    return new String(t, 0, i + 1) + getMinString(cnt);
                }
            }
        }

        // No permutation of s is greater than target.
        return "";
    }

    // Build the smallest possible string in ascending order.
    private String getMinString(int[] cnt) {
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

Approach 1: Greedy + Backtracking

Let: n = length of the string
The character set is fixed at 26 characters.

Time Complexity: O(26 * n²)
The algorithm may construct and compare strings of length O(n) for multiple positions.
Since the alphabet contains only 26 characters, the practical performance is efficient for the given constraints.

Space Complexity: O(n)
The constructed strings and StringBuilder require O(n) space.

---------------------------------------------------------

Approach 2: Greedy from Right to Left

Time Complexity: O(26 * n)
For every position, we may scan the 26 possible characters.
The frequency check also operates over a fixed alphabet of 26 characters.

Space Complexity: O(n)
The character array and resulting strings require O(n) space.

---------------------------------------------------------

Key Observation:

To obtain the smallest permutation that is strictly greater than target, we should keep the prefix equal to target for as long as possible.
When equality can no longer continue, increase the rightmost possible character by the smallest amount.
After increasing that character, arrange all remaining characters in ascending order.
This guarantees the lexicographically smallest valid permutation.

---------------------------------------------------------
*/