package LeetCode.Arrays;

public class LeetCode_3736_MinMovesToEqualArrayElements_III {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(minMovesSimulate(nums.clone()));
        System.out.println(minMovesDirect(nums));
    }

    /* Approach 1: Simulate one move at a time
       A single move picks one element and raises it by 1 while leaving all
       others untouched (any element can be raised each step). Repeatedly
       raise the current minimum to the current maximum and add (max - min)
       to the answer; this simulates the optimal sequence. Stops when every
       element equals the max. Efficient only for small inputs but shows the
       invariant behind the formula.
       Example: nums = [1,2,3] -> raise 1->3 (2 moves) -> [3,2,3]
                raise 2->3 (1 move) -> [3,3,3] -> answer 3.
    */
    static int minMovesSimulate(int[] nums) {
        int n = nums.length;
        int moves = 0;

        while (true) {
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            int maxIdx = 0, minIdx = 0;

            for (int i = 0; i < n; i++) {
                if (nums[i] > max) { max = nums[i]; maxIdx = i; }
                if (nums[i] < min) { min = nums[i]; minIdx = i; }
            }

            if (max == min) break;

            nums[minIdx] = max;
            moves += max - min;
        }

        return moves;
    }

    /* Approach 2: Direct formula (optimal)
       Each move raises exactly one element by 1, so the minimum total moves
       equals the sum over all elements of (max - nums[i]). Equivalently,
       n * max - sum(nums).
       Example: nums = [1,2,3] -> max = 3 -> (3-1)+(3-2)+(3-3) = 2+1+0 = 3.
    */
    static int minMovesDirect(int[] nums) {
        int max = nums[0];
        for (int x : nums) max = Math.max(max, x);

        int moves = 0;
        for (int x : nums) moves += max - x;

        return moves;
    }
}

/* Complexity Analysis
   Let n = nums.length, M = max(nums), m = min(nums), R = M - m.

   Approach 1: Simulate one move at a time
     Time : O(n * number_of_moves) - each iteration rescans nums to find the
            current max/min, and the number of moves can be large.
     Space: O(1)                   - a few scalars only.
     Key Observation: raising the current minimum to the current maximum is a
     safe greedy step; the sequence terminates when max equals min.

   Approach 2: Direct formula (recommended)
     Time : O(n)  - one pass to find max, one pass to sum the gaps.
     Space: O(1)  - running max and running sum of moves.
     Key Observation: each unit of (max - nums[i]) requires exactly one move,
     and no move can help two elements at once, so the total is just the sum
     of per-element gaps to the global maximum.
*/

