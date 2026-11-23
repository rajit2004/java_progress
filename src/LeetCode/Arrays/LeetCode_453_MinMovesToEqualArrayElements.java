package LeetCode.Arrays;

import java.util.*;

public class LeetCode_453_MinMovesToEqualArrayElements {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(minMovesFormula(nums));
        System.out.println(minMovesDeltaSum(nums));
    }

    /* Approach 1: Sum minus n * min (single pass)
       One move decrements n-1 elements by 1 — equivalently, increments a
       single element by 1 in a "relative" view. To make every element equal,
       the final value must be the original minimum. Each element x needs
       (x - min) increments, so total moves = sum(x - min) = sum - n * min.
       Example: [1,2,3] -> sum = 6, min = 1, n = 3 -> 6 - 3*1 = 3.
    */
    static int minMovesFormula(int[] nums) {
        int sum = 0, min = Integer.MAX_VALUE;
        for (int x : nums) {
            sum += x;
            min = Math.min(min, x);
        }
        return sum - nums.length * min;
    }

    /* Approach 2: Direct sum of (x - min)
       Same idea expressed as an explicit delta sum: find the minimum in one
       pass, then add (x - min) for every element. Slower by a constant factor
       (two passes), but is a more direct restatement of the invariant.
       Example: [1,2,3] -> min = 1 -> (0) + (1) + (2) = 3.
    */
    static int minMovesDeltaSum(int[] nums) {
        int min = Arrays.stream(nums).min().getAsInt();
        int moves = 0;
        for (int x : nums) moves += x - min;
        return moves;
    }
}

/* Complexity Analysis
   Let n = nums.length.

   Approach 1: Sum minus n * min (single pass)
     Time : O(n)  - one pass tracking sum and minimum simultaneously.
     Space: O(1)  - two scalars.
     Key Observation: each move shifts (n-1) elements down by 1, so it is
     equivalent to lifting one element up by 1; the equilibrium point is the
     array minimum, and the total moves = sum - n * min.

   Approach 2: Direct sum of (x - min)
     Time : O(n)  - one pass for the minimum plus one pass for the sum.
     Space: O(1)  - running min and moves.
     Key Observation: same invariant as Approach 1, just summing each
     element's individual gap to the minimum directly instead of using the
     algebraic shortcut; less efficient per element but easier to read.
*/