package LeetCode.Recursion;

public class LeetCode_486_PredictTheWinner {
    public static void main(String[] args) {

        int[] nums = {1, 5, 2};

        System.out.println(predictTheWinner(nums));
    }

    /*
        Recursive Minimax Approach : At every turn, the current player has two choices:

        1. Pick the leftmost number.
        2. Pick the rightmost number.

        The opponent will also play optimally.

        Instead of calculating both player's scores separately, return the maximum score difference the current player can achieve over the opponent.

        If the final score difference is non-negative, Player 1 can win or tie.
     */
    static boolean predictTheWinner(int[] nums) {

        return solve(nums, 0, nums.length - 1) >= 0;
    }

//        Returns the maximum score difference the current player can achieve from the subarray [left...right].
    static int solve(int[] nums, int left, int right) {

        // Only one number is available.
        if (left == right) {
            return nums[left];
        }

//            Pick the left element. The opponent's best result is subtracted because both players play optimally.
        int pickLeft = nums[left] - solve(nums, left + 1, right);

//            Pick the right element. Again subtract the opponent's optimal score difference.
        int pickRight = nums[right] - solve(nums, left, right - 1);

        // Choose the better option.
        return Math.max(pickLeft, pickRight);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(2ⁿ)

Reason: For every state, two recursive choices are explored.

The same subproblems are solved repeatedly, leading to exponential time.

Overall: O(2ⁿ)

---------------------------------------------------------

Space Complexity: O(n)

Reason: The maximum recursion depth is equal to the length of the array.

Overall: O(n)

---------------------------------------------------------

Key Observation:

Instead of computing both players' scores, compute only the score difference between the current player and the opponent.

If the maximum achievable difference is non-negative, the first player can guarantee at least a tie, which counts as a win.

---------------------------------------------------------
*/