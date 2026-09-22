package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_3525_FindXValueOfArray_II {
    public static void main(String[] args) {

        // Test 1: no-op updates, various starts and targets
        //   nums = [1,2,3,4], k = 4
        //   For each query, count subarrays STARTING at `start` whose product ≡ x (mod 4)
        int[] nums1 = {1, 2, 3, 4};
        int[][] queries1 = {
                {0, 1, 0, 0},   // from index 0, x=0 -> [1,2,3,4] (24%4=0) -> 1
                {0, 1, 0, 2},   // from index 0, x=2 -> [1,2] and [1,2,3] -> 2
                {0, 1, 1, 2},   // from index 1, x=2 -> [2] and [2,3] -> 2
                {0, 1, 1, 0},   // from index 1, x=0 -> [2,3,4] (24%4=0) -> 1
                {0, 1, 2, 2}    // from index 2, x=2 -> none ([3]=3, [3,4]=0) -> 0
        };
        System.out.println("Test 1 -> " + Arrays.toString(resultArray(nums1, 4, queries1))
                + " (expected: [1, 2, 2, 1, 0])");

        // Test 2: real update changes subsequent queries
        //   nums = [2,2,2], k = 4
        //   Q1 updates nums[0] = 3 -> [3,2,2]
        //   subarrays from 0: [3]=3, [3,2]=6%4=2, [3,2,2]=12%4=0 -> x=0 gives 1
        int[] nums2 = {2, 2, 2};
        int[][] queries2 = {{0, 3, 0, 0}};
        System.out.println("Test 2 -> " + Arrays.toString(resultArray(nums2, 4, queries2))
                + " (expected: [1])");

        // Test 3: all ones, k = 2 -> every subarray product is 1 (odd)
        //   nums = [1,1,1,1], k = 2, start = 0
        //   x = 0 -> 0, x = 1 -> 4
        int[] nums3 = {1, 1, 1, 1};
        int[][] queries3 = {{0, 1, 0, 0}, {0, 1, 0, 1}};
        System.out.println("Test 3 -> " + Arrays.toString(resultArray(nums3, 2, queries3))
                + " (expected: [0, 4])");

        // Test 4: single element
        //   nums = [5], k = 3, start = 0, x = 2 -> [5] gives 5%3=2 -> 1
        int[] nums4 = {5};
        int[][] queries4 = {{0, 5, 0, 2}};
        System.out.println("Test 4 -> " + Arrays.toString(resultArray(nums4, 3, queries4))
                + " (expected: [1])");

        // Test 5: chained updates
        //   nums = [2,3,5], k = 3
        //   Q1: nums[1] = 7 -> [2,7,5], start=0, x=1
        //       [2]=2, [2,7]=14%3=2, [2,7,5]=70%3=1 -> x=1 gives 1
        //   Q2: nums[0] = 4 -> [4,7,5], start=1, x=1
        //       [7]=7%3=1, [7,5]=35%3=2 -> x=1 gives 1
        int[] nums5 = {2, 3, 5};
        int[][] queries5 = {{1, 7, 0, 1}, {0, 4, 1, 1}};
        System.out.println("Test 5 -> " + Arrays.toString(resultArray(nums5, 3, queries5))
                + " (expected: [1, 1])");
    }


    /*
        Approach: Segment tree with per-node remainder-count array + point updates

        Problem restated:
        We are given an array nums, a modulus k, and a list of queries.
        Each query is [index, value, start, x]:
            1. Point-update nums[index] = value.
            2. Then, among all subarrays STARTING at position `start` and
               ending anywhere in [start, n-1], count how many have
               (product of elements) mod k == x.

        Key idea:
        Build a segment tree where each node covering a segment [l, r] stores:
            tree[node][r] for r in [0, k)   = count of subarrays starting at l
                                              and ending in [l, r] whose
                                              product mod k equals r
            tree[node][k]                   = product of the entire segment,
                                              mod k

        Merge of left child and right child into parent covering [l, r]:
            - parent[k] = (left[k] * right[k]) % k          (segment product)
            - parent[x] = left[x]                            (subarrays in left)
            - for each x: parent[(left[k] * x) % k] += right[x]
              -> subarrays that start at l, span all of left, and end in right

        This is exactly what `mergePre` implements.

        Query [start, n-1]:
            Standard segment tree range query. Nodes that are fully inside
            the range are combined left-to-right using mergePre. Because the
            leftmost covered node's left boundary equals `start`, the merged
            result correctly represents "subarrays starting at start".

            (Nodes returned as references are only read, never mutated,
             so sharing the internal arrays is safe here.)

        Point update:
            Standard "walk down to leaf, rewrite leaf, re-merge on the way up".

        Note on MAXK:
            The array per node is fixed at 6 slots, which supports k up to 5
            (indices 0..k-1 for remainders, index k for the segment product).
            If k can be larger, MAXK must be increased accordingly.

        Time:
            Build  : O(n * k)
            Update : O(k * log n)
            Query  : O(k * log n)
            Total  : O((n + q * log n) * k)

        Space:
            O(n * k) for the segment tree.
    */
    static int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        SegmentTree seg = new SegmentTree(nums, k);
        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int index = q[0];
            int value = q[1];
            int start = q[2];
            int x     = q[3];

            // Step 1: apply the point update
            seg.update(1, 0, n - 1, index, value);

            // Step 2: query [start, n-1] and read off the count for remainder x
            int[] pre = seg.query(1, 0, n - 1, start, n - 1);
            ans[i] = pre[x];
        }

        return ans;
    }


    /*
        Segment tree over an int[] array with a per-node remainder-count array.

        Node state layout (length MAXK = 6):
            tree[node][0 .. k-1] = # subarrays starting at this node's left
                                    boundary, ending anywhere in this node's
                                    range, whose product mod k equals the index
            tree[node][k]        = product of this node's entire range, mod k

        The count array is length k (not MAXK) conceptually — the extra
        slots up to MAXK are just unused padding.
    */
    static class SegmentTree {

        private static final int MAXK = 6;   // supports k <= 5

        private final int k;
        private final int n;
        private final int[][] tree;          // tree[node][remainder] and tree[node][k]

        SegmentTree(int[] nums, int k) {
            this.k = k;
            this.n = nums.length;

            // Size heuristic: 2 << bit-length of n. This is >= 4*n in the worst
            // case and exactly enough to hold every node index the recursion uses.
            int size = 2 << Integer.toBinaryString(n).length();
            tree = new int[size][MAXK];

            build(nums, 1, 0, n - 1);
        }

        /*
            Leaf initialization: a single value v contributes one subarray
            (itself) with product v mod k. The segment product is also v mod k.
        */
        private void makeLeaf(int o, int value) {
            Arrays.fill(tree[o], 0);
            int r = value % k;
            tree[o][r] = 1;
            tree[o][k] = r;
        }

        /*
            Merge two child states into a parent state.

            result[k]   = (left segment product) * (right segment product) % k

            result[x]   = left[x]                             // subarrays in left
            for each x:
                result[(left[k] * x) % k] += right[x]         // left-span + right ending

            The second loop counts subarrays that START at the combined range's
            left boundary, span ALL of the left child, and END somewhere in the
            right child. For each right-side product x, multiplying by left[k]
            gives the full product.
        */
        private void mergePre(int[] left, int[] right, int[] result) {
            int mulL = left[k];
            int mulR = right[k];
            result[k] = (mulL * mulR) % k;

            // Carry over counts from left
            for (int x = 0; x < k; x++) {
                result[x] = left[x];
            }

            // Extend right subarrays by the entire left segment's product
            for (int x = 0; x < k; x++) {
                result[(mulL * x) % k] += right[x];
            }
        }

        private void maintain(int o) {
            mergePre(tree[o * 2], tree[o * 2 + 1], tree[o]);
        }

        private void build(int[] nums, int o, int l, int r) {
            if (l == r) {
                makeLeaf(o, nums[l]);
                return;
            }
            int m = (l + r) / 2;
            build(nums, o * 2, l, m);
            build(nums, o * 2 + 1, m + 1, r);
            maintain(o);
        }

        /*
            Point update: replace nums[index] with value, refreshing leaf
            and re-merging every ancestor on the path back to the root.
        */
        void update(int o, int l, int r, int index, int value) {
            if (l == r) {
                makeLeaf(o, value);
                return;
            }
            int m = (l + r) / 2;
            if (index <= m) {
                update(o * 2, l, m, index, value);
            } else {
                update(o * 2 + 1, m + 1, r, index, value);
            }
            maintain(o);
        }

        /*
            Range query over [L, R].

            - Full coverage: return this node's array (safe because callers
              only read from it).
            - Entirely in left or right child: recurse directly.
            - Straddling: combine left and right results with mergePre,
              allocating a fresh array for the merged result.

            Because the decomposition always includes the leftmost node whose
            left boundary equals `L`, the merged result represents "subarrays
            starting at index L within [L, R]".
        */
        int[] query(int o, int l, int r, int L, int R) {
            if (L <= l && r <= R) {
                return tree[o];
            }

            int m = (l + r) / 2;
            if (R <= m) {
                return query(o * 2, l, m, L, R);
            }
            if (L > m) {
                return query(o * 2 + 1, m + 1, r, L, R);
            }

            int[] left = query(o * 2, l, m, L, R);
            int[] right = query(o * 2 + 1, m + 1, r, L, R);
            int[] result = new int[MAXK];
            mergePre(left, right, result);
            return result;
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Segment tree with per-node remainder-count array + point updates

Let n = nums.length, q = queries.length, and k = modulus (bounded by 5).

Time Complexity:

- Build:     O(n * k)
- Per query: O(k * log n) for the point update + O(k * log n) for the range query
- Total:     O(n * k + q * k * log n)

Space Complexity: O(n * k)

- Segment tree holds O(n) nodes, each storing a length-6 array.

Key Observation:
A subarray starting at the left boundary of a segment can be described
by the product of the entire left child and a subarray starting at the
right child's left boundary. This gives a clean merge rule, so a segment
tree over "product mod k" counts can answer "how many subarrays starting
at `start` have product ≡ x (mod k)?" in O(k log n) per query after an
O(k log n) point update.

Note: MAXK = 6 constrains k <= 5. Larger k would need a bigger constant (or a dynamically-sized node array).

---------------------------------------------------------
*/