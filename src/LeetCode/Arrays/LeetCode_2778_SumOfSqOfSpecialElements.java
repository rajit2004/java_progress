package LeetCode.Arrays;

public class LeetCode_2778_SumOfSqOfSpecialElements {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): [1,2,3,4], n=4
        //   Special positions: 1, 2, 4 (divide 4)
        //   1² + 2² + 4² = 1 + 4 + 16 = 21
        System.out.println("Sample 1 -> Linear: " + sumOfSquaresLinear(new int[]{1, 2, 3, 4})
                + " | Divisor: " + sumOfSquares(new int[]{1, 2, 3, 4})
                + " (expected: 21)");

        // Sample 2 (LeetCode): [2,7,1,19,18,3], n=6
        //   Special positions: 1, 2, 3, 6
        //   2² + 7² + 1² + 3² = 4 + 49 + 1 + 9 = 63
        System.out.println("Sample 2 -> Linear: " + sumOfSquaresLinear(new int[]{2, 7, 1, 19, 18, 3})
                + " | Divisor: " + sumOfSquares(new int[]{2, 7, 1, 19, 18, 3})
                + " (expected: 63)");

        // Edge case: single element -> position 1 always divides n=1
        System.out.println("Edge (single) -> Linear: " + sumOfSquaresLinear(new int[]{5})
                + " | Divisor: " + sumOfSquares(new int[]{5})
                + " (expected: 25)");

        // Edge case: n = prime -> only positions 1 and n are special
        //   [3,4,5,6,7], n=5 -> pos 1: 9, pos 5: 49 -> 58
        System.out.println("Edge (prime n) -> Linear: " + sumOfSquaresLinear(new int[]{3, 4, 5, 6, 7})
                + " | Divisor: " + sumOfSquares(new int[]{3, 4, 5, 6, 7})
                + " (expected: 58)");

        // Edge case: perfect square n -> ensure no double-count of sqrt position
        //   [1,2,3,4,5,6,7,8,9], n=9 -> special positions 1,3,9
        //   1² + 3² + 9² = 1 + 9 + 81 = 91
        System.out.println("Edge (perfect square n) -> Linear: "
                + sumOfSquaresLinear(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9})
                + " | Divisor: " + sumOfSquares(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9})
                + " (expected: 91)");

        // Edge case: zeros in array -> contribute 0 regardless
        //   [0,0,0,0], n=4 -> all special positions are 0 -> 0
        System.out.println("Edge (zeros) -> Linear: " + sumOfSquaresLinear(new int[]{0, 0, 0, 0})
                + " | Divisor: " + sumOfSquares(new int[]{0, 0, 0, 0})
                + " (expected: 0)");

        // Edge case: negative values -> squares are positive
        //   [-1,-2,-3,-4], n=4 -> pos 1,2,4 -> 1+4+16 = 21
        System.out.println("Edge (negatives) -> Linear: " + sumOfSquaresLinear(new int[]{-1, -2, -3, -4})
                + " | Divisor: " + sumOfSquares(new int[]{-1, -2, -3, -4})
                + " (expected: 21)");
    }


    /*
        Approach 1: Linear scan (O(n))

        For each 1-indexed position (i + 1), check if it divides n.
        If yes, add nums[i]² to the answer.

        Straightforward and O(n) — the simplest correct solution.

        Time:  O(n)
        Space: O(1)
    */
    static int sumOfSquaresLinear(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; ++i) {
            // i + 1 is the 1-indexed position; check divisibility by n
            if (n % (i + 1) == 0) {
                ans += nums[i] * nums[i];
            }
        }

        return ans;
    }


    /*
        Approach 2: Divisor enumeration up to sqrt(n)

        Instead of scanning all n positions, enumerate divisors of n up to
        sqrt(n). For each divisor i (1-indexed position), we immediately
        know its "partner" divisor n/i is also a divisor.

        Special care: when i * i == n (perfect square), n/i == i, so we
        must only add that position's square ONCE.

        This is asymptotically faster when n is large (O(sqrt(n)) vs O(n)),
        though for typical constraints (n <= 100) both are trivially fast.

        Time:  O(sqrt(n))
        Space: O(1)
    */
    static int sumOfSquares(int[] nums) {
        int n = nums.length;
        int ans = 0;

        // Iterate over divisors i of n
        for (int i = 1; i * i <= n; ++i) {
            if (n % i == 0) {
                // Position i (1-indexed) -> nums[i - 1]
                ans += nums[i - 1] * nums[i - 1];

                // Also handle the partner divisor n / i, unless it equals i
                // (which happens only when n is a perfect square and i = sqrt(n))
                if (i * i != n) {
                    ans += nums[n / i - 1] * nums[n / i - 1];
                }
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let n = nums.length.

Approach 1: Linear scan

Time Complexity: O(n)
- Iterate over all n positions, O(1) work per position.

Space Complexity: O(1)
- Only a few integer variables.


Approach 2: Divisor enumeration

Time Complexity: O(sqrt(n))
- Iterate i from 1 while i² <= n, so at most sqrt(n) iterations.
- Each iteration is O(1).

Space Complexity: O(1)
- Only a few integer variables.

Key Observation:

Positions that are "special" are exactly the divisors of n. Approach 1
enumerates every position and checks divisibility — O(n). Approach 2
enumerates divisors directly, exploiting the fact that divisors come in
pairs (i, n/i) around sqrt(n) — O(sqrt(n)).

For the actual LeetCode constraints (n <= 100), both are trivially fast.
The divisor approach is the more "algorithmically interesting" one to
know, but the linear scan is what most people write and is equally
accepted. If you ever face a problem where n can be up to 10^12 or so,
only the divisor approach is viable.

---------------------------------------------------------
*/