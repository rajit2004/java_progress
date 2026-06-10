package LeetCode.DynamicProgramming;

/*
You are climbing a staircase. It takes n steps to reach the top.
Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
*/

public class LeetCode_70_ClimbingStairs {
    public static void main(String[] args) {
        System.out.println(climbStairs(3));
        System.out.println(climbStairs(2));
    }

    /*
        Dynamic Programming Observation:

        To reach step n:
            1. We can come from step (n-1)
            2. We can come from step (n-2)

        Therefore: ways(n) = ways(n-1) + ways(n-2)

        This is identical to the Fibonacci pattern.
        Instead of recursion, we build the answer iteratively while storing only the previous two states.
     */

    static int climbStairs(int n) {

        /*
            Base Cases:

            n = 1 -> 1 way
            n = 2 -> 2 ways
         */
        if (n <= 2) {
            return n;
        }

        // Ways to reach step 1.
        int first = 1;

        // Ways to reach step 2.
        int second = 2;

        /*
            Build answers from: step 3 -> step n
                current = ways(step-1) + ways(step-2)
         */
        for (int i = 3; i <= n; i++) {

            // Ways to reach the current step.
            int current = first + second;

            // Move the window forward.
            first = second;
            second = current;
        }

        // Stores ways to reach step n.
        return second;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason: A single loop runs from: 3 -> n , Each iteration performs constant-time work.

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only three integer variables are used:
    first
    second
    current

No recursion stack or DP array is required.

---------------------------------------------------------

Key Observation:

To reach any step n:

ways(n) = ways(n-1) + ways(n-2)

because the final move must be either: 1 step from (n-1) or 2 steps from (n-2)

This transforms the problem into a Fibonacci-style Dynamic Programming problem.

---------------------------------------------------------
*/