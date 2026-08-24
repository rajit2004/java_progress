package LeetCode.PrefixSum;

public class LeetCode_1872_StoneGame_VIII {
    public static void main(String[] args) {

        int[] stones = {-1, 2, -3, 4, -5};

        System.out.println(stoneGameVIII(stones));
    }

    /*
        Prefix Sum + Dynamic Programming Approach : In this game, Alice must take at least one stone and then combine the selected stones into one value.

        The important observation is that after the first move, the remaining game can be represented using prefix sums.

        We build a prefix sum array where: prefix[i] = sum of stones[0...i]
        Starting with the case where Alice takes the entire array, we work backwards and determine the best score Alice can achieve.

        At every position: currentScore = prefix[i] - best
        where 'best' represents the opponent's optimal score from the remaining game.
     */
    static int stoneGameVIII(int[] stones) {

        int n = stones.length;

        // Create a copy so the original input array remains unchanged.
        int[] prefix = stones.clone();

        /*
            Build the prefix sum array.
            After this: prefix[i] = stones[0] + ... + stones[i]
         */
        for (int i = 1; i < n; i++)
            prefix[i] += prefix[i - 1];


//  Base case: If Alice takes all stones, the resulting score is the sum of the complete array.
        int best = prefix[n - 1];

        /*
            Process every valid earlier prefix from right to left.
            Alice's score for choosing prefix[i] is: prefix[i] - best because 'best' represents the optimal score the opponent can obtain afterward.
         */
        for (int i = n - 2; i >= 1; i++)
            best = Math.max(best,prefix[i] - best);

        return best;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = stones.length

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Building the prefix sum array takes O(n).
2. The DP calculation traverses the prefix array once from right to left, taking O(n).

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: A copy of the input array is created to store the prefix sums.

Therefore: O(n)

---------------------------------------------------------

Key Observation:

The game can be reduced to choosing a prefix sum.
After calculating all prefix sums, let 'best' represent the best score difference obtainable from the current state.
For every valid prefix: best = max(best, prefix[i] - best)
Processing from right to left ensures that the future state has already been calculated.
This converts the game into a simple one-dimensional dynamic programming recurrence.

---------------------------------------------------------
*/