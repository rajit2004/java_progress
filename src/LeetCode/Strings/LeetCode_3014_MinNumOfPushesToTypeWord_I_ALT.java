package LeetCode.Strings;

public class LeetCode_3014_MinNumOfPushesToTypeWord_I_ALT {
    public static void main(String[] args) {

        String word = "abcdefghijk";

        System.out.println(minimumPushes(word));
    }

    /*
        Mathematical Approach :Every 8 characters require one additional key press.

        Let: q = number of complete groups of 8 characters and r = remaining characters

        Then: Groups contribute: 8 × (1 + 2 + ... + q)

        Remaining characters contribute:r × (q + 1)

        This simplifies to the formula used below.
     */
    static int minimumPushes(String word) {

        // Number of complete groups of 8 characters.
        int q = word.length() >> 3;

        // Remaining characters after complete groups.
        int r = word.length() & 7;

        // Compute the minimum total number of pushes.
        return ((q << 2) + r) * (q + 1);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of the word

---------------------------------------------------------

Time Complexity: O(1)

Reason: Only a few arithmetic and bitwise operations are performed.

Overall: O(1)

---------------------------------------------------------

Space Complexity: O(1)

Reason: No extra data structures are used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The answer depends only on the length of the word, not on the characters.

Every complete group of 8 characters requires one extra key press, allowing the total number of pushes to be computed directly using a mathematical formula.

---------------------------------------------------------
*/