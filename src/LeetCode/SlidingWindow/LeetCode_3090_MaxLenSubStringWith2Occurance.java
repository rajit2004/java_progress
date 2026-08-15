package LeetCode.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class LeetCode_3090_MaxLenSubStringWith2Occurance {
    public static void main(String[] args) {

        String s = "bcbbbcba";

        System.out.println(maximumLengthSubstring(s));
    }

    /*
        Sliding Window + HashMap Approach : Maintain a window where every character appears at most twice.

        For every character:
            1. Add it to the current window.
            2. If its frequency becomes greater than 2, shrink the window from the left.
            3. Continue shrinking until the window becomes valid again.
            4. Track the maximum valid window length.
     */
    static int maximumLengthSubstring(String s) {

        // Stores the frequency of each character inside the current window.
        Map<Character, Integer> count = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        // Expand the window using the right pointer.
        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);
            // Add the current character to the window.
            count.put(current,count.getOrDefault(current, 0) + 1 );

//  If the current character appears more than twice, shrink the window from the left until its frequency becomes valid again.
            while (count.get(current) > 2) {
                char removed = s.charAt(left);
                count.put(removed,count.get(removed) - 1);
                left++;
            }
            // Update the longest valid window.
            maxLength = Math.max(maxLength,right - left + 1);
        }

        return maxLength;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = s.length()

---------------------------------------------------------

Time Complexity: O(n)

Reason:

The right pointer traverses the string once.
The left pointer also moves from left to right at most n times.
Therefore, each character is added and removed from the window at most once.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

The HashMap stores frequencies of characters.
Since the input consists of lowercase English letters, at most 26 characters are stored.
Therefore: O(26) = O(1)

---------------------------------------------------------

Key Observation:

The window is valid when every character appears at most twice.
When adding s[right] makes its frequency exceed 2, only the left side of the window needs to be removeduntil the constraint becomes valid again.
This allows the entire string to be processed in a single pass.

---------------------------------------------------------
*/