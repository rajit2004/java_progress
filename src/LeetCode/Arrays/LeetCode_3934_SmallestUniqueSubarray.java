package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3934_SmallestUniqueSubarray {

    public static void main(String[] args) {
        System.out.println(smallestUniqueSubarrayHash(new int[]{1, 2, 1, 3, 2}) + " " + smallestUniqueSubarraySA(new int[]{1, 2, 1, 3, 2}));
        System.out.println(smallestUniqueSubarrayHash(new int[]{1, 2, 1, 2})    + " " + smallestUniqueSubarraySA(new int[]{1, 2, 1, 2}));
        System.out.println(smallestUniqueSubarrayHash(new int[]{1, 1, 1})       + " " + smallestUniqueSubarraySA(new int[]{1, 1, 1}));
    }

    /* Approach 1: Suffix array + LCP
       Build the suffix array of nums using a doubling (prefix-doubling) sort
       with two counting-sort passes per round (second key first, then first
       key). Build the LCP array with Kasai's algorithm. The shortest unique
       substring starting at position sa[i] must be longer than both of its
       adjacent LCP values, i.e. its length is
         need = max(lcp[i], lcp[i+1]) + 1
       where lcp[i] = LCP(sa[i-1], sa[i]) and lcp[i+1] = LCP(sa[i], sa[i+1]).
       If the suffix is long enough to contain such a prefix, `need` is a
       valid answer candidate; take the minimum over all suffixes.
       Example: nums = [1,2,1,3,2] -> answer 1 (the value 3 is unique).
    */
    static int smallestUniqueSubarraySA(int[] nums) {
        int n = nums.length;

        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] nextRank = new int[n];
        int[] temp = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = nums[i];
        }

        int maxRank = 0;
        for (int x : nums) maxRank = Math.max(maxRank, x);

        int cntSize = Math.max(n + 1, maxRank + 2);
        int[] cnt = new int[cntSize];

        for (int k = 1; k < n; k <<= 1) {
            final int kk = k;

            // Sort by the second key first.
            Arrays.fill(cnt, 0);
            for (int i : sa) {
                int key = (i + kk < n ? rank[i + kk] : -1) + 1;
                cnt[key]++;
            }
            for (int i = 1; i <= maxRank + 1; i++) cnt[i] += cnt[i - 1];
            for (int p = n - 1; p >= 0; p--) {
                int i = sa[p];
                int key = (i + kk < n ? rank[i + kk] : -1) + 1;
                temp[--cnt[key]] = i;
            }
            int[] swap = sa; sa = temp; temp = swap;

            // Then stably sort by the first key.
            Arrays.fill(cnt, 0);
            for (int i : sa) {
                int key = rank[i] + 1;
                cnt[key]++;
            }
            for (int i = 1; i <= maxRank + 1; i++) cnt[i] += cnt[i - 1];
            for (int p = n - 1; p >= 0; p--) {
                int i = sa[p];
                int key = rank[i] + 1;
                temp[--cnt[key]] = i;
            }
            swap = sa; sa = temp; temp = swap;

            nextRank[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                int a = sa[i - 1], b = sa[i];
                int ax = rank[a], ay = a + kk < n ? rank[a + kk] : -1;
                int bx = rank[b], by = b + kk < n ? rank[b + kk] : -1;
                nextRank[b] = nextRank[a] + (ax != bx || ay != by ? 1 : 0);
            }

            int[] t = rank; rank = nextRank; nextRank = t;
            maxRank = rank[sa[n - 1]];

            if (maxRank == n - 1) break;
        }

        // Kasai LCP.
        int[] pos = new int[n];
        int[] lcp = new int[n];
        for (int i = 0; i < n; i++) pos[sa[i]] = i;

        int h = 0;
        for (int i = 0; i < n; i++) {
            if (pos[i] == 0) { h = 0; continue; }
            int j = sa[pos[i] - 1];
            while (i + h < n && j + h < n && nums[i + h] == nums[j + h]) h++;
            lcp[pos[i]] = h;
            if (h > 0) h--;
        }

        int answer = n;
        for (int i = 0; i < n; i++) {
            int next = i + 1 < n ? lcp[i + 1] : 0;
            int need = Math.max(lcp[i], next) + 1;
            if (sa[i] + need - 1 < n) answer = Math.min(answer, need);
        }

        return answer;
    }

    /* Approach 2: Rolling hash + binary search on length (optimal)
       The property "there exists a unique subarray of length L" is monotone:
       if some length-L subarray occurs exactly once, then extending it to
       length L+1 (when it fits) gives a unique (L+1)-subarray. So binary
       search the smallest L whose `check(L)` returns true. For each L, hash
       every length-L window with a modular rolling hash and count
       occurrences; a count of 1 means a unique subarray exists.
       Example: nums = [1,2,1,2] -> L=1 all values repeat, L=2 has "21" once
                -> answer 2.
    */
    static int smallestUniqueSubarrayHash(int[] nums) {
        final long MOD = 1_000_000_007L;
        final long BASE = 911_382_323L;

        int n = nums.length;
        long[] h = new long[n + 1];
        long[] p = new long[n + 1];
        p[0] = 1;

        for (int i = 0; i < n; i++) {
            h[i + 1] = (h[i] * BASE + nums[i]) % MOD;
            p[i + 1] = (p[i] * BASE) % MOD;
        }

        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (hasUniqueOfLength(mid, n, h, p, MOD)) hi = mid;
            else                                       lo = mid + 1;
        }

        return lo;
    }

    static boolean hasUniqueOfLength(int len, int n, long[] h, long[] p, long MOD) {
        Map<Long, Integer> cnt = new HashMap<>();

        for (int l = 0; l + len <= n; l++) {
            int r = l + len;
            long key = (h[r] - (h[l] * p[len]) % MOD + MOD) % MOD;
            cnt.merge(key, 1, Integer::sum);
        }

        for (int c : cnt.values()) {
            if (c == 1) return true;
        }
        return false;
    }
}

/* Complexity Analysis
   Let n = nums.length, V = max(nums[i]).

   Approach 1: Suffix array + LCP
     Time : O(n log n)  - O(log n) doubling rounds, each a pair of O(n + V)
            counting-sort passes; Kasai's LCP is O(n).
     Space: O(n + V)    - sa, rank, nextRank, temp, cnt, pos, lcp arrays.
     Key Observation: a substring starting at suffix sa[i] is unique iff its
     length is greater than both neighbouring LCPs; the shortest such prefix
     for sa[i] has length max(lcp[i], lcp[i+1]) + 1, so the answer is the
     minimum of those across all suffixes that can actually supply it.

   Approach 2: Rolling hash + binary search (recommended)
     Time : O(n log n)  - O(log n) binary-search steps, each hashing O(n)
            windows with an O(1) rolling-hash query, plus O(n) HashMap work.
     Space: O(n)        - prefix hash and power arrays plus a HashMap of up to
            n entries per check.
     Key Observation: "a unique subarray of length L exists" is monotone in L
     (extending a unique window stays unique), so binary search on L is valid
     and the per-L check is a single pass with rolling hashes; hash collisions
     are the only correctness caveat and are negligible under a prime modulus
     with a large base.
*/

