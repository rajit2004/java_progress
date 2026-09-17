package LeetCode.DynamicProgramming;

import java.util.*;

public class LeetCode_1477_FindTwoNonOverlappingSubArraysEachWithTargetSum {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): [3,2,2,4,3], target = 3 -> [3] and [3] -> 2 + 2 = 4
        System.out.println("Sample 1 -> HashMap: " + minSumOfLengthsHashMap(new int[]{3,2,2,4,3}, 3)
                + " | SlidingWindow: " + minSumOfLengths(new int[]{3,2,2,4,3}, 3) + " (expected: 2)");

        // Sample 2 (LeetCode): [7,3,4,7], target = 7 -> [7] and [7] -> 1 + 1 = 2
        System.out.println("Sample 2 -> HashMap: " + minSumOfLengthsHashMap(new int[]{7,3,4,7}, 7)
                + " | SlidingWindow: " + minSumOfLengths(new int[]{7,3,4,7}, 7) + " (expected: 2)");

        // Sample 3 (LeetCode): [4,3,2,6,2,3,4], target = 6 -> [4,2] and [2,4] -> 3 + 3 = 6
        System.out.println("Sample 3 -> HashMap: " + minSumOfLengthsHashMap(new int[]{4,3,2,6,2,3,4}, 6)
                + " | SlidingWindow: " + minSumOfLengths(new int[]{4,3,2,6,2,3,4}, 6) + " (expected: -1)");

        // Sample 4 (LeetCode): [5,5,4,4,5], target = 3 -> no subarray sums to 3 -> -1
        System.out.println("Sample 4 -> HashMap: " + minSumOfLengthsHashMap(new int[]{5,5,4,4,5}, 3)
                + " | SlidingWindow: " + minSumOfLengths(new int[]{5,5,4,4,5}, 3) + " (expected: -1)");

        // Sample 5 (LeetCode): [3,1,1,1,5,1,2,1], target = 3 -> [3] and [1,2] or [2,1] -> 1 + 2 = 3
        System.out.println("Sample 5 -> HashMap: " + minSumOfLengthsHashMap(new int[]{3,1,1,1,5,1,2,1}, 3)
                + " | SlidingWindow: " + minSumOfLengths(new int[]{3,1,1,1,5,1,2,1}, 3) + " (expected: 3)");

        // Edge case: no two non-overlapping subarrays exist
        System.out.println("Edge (no pair) -> HashMap: " + minSumOfLengthsHashMap(new int[]{1,1,1,1}, 100)
                + " | SlidingWindow: " + minSumOfLengths(new int[]{1,1,1,1}, 100) + " (expected: -1)");

        // Edge case: only one subarray sums to target -> cannot pick two
        System.out.println("Edge (one match) -> HashMap: " + minSumOfLengthsHashMap(new int[]{2,2,3}, 3)
                + " | SlidingWindow: " + minSumOfLengths(new int[]{2,2,3}, 3) + " (expected: -1)");
    }


    /*
        Approach 1: Prefix sum + HashMap (in-place DP)

        We use a prefix-sum map to find, for each right end i, the shortest subarray ending at i whose sum equals target.

        Let minL = shortest subarray length ending at or before position i that sums to target.
        We store minL back into arr[i] itself (arr[i] = minL) — this is a deliberate in-place DP trick.

        When a new subarray [j+1 .. i] of length `len` sums to target is found:
            - j == -1 means the subarray starts at index 0, so there's no earlier subarray to pair with → we add `n` (effectively "infinity").
            - Otherwise, arr[j] holds the shortest target-sum subarray length found up to index j, which gives us the best pair ending before j.

        Answer = min over all valid pairs of (len + earlier_min_len).

        Time:  O(n) (single pass, O(1) average hashmap ops)
        Space: O(n) (hashmap + in-place array mutation)

        Note: the array is mutated in place — call on a copy if you need the original preserved.
    */
    static int minSumOfLengthsHashMap(int[] arr, int target) {
        Map<Integer, Integer> pos = new HashMap<>();
        pos.put(0, -1);                 // prefix sum 0 occurs "before" index 0

        int n = arr.length;
        int s = 0;
        int ans = n + 1;                // sentinel "infinity"
        int minL = n;                   // shortest target-sum subarray length seen so far

        for (int i = 0; i < n; i++) {
            s += arr[i];

            // If (current prefix - target) is a known prefix, arr[j+1 .. i] sums to target
            if (pos.containsKey(s - target)) {
                int j = pos.get(s - target);
                int len = i - j;

                // Pair with best earlier subarray. If j == -1, no earlier space → use n.
                ans = Math.min(ans, len + (j == -1 ? n : arr[j]));

                // Update the running shortest length
                minL = Math.min(minL, len);
            }

            arr[i] = minL;              // in-place DP: store best so far at this index
            pos.put(s, i);              // remember this prefix sum's latest position
        }

        return ans == n + 1 ? -1 : ans;
    }

    /*
        Approach 2: Sliding window + DP (cleaner, no array mutation)

        Since all values in arr are positive, we can find each subarray summing to target with a classic sliding window (expand right, shrink left while sum > target).

        dp[i] = minimum length of a subarray summing to target that ends at or before index i-1 (dp has size n+1, initialized to n = "infinity").

        For each right end r:
            - expand window: add arr[r] to sum
            - shrink while sum > target
            - if sum == target:
                  candidate pair length = current window len + dp[l]
                  (l is the left boundary; dp[l] is best subarray fully ending
                  before index l, ensuring no overlap)
                  update dp[r+1] = min(dp[r], window length)

        Answer = min over all valid pairs, or -1 if none found.

        Time:  O(n) — each index enters and leaves the window at most once
        Space: O(n) for dp
    */
    static int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int ans = n + 1;                // sentinel "infinity"
        int sum = 0;

        // dp[i] = best (minimum) length of a target-sum subarray in arr[0 .. i-1]
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n);             // "infinity" = n (larger than any real length)

        for (int l = 0, r = 0; r < n; r++) {
            sum += arr[r];

            // Shrink window from the left while sum exceeds target
            while (sum > target) sum -= arr[l++];

            // Carry forward the best-so-far for this prefix
            dp[r + 1] = dp[r];

            if (sum == target) {
                // dp[l] gives the shortest subarray ending strictly before index l, guaranteeing the two subarrays do not overlap.
                ans = Math.min(ans, r - l + 1 + dp[l]);
                dp[r + 1] = Math.min(dp[r], r - l + 1);
            }
        }

        return ans == n + 1 ? -1 : ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Prefix sum + HashMap (in-place DP)

Time Complexity: O(n)

- Single pass over arr with O(1) average hashmap operations.

Space Complexity: O(n)

- HashMap stores up to n prefix sums.
- Mutates arr[] in place (no extra array beyond the map).


Approach 2: Sliding window + DP

Time Complexity: O(n)

- Each index enters and leaves the window at most once (l and r only move forward).

Space Complexity: O(n)

- dp[n + 1] array.

Key Observation:
Both approaches reduce the problem to "for each position, know the shortest target-sum subarray ending at or before it", then pair each new subarray with the best non-overlapping one to its left.
The HashMap version exploits the prefix-sum identity (s - target) to find subarrays, while the sliding window version exploits the fact that arr values are positive to keep a monotone window.
The window version is cleaner because it uses a real DP array instead of mutating the input.

---------------------------------------------------------
*/