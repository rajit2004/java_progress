package LeetCode.Strings;

public class LeetCode_1446_ConsecutiveCharacters {
    public static void main(String[] args) {

        String s = "abbcccdddde";

        System.out.println(maxPower(s));
    }

    /*
        Linear Traversal Approach : Traverse the string from left to right.

        For every character:
            1. If it is the same as the previous character, increase the current consecutive count.
            2. Otherwise, reset the current count to 1.
            3. Keep track of the maximum consecutive count.

        The maximum consecutive count represents the power of the string.
     */
    static int maxPower(String s) {

        // Maximum consecutive characters found so far.
        int max = 1;

        // Length of the current consecutive sequence.
        int current = 1;

        // Start from the second character.
        for (int i = 1; i < s.length(); i++) {

            // Continue the current sequence if the current character matches the previous one.
            if (s.charAt(i) == s.charAt(i - 1)) {
                current++;
            } else {

                // Start a new sequence.
                current = 1;
            }

            // Update the maximum consecutive length.
            max = Math.max(max, current);
        }

        return max;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = s.length()

---------------------------------------------------------

Time Complexity: O(n)

Reason: The string is traversed exactly once. Each character is processed in O(1) time.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only two integer variables are used:
            1. max
            2. current

No additional data structures are required.

Overall: O(1)

---------------------------------------------------------

Key Observation:

A consecutive sequence continues only when the current character is equal to the previous character.
Otherwise, the sequence starts again from 1.
By maintaining the current sequence length and the maximum sequence length, we can solve the problem in a single traversal.

---------------------------------------------------------
*/