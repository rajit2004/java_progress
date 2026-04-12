package LeetCode;

public class LeetCode_28_FindFirstOccurrenceInString_BruteForce {
    public static void main(String[] args) {
        String word = "leetcode";
        String part = "code";
        System.out.println(check(word,part));
    }
    static int check(String word, String part) {
        if (part.isEmpty()) return 0;
//    alternate of this =>  if (part.length() == 0) return 0;
        for (int i = 0; i <= word.length() - part.length(); i++) {
            int j = 0;

            while (j < part.length() &&
                    word.charAt(i + j) == part.charAt(j)) {
                j++;
            }
            if (j == part.length()) {
                return i;
            }
        }
        return -1;
    }
}
