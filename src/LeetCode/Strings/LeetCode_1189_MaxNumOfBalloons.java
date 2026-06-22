package LeetCode.Strings;

public class LeetCode_1189_MaxNumOfBalloons {
    public static void main(String[] args) {
        String text = "nlaebolko";

        System.out.println(maxNumberOfBalloons(text));
    }

    /*
        Approach: To form the word: "balloon"; we need:
            b -> 1
            a -> 1
            l -> 2
            o -> 2
            n -> 1

        Count the frequency of every character in the given string. Then determine how many complete "balloon" words can be formed.
        The limiting character determines the final answer.
     */

    static int maxNumberOfBalloons(String text) {

        // Frequency array for lowercase letters.
        int[] freq = new int[26];

        /*
            Count occurrences of every character.

            Example:

            text = "balloon"

            freq['b'] = 1
            freq['a'] = 1
            freq['l'] = 2
            ...
         */
        for (char ch : text.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Required once in "balloon".
        int b = freq['b' - 'a'];

        // Required once in "balloon".
        int a = freq['a' - 'a'];

        /*
            'l' appears twice in "balloon".

            Example: l count = 4

            Can contribute to: 4 / 2 = 2 balloons
         */
        int l = freq['l' - 'a'] / 2;

        /*
            'o' appears twice in "balloon".

            Example: o count = 6

            Can contribute to:
            6 / 2 = 3 balloons
         */
        int o = freq['o' - 'a'] / 2;

        // Required once in "balloon".
        int n = freq['n' - 'a'];

        /*
            The smallest available count determines how many complete "balloon" words can be formed.
         */
        return Math.min(
                Math.min(b, a),
                Math.min(Math.min(l, o), n)
        );
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of the input string

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. One traversal of the string to count character frequencies.
2. Remaining operations are O(1).

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

The frequency array always has a fixed size of 26.

Regardless of input size: freq[26]

Therefore: O(1)

---------------------------------------------------------

Key Observation:

To form one "balloon":

b -> 1
a -> 1
l -> 2
o -> 2
n -> 1

Characters 'l' and 'o' are required twice, so their frequencies must be divided by 2 before comparison.

The minimum available required character determines the maximum number of complete "balloon" words that can be formed.

---------------------------------------------------------
*/