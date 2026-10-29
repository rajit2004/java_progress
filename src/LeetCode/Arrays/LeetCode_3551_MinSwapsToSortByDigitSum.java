package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3551_MinSwapsToSortByDigitSum {
    public static void main(String[] args) {
        System.out.println(minSwaps(new int[]{3, 4, 63, 5}));        // 1
        System.out.println(minSwaps(new int[]{5, 4, 3, 2, 1}));     // 2
    }


    /*
        Approach 1: Cycle counting on the target permutation

        Step 1: Compute digit sum of every element.
        Step 2: Sort the indices by (digitSum asc, value asc) to get the
                target order.
        Step 3: Build targetPos — where each original index should end up.
        Step 4: The minimum swaps needed to sort equals n - (number of cycles
                in the permutation), where each cycle is a closed loop of
                elements that are permuted among themselves.

        Time:  O(n log n) — dominated by sort
        Space: O(n)
    */
    static int minSwaps(int[] nums) {
        int n = nums.length;

        // Digit sums
        int[] digitSum = new int[n];
        for (int i = 0; i < n; i++) {
            int x = nums[i], sum = 0;
            while (x > 0) {
                sum += x % 10;
                x /= 10;
            }
            digitSum[i] = sum;
        }

        // Sort indices by (digitSum, value)
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        Arrays.sort(indices, (a, b) -> {
            if (digitSum[a] != digitSum[b]) {
                return Integer.compare(digitSum[a], digitSum[b]);
            }
            return Integer.compare(nums[a], nums[b]);
        });

        // targetPos[i] = final position of the element currently at index i
        int[] targetPos = new int[n];
        for (int pos = 0; pos < n; pos++) {
            targetPos[indices[pos]] = pos;
        }

        // Count cycles in the permutation
        boolean[] visited = new boolean[n];
        int cycles = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                cycles++;
                int j = i;
                while (!visited[j]) {
                    visited[j] = true;
                    j = targetPos[j];
                }
            }
        }

        return n - cycles;
    }


    /*
        Approach 2: Union-Find on the permutation

        Identical sort + targetPos steps as Approach 1. Instead of counting
        cycles with a visited array, union each index i with its targetPos[i].
        The number of connected components equals the number of cycles, so
        the answer is n - components.

        Same complexity; typically a bit slower than the cycle-count version
        due to path compression overhead, but a useful alternative if you
        already have a Union-Find template handy.

        Time:  O(n log n)
        Space: O(n)
    */
    static int minSwapsUnionFind(int[] nums) {
        int n = nums.length;

        int[] digitSum = new int[n];
        for (int i = 0; i < n; i++) {
            int x = nums[i], sum = 0;
            while (x > 0) {
                sum += x % 10;
                x /= 10;
            }
            digitSum[i] = sum;
        }

        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        Arrays.sort(indices, (a, b) -> {
            if (digitSum[a] != digitSum[b]) {
                return Integer.compare(digitSum[a], digitSum[b]);
            }
            return Integer.compare(nums[a], nums[b]);
        });

        int[] targetPos = new int[n];
        for (int pos = 0; pos < n; pos++) {
            targetPos[indices[pos]] = pos;
        }

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int components = n;
        for (int i = 0; i < n; i++) {
            int ri = find(parent, i);
            int rj = find(parent, targetPos[i]);
            if (ri != rj) {
                parent[ri] = rj;
                components--;
            }
        }

        return n - components;
    }

    private static int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Both approaches

Time Complexity: O(n log n)

- Sorting indices: O(n log n).
- Cycle counting / Union-Find: O(n α(n)) ≈ O(n).

Space Complexity: O(n)

- For digit sums, index array, targetPos, and visited / parent arrays.

Key Observation:
Sorting by (digit sum, value) gives a target permutation. The minimum
number of swaps to sort the array equals the number of elements NOT
already in cycle position, i.e. n - (number of permutation cycles).
Both approaches compute this cycle count — Approach 1 counts cycles
directly via a visited array, Approach 2 merges elements with their
target positions and counts the resulting components.

---------------------------------------------------------
*/