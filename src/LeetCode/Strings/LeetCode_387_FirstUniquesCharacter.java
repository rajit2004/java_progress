package LeetCode.Strings;

class LeetCode_387_FirstUniquesCharacter {
    public static void main(String[] args) {
        String word = "leetcode";
        System.out.println(firstUniqChar(word));
    }
    static int firstUniqChar(String s) {

        // Array to store frequency of characters
        int[] freq = new int[26];

        // Step 1: Count frequency
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            freq[ch - 'a']++;
        }

        // Step 2: Find first unique character
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (freq[ch - 'a'] == 1) {
                return i;
            }
        }

        // No unique character found
        return -1;
    }
}