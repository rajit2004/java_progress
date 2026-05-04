package LeetCode.Strings;

public class LeetCode_383_RansomNote {
    public static void main(String[] args) {
        String ransomNote = "a";
        String magazine = "b";
        System.out.println(canConstruct(ransomNote,magazine));
    }
    static boolean canConstruct(String ransomNote, String magazine) {
        // Step 1: Count letters in magazine
        int[] count = new int[26];

        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            count[c - 'a']++;
        }

        // Step 2: Try to build ransomNote
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            count[c - 'a']--;

            // If count goes negative → not enough letters
            if (count[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }
}
