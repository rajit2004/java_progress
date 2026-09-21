package LeetCode.DynamicProgramming;

public class LeetCode_3524_FindXValueOfArray_I {
    public static void main(String[] args) {

        // Sample 1: nums = [1,2,3], k = 2
        //   All subarrays and their (product mod 2):
        //     [1] -> 1, [2] -> 0, [3] -> 1
        //     [1,2] -> 0, [2,3] -> 0, [1,2,3] -> 0
        //   Counts: r=0 -> 4, r=1 -> 2
        System.out.println("Sample 1 -> " + java.util.Arrays.toString(resultArray(new int[]{1, 2, 3}, 2))
                + " (expected: [4, 2])");

        // Sample 2: nums = [2,3,4], k = 3
        //   [2]->2, [3]->0, [4]->1, [2,3]->0, [3,4]->0, [2,3,4]->0
        //   Counts: r=0 -> 4, r=1 -> 1, r=2 -> 1
        System.out.println("Sample 2 -> " + java.util.Arrays.toString(resultArray(new int[]{2, 3, 4}, 3))
                + " (expected: [4, 1, 1])");

        // Sample 3: nums = [1,2,3,4,5], k = 3
        //   All 15 subarrays; counts by (product mod 3):
        //     r=0 -> 9, r=1 -> 2, r=2 -> 4
        System.out.println("Sample 3 -> " + java.util.Arrays.toString(resultArray(new int[]{1, 2, 3, 4, 5}, 3))
                + " (expected: [9, 2, 4])");

        // Edge case: single element
        //   nums = [5], k = 3 -> [5]: 5%3=2 -> r=2 count is 1
        System.out.println("Edge (single) -> " + java.util.Arrays.toString(resultArray(new int[]{5}, 3))
                + " (expected: [0, 0, 1])");

        // Edge case: all zeros -> every subarray has product 0
        //   nums = [0,0], k = 5 -> 3 subarrays, all ≡ 0 -> [3,0,0,0,0]
        System.out.println("Edge (all zeros) -> " + java.util.Arrays.toString(resultArray(new int[]{0, 0}, 5))
                + " (expected: [3, 0, 0, 0, 0])");

        // Edge case: values larger than k -> reduced mod k internally
        //   nums = [7, 10], k = 4
        //     [7] -> 3, [10] -> 2, [7,10] -> 70%4=2
        //     Counts: r=2 -> 2, r=3 -> 1
        System.out.println("Edge (big values) -> " + java.util.Arrays.toString(resultArray(new int[]{7, 10}, 4))
                + " (expected: [0, 0, 2, 1])");
    }


    /*
        Approach: DP over product-remainders with rolling array

        We want, for each remainder r in [0, k), the number of contiguous subarrays whose product ≡ r (mod k).

        dp[r] = number of subarrays ENDING at the previous index whose product mod k equals r.

        For each new element nums[i] with v = nums[i] % k:
            ndp[v]++                              // start a new subarray [i..i]
            for each r: ndp[(r * v) % k] += dp[r] // extend each previous subarray

        After computing ndp for index i, add each ndp[r] to result[r]. That's because ndp[r] counts exactly the subarrays ending at i with product ≡ r.

        Note on the rolling trick: dp is replaced with ndp each iteration, so the previous layer is discarded after we've used it.
        This keeps space at O(k) instead of O(n*k).

        Note on the initial state: dp = new long[k] is all zeros, which correctly represents "no subarrays exist before the first element" , so no multiplication step contributes anything on the first iteration.

        Overflow: r * nums[i] can exceed int range when k and nums[i] are large, so we cast r to long before multiplying.

        Time:  O(n * k)
        Space: O(k)
    */
    static long[] resultArray(int[] nums, int k) {
        int n = nums.length;

        long[] result = new long[k];

        // dp[r] = # subarrays ending at previous index whose product ≡ r (mod k).
        // Initial state: no elements processed -> no subarrays -> all zeros.
        long[] dp = new long[k];

        for (int i = 0; i < n; i++) {

            // ndp = current layer's counts (subarrays ending at i)
            long[] ndp = new long[k];

            // New subarray starting at i: just nums[i]
            ndp[nums[i] % k]++;

            // Extend every subarray ending at i-1 by nums[i]
            for (int r = 0; r < k; r++) {
                ndp[(int) (((long) r * nums[i]) % k)] += dp[r];
            }

            // Roll the layer forward
            dp = ndp;

            // Every subarray ending at i is now finalized — add it to the answer
            for (int r = 0; r < k; r++) {
                result[r] += dp[r];
            }
        }

        return result;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: DP over product-remainders with rolling array

Time Complexity: O(n * k)

- For each of the n elements, we do O(k) work to compute the new layer
  and O(k) work to accumulate into the answer.

Space Complexity: O(k)

- dp and ndp each of size k; result of size k.
- No O(n) state is retained.

Key Observation:
Counting subarrays by (product mod k) is a linear DP: state = remainder of
the subarray's product ending at the current index. Only the previous
layer's remainder counts are needed, which lets us roll the array in place
and keep space at O(k). Every layer's counts are also added to the global
answer, because each subarray is "born" at some index i and is finalized
at that index.

Note: (r * nums[i]) can overflow int when k and nums[i] are large, so the multiplication is promoted to long before taking mod k.

---------------------------------------------------------
*/