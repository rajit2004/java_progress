package LeetCode.Strings;

public class LeetCode_796_RotatedStringAlt {
    public static void main(String[] args) {
        String s = "abcde";
        String goal = "cdeab";
        System.out.println(rotateString(s,goal));
    }
    static boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) return false;

        for (int i = 0; i < s.length(); i++) {
            s = s.substring(1) + s.charAt(0);
            if (s.equals(goal)) return true;
        }
        return false;
    }
}
