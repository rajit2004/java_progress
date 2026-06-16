package LeetCode.Strings;

public class LeetCode_3612_ProcessStringWithSpecialOperations_I {
    public static void main(String[] args) {
        String s = "a#b%*";

        System.out.println(processStr(s));
    }

    /*
        Approach: Traverse the string character by character.

        Operations:

            lowercase letter -> append to result

            '*' -> remove the last character
                   if one exists

            '#' -> duplicate the current result

            '%' -> reverse the current result

        A StringBuilder is used because it allows efficient string modifications compared to repeatedly creating new String objects.
     */

    static String processStr(String s) {

        // Stores the processed string.
        StringBuilder result = new StringBuilder();

        for (char ch : s.toCharArray()) {

            /*
                Normal character. Append directly to the result.
             */
            if (ch >= 'a' && ch <= 'z') {
                result.append(ch);
            }

            /*
                Remove the last character.

                Example: "abc*" -> "ab"
             */
            else if (ch == '*') {

                // Prevent index out of bounds.
                if (!result.isEmpty()) {
                    result.deleteCharAt(result.length() - 1);
                }
            }

            /*
                Duplicate the current string.

                Example: "ab#" -> "abab"
             */
            else if (ch == '#') {

                String current = result.toString();

                result.append(current);
            }

            /*
                Reverse the current string.

                Example: "abc%" -> "cba"
             */
            else if (ch == '%') {
                result.reverse();
            }
        }

        return result.toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of the input string and m = length of the generated output

---------------------------------------------------------

Time Complexity: O(n + m)

Reason:

1. Appending characters: O(1) per operation.

2. '*' operation: O(1)

3. '%' operation: O(currentStringLength)

4. '#' operation: O(currentStringLength)

Since '#' can duplicate the string, the output itself may grow significantly.

Overall complexity is proportional to: input processed + total output produced

Therefore: O(n + m)

---------------------------------------------------------

Space Complexity: O(m)

Reason:

StringBuilder stores the final processed string. In the worst case, repeated '#' operations can greatly increase the output size.

Therefore: O(m) , where m is the length of the final string.

---------------------------------------------------------

Key Observation:

StringBuilder is ideal here because it supports:

append()
deleteCharAt()
reverse()

efficiently while avoiding repeated creation of immutable String objects.

---------------------------------------------------------
*/