package LeetCode.Strings;

public class LeetCode_940_DistinctSubsequence_II {
    public static void main(String[] args) {

        LeetCode_940_DistinctSubsequence_II solution =
                new LeetCode_940_DistinctSubsequence_II();

        // Test Case 1: Distinct subsequences are a, b, and ab.
        String s1 = "ab";
        System.out.println("Test Case 1: " + solution.distinctSubseqII(s1));

        // Test Case 2: Repeated characters must not create duplicate subsequences.
        String s2 = "aba";
        System.out.println("Test Case 2: " + solution.distinctSubseqII(s2));

        // Test Case 3: Repeated copies of one character create one subsequence per length.
        String s3 = "aaa";
        System.out.println("Test Case 3: " + solution.distinctSubseqII(s3));

        // Test Case 4: The empty string has no non-empty subsequences.
        String s4 = "";
        System.out.println("Test Case 4: " + solution.distinctSubseqII(s4));
    }

    /*
        Dynamic Programming with Last-Occurrence Tracking:

        Let count[c] represent the number of distinct non-empty subsequences whose last character is c.

        Before processing the current character c:
            1. Every existing subsequence can either keep its current form or append c.
            2. The single-character subsequence c can also be created.

        Therefore, the number of new subsequences ending with c is: total = 1 + sum of all existing subsequences

        If c has appeared before, the subsequences previously ending with c are replaced.
        This prevents duplicate subsequences from being counted multiple times.

        The final answer is the sum of count for all 26 characters.
     */
    public int distinctSubseqII(String s) {
        final long MOD = 1_000_000_007L;

        // count[c] stores distinct subsequences ending with character c.
        long[] count = new long[26];
        long sum = 0;

        for (char currentCharacter : s.toCharArray()) {
            int index = currentCharacter - 'a';

            // Add the current character alone and append it to all old subsequences.
            long newSubsequences = (1 + sum) % MOD;

            /*
                Replace the previous contribution for this character.
                Subtracting the old value removes duplicates created by an earlier occurrence of the same character.
            */
            sum = (sum + newSubsequences - count[index] + MOD) % MOD;
            count[index] = newSubsequences;
        }

        return (int) sum;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Each character is processed exactly once.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

The count array has a fixed size of 26 because the input contains lowercase English letters.

Overall: O(1)

---------------------------------------------------------

Key Observation:

When a character appears again, all subsequences ending with its previous occurrence become duplicates of subsequences that can now be formed.
Replacing the old contribution for that character avoids double-counting.

The sum of the 26 ending-character groups gives the number of all distinct non-empty subsequences.

---------------------------------------------------------
*/
