package LeetCode.Strings;

public class LeetCode_3_LongestSubstringWithoutRepeatingChars {
    public static void main(String[] args) {
        String s = "abcabcbb";
        System.out.println(lengthOfLongestSubstring(s));
    }
    static int lengthOfLongestSubstring(String s) {
        boolean[] visited = new boolean[256];

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);

            // If duplicate found
            while (visited[current]) {
                visited[s.charAt(left)] = false;
                left++;
            }

            // Mark current as visited
            visited[current] = true;

            // Update max length
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}
