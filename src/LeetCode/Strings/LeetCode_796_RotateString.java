package LeetCode.Strings;

/*
Given two strings s and goal, return true if and only if s can become goal after some number of shifts on s.
A shift on s consists of moving the leftmost character of s to the rightmost position.
*/

public class LeetCode_796_RotateString {
    public static void main(String[] args) {
        String s = "abcde";
        String goal = "cdeab";
        System.out.println(rotateString(s,goal));
    }
    static boolean rotateString(String s, String goal){
        // Step 1: Length check
        if (s.length() != goal.length()) {
            return false;
        }

        // Step 2: Combine string
        String combined = s + s;

        // Step 3: Check substring
        return combined.contains(goal);
    }
}
/*

s = "abcde"
goal = "cdeab"

combined = "abcdeabcde"

Does combined contain "cdeab"? → YES

*/
