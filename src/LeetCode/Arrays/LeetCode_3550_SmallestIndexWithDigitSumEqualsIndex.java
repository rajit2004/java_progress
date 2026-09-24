package LeetCode.Arrays;

public class LeetCode_3550_SmallestIndexWithDigitSumEqualsIndex {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): [1,3,2] -> index 1: digitSum(3)=3 != 1, index 2: digitSum(2)=2 == 2 -> 2
        System.out.println("Sample 1 -> " + smallestIndex(new int[]{1, 3, 2}) + " (expected: 2)");

        // Sample 2 (LeetCode): [1,10,11] -> index 0: digitSum(1)=1 == 0? no. index 1: 1+0=1 == 1 yes -> 1
        System.out.println("Sample 2 -> " + smallestIndex(new int[]{1, 10, 11}) + " (expected: 1)");

        // Sample 3 (LeetCode): [1,2,3] -> index 0: 1 != 0, index 1: 2 != 1, index 2: 3 != 2 -> -1
        System.out.println("Sample 3 -> " + smallestIndex(new int[]{1, 2, 3}) + " (expected: -1)");

        // Edge case: index 0 must have nums[0] with digitSum 0 -> only 0 qualifies
        System.out.println("Edge (nums[0] = 0) -> " + smallestIndex(new int[]{0, 5, 9}) + " (expected: 0)");

        // Edge case: first index with match is far right
        //   [11, 22, 33, 4] -> idx 3: digitSum(4)=4 == 3? no. Wait, expected -1.
        //   Let's use [10, 20, 30, 40, 5] -> idx 4: digitSum(5)=5 == 4? no.
        //   Use [11, 21, 31, 41, 52] -> idx 4: 5+2=7 no.
        //   Better: [1, 2, 3, 4, 14] -> idx 4: 1+4=5 no. Use [1,2,3,4,23]: 2+3=5 no.
        //   Simplest: [1, 2, 3, 4, 5] -> idx 4: 5 == 4? no.
        //   [100000000? ] overkill. Use [1, 2, 3, 4, 50] -> idx 4: 5+0=5 != 4.
        //   Let's just pick: [9, 9, 9, 9, 4] -> idx 4: 4 != 4? yes! match at idx 4
        System.out.println("Edge (match at last) -> " + smallestIndex(new int[]{9, 9, 9, 9, 4})
                + " (expected: 4)");

        // Edge case: single element, index 0 with digitSum 0
        System.out.println("Edge (single 0) -> " + smallestIndex(new int[]{0}) + " (expected: 0)");

        // Edge case: single element, no match possible
        System.out.println("Edge (single 1) -> " + smallestIndex(new int[]{1}) + " (expected: -1)");

        // Edge case: larger number at matching index
        //   idx 2 with nums[2] = 20 -> digitSum(20)=2 -> match
        System.out.println("Edge (20 at idx 2) -> " + smallestIndex(new int[]{5, 5, 20, 5}) + " (expected: 2)");
    }


    /*
        Approach: Linear scan + digit-sum check

        Iterate indices i from 0 to n-1. For each nums[i], compute the sum
        of its decimal digits, and if it equals i, return i immediately.
        If the loop completes without a match, return -1.

        Digit sum of a non-negative integer:
            while (num > 0) { digitSum += num % 10; num /= 10; }

        Note: for num = 0, the loop body never executes, so digitSum remains
        0 — which is exactly what we want for index 0 matching nums[0] = 0.

        Returning on the first match guarantees the SMALLEST matching index,
        so no extra tracking is needed.

        Time:  O(n * d), where d = max number of digits in any element
        Space: O(1)
    */
    static int smallestIndex(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int digitSum = 0;

            // Compute the decimal digit sum of nums[i]
            while (num > 0) {
                digitSum += num % 10;
                num /= 10;
            }

            // First index whose value's digit sum equals the index
            if (digitSum == i) {
                return i;
            }
        }

        return -1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Linear scan + digit-sum check

Time Complexity: O(n * d)

- n = nums.length, d = number of digits of the largest element.
- d is bounded (at most ~10 for typical constraints), so effectively O(n).

Space Complexity: O(1)

- Only a few integer variables; no auxiliary data structures.

Key Observation:
The problem asks for the SMALLEST index, so a simple left-to-right scan
that returns the first match is already optimal. Digit sum of 0 is 0,
which naturally handles the nums[0] = 0 / index 0 case.

---------------------------------------------------------
*/