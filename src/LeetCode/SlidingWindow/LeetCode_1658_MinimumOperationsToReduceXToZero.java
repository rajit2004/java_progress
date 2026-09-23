package LeetCode.SlidingWindow;

public class LeetCode_1658_MinimumOperationsToReduceXToZero {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): nums = [1,1,4,2,3], x = 5
        //   Best: remove [4] from right-end + [1] from left-end = 5 with 2 ops
        //   Complement: longest middle subarray with sum = 11 - 5 = 6 -> [1,4,?] no,
        //   actually [1,1,4] no; [4,2] = 6 (len 2) -> 5 - 2 = 3? Let's just print.
        System.out.println("Sample 1 -> " + minOperations(new int[]{1,1,4,2,3}, 5)
                + " (expected: 2)");

        // Sample 2 (LeetCode): nums = [5,6,7,8,9], x = 4 -> impossible
        System.out.println("Sample 2 (impossible) -> " + minOperations(new int[]{5,6,7,8,9}, 4)
                + " (expected: -1)");

        // Sample 3 (LeetCode): nums = [3,2,20,1,1,3], x = 10
        //   Remove [3] left + [3,1,1,2] right = 10 in 5 ops
        System.out.println("Sample 3 -> " + minOperations(new int[]{3,2,20,1,1,3}, 10)
                + " (expected: 5)");

        // Edge case: x equals total sum -> remove everything
        System.out.println("Edge (x == total) -> " + minOperations(new int[]{1,2,3}, 6)
                + " (expected: 3)");

        // Edge case: x = 0 -> no removals needed
        System.out.println("Edge (x = 0) -> " + minOperations(new int[]{1,2,3}, 0)
                + " (expected: 0)");

        // Edge case: x > total -> impossible
        System.out.println("Edge (x > total) -> " + minOperations(new int[]{1,2,3}, 100)
                + " (expected: -1)");

        // Edge case: single element equal to x
        System.out.println("Edge (single match) -> " + minOperations(new int[]{7}, 7)
                + " (expected: 1)");

        // Edge case: single element not equal to x
        System.out.println("Edge (single mismatch) -> " + minOperations(new int[]{7}, 3)
                + " (expected: -1)");
    }


    /*
        Approach: Sliding window on the complement subarray

        Key insight (complement trick):
        Removing elements from the two ends of the array to sum to x is
        equivalent to keeping a contiguous MIDDLE subarray whose sum equals
        (total - x). The number of removed elements is then n - (length of
        that middle subarray).

        Since we want to MINIMIZE removals, we want to MAXIMIZE the length
        of the kept middle subarray with sum = total - x.

        Steps:
        1. Compute total = sum(nums), and target = total - x.
        2. If target < 0  -> x > total, impossible -> return -1.
        3. If target == 0 -> the whole array must be removed -> return n.
        4. Sliding window [i, j] over nums to find the longest window with sum == target.
        5. Answer = n - bestLength, or -1 if no such window exists.

        Window invariant: s = sum(A[i..j]); whenever s > target, shrink from
        the left. Because all values are positive (LeetCode constraint),
        this monotonically moves both pointers and runs in linear time.

        Example:
        nums = [3,2,20,1,1,3], x = 10
            total = 30, target = 20
            longest subarray summing to 20 -> [20] (length 1) or [3,2,20,...]?
              Actually [3,2,20,1,1,3] sum=30; sliding window finds [20]? yes length 1.
              Wait -- target 20, we need sum 20. [3,2,20] sum=25 no. [20] yes len 1.
              [1,1,3] sum=5 no. So best = 1? But expected answer is 5.

            Hmm, [20] is NOT the longest. Longest subarray with sum 20 is:
              [3,2,20,1,1,3] sum 30 no.
              [2,20,1,1,?]... let's see: [2,20,1,1] = 24, [20,1,1,3]=25, ...
              Actually with all positive, if no subarray sums to exactly 20,
              best = -1 and answer = -1? But expected is 5.

            Let me re-check: total = 3+2+20+1+1+3 = 30, target = 30-10 = 20.
            Subarray [3,2,20,1,1,3] no (sum 30).
            Hmm, is there any subarray summing to 20? [20] yes. Any longer?
              [2,20]=22, [20,1]=21, [1,20]? not contiguous.
            So longest middle = 1 (just [20]). Then answer = 6 - 1 = 5. ✓

            Correct! The kept middle is [20], and we remove [3,2] from left
            and [1,1,3] from right -> total removed = 3+2+1+1+3 = 10 = x. ✓
    */
    static int minOperations(int[] nums, int x) {
        int n = nums.length;

        // target = sum of the middle subarray we want to KEEP
        int target = -x;
        for (int a : nums) target += a;

        // x > total sum -> impossible
        if (target < 0) return -1;

        // x == total sum -> must remove entire array
        if (target == 0) return n;

        int best = -1;      // longest length of a subarray with sum == target
        int i = 0;
        int s = 0;

        for (int j = 0; j < n; j++) {
            s += nums[j];

            // Shrink window until sum <= target (all values positive)
            while (s > target) {
                s -= nums[i++];
            }

            // Window sum matches exactly -> record best length so far
            if (s == target) {
                best = Math.max(best, j - i + 1);
            }
        }

        // No valid middle -> impossible; else remove everything outside it
        return best < 0 ? -1 : n - best;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Sliding window on the complement subarray

Time Complexity: O(n)

- One pass to compute total, then a single two-pointer pass.
- Each index enters and leaves the window at most once (i and j move forward only).

Space Complexity: O(1)

- Only a few integer variables; no auxiliary data structures.

Key Observation:
Removing from both ends to sum to x is equivalent to keeping a contiguous
middle subarray with sum = total - x. Minimizing removals = maximizing the
kept middle subarray, which is a classic longest-subarray-with-target-sum
problem solvable with a sliding window because all values are positive.

---------------------------------------------------------
*/