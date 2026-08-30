package LeetCode.Greedy;

public class LeetCode_3734_LexicographicallySmallestPalindromicPermutationGreaterThanTarget {
    public static void main(String[] args) {

        String s = "aabb";
        String target = "abba";

        System.out.println(lexPalindromicPermutation(s, target));
    }

    /*
        Greedy + Frequency Counting Approach : A palindrome can be formed only when at most one character has an odd frequency.

        We construct the left half of the palindrome because the right half is automatically determined by reversing the left half.
        First, try to match the target's left half exactly. If that does not produce a palindrome greater than the target, move from right to left and increase the first possible character.
        After increasing a character, fill the remaining characters in ascending order to obtain the smallest possible palindrome.
     */
    static String lexPalindromicPermutation(String s, String target) {

        // Count the frequency of every character.
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++)
            freq[s.charAt(i) - 'a']++;
        char center = 0;

        /*
            A palindrome can contain at most one character with an odd frequency.
            The odd-frequency character becomes the center of the palindrome.
         */
        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                // More than one odd-frequency character means a palindrome cannot be formed.
                if (center != 0)
                    return "";
                center = (char) ('a' + i);
                // Remove the center character from the characters used to build both halves.
                freq[i]--;
            }
        }

        int sz = s.length();
        int half = sz / 2;

        /*
            Try to match the left half of target.
            Every character in the left half consumes two copies from the frequency array because one copy is needed on each side of the palindrome.
         */
        for (int i = 0; i < half; i++)
            freq[target.charAt(i) - 'a'] -= 2;

//            If the target's left half can be constructed, check whether the complete palindrome is already greater than target.
        if (check(freq)) {
            String head = target.substring(0, half);

            // Mirror the left half.
            String rev = new StringBuilder(head).reverse().toString();
            String tail = "";

            // Add the center character if one exists.
            if (center != 0)
                tail += center;
            tail += rev;

            // Check whether the resulting palindrome is greater.
            if (tail.compareTo(target.substring(half)) > 0)
                return head + tail;
        }

        /*
            The target's exact prefix cannot produce a valid answer.
            Move from right to left and try to increase the rightmost possible character.
         */
        for (int i = half - 1; i >= 0; i--) {
            char w = target.charAt(i);

            // Restore the two copies used by target[i].
            freq[w - 'a'] += 2;

            // Continue if the remaining frequencies are invalid.
            if (!check(freq))
                continue;

//                Try the smallest available character that is greater than target[i].
            for (int j = (w - 'a') + 1; j < 26; j++) {
                if (freq[j] == 0)
                    continue;

                // Use two copies for the palindrome.
                freq[j] -= 2;

                // Keep the target prefix and replace position i.
                StringBuilder answer = new StringBuilder(target.substring(0, i + 1));
                answer.setCharAt(i, (char) ('a' + j));

//                    Fill the remaining left half in ascending order so that the resulting palindrome is lexicographically as small as possible.
                for (int k = 0; k < 26; k++) {
                    int cnt = freq[k] / 2;
                    for (int m = 0; m < cnt; m++)
                        answer.append((char) ('a' + k));
                }

                // Create the right half by reversing the left half.
                String part = new StringBuilder(answer).reverse().toString();

                // Add the center character if one exists.
                if (center != 0)
                    answer.append(center);

                answer.append(part);
                return answer.toString();
            }
        }

        // No palindromic permutation is greater than target.
        return "";
    }

    /*
        Check whether all remaining character frequencies are valid.
        A negative frequency means that the target prefix requires more copies of a character than are available.
     */
    static boolean check(int[] freq) {
        for (int v : freq) {
            if (v < 0)
                return false;
        }
        return true;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of s

The alphabet contains only 26 characters.

---------------------------------------------------------

Time Complexity: O(26 * n)

The algorithm processes at most n / 2 positions. For each position, it may check up to 26 characters.
Constructing the remaining palindrome takes O(n).
Since 26 is constant: O(n)

---------------------------------------------------------

Space Complexity: O(n)

The frequency array requires O(26), which is constant.
The generated StringBuilder and resulting strings require O(n).

Overall: O(n)

---------------------------------------------------------

Key Observation:

A palindrome is completely determined by:
    1. Its left half
    2. Its optional center character

The right half is simply the reverse of the left half.

Therefore, instead of generating complete permutations, we only need to construct the left half.

To find the smallest palindrome greater than target:
    1. Try to keep the target's left half unchanged.
    2. If that fails, move from right to left.
    3. Increase the rightmost possible character by the smallest amount.
    4. Fill the remaining positions in ascending order.
    5. Mirror the left half to form the palindrome.

---------------------------------------------------------
*/