package LeetCode.Strings;

/*
You are given a string word. A letter is called special if it appears both in lowercase and uppercase in word.

Return the number of special letters in word.
*/

public class Leetcode_3120_NumSpecialChara {
    public static void main(String[] args) {
        String word = "aaAbcBC";
        System.out.println(numberOfSpecialChars(word));
    }
    static int numberOfSpecialChars(String word){
        // Store lowercase letters
        boolean[] lower = new boolean[26];

        // Store uppercase letters
        boolean[] upper = new boolean[26];

        // Traverse the string
        for (char ch : word.toCharArray()) {

            if (Character.isLowerCase(ch)) {
                lower[ch - 'a'] = true;
            } else {
                upper[ch - 'A'] = true;
            }
        }

        int count = 0;

        // Check if both lowercase and uppercase exist
        for (int i = 0; i < 26; i++) {
            if (lower[i] && upper[i]) {
                count++;
            }
        }

        return count;
    }
}
