package LeetCode.SlidingWindow;

public class LeetCode_1358_NumSubStringHavingAll3Charas {
    public static void main(String[] args) {

        String s = "abcabc";

        System.out.println(numberOfSubstrings(s));
    }

    /*
        Sliding Window Approach : Maintain a window [left, right].
            Expand the window by moving right.
            Whenever the window contains at least one:
                'a'
                'b'
                'c'
        then every substring starting from current left and ending at right or beyond will also be valid.

        Therefore, add: n - right to the answer and shrink the window from the left.
     */

    static int numberOfSubstrings(String s) {

        int n = s.length();

        // Stores frequency of 'a', 'b' and 'c' inside the current window.
        int[] count = new int[3];

        // Left boundary of the sliding window.
        int left = 0;

        // Stores total number of valid substrings.
        int result = 0;

        // Expand the window.
        for (int right = 0; right < n; right++) {

            // Include current character in the window.
            count[s.charAt(right) - 'a']++;

//                While the current window contains at least one 'a', one 'b' and one 'c'.
            while (count[0] > 0 &&
                    count[1] > 0 &&
                    count[2] > 0) {

                /*
                    Current window is valid.

                    Since extending the window further will still keep it valid, all substrings:

                        [left...right]
                        [left...right+1]
                        ...
                        [left...n-1]

                    are also valid.

                    Number of such substrings: n - right
                 */
                result += (n - right);

                // Shrink the window from the left.
                count[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return result;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = s.length()

---------------------------------------------------------

Time Complexity: O(n)

Reason: Each character enters the sliding window once and leaves the window at most once.

Therefore: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: The frequency array always stores counts for only: 'a' or 'b' or 'c'

Size remains constant: count[3]
Hence: O(1)

---------------------------------------------------------

Key Observation:

Once a window contains all three characters: 'a', 'b' and 'c'

any extension of this window to the right will also remain valid.

Thus, instead of checking every substring explicitly, we can directly add: n - right to the answer.

This reduces the brute-force O(n²) solution to an O(n) sliding window solution.

---------------------------------------------------------
*/