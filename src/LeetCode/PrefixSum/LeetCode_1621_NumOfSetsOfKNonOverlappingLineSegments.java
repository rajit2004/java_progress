package LeetCode.PrefixSum;

public class LeetCode_1621_NumOfSetsOfKNonOverlappingLineSegments {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): n = 4, k = 2 -> 5
        // Segments: [0-1,2-3], [0-1,3-3], [1-1,2-3], [1-1,3-3], [2-2,3-3]
        System.out.println("Sample 1 (n=4, k=2) -> DP: " + numberOfSetsDP(4, 2)
                + " | Combinatorics: " + numberOfSets(4, 2) + " (expected: 5)");

        // Sample 2 (LeetCode): n = 3, k = 1 -> 3
        System.out.println("Sample 2 (n=3, k=1) -> DP: " + numberOfSetsDP(3, 1)
                + " | Combinatorics: " + numberOfSets(3, 1) + " (expected: 3)");

        // Sample 3 (LeetCode): n = 5, k = 2 -> C(6,4) = 15
        System.out.println("Sample 3 (n=5, k=2) -> DP: " + numberOfSetsDP(5, 2)
                + " | Combinatorics: " + numberOfSets(5, 2) + " (expected: 15)");

        // Edge case: k = 0 -> exactly one way (choose nothing)
        System.out.println("Edge (n=4, k=0) -> DP: " + numberOfSetsDP(4, 0)
                + " | Combinatorics: " + numberOfSets(4, 0) + " (expected: 1)");

        // Edge case: k = 1, n = 2 -> C(2,2) = 1
        System.out.println("Edge (n=2, k=1) -> DP: " + numberOfSetsDP(2, 1)
                + " | Combinatorics: " + numberOfSets(2, 1) + " (expected: 1)");

        // Larger case to exercise modular arithmetic: n = 1000, k = 500
        System.out.println("Large (n=1000, k=500) -> DP: " + numberOfSetsDP(1000, 500)
                + " | Combinatorics: " + numberOfSets(1000, 500));
    }


    /*
        Approach 1: DP with prefix sums

        Let dp[j] = number of ways to place the CURRENT number of segments such that the last segment ends exactly at point j (0-indexed), using points 0..j.

        Base case (1 segment):  dp[j] = 1 for all j >= 0. (a single segment can end at any point)

        Transition (i-th segment, i > 1): a segment ending at j can be preceded by any set of (i-1) segments ending at some point t < j.
        So dp[j] = sum over t < j of prevDP[t] = prefixSums[j].

        We also need to carry forward "ending exactly at j OR earlier" — that's what dp[j - 1] + prefixSums[j] does: dp[j] = dp[j-1] + prefixSums[j].

        After k iterations, dp[n-1] is the answer (any valid set of k segments ends at or before the last point n-1).

        Time:  O(n * k)
        Space: O(n)
    */
    static final int MOD = 1000000007;

    static int numberOfSetsDP(int n, int k) {
        int[] dp = new int[n];
        int[] prefixSums = new int[n + 1];

        // Base: with 1 segment, every endpoint j has exactly 1 configuration
        for (int j = 0; j < n; j++) {
            dp[j] = 1;
            prefixSums[j + 1] = (prefixSums[j] + dp[j]) % MOD;
        }

        // Add one segment at a time
        for (int i = 1; i <= k; i++) {
            dp[0] = 0;                          // cannot end at point 0 after 2+ segments
            for (int j = 1; j < n; j++) {
                dp[j] = (dp[j - 1] + prefixSums[j]) % MOD;
            }
            // Rebuild prefix sums for the next iteration
            for (int j = 0; j < n; j++) {
                prefixSums[j + 1] = (prefixSums[j] + dp[j]) % MOD;
            }
        }

        return dp[n - 1];
    }

    /*
        Approach 2: Combinatorial formula (Stars and Bars)

        Placing k non-overlapping line segments among n points is equivalent to choosing 2k endpoints from n + k - 1 slots — this reduces to the closed form:
            answer = C(n + k - 1, 2k)

        We compute it modulo 1e9+7 using Fermat's little theorem:
            C(a, b) = a! / (b! * (a-b)!)  ≡  a! * inverse(b! * (a-b)!) (mod p)

        Since a = n + k - 1 and b = 2k:
            numerator   = product of (n + k - 1), (n + k - 2), ..., (n - k)   [2k terms]
            denominator = (2k)!

        Then answer = numerator * modInverse(denominator) mod p.

        Time:  O(k + log MOD)
        Space: O(1)
    */
    static final long MOD_LONG = 1000000007L;

    static int numberOfSets(int n, int k) {
        int m = 2 * k;                    // number of factors in numerator / denominator
        long numerator = 1, denominator = 1;

        // numerator   = (n+k-1) * (n+k-2) * ... * (n-k)
        // denominator = (2k)!
        for (int i = 1; i <= m; i++) {
            numerator   = (numerator * (n + k - i)) % MOD_LONG;
            denominator = (denominator * i) % MOD_LONG;
        }

        // Fermat's little theorem: a^(p-2) ≡ a^(-1) (mod p) for prime p
        return (int) ((numerator * quickPow(denominator, MOD_LONG - 2)) % MOD_LONG);
    }

    /*
        Modular exponentiation: (a^e) mod MOD_LONG, in O(log e).
        Used to compute the modular inverse via Fermat's little theorem.
    */
    static long quickPow(long a, long e) {
        long result = 1;
        while (e > 0) {
            if ((e & 1) != 0) result = (result * a) % MOD_LONG;
            a = (a * a) % MOD_LONG;
            e >>= 1;
        }
        return result;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: DP with prefix sums

Time Complexity: O(n * k)

- Outer loop over k segments; inner loop over n points for dp + n points for prefix sums.

Space Complexity: O(n)

- dp[] and prefixSums[] each of size n (plus 1).


Approach 2: Combinatorial formula (Stars and Bars)

Time Complexity: O(k + log MOD)

- 2k iterations to compute numerator and denominator.
- quickPow runs in O(log MOD) ≈ 30 iterations.

Space Complexity: O(1)

- Only a few long variables.

Key Observation:
Choosing k non-overlapping segments among n points on a line reduces to a stars-and-bars count: C(n + k - 1, 2k). The DP approach is an alternate derivation of the same closed form, useful when modular inverse isn't available or when the recurrence itself needs to be inspected.

---------------------------------------------------------
*/