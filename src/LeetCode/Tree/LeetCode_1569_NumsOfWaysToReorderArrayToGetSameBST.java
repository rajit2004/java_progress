package LeetCode.Tree;

import java.util.*;

public class LeetCode_1569_NumsOfWaysToReorderArrayToGetSameBST {
    public static void main(String[] args) {
        System.out.println(numOfWays(new int[]{2, 1, 3}));       // 1
        System.out.println(numOfWays(new int[]{3, 4, 5, 1, 2})); // 5
    }


    /*
        Approach: Recursive decomposition + combinatorics (Pascal's triangle)

        Key observation:
            The first element of the array MUST be the root of the BST.
            Every subsequent element goes either to the left subtree (if < root)
            or the right subtree (if > root). Both subtrees must themselves
            produce a BST identical to the original — the relative order within
            each subtree is what matters.

        Recursive step:
            Given a list nums:
                - If size < 3, only one ordering is possible -> 1 way.
                - Split nums[1..] into leftNodes (< nums[0]) and rightNodes (> nums[0]).
                - Number of ways = dfs(left) * dfs(right) * C(m-1, leftSize)
                  where C(m-1, leftSize) is the number of interleavings of the
                  left and right subsequences among the m-1 remaining positions.

        Answer = (dfs(nums) - 1) mod 1e9+7 — subtract 1 to exclude the original.

        Pascal's triangle precomputation gives O(1) binomial lookups.

        Time:  O(n^2) — dfs decomposes O(n) levels, each doing O(n) work;
                        Pascal table build is O(n^2)
        Space: O(n^2) — Pascal table + recursion depth O(n)
    */
    static long mod = 1_000_000_007L;
    static long[][] table;

    static int numOfWays(int[] nums) {
        int m = nums.length;

        // Build Pascal's triangle for binomial coefficients mod 1e9+7
        table = new long[m][m];
        for (int i = 0; i < m; ++i) {
            table[i][0] = table[i][i] = 1;
        }
        for (int i = 2; i < m; i++) {
            for (int j = 1; j < i; j++) {
                table[i][j] = (table[i - 1][j - 1] + table[i - 1][j]) % mod;
            }
        }

        // Convert to List for easier sublist partitioning
        List<Integer> arrList = new ArrayList<>();
        for (int v : nums) arrList.add(v);

        return (int) ((dfs(arrList) - 1) % mod);
    }

    /*
        Returns the number of distinct reorderings of `nums` that produce
        the same BST as the one rooted at nums[0].
    */
    static long dfs(List<Integer> nums) {
        int m = nums.size();
        if (m < 3) return 1;

        // Partition remaining elements around the root nums[0]
        List<Integer> leftNodes = new ArrayList<>();
        List<Integer> rightNodes = new ArrayList<>();
        for (int i = 1; i < m; ++i) {
            if (nums.get(i) < nums.get(0)) leftNodes.add(nums.get(i));
            else                            rightNodes.add(nums.get(i));
        }

        long leftWays  = dfs(leftNodes)  % mod;
        long rightWays = dfs(rightNodes) % mod;

        // Interleave left and right: choose leftNodes.size() slots out of m-1
        return (((leftWays * rightWays) % mod) * table[m - 1][leftNodes.size()]) % mod;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Recursive decomposition + combinatorics

Time Complexity: O(n^2)

- Pascal's triangle construction: O(n^2)
- DFS: at each node we scan its sublist to partition, and across all
  recursion levels each element is examined O(depth) times, bounded by O(n^2).
- Binomial lookup per recursive call: O(1).

Space Complexity: O(n^2)

- Pascal's table is n x n.
- Recursion depth O(n) worst case (skewed tree).

Key Observation:
The root of the BST is fixed as the first element. Once the root is placed,
all subsequent elements are forced to one side based on comparison — so
the only flexibility is in INTERLEAVING the left and right subtrees'
relative orders. For each recursive node with l left-nodes and r right-nodes,
there are C(l + r, l) ways to interleave them. Multiplying down the
recursion tree counts all valid reorderings.

---------------------------------------------------------
*/