package LeetCode.Arrays;

import java.util.*;

public class LeetCode_2913_SubArrayDistinctEleSumOfSqs_I {
    public static void main(String[] args) {
        System.out.println(sumCounts(new int[]{1, 2, 1}));        // 15
        System.out.println(sumCounts(new int[]{1, 1}));           // 3
    }


    /*
        Approach 1: Brute force (O(n^2))

        For every start i, expand j and maintain the distinct count using
        a HashSet. Add (distinct count)^2 to the answer at each step.

        Time:  O(n^2)
        Space: O(n) for the per-start HashSet
    */
    static int sumCountsBrute(int[] nums) {
        int n = nums.length;
        int res = 0;

        for (int i = 0; i < n; i++) {
            Set<Integer> s = new HashSet<>();
            for (int j = i; j < n; j++) {
                s.add(nums[j]);
                res += s.size() * s.size();
            }
        }
        return res;
    }


    /*
        Approach 2: BIT (Fenwick tree) with range-update / range-query

        Key idea:
            Let cur = sum of (distinct count)^2 over all subarrays ending at i.
            When we append nums[i] = a:
                - For every start j, the distinct count increases by 1
                  iff a did not already appear in [j, i-1].
                - a first appeared at last[a]; so the distinct count
                  increments exactly for j in [last[a] + 1, i].
                - For each such j, contribution goes from c^2 to (c+1)^2,
                  i.e. increases by 2c + 1.

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
            int j = seen.getOrDefault(a, -1) + 1;   // first start where `a` is new
            long q = bit.query(j, i);               // sum of current distinct counts in [j, i]

            // New subarray contributes 1; extension adds 2*c+1 for each start in [j, i]
            cur = (cur + q * 2 + i - j + 1) % mod;
            res = (res + cur) % mod;

            bit.add(j, i, 1);                       // c++ for starts in [j, i]
            seen.put(a, i);
        }
        return (int) res;
    }


    /*
        Fenwick tree (BIT) with range update + range sum query.

        Uses two internal trees to support:
            add(l, r, delta)        -> range increment
            query(l, r)             -> range sum

        Standard formulation: range-add, range-sum via two BITs:
            tree1 stores the delta array
            tree2 stores delta * index
        The prefix sum of an updated array up to index x is:
            sum = (x+1) * prefix(tree1, x) - prefix(tree2, x)

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

        // Range add: [left, right] += delta
        void add(int left, int right, int delta) {
            internalAdd(left, delta);
            internalAdd(right + 1, -delta);
        }

        // Range sum: sum of [left, right]
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

Approach 1: Brute force

Time Complexity: O(n^2)
- Two nested loops; O(1) amortized for HashSet add.

Space Complexity: O(n)
- HashSet may hold up to n distinct values.


Approach 2: BIT with range update / range query

Time Complexity: O(n log n)
- n iterations, each with O(log n) BIT operations.

Space Complexity: O(n)
- BIT of size n plus a HashMap of last-seen positions.

Key Observation:
When appending nums[i] = a, the distinct count for every start j in
[lastSeen[a]+1, i] increases by exactly 1. Using 2c+1 for the square
update (instead of (c+1)^2 - c^2 recomputation) lets us batch the whole
range with a single BIT query and one range update.

---------------------------------------------------------
*/