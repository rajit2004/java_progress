package LeetCode;

public class LeetCode_242_Valid_Anagram {
    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println(isAnagram(s,t));
    }
    static boolean isAnagram(String s, String t){
        // Step 1: Different lengths can't be anagrams
        if (s.length()!= t.length()) {
            return false;
        }

        // Step 2: Array to store count of each letter a-z
        int[] count = new int[26];

        // Step 3: Count letters in s
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++; // 'a' - 'a' = 0, 'b' - 'a' = 1, etc.
        }

        // Step 4: Subtract letters in t
        for (int i = 0; i < t.length(); i++) {
            count[t.charAt(i) - 'a']--;
            // Optional early exit: if count goes negative, not an anagram
            if (count[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }

        // Step 5: If we made it here, all counts are 0
        return true;
    }
}
