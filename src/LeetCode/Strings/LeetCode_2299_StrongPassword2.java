package LeetCode.Strings;

/*
A password is said to be strong if it satisfies all the following criteria:

It has at least 8 characters.
It contains at least one lowercase letter.
It contains at least one uppercase letter.
It contains at least one digit.
It contains at least one special character. The special characters are the characters in the following string: "!@#$%^&*()-+".
It does not contain 2 of the same character in adjacent positions (i.e., "aab" violates this condition, but "aba" does not).
Given a string password, return true if it is a strong password. Otherwise, return false.
*/

import java.util.regex.Pattern;

public class LeetCode_2299_StrongPassword2 {
    public static void main(String[] args) {
        String pswd = "IloveLe3tcode!";
        System.out.println(strongPasswordCheckerII(pswd));
    }
    static boolean strongPasswordCheckerII(String password) {
        if (password == null || password.length() < 8)
            return false;

        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        String specialChars = "!@#$%^&*()-+";

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChars.indexOf(c) >= 0) hasSpecial = true;

            // adjacent duplicate check
            if (i > 0 && c == password.charAt(i - 1)) {
                return false;
            }
        }

        return hasLower && hasUpper && hasDigit && hasSpecial;
    }
}
