package LeetCode.Strings;

public class LeetCode_415_AddStrings {
    public static void main(String[] args) {

        String num1 = "456";
        String num2 = "77";

        System.out.println(addStrings(num1, num2));
    }

    /*
        Simulation Approach : Traverse both strings from right to left, just like manual addition.

        At every step:

        1. Read one digit from each string.
        2. Add both digits along with the carry.
        3. Store the current digit.
        4. Carry the remaining value to the next position.

        Since digits are processed from the end, reverse the final result before returning it.
     */
    static String addStrings(String num1, String num2) {

        // Stores the resulting digits.
        StringBuilder result = new StringBuilder();

        // Start from the last digit of both strings.
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        // Stores the carry generated after every addition.
        int carry = 0;

//            Continue while at least one string has digits remaining or a carry exists.
        while (i >= 0 || j >= 0 || carry != 0) {

            // Current digit from the first string.
            int digit1 = (i >= 0) ? num1.charAt(i--) - '0' : 0;

            // Current digit from the second string.
            int digit2 = (j >= 0) ? num2.charAt(j--) - '0' : 0;

            // Total value at the current position.
            int sum = digit1 + digit2 + carry;

            // Store the current digit.
            result.append(sum % 10);

            // Carry forwarded to the next position.
            carry = sum / 10;
        }

        // Digits were added from right to left, so reverse the result.
        return result.reverse().toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

m = length of num1
n = length of num2

---------------------------------------------------------

Time Complexity: O(max(m, n))

Reason:

Each digit from both strings is processed exactly once. Reversing the StringBuilder also takes linear time.

Overall: O(max(m, n))

---------------------------------------------------------

Space Complexity: O(max(m, n))

Reason:

The StringBuilder stores the digits of the resulting sum.

Overall: O(max(m, n))

---------------------------------------------------------

Key Observation:

Instead of converting the strings into integers (which may overflow),

perform digit-by-digit addition exactly like manual arithmetic while maintaining a carry.

---------------------------------------------------------
*/