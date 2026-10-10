package LeetCode.PrefixSum;

public class LeetCode_303_RangeSumQuery_Immutable {
    public static void main(String[] args) {

        // Sample from LeetCode:
        //   NumArray nums = new NumArray([-2, 0, 3, -5, 2, -1]);
        //   sumRange(0, 2) -> -2 + 0 + 3 = 1
        //   sumRange(2, 5) -> 3 + (-5) + 2 + (-1) = -1
        //   sumRange(0, 5) -> -2 + 0 + 3 + (-5) + 2 + (-1) = -3

        int[] nums = {-2, 0, 3, -5, 2, -1};

        // ---- Approach 1: Prefix Sum (optimal) ----
        NumArray prefix = new NumArray(nums);
        System.out.println("Prefix sum(0, 2) -> " + prefix.sumRange(0, 2) + " (expected: 1)");
        System.out.println("Prefix sum(2, 5) -> " + prefix.sumRange(2, 5) + " (expected: -1)");
        System.out.println("Prefix sum(0, 5) -> " + prefix.sumRange(0, 5) + " (expected: -3)");

        // ---- Approach 2: Brute Force (comparison) ----
        NumArrayBrute brute = new NumArrayBrute(nums);
        System.out.println("Brute  sum(0, 2) -> " + brute.sumRange(0, 2) + " (expected: 1)");
        System.out.println("Brute  sum(2, 5) -> " + brute.sumRange(2, 5) + " (expected: -1)");
        System.out.println("Brute  sum(0, 5) -> " + brute.sumRange(0, 5) + " (expected: -3)");

        // Edge cases: single element ranges
        System.out.println("Prefix sum(0, 0) -> " + prefix.sumRange(0, 0) + " (expected: -2)");
        System.out.println("Prefix sum(5, 5) -> " + prefix.sumRange(5, 5) + " (expected: -1)");

        // Edge case: single-element array
        NumArray single = new NumArray(new int[]{7});
        System.out.println("Single array sum(0, 0) -> " + single.sumRange(0, 0) + " (expected: 7)");

        // Edge case: all zeros
        NumArray zeros = new NumArray(new int[]{0, 0, 0, 0});
        System.out.println("All zeros sum(0, 3) -> " + zeros.sumRange(0, 3) + " (expected: 0)");

        // Edge case: large values (to confirm int overflow is not an issue for LC constraints)
        NumArray large = new NumArray(new int[]{10000, -10000, 5000, -5000});
        System.out.println("Large mix sum(0, 3) -> " + large.sumRange(0, 3) + " (expected: 0)");
    }


    /*
        Approach 1: Prefix Sum (optimal)

        Build a prefix sum array once in the constructor:
            prefixSum[i] = sum of nums[0..i-1]
            prefixSum[0] = 0 (empty prefix)

        Then sumRange(left, right) = prefixSum[right + 1] - prefixSum[left]
        in O(1) time.

        Trade-off: O(n) build time and O(n) space up front to make each
        query O(1). Best when there are many queries on a static array.

        Time:
            Constructor: O(n)
            sumRange:    O(1)
        Space: O(n) for the prefix sum array
    */
    static class NumArray {

        private int[] prefixSum;

        public NumArray(int[] nums) {
            int n = nums.length;
            prefixSum = new int[n + 1];

            prefixSum[0] = 0;                       // empty prefix has sum 0
            for (int i = 1; i <= n; i++) {
                prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
            }
        }

        public int sumRange(int left, int right) {
            // sum(nums[left..right]) = prefix[right+1] - prefix[left]
            return prefixSum[right + 1] - prefixSum[left];
        }
    }


    /*
        Approach 2: Brute Force (no preprocessing)

        Store the array as-is and sum nums[left..right] on each query.

        Trade-off: O(1) build time and O(n) space, but O(n) per query.
        Only useful when the number of queries is very small.

        Time:
            Constructor: O(1)
            sumRange:    O(n)
        Space: O(n) for storing the array
    */
    static class NumArrayBrute {

        private int[] nums;

        public NumArrayBrute(int[] nums) {
            this.nums = nums;
        }

        public int sumRange(int left, int right) {
            int sum = 0;
            for (int i = left; i <= right; i++) {
                sum += nums[i];
            }
            return sum;
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Prefix Sum (optimal)

Time Complexity:
    Constructor: O(n)
    sumRange:    O(1)
Space Complexity: O(n) — prefix array of size n+1

Approach 2: Brute Force

Time Complexity:
    Constructor: O(1)
    sumRange:    O(n)
Space Complexity: O(n) — same array, no extra structure

Key Observation:

The prefix sum trick turns a range-sum query into a simple subtraction:
    sum(left..right) = prefix[right + 1] - prefix[left]
This is the classic build-once / query-many trade-off. Since the array is
IMMUTABLE (per the problem name), paying O(n) once at construction is
always worth it — every future query drops from O(n) to O(1).

Rule of thumb:
    - Few queries (< log n): brute force may win.
    - Many queries (>= n / log n): prefix sum wins.
LeetCode 303 guarantees many queries, so prefix sum is the intended solution.

---------------------------------------------------------
*/