package LeetCode.Strings;

public class LeetCode_3614_ProcessStringWithSpecialOperations_II {
    public static void main(String[] args) {
        String s = "a#b%*";
        int k = 1;

        System.out.println(processStr(s, k));
    }

    /*
        Approach: Directly building the final string can be extremely expensive because '#' duplicates the entire string.

        Instead:
            1. Calculate only the final length.
            2. Work backwards through the operations.
            3. Trace where the kth character originated from.

        This avoids constructing the actual string.
     */

    static char processStr(String s, long k) {

        // Stores the length of the final processed string.
        long len = 0;

//        First Pass: Compute the final string length without actually building the string.

        for (char c : s.toCharArray()) {

            if (c == '*') {

                // Remove one character if possible.
                len = Math.max(0, len - 1);

            } else if (c == '#') {

                // Duplicate the current string.
                len *= 2;

            } else if (c == '%') {

                // Reverse does not change length.

            } else {

                // Normal character increases length by 1.
                len++;
            }
        }

        // Requested index is outside the final string.
        if (k >= len) {
            return '.';
        }

//  Second Pass (Reverse Simulation): Walk backwards through the operations and determine which original character ended up at position k.
        for (int i = s.length() - 1; i >= 0; i--) {

            char c = s.charAt(i);

            if (c == '*') {

//                    Forward: delete one character
//                    Reverse:length increases by one

                len++;
            }

            else if (c == '#') {

//                    Forward: abc -> abcabc
//                    Reverse: If k lies in the second half, map it back to the first half.

                len /= 2;

                if (k >= len) {
                    k -= len;
                }
            }

            else if (c == '%') {

//                Forward: abc -> cba
//                Reverse: Mirror the index.

                k = len - 1 - k;
            }

            else { // letter

//                    Forward: append one character
//                    Reverse: remove that character from the current length.

                len--;

//                    If k points exactly to the character that was appended, we found our answer.

                if (k == len) {
                    return c;
                }
            }
        }

        return '.';
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

1. First pass computes the final length.
2. Second pass performs reverse simulation.

O(n) + O(n) = O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Only a few variables are used: len and k and c

No StringBuilder or extra data structures are created.

---------------------------------------------------------

Key Observation:

Actually constructing the final string can be impossible because repeated '#' operations may make the string exponentially large.

Instead of building the string: Track only its length and work backwards to determine where the kth character originated from.

This converts a potentially huge simulation into a linear-time solution.
---------------------------------------------------------
*/