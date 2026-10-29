package LeetCode.Arrays;

import java.util.*;

public class LeetCode_2916_SubArrayDistinctEleSumOfSqs_II {
    public static void main(String[] args) {
        System.out.println(sumCounts(new int[]{1, 2, 1}));   // 15
        System.out.println(sumCounts(new int[]{1, 1}));      // 3
    }


    /*
        Approach: BIT (Fenwick tree) with range-update / range-query

        Key idea:
            Let cur = sum of (distinct count)^2 over all subarrays ending at i.
            When we append nums[i] = a:
                - For every start j, the distinct count increases by 1 iff
                  a did not already appear in [j, i-1].
                - The last occurrence of a is at last[a], so the distinct
                  count increments exactly for j in [last[a] + 1, i].
                - Each affected c becomes c+1, so the square increases by
                  2c + 1.

            Let q = sum of c over j in [last[a]+1, i] (before update).
            Number of affected starts = i - last[a].
            So:
                cur += 2*q + (i - last[a])
                res += cur
                bit.add(last[a]+1, i, 1)   // c++ for those starts
                last[a] = i

            The BIT tracks c_j = distinct count for each start position j,
            with range increment and range sum query.

        Time:  O(n log n)
        Space: O(n)
    */
    static int sumCounts(int[] nums) {
        int n = nums.length;
        long res = 0, cur = 0;
        long mod = 1_000_000_007;

        BIT bit = new BIT(n + 10);
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int a = nums[i];
            int j = seen.getOrDefault(a, -1) + 1;   // first start where a is new
            long q = bit.query(j, i);               // sum of distinct counts in [j, i]

            // Each affected subarray's square grows by 2*c+1; plus 1 for the new one
            cur = (cur + q * 2 + i - j + 1) % mod;
            res = (res + cur) % mod;

            bit.add(j, i, 1);                       // increment c_j for starts in [j, i]
            seen.put(a, i);
        }
        return (int) res;
    }


    /*
        Fenwick tree (BIT) supporting:
            add(l, r, delta)   -> range increment
            query(l, r)        -> range sum

        Range-add + range-sum is implemented with two internal BITs:
            tree1 stores the delta array
            tree2 stores delta * index
        Prefix sum up to index x:
            (x+1) * prefix(tree1, x) - prefix(tree2, x)

        Time:  O(log n) per operation
        Space: O(n)
    */
    static class BIT {

        int size;
        long[] tree1;
        long[] tree2;

        BIT(int n) {
            this.size = n;
            this.tree1 = new long[n + 2];
            this.tree2 = new long[n + 2];
        }

        private int lowbit(int index) {
            return index & -index;
        }

        void add(int left, int right, int delta) {
            internalAdd(left, delta);
            internalAdd(right + 1, -delta);
        }

        long query(int left, int right) {
            return prefix(right) - prefix(left - 1);
        }

        private void internalAdd(int index, int delta) {
            index += 1;
            long rawIndex = index;
            while (index <= size) {
                tree1[index] += delta;
                tree2[index] += rawIndex * delta;
                index += lowbit(index);
            }
        }

        private long prefix(int index) {
            index += 1;
            long rawIndex = index + 1;
            if (index > size) index = size;
            long res = 0;
            while (index > 0) {
                res += rawIndex * tree1[index] - tree2[index];
                index -= lowbit(index);
            }
            return res;
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: BIT with range-update / range-query

Time Complexity: O(n log n)

- n iterations, each performing O(log n) BIT operations.

Space Complexity: O(n)

- BIT of size n + HashMap of last-seen positions.

Key Observation:
When appending nums[i] = a, the distinct count of every subarray
starting at j in [lastSeen[a] + 1, i] increases by exactly 1. Using
2c + 1 (the increment of c^2 when c becomes c + 1) lets us batch the
entire range with one BIT query and one range update, avoiding the
O(n^2) per-endpoint recomputation of the brute-force version.

---------------------------------------------------------
*/