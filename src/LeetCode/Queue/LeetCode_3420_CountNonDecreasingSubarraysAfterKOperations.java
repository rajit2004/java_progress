package LeetCode.Queue;

import java.util.*;

public class LeetCode_3420_CountNonDecreasingSubarraysAfterKOperations {

    public static void main(String[] args) {
        int[] nums = {6, 3, 1, 2, 4, 4};
        int k = 5;
        System.out.println(countNonDecreasingSubarraysBrute(nums, k));
        System.out.println(countNonDecreasingSubarraysOptimal(nums, k));
    }

    /* Approach 1: Brute force -- extend every window, track cost
       For each left endpoint l, extend right one step at a time while keeping
       (a) maxSoFar = running max of nums[l..r] and (b) cost = running cost to
       make nums[l..r] non-decreasing. When nums[r] < maxSoFar, that element
       must be raised by (maxSoFar - nums[r]); otherwise the running max is
       refreshed to nums[r]. Every r with cost <= k contributes one valid
       subarray. Since cost is monotone non-decreasing in r for fixed l, the
       inner loop breaks the moment cost exceeds k.
       Example: nums = [6,3,1,2,4,4], k = 5 -> 17 valid subarrays.
    */
    static long countNonDecreasingSubarraysBrute(int[] nums, int k) {
        int n = nums.length;
        long ans = 0;

        for (int l = 0; l < n; l++) {
            int maxSoFar = nums[l];
            long cost = 0;
            ans++;  // [l, l] is always non-decreasing, cost 0

            for (int r = l + 1; r < n; r++) {
                if (nums[r] < maxSoFar) {
                    cost += maxSoFar - nums[r];
                } else {
                    maxSoFar = nums[r];
                }
                if (cost <= k) ans++;
                else break;  // cost only grows as r extends
            }
        }

        return ans;
    }

    /* Approach 2: Sliding window + monotonic stack + max-heap (optimal)
       Maintain a window [left, right] whose cost (operations to make it
       non-decreasing) is <= k, and count all valid subarrays ending at right
       as (right - left + 1).

       Key structures:
         pos[i]    = first index j > i with nums[j] >= nums[i] (or n), found
                     via a decreasing monotonic stack.
         cnt[j]    = set of indices i > j whose nearest greater-or-equal to
                     the left is j (so nums[j] >= nums[i] and every element
                     strictly between j and i is < nums[i]).
         maxHeap   = current window maximum, cleaned of stale indices each
                     iteration so peek() is exactly max(nums[left..right]).
         operations = cost of the current window.

       Adding nums[right]:
         operations += max(0, window max - nums[right]).
       Shrinking left while operations > k:
         For every i in cnt[left], the "extra" cost contributed by having
         nums[left] as the dominating peak was
           (nums[left] - nums[i]) * (min(pos[i], right+1) - i)
         so we subtract that for each i.
       Example: nums = [6,3,1,2,4,4], k = 5 -> 17 (same as Approach 1).
    */
    static long countNonDecreasingSubarraysOptimal(int[] nums, int k) {
        int n = nums.length;
        long ans = 0, operations = 0;

        // Max-heap of (value, index) for the current window.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));

        // cnt[j] = indices i whose nearest greater-or-equal to the left is j.
        List<List<Integer>> cnt = new ArrayList<>(n);
        for (int i = 0; i < n; i++) cnt.add(new ArrayList<>());

        // pos[i] = first index to the right of i with nums >= nums[i].
        int[] pos = new int[n];
        Arrays.fill(pos, n);

        // Decreasing monotonic stack of indices.
        Deque<Integer> stack = new ArrayDeque<>();

        for (int left = 0, right = 0; right < n; right++) {
            // Pop smaller-or-equal elements; their next >= is right.
            while (!stack.isEmpty() && nums[right] >= nums[stack.peek()]) {
                pos[stack.peek()] = right;
                stack.pop();
            }
            // The stack top (nearest >= to the left) becomes right's parent.
            if (!stack.isEmpty()) {
                cnt.get(stack.peek()).add(right);
            }
            stack.push(right);

            // Cost of extending the window with nums[right].
            maxHeap.offer(new int[]{nums[right], right});
            operations += Math.max(0L, (long) maxHeap.peek()[0] - nums[right]);

            // Shrink the window while it exceeds k operations.
            while (operations > k) {
                for (int i : cnt.get(left)) {
                    operations -= (long) (nums[left] - nums[i])
                            * (Math.min(pos[i], right + 1) - i);
                }
                left++;
            }

            // Drop heap entries that fell out of the window.
            while (!maxHeap.isEmpty() && maxHeap.peek()[1] < left) {
                maxHeap.poll();
            }

            // All subarrays [start, right] with start in [left, right] are valid.
            ans += right - left + 1;
        }

        return ans;
    }
}

/* Complexity Analysis
   Let n = nums.length.

   Approach 1: Brute force
     Time : O(n^2)  - for each l, extend r with O(1) work per step; break as
            soon as cost exceeds k.
     Space: O(1)    - running max and running cost only.
     Key Observation: the cost of a window is monotone non-decreasing as r
     extends (each step adds max(0, running max - nums[r]) >= 0), so the inner
     loop can break early without missing any valid subarray.

   Approach 2: Sliding window + monotonic stack + max-heap (recommended)
     Time : O(n log n)  - one push/pop on the heap per index; monotonic-stack
            work amortises to O(n); window shrink is amortised O(n) because
            each cnt[left] entry is subtracted at most once per i.
     Space: O(n)        - pos, cnt, heap, monotonic stack.
     Key Observation: each index i "owes" (nums[parent] - nums[i]) per position
     up to min(pos[i]-1, right); summing over i in cnt[left] when left leaves
     the window exactly subtracts that owed cost from operations, so the
     window cost stays equal to the true non-decreasing cost.
*/

