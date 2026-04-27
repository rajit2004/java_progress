package LeetCode.Strings;

public class LeetCode_389_FindTheDifference    {
    public static void main(String[] args) {
        String s = "abcd";
        String t = "abcde";
        System.out.println(findTheDifference(s,t));
    }
    static char findTheDifference(String s, String t) {
        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        for (char c : t.toCharArray()) {
            if (count[c - 'a'] == 0) {
                return c;
            }
            count[c - 'a']--;
        }

        return ' ';
    }
}
