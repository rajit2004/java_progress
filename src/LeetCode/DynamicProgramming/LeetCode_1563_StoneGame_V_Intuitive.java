package LeetCode.DynamicProgramming;

public class LeetCode_1563_StoneGame_V_Intuitive {
    public static void main(String[] args) {

        int[] stoneValue = {6, 2, 3, 4, 5, 5};

        System.out.println(stoneGameV(stoneValue));
    }

    static int[][] dp;

    /*
        Intuitive Dynamic Programming + Recursion : For every subarray [left...right], try every possible split position.

        For each split:
            leftSum  = sum of left part
            rightSum = sum of right part

        Rules:
            1. If leftSum < rightSum: Alice keeps the left part.
            2. If leftSum > rightSum: Alice keeps the right part.
            3. If leftSum == rightSum: Alice can choose either part.

        dp[left][right] stores the maximum score Alice can obtain from the subarray [left...right].

        Memoization prevents recalculating the same subarray multiple times.
     */

    static int stoneGameV(int[] stoneValue) {

        int n = stoneValue.length;

        // dp[left][right] stores the best score for the subarray [left...right].
        dp = new int[n][n];

        // Start with the complete array.
        return dfs(stoneValue, 0, n - 1);
    }
//    Recursively calculates the maximum score obtainable from stoneValue[left...right].
    static int dfs(int[] stoneValue, int left, int right) {

        // A single stone cannot be split further, so Alice gains no additional score.
        if (left == right)
            return 0;


//            A value of 0 means this state has not been calculated yet.
        if (dp[left][right] != 0)
            return dp[left][right];


        // Calculate the total sum of the current range.
        int totalSum = 0;

        for (int i = left; i <= right; i++)
            totalSum += stoneValue[i];


        // Sum of the left part of the current split.
        int leftSum = 0;

//  Try every possible split point. [left ... i] | [i + 1 ... right]
        for (int i = left; i < right; i++) {

            leftSum += stoneValue[i];

            // Sum of the right part.
            int rightSum = totalSum - leftSum;

//  Case 1: leftSum < rightSum => Alice keeps the left portion.
            if (leftSum < rightSum)
                dp[left][right] = Math.max(dp[left][right],dfs(stoneValue, left, i) + leftSum);

//  Case 2: leftSum > rightSum => Alice keeps the right portion.
            else if (leftSum > rightSum)
                dp[left][right] = Math.max(dp[left][right],dfs(stoneValue, i + 1, right) + rightSum);

//  Case 3: leftSum == rightSum => Alice can choose either side.
            else
                dp[left][right] = Math.max(dp[left][right],Math.max(dfs(stoneValue, left, i),dfs(stoneValue, i + 1, right)) + leftSum);
        }

        return dp[left][right];
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = stoneValue.length

---------------------------------------------------------

Time Complexity: O(n³)

Reason: There are O(n²) possible subarray states: dp[left][right]
For every state, we:
    1. Calculate the total sum in O(n).
    2. Try every possible split in O(n).

Therefore: O(n²) × O(n) = O(n³)

---------------------------------------------------------

Space Complexity: O(n²)

Reason: The DP table contains n × n states.

The recursion stack can use up to O(n) additional space.

The dominant space usage is: O(n²)

---------------------------------------------------------

Key Observation:

The most intuitive way to solve Stone Game V is to treat every subarray as a DP state.

For every possible split: [left...i] | [i+1...right] compare the two sums and recursively solve the portion Alice is allowed to keep.
Memoization ensures that each subarray is solved only once.
This approach is easier to understand than the optimized O(n²) solution, although its overall time complexity is O(n³).

---------------------------------------------------------
*/