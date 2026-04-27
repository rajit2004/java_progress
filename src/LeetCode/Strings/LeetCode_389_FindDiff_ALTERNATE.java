package LeetCode.Strings;

public class LeetCode_389_FindDiff_ALTERNATE {
    public static void main(String[] args) {
        String s = "abcd";
        String t = "abcde";
        System.out.println(findTheDifference(s,t));
    }
    static char findTheDifference(String s, String t){
        char result = 0;

        // XOR all characters from s
        for (char c : s.toCharArray()) {
            result ^= c;
        }

        // XOR all characters from t
        for (char c : t.toCharArray()) {
            result ^= c;
        }

        return result;
    }
}
