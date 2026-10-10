package LeetCode.Arrays;

import java.util.*;

public class LeetCode_2333_MinSumOfSquaredDifference {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): nums1 = [1,2,3,4], nums2 = [2,10,20,19], k1=0, k2=0
        //   diffs = [1,8,17,15] -> sum of squares = 1+64+289+225 = 579
        System.out.println("Sample 1 -> BinarySearch: " + minSumSquareDiffBinarySearch(
                new int[]{1,2,3,4}, new int[]{2,10,20,19}, 0, 0)
                + " | SortGreedy: " + minSumSquareDiff(
                new int[]{1,2,3,4}, new int[]{2,10,20,19}, 0, 0)
                + " (expected: 579)");

        // Sample 2 (LeetCode): nums1 = [1,4,10,12], nums2 = [5,8,6,9], k1=1, k2=1
        //   diffs = [4,4,4,3] -> best with 2 reductions = [3,4,4,3]? No — [4,4,4,3] -> reduce one 4 to 3 and another to 3 -> [3,3,4,3] -> 9+9+16+9 = 43
        System.out.println("Sample 2 -> BinarySearch: " + minSumSquareDiffBinarySearch(
                new int[]{1,4,10,12}, new int[]{5,8,6,9}, 1, 1)
                + " | SortGreedy: " + minSumSquareDiff(
                new int[]{1,4,10,12}, new int[]{5,8,6,9}, 1, 1)
                + " (expected: 43)");

        // Sample 3 (LeetCode): identical arrays -> 0
        System.out.println("Sample 3 -> BinarySearch: " + minSumSquareDiffBinarySearch(
                new int[]{1,1,1}, new int[]{1,1,1}, 0, 0)
                + " | SortGreedy: " + minSumSquareDiff(
                new int[]{1,1,1}, new int[]{1,1,1}, 0, 0)
                + " (expected: 0)");

        // Edge case: k huge -> reduce everything to 0
        System.out.println("Edge (k huge) -> BinarySearch: " + minSumSquareDiffBinarySearch(
                new int[]{1,2,3}, new int[]{100,100,100}, 1000, 1000)
                + " | SortGreedy: " + minSumSquareDiff(
                new int[]{1,2,3}, new int[]{100,100,100}, 1000, 1000)
                + " (expected: 0)");

        // Edge case: single element, partial reduction
        //   diff = 10, k = 3 -> best is 7 -> 49
        System.out.println("Edge (single) -> BinarySearch: " + minSumSquareDiffBinarySearch(
                new int[]{10}, new int[]{0}, 1, 2)
                + " | SortGreedy: " + minSumSquareDiff(
                new int[]{10}, new int[]{0}, 1, 2)
                + " (expected: 49)");

        // Edge case: all diffs equal, k exactly covers one level
        //   diffs = [5,5,5], k = 3 -> [4,4,4] -> 48
        System.out.println("Edge (equal diffs) -> BinarySearch: " + minSumSquareDiffBinarySearch(
                new int[]{5,5,5}, new int[]{0,0,0}, 3, 0)
                + " | SortGreedy: " + minSumSquareDiff(
                new int[]{5,5,5}, new int[]{0,0,0}, 3, 0)
                + " (expected: 48)");

        // Edge case: alternating extremes
        //   diffs = [10, 0, 10, 0], k = 4 -> reduce 10s to 8s -> [8,0,8,0] -> 128
        System.out.println("Edge (alternating) -> BinarySearch: " + minSumSquareDiffBinarySearch(
                new int[]{10,0,10,0}, new int[]{0,0,0,0}, 2, 2)
                + " | SortGreedy: " + minSumSquareDiff(
                new int[]{10,0,10,0}, new int[]{0,0,0,0}, 2, 2)
                + " (expected: 128)");
    }


    /*
        Approach 1: Binary search on threshold + greedy top-k reduction

        Step 1: Compute absolute differences |nums1[i] - nums2[i]| and track
                the maximum difference.

        Step 2: Binary search for the LARGEST threshold `res` such that the
                total cost to bring every element down to `res` is <= k:
                    cost(res) = sum over all diffs d of max(d - res, 0)

                This is monotonic in `res` (smaller res -> larger cost), so
                binary search applies.

        Step 3: Subtract the cost of bringing all elements above `res` down
                to `res` from k. The remaining k is guaranteed to be less
                than the count of elements that were above `res`, so each
                such element can be reduced by at most 1 more.

        Step 4: Sort ascending, iterate from largest to smallest. For each
                element, cap it at `res`, and if k > 0, reduce by 1 more
                (use up the remaining budget).

        Step 5: Sum the squares of the final values.

        Time:  O(n log maxDiff) for the binary search + O(n log n) for the sort
        Space: O(1) auxiliary (in-place mutations on nums1)
    */
    static long minSumSquareDiffBinarySearch(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long k = (long) k1 + k2;            // cast to long to avoid any overflow edge case
        int maxDif = 0;
        long totalSum = 0;

        // Step 1: compute absolute differences and track max
        for (int i = 0; i < n; i++) {
            nums1[i] = Math.abs(nums1[i] - nums2[i]);
            maxDif = Math.max(maxDif, nums1[i]);
            totalSum += nums1[i];
        }

        // Early exit: if we can zero everything, the answer is trivially 0
        if (totalSum <= k) return 0;

        // Step 2: binary search for the largest threshold with cost <= k
        int lo = 0, hi = maxDif, threshold = 0;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            long cost = 0;
            for (int num : nums1) {
                cost += num > mid ? num - mid : 0;
            }
            if (cost <= k) {
                hi = mid - 1;
                threshold = mid;
            } else {
                lo = mid + 1;
            }
        }

        // Step 3: spend the cost of capping everyone at `threshold`
        for (int num : nums1) {
            if (num > threshold) {
                k -= num - threshold;
            }
        }

        // Step 4: sort and use the leftover budget on the largest elements
        Arrays.sort(nums1);
        long ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            long diff = Math.min(nums1[i], threshold);
            if (k > 0 && diff > 0) {
                diff--;                     // one more reduction
                k--;
            }
            ans += diff * diff;
        }

        return ans;
    }


    /*
        Approach 2: Sort descending + level-by-level greedy consumption

        Step 1: Compute absolute differences and their sum. If sum <= k,
                we can zero everything -> return 0.

        Step 2: Sort ascending and build `d` in DESCENDING order (largest
                diff first). Append a sentinel 0 at the end so we have a
                "target level" for the final iteration.

        Step 3: Walk through the sorted array. At index i, `d[i-1]` is the
                current top level and `d[i]` is the next level down. The
                cost to bring the top `i` elements from `d[i-1]` to `d[i]`
                is:
                    cost = (d[i-1] - d[i]) * i

                If cost <= k: pay it, decrement k, and continue to the
                next level.

                If cost > k: we can't fully reach the next level. Instead,
                distribute the remaining k reductions among the top `i`
                elements:
                    q = k / i   (whole levels we can shave off)
                    r = k % i   (extras of 1 for r elements)
                    hi = d[i-1] - q
                So (i - r) elements become hi, and r elements become hi - 1.

                Add their squared contributions plus the untouched tail
                (d[i..n-1]) squared, and return.

        Step 4: If we somehow fall through (k exhausted exactly on a level
                boundary and no elements remain), return 0.

        Time:  O(n log n) for the sort + O(n) for the level walk
        Space: O(n) for the descending array `d`
    */
    static long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long k = (long) k1 + k2;
        int n = nums1.length;

        // Step 1: compute absolute differences and their sum
        long sum = 0;
        for (int i = 0; i < n; i++) {
            nums1[i] = Math.abs(nums1[i] - nums2[i]);
            sum += nums1[i];
        }
        if (sum <= k) return 0;             // can zero everything

        // Step 2: sort and build descending array with a 0 sentinel
        Arrays.sort(nums1);
        int[] d = new int[n + 1];
        for (int i = 0; i < n; i++) {
            d[i] = nums1[n - 1 - i];        // descending
        }
        // d[n] = 0 by default -> the sentinel target for the last iteration

        // Step 3: level-by-level consumption
        for (int i = 1; i <= n; i++) {
            long cost = (long) (d[i - 1] - d[i]) * i;
            if (cost > k) {
                // We can't fully descend to level d[i]; distribute the
                // remaining k among the top i elements.
                long q = k / i;             // full levels we can shave
                long r = k % i;             // extras (each a single 1)
                long hi = d[i - 1] - q;     // new height for most elements

                // (i - r) elements at hi, r elements at hi - 1
                long ans = hi * hi * (i - r) + (hi - 1) * (hi - 1) * r;

                // Add the untouched tail (elements below the top i)
                for (int j = i; j < n; j++) {
                    ans += (long) d[j] * d[j];
                }
                return ans;
            }
            k -= cost;                      // fully pay to descend this level
        }

        // Fallthrough: k exhausted exactly at a boundary -> everything zero
        return 0;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let n = nums1.length, M = max |nums1[i] - nums2[i]|.

Approach 1: Binary search on threshold + greedy top-k reduction

Time Complexity: O(n log M + n log n)

- Computing diffs: O(n)
- Binary search over [0, M]: O(log M) iterations, each O(n) -> O(n log M)
- Sorting for the leftover pass: O(n log n)

Space Complexity: O(1) auxiliary

- Mutates nums1 in place; no extra data structures.

Approach 2: Sort descending + level-by-level greedy consumption

Time Complexity: O(n log n)

- Computing diffs: O(n)
- Sort: O(n log n)
- Level walk: O(n) amortized

Space Complexity: O(n)

- Descending array d of size n+1.

Key Observation:

The problem reduces to: "given n non-negative values, reduce them by a
total of at most k, minimizing the sum of squares." The optimal strategy
is always to reduce the LARGEST values first, because d/dx (x^2) = 2x —
the marginal gain from reducing a larger value is greater.

Both approaches exploit this by working with the sorted magnitude
distribution:
    - Approach 1 binary-searches the "final threshold" that all large
      values get capped at, then spends leftovers on the very top ones.
    - Approach 2 walks down the sorted levels, paying level by level
      until k runs out, then distributing the remainder evenly.

Both are correct and efficient. Approach 2 is typically faster in practice
(one sort, one linear walk) while Approach 1 has O(1) auxiliary space.

---------------------------------------------------------
*/