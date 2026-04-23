package LeetCode;

import java.util.Arrays;

public class LeetCode_242_ALTERNATIVE {
    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println(isAnagram(s, t));
    }

    static boolean isAnagram(String s, String t) {
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        Arrays.sort(sArr);
        Arrays.sort(tArr);
        return Arrays.equals(sArr, tArr);
    }
}
