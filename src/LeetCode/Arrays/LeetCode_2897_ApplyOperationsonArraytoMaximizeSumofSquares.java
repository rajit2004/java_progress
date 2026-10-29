package LeetCode.Arrays;

import java.util.*;

public class LeetCode_2897_ApplyOperationsonArraytoMaximizeSumofSquares {
    public static void main(String[] args) {
        System.out.println(maxSum(Arrays.asList(2, 6, 5, 8), 2));   // 261
        System.out.println(maxSum(Arrays.asList(4, 5, 4, 7), 3));   // 101
    }


    /*
        Approach: Bit-frequency greedy construction

        Key observation:
            The allowed AND/OR operation preserves the total number of set bits
            at every bit position across the whole array. Bits can be moved
            between elements but cannot be created or destroyed.

            Since x^2 is convex, concentrating value into fewer elements
            maximizes the sum of squares. So the optimal strategy is to build
            the largest possible number first, then the next largest from the
            remaining bit counts, and so on for k operations.

        Algorithm:
            1. Count, for each bit j in [0, 30), how many numbers have bit j set.
               (30 bits because nums[i] <= 10^9 < 2^30.)

            2. For each of the k operations, construct the largest possible value:
                   - Walk bits from 29 down to 0.
                   - If cnt[j] > 0, set bit j in `cur` and decrement cnt[j].

            3. Add cur^2 to the answer modulo 1e9+7.

        Early exit optimization:
            Once cur becomes 0, all bit counts are exhausted — every remaining
            iteration would contribute 0. So we can break out of the loop
            immediately. This only matters when k >> n (not typical for the
            LeetCode constraints but a cheap defensive improvement).

        Time:  O(n * 30 + k * 30)  → effectively O(n + k) since 30 is constant
        Space: O(30) = O(1)
    */
    static int maxSum(List<Integer> nums, int k) {
        final int MOD = 1_000_000_007;

        // cnt[j] = number of elements whose j-th bit is set
        int[] cnt = new int[30];
        for (int num : nums) {
            for (int i = 0; i < 30; i++) {
                if ((num & (1 << i)) != 0) {
                    cnt[i]++;
                }
            }
        }

        long res = 0;

        // Build up to k values, each consuming one available bit per position
        for (int i = 0; i < k; i++) {
            long cur = 0;

            // Take highest bits first to maximize this value
            for (int j = 29; j >= 0; j--) {
                if (cnt[j] > 0) {
                    cur |= 1L << j;
                    cnt[j]--;
                }
            }

            // All bit counts exhausted: remaining iterations would add 0
            if (cur == 0) break;

            // Accumulate square of the constructed value
            res = (res + cur * cur) % MOD;
        }

        return (int) res;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Bit-frequency greedy construction

Let n = nums.size(), k = number of operations, B = 30 (bit width).

Time Complexity: O(n * B + k * B)

- Counting bits: O(n * 30)
- Building k values: O(k * 30)
- With B = 30 constant, this is O(n + k).
- The early-exit on cur == 0 shortens the loop when k > (total buildable items).

Space Complexity: O(B) = O(1)

- Only a fixed-size cnt[30] array and a few scalars.

Key Observation:

The AND/OR operation preserves the total count of set bits per bit
position. So the problem reduces to distributing these bit counts into k
numbers to maximize the sum of squares. Because x^2 is convex, the optimal
strategy is to concentrate bits — build the largest possible number first,
then the next largest, and so on. Taking bits from highest to lowest
inside each construction guarantees each number is as large as possible
given the remaining bit counts.

---------------------------------------------------------
*/