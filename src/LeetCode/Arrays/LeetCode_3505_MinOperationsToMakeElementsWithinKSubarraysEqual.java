package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3505_MinOperationsToMakeElementsWithinKSubarraysEqual {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int x = 2, k = 2;
        System.out.println(minOperationsTreeMap(nums, x, k));
        System.out.println(minOperationsDualHeap(nums, x, k));
    }

    /* Approach 1: Sliding-window median cost via TreeMap multiset + rolling DP
       Part A - cost per window of length x:
         Maintain a window of x elements split into a lower half `left` (a
         TreeMap-backed multiset) and an upper half `right`, kept balanced so
         left.size is right.size or right.size + 1. The median is left.max().
         The cost to make all x elements equal to the median is
           median * left.size  - left.sum  +  right.sum - median * right.size.
         ops[i] records this cost for the window ending at i.
       Part B - pick k non-overlapping windows of length x:
         pre[i] = best cost using (j-1) windows within the first i elements.
         cur[i] = min(cur[i-1], pre[i-x] + ops[i-1]) for i >= j*x.
         Roll j from 1 to k with two 1D arrays.
       Example: nums = [1,2,3,4], x = 2, k = 2
                window costs: [1,1,1] -> pick two non-overlapping -> 2.
    */
    static long minOperationsTreeMap(int[] nums, int x, int k) {
        int n = nums.length;
        long[] ops = getCostsTreeMap(nums, x);

        long[] pre = new long[n + 1];
        long[] cur = new long[n + 1];

        for (int j = 1; j <= k; j++) {
            Arrays.fill(cur, Long.MAX_VALUE);
            for (int i = j * x; i <= n; i++) {
                cur[i] = Math.min(cur[i - 1], pre[i - x] + ops[i - 1]);
            }
            long[] tmp = pre;
            pre = cur;
            cur = tmp;
        }

        return pre[n];
    }

    static long[] getCostsTreeMap(int[] nums, int x) {
        int n = nums.length;
        long[] ops = new long[n];

        MultiSet left = new MultiSet();
        MultiSet right = new MultiSet();

        for (int i = 0; i < n; i++) {
            int value = nums[i];

            if (left.size == 0 || value <= left.max()) left.add(value);
            else                                       right.add(value);

            if (i >= x) {
                value = nums[i - x];
                if (left.contains(value)) left.remove(value);
                else                       right.remove(value);
            }

            if (left.size > right.size + 1) {
                value = left.max();
                left.remove(value);
                right.add(value);
            } else if (left.size < right.size) {
                value = right.min();
                right.remove(value);
                left.add(value);
            }

            if (i >= x - 1) {
                long mid = left.max();
                ops[i] = mid * left.size - left.sum + right.sum - mid * right.size;
            }
        }

        return ops;
    }

    static class MultiSet {
        TreeMap<Integer, Integer> counts = new TreeMap<>();
        int size = 0;
        long sum = 0;

        void add(int value) {
            counts.put(value, counts.getOrDefault(value, 0) + 1);
            size++;
            sum += value;
        }

        void remove(int value) {
            int c = counts.get(value);
            if (c == 1) counts.remove(value);
            else        counts.put(value, c - 1);
            size--;
            sum -= value;
        }

        boolean contains(int value) {
            return counts.containsKey(value);
        }

        int min() { return counts.firstKey(); }
        int max() { return counts.lastKey(); }
    }

    /* Approach 2: Sliding-window median cost via dual heaps + 2D DP
       Part A - cost per window of length x:
         Keep the lower half in a max-heap `left`, the upper half in a
         min-heap `right`, and lazily delete via a `delayed` count map. The
         median is left.peek() and the cost is
           sumR - sumL + median * (leftSize - rightSize).
         ops[i] records this cost for the window ending at i.
       Part B - pick k non-overlapping windows of length x:
         dp[i][j] = best cost using j windows within the first i+1 elements.
         Base: dp[i][0] = 0; first-window and stacked-window seeds fill
         dp[x-1][1], dp[2x-1][2], ...
         Transition: dp[i][j] = min(dp[i-x][j-1] + ops[i], dp[i-1][j]).
       Example: nums = [1,2,3,4], x = 2, k = 2 -> 2 (same as Approach 1).
    */
    static long minOperationsDualHeap(int[] nums, int x, int k) {
        int n = nums.length;
        DualHeap heaps = new DualHeap();
        long[] ops = new long[n];

        for (int i = 0; i < n; i++) {
            heaps.add(nums[i]);
            if (i >= x) heaps.remove(nums[i - x]);
            if (i >= x - 1) {
                long mid = heaps.left.peek();
                ops[i] = heaps.sumR - heaps.sumL
                        + mid * (heaps.leftSize - heaps.rightSize);
            }
        }

        long[][] dp = new long[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Long.MAX_VALUE);
            dp[i][0] = 0;
        }

        for (int j = 1; j <= k; j++) {
            if (j == 1) {
                dp[x - 1][j] = ops[x - 1];
            } else {
                dp[j * x - 1][j] = dp[(j - 1) * x - 1][j - 1] + ops[j * x - 1];
            }
        }

        for (int j = 1; j <= k; j++) {
            for (int i = j * x; i < n; i++) {
                dp[i][j] = Math.min(dp[i - x][j - 1] + ops[i], dp[i - 1][j]);
            }
        }

        return dp[n - 1][k];
    }

    static class DualHeap {
        PriorityQueue<Integer> left  = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> right = new PriorityQueue<>();
        Map<Integer, Integer> delayed = new HashMap<>();
        int leftSize = 0, rightSize = 0;
        long sumL = 0, sumR = 0;

        void prune(PriorityQueue<Integer> heap) {
            while (!heap.isEmpty() && delayed.getOrDefault(heap.peek(), 0) > 0) {
                int num = heap.poll();
                delayed.put(num, delayed.get(num) - 1);
            }
        }

        void balance() {
            if (leftSize > rightSize + 1) {
                int num = left.poll();
                right.offer(num);
                sumR += num;
                sumL -= num;
                rightSize++;
                leftSize--;
                prune(left);
            } else if (rightSize > leftSize) {
                int num = right.poll();
                left.offer(num);
                sumL += num;
                sumR -= num;
                leftSize++;
                rightSize--;
                prune(right);
            }
        }

        void add(int num) {
            if (left.isEmpty() || num <= left.peek()) {
                left.offer(num);
                leftSize++;
                sumL += num;
            } else {
                right.offer(num);
                rightSize++;
                sumR += num;
            }
            balance();
        }

        void remove(int num) {
            delayed.put(num, delayed.getOrDefault(num, 0) + 1);
            if (num <= left.peek()) {
                leftSize--;
                sumL -= num;
                if (num == left.peek()) prune(left);
            } else {
                rightSize--;
                sumR -= num;
                if (num == right.peek()) prune(right);
            }
            balance();
        }
    }
}

/* Complexity Analysis
   Let n = nums.length, x = window length, k = number of subarrays.

   Approach 1: TreeMap multiset + rolling DP
     Time : O(n log x + k * n)  - each window add/remove is O(log x); DP has
            k layers of O(n) transitions.
     Space: O(n + x)            - ops + two DP rows + two TreeMaps of size x.
     Key Observation: the cost of making a length-x window equal is minimised
     at its median; maintaining two balanced halves gives the median and the
     total absolute deviation in O(log x) per slide.

   Approach 2: Dual heaps + 2D DP (recommended)
     Time : O(n log x + k * n)  - same asymptotics; heap operations are
            amortised O(log x) with lazy deletions.
     Space: O(n * k + x)        - 2D dp table + heaps + delayed map.
     Key Observation: identical sliding-median idea as Approach 1 but with a
     dual-heap structure instead of a TreeMap; the DP table is kept 2D, so it
     uses more memory while giving the same answer.
*/

