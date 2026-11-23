package LeetCode.Strings;

import java.util.*;

public class LeetCode_2911_MinChangesToMakeKSemiPalindromes {

    public static void main(String[] args) {
        String s = "abcac";
        int k = 2;
        System.out.println(minimumChangesIterative(s, k));
        System.out.println(minimumChangesRecursive(s, k));
    }

    /* Approach 1: Iterative DP over segments
       Step 1 - cost[l][r]: minimum character changes to turn s[l..r] into a
       semi-palindrome. For every proper divisor d of the length m = r-l+1,
       split the substring into d interleaved groups (offset = 0..d-1) and
       count mismatches between group positions t and (groups-1-t), i.e. pair
       characters from opposite ends of each group. Take the minimum over d.
       Step 2 - partition DP: prev[start] = best total cost to cover the suffix
       s[start..n-1] using seg segments. Roll seg from 2 to k, and for each
       start try every possible end e of the first segment, adding
       cost[start][e] + prev[e+1]. Only allow e up to n-1-2*(seg-1) so there
       is room for the remaining (seg-1) segments, each of length >= 2.
       Example: s = "abcac", k = 2 -> cost table gives
                ["abc" | "ac"] -> 1 change -> answer 1.
    */
    static int minimumChangesIterative(String s, int k) {
        int n = s.length();
        char[] ch = s.toCharArray();
        List<Integer>[] factors = buildFactors(n);
        int[][] cost = buildCost(ch, factors);

        int[] prev = new int[n];
        for (int start = 0; start < n; start++) {
            prev[start] = cost[start][n - 1];  // seg = 1
        }

        for (int seg = 2; seg <= k; seg++) {
            int[] cur = new int[n];
            Arrays.fill(cur, n);

            int limit = n - 1 - 2 * (seg - 1);   // leave room for seg-1 segments
            for (int start = 0; start + 2 * seg <= n; start++) {
                for (int e = start + 1; e <= limit; e++) {
                    cur[start] = Math.min(cur[start], cost[start][e] + prev[e + 1]);
                }
            }
            prev = cur;
        }

        return prev[0];
    }

    /* Approach 2: Memoized recursion
       Same cost[l][r] table as Approach 1, but the partition DP is expressed
       as a top-down recursion solve(seg, start): "using seg segments, cover
       s[start..n-1]". Base case seg == 1 returns cost[start][n-1]; otherwise
       try every end e of the first segment (again bounded so seg-1 segments
       of length >= 2 fit), recurse on e+1, and memoise the best in
       memo[seg][start]. Equivalent to Approach 1, just written recursively.
       Example: s = "abcac", k = 2 -> 1 (same answer).
    */
    static int minimumChangesRecursive(String s, int k) {
        int n = s.length();
        char[] ch = s.toCharArray();
        List<Integer>[] factors = buildFactors(n);
        int[][] cost = buildCost(ch, factors);

        int[][] memo = new int[k + 1][n];
        for (int seg = 0; seg <= k; seg++) Arrays.fill(memo[seg], -1);

        return solve(k, 0, n, cost, memo);
    }

    static int solve(int seg, int start, int n, int[][] cost, int[][] memo) {
        if (seg == 1) return cost[start][n - 1];
        if (memo[seg][start] != -1) return memo[seg][start];

        int best = n;
        int limit = n - 1 - 2 * (seg - 1);
        for (int e = start + 1; e <= limit; e++) {
            best = Math.min(best, cost[start][e] + solve(seg - 1, e + 1, n, cost, memo));
        }

        memo[seg][start] = best;
        return best;
    }

    // factors[m] = proper divisors of m (each >= 1, m/1 excluded because a
    // semi-palindrome block must have d groups with >= 2 characters each).
    @SuppressWarnings("unchecked")
    static List<Integer>[] buildFactors(int n) {
        List<Integer>[] factors = new ArrayList[n + 1];
        Arrays.setAll(factors, x -> new ArrayList<>());
        for (int d = 1; d <= n / 2; d++) {
            for (int m = d * 2; m <= n; m += d) {
                factors[m].add(d);
            }
        }
        return factors;
    }

    // cost[l][r] = minimum changes to make s[l..r] a semi-palindrome.
    static int[][] buildCost(char[] s, List<Integer>[] factors) {
        int n = s.length;
        int[][] cost = new int[n][n];
        for (int l = 0; l + 1 < n; l++) {
            for (int r = l + 1; r < n; r++) {
                cost[l][r] = segmentCost(s, l, r - l + 1, factors);
            }
        }
        return cost;
    }

    // For a block s[l..l+m-1], try every proper divisor d of m and count
    // mismatches when pairing group positions t and (groups-1-t).
    static int segmentCost(char[] s, int l, int m, List<Integer>[] factors) {
        int best = m;
        for (int d : factors[m]) {
            int groups = m / d;
            int changes = 0;
            for (int offset = 0; offset < d; offset++) {
                for (int t = 0; t < groups / 2; t++) {
                    if (s[l + offset + t * d] != s[l + offset + (groups - 1 - t) * d]) {
                        changes++;
                    }
                }
            }
            best = Math.min(best, changes);
        }
        return best;
    }
}

/* Complexity Analysis
   Let n = s.length().

   Shared preprocessing (both approaches)
     factors  : O(n log n)  - divisor sieve over multiples.
     cost     : O(n^3)      - O(n^2) substrings, each costed in O(length) by
                iterating divisors d of the length and pairing characters.
     Space    : O(n^2)      - cost table.

   Approach 1: Iterative DP over segments
     Time : O(n^3 + k * n^2)  - cost table + k-layer partition DP, each layer
            O(n^2) transitions.
     Space: O(n^2)            - cost + two O(n) DP rows.
     Key Observation: cost[l][r] is independent of k, so it is precomputed once
     and the partition DP just chains segment costs.

   Approach 2: Memoized recursion
     Time : O(n^3 + k * n^2)  - same asymptotic work as Approach 1; memo
            ensures each (seg, start) state is solved once.
     Space: O(n^2 + k*n)      - cost table + memo table + recursion depth O(k).
     Key Observation: identical DP expressed top-down; prefer Approach 1 to
     avoid recursion overhead if stack depth or constants matter.
*/

