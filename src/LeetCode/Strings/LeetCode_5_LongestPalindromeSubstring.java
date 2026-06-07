package LeetCode.Strings;

public class LeetCode_5_LongestPalindromeSubstring {
    public static void main(String[] args) {
        String s = "babad";
        System.out.println(longestPalindrome(s));
    }
    static String longestPalindrome(String s) {

        // Edge case
        if (s == null || s.length() < 1) {
            return "";
        }

        int start = 0;
        int end = 0;

        // Try every character as center
        for (int i = 0; i < s.length(); i++) {

            // Odd length palindrome
            int len1 = expandFromCenter(s, i, i);

            // Even length palindrome
            int len2 = expandFromCenter(s, i, i + 1);

            // Take maximum length
            int len = Math.max(len1, len2);

            // Update answer
            if (len > end - start) {

                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    // Helper function
    static int expandFromCenter(String s, int left, int right) {

        // Expand while valid palindrome
        while (left >= 0 &&
                right < s.length() &&
                s.charAt(left) == s.charAt(right)) {

            left--;
            right++;
        }

        // Length of palindrome
        return right - left - 1;
    }
}
