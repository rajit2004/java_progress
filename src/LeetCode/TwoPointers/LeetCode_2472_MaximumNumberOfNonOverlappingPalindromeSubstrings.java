package LeetCode.TwoPointers;

public class LeetCode_2472_MaximumNumberOfNonOverlappingPalindromeSubstrings {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "abaccdbbd", k = 3 -> at most 2 non-overlapping palindromes of length >= 3
        System.out.println("Sample 1 -> DP: " + maxPalindromesDP("abaccdbbd", 3)
                + " | Greedy: " + maxPalindromes("abaccdbbd", 3) + " (expected: 2)");

        // Sample 2 (LeetCode): "adbcda", k = 2 -> {ad? no, db? no, bc? no, cd? no, da? no} -> wait, k=2 => {d b? no} actually count
        System.out.println("Sample 2 -> DP: " + maxPalindromesDP("adbcda", 2)
                + " | Greedy: " + maxPalindromes("adbcda", 2) + " (expected: 0)");

        // Sample 3 (LeetCode): "aababaab", k = 3 -> "aba" and "baab" -> 2
        System.out.println("Sample 3 -> DP: " + maxPalindromesDP("aababaab", 3)
                + " | Greedy: " + maxPalindromes("aababaab", 3) + " (expected: 2)");

        // Edge case: k > n -> impossible to pick any
        System.out.println("Edge (k > n) -> DP: " + maxPalindromesDP("abc", 5)
                + " | Greedy: " + maxPalindromes("abc", 5) + " (expected: 0)");

        // Edge case: whole string is a palindrome and k fits
        System.out.println("Edge (whole palindrome) -> DP: " + maxPalindromesDP("aaaa", 4)
                + " | Greedy: " + maxPalindromes("aaaa", 4) + " (expected: 1)");

        // Edge case: many small palindromes "aaaa" k=2 -> "aa" + "aa" = 2
        System.out.println("Edge (aaaa, k=2) -> DP: " + maxPalindromesDP("aaaa", 2)
                + " | Greedy: " + maxPalindromes("aaaa", 2) + " (expected: 2)");
    }


    /*
        Approach 1: Palindrome table + linear DP

        Step 1: Precompute isPalindrome[i][j] = true iff s[i..j] is a palindrome.
            - Base: length 1 and 2 palindromes (len <= 2 -> just char equality).
            - Recurrence: isPalindrome[i][j] = (s[i] == s[j]) && isPalindrome[i+1][j-1].

        Step 2: dp[i] = max number of non-overlapping palindromic substrings of length >= k, using only s[0..i-1].

            Transition: either skip position i-1 -> dp[i-1] or take a palindrome ending at i-1 and starting at j (with length >= k): dp[j] + 1.

        Answer: dp[n].

        Time: O(n^2) for the palindrome table + O(n^2) for the DP.
        Space: O(n^2) for the table.
    */
    static int maxPalindromesDP(String s, int k) {
        int n = s.length();

        // isPalindrome[left][right] = true iff s[left..right] is a palindrome
        boolean[][] isPalindrome = new boolean[n][n];

        for (int len = 1; len <= n; ++len) {
            for (int left = 0; left + len <= n; ++left) {
                int right = left + len - 1;
                isPalindrome[left][right] =
                        s.charAt(left) == s.charAt(right) &&
                                (len <= 2 || isPalindrome[left + 1][right - 1]);
            }
        }

        // dp[i] = best answer using prefix s[0..i-1]
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            dp[i] = dp[i - 1];                          // skip s[i-1]
            for (int j = 0; j + k <= i; ++j) {          // length i - j >= k
                if (isPalindrome[j][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n];
    }

    /*
        Approach 2: Greedy scan with two-pointer palindrome check

        Key observation: a greedy strategy works here — always take the EARLIEST-ENDING palindrome (of length k or k+1) that starts at or after the current `start`. This maximizes the remaining room for subsequent palindromes.

        We only need to check windows of length exactly k or k+1, because:
            - Any longer palindrome contains a shorter palindromic core.
            - For even-length palindromes the minimal useful length is k.
            - For odd-length palindromes if k is even we may need one extra character, hence k+1.

        Scan r from k-1 to n-1:
            - try window [r-k+1, r] of length k
            - if not a palindrome, try window [r-k, r] of length k+1
            - if either succeeds (and starts at/after `start`), take it, increment answer, and move `start` to r+1 so we don't reuse chars.

        This runs in O(n * k) time and O(1) extra space.

        Example: s = "aababaab", k = 3
            - r = 2: [0..2] = "aab" -> not palindrome
            - r = 3: [1..3] = "aba" -> palindrome -> take it, start = 4
            - r = 7: [5..7] = "aab" -> no; [4..7] = "baab" -> yes -> take it -> ans = 2
    */
    static int maxPalindromes(String s, int k) {
        int n = s.length();
        int ans = 0, start = 0;

        for (int r = k - 1; r < n; ++r) {
            int l = r - k + 1;
            if (l >= start && isPalindrome(s, l, r)) {
                ++ans;
                start = r + 1;      // next candidate must start after this palindrome
                continue;
            }

            // Try length k+1 (needed for some odd/even alignments)
            l = r - k;
            if (l >= start && isPalindrome(s, l, r)) {
                ++ans;
                start = r + 1;
            }
        }

        return ans;
    }

    /*
        Two-pointer palindrome check for s[l..r] inclusive.
        Returns true iff the substring is a palindrome.
    */
    static boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) {
                return false;
            }
        }
        return true;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Palindrome table + linear DP

Time Complexity: O(n^2)

- Building isPalindrome takes O(n^2).
- DP loop is O(n^2) (outer i, inner j).

Space Complexity: O(n^2)

- isPalindrome[n][n] dominates.
- dp is O(n).


Approach 2: Greedy scan with two-pointer palindrome check

Time Complexity: O(n * k)

- Outer loop runs O(n) times; each palindrome check costs O(k).

Space Complexity: O(1)

- Only a few integer variables; no extra data structures.

Key Observation:
Greedily taking the earliest-ending palindrome of length k or k+1 starting at/after the current cursor maximizes room for later picks, so a single left-to-right pass suffices. Only lengths k and k+1 need checking because any longer palindrome contains a shorter palindromic core of those lengths.

---------------------------------------------------------
*/