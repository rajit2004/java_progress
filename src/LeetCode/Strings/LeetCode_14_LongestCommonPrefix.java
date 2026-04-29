package LeetCode.Strings;

public class LeetCode_14_LongestCommonPrefix {
    public static void main(String[] args) {
        String[] str ={"flower","flow","flight"};
        System.out.println(longestCommonPrefix(str));
    }
    static String longestCommonPrefix(String[] strs) {
        // Edge case: empty array
        if (strs == null || strs.length == 0) {
            return "";
        }

        // Take first string as reference
        String first = strs[0];

        for (int i = 0; i < first.length(); i++) {
            char ch = first.charAt(i);

            // Compare this character with same position in other strings
            for (int j = 1; j < strs.length; j++) {
                // If index out of bounds OR mismatch
                if (i >= strs[j].length() || strs[j].charAt(i) != ch) {
                    return first.substring(0, i);
                }
            }
        }

        // If all characters matched
        return first;
    }
}
