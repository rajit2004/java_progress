package LeetCode.SlidingWindow;

public class ALT_LeetCode_3090_MaxLenSubStringWith2Occurance {
    public static void main(String[] args) {

        String s = "bcbbbcba";

        System.out.println(maximumLengthSubstring(s));
    }

    /*
        Optimized Sliding Window Approach : Since the input contains only lowercase English characters, we can use a fixed-size frequency array instead of a HashMap.

        For every character:
            1. Add it to the current window.
            2. If its frequency becomes greater than 2, shrink the window from the left.
            3. Continue until the current character appears at most twice.
            4. Track the maximum valid window length.

        The expression: (s.charAt(r) & 31) - 1 maps lowercase characters 'a' to 'z' to indices 0 to 25.
     */
    static int maximumLengthSubstring(String s) {

        // Stores frequency of each lowercase character.
        int[] frequency = new int[26];

        int left = 0;
        int maxLength = 0;

        // Expand the sliding window using right pointer.
        for (int right = 0; right < s.length(); right++) {

            // Convert current character to index 0-25.
            int currentIndex =(s.charAt(right) & 31) - 1;

            // Add the current character to the window.
            frequency[currentIndex]++;

//  If the current character appears more than twice, shrink the window from left until the frequency becomes valid again.
            while (frequency[currentIndex] > 2) {
                int leftIndex = (s.charAt(left) & 31) - 1;
                frequency[leftIndex]--;
                left++;
            }
            // Update the maximum valid window length.
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
The left pointer also moves forward at most n times.
Therefore, every character is processed a constant number of times.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: The frequency array always contains exactly 26 entries for lowercase English letters.

Overall: O(26) = O(1)

---------------------------------------------------------

Key Observation:

Because the input contains only lowercase English characters, a fixed-size array is more efficient than a HashMap.
The sliding window remains valid when every character appears at most twice.
When the current character exceeds frequency 2, we move the left pointer until the constraint is restored.

---------------------------------------------------------
*/