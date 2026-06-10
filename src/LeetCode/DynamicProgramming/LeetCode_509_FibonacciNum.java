package LeetCode.DynamicProgramming;

public class LeetCode_509_FibonacciNum {
    public static void main(String[] args) {
        System.out.println(fib(2));
        System.out.println(fib(3));
        System.out.println(fib(59));
    }

    /*
        Dynamic Programming (Bottom-Up)

        Fibonacci Relation: F(n) = F(n-1) + F(n-2)

        Instead of using recursion and repeatedly calculating the same values, we build the answer iteratively from smaller Fibonacci numbers.

        Observation: To calculate F(n), we only need: F(n-1) and F(n-2)

        Therefore, instead of storing an entire DP array, we keep track of only the previous two values.
     */

    static int fib(int n) {

        // Base cases.
        if (n == 0) return 0;
        if (n == 1) return 1;

        // Represents F(0).
        int prev2 = 0;

        // Represents F(1).
        int prev1 = 1;

        /*
            Build Fibonacci numbers from: F(2) → F(n)

            After each iteration:
                prev2 = old F(n-1)
                prev1 = new F(n)
         */
        for (int i = 2; i <= n; i++) {

            // Current Fibonacci number.
            int current = prev1 + prev2;

            // Shift the window forward.
            prev2 = prev1;
            prev1 = current;
        }

        // Stores F(n).
        return prev1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason: A single loop runs from: 2 → n , Each iteration performs constant-time work.

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only three variables are used: prev1 , prev2 , current

No recursion stack or DP array is required.

---------------------------------------------------------

Key Observation:

Although Fibonacci is naturally recursive: F(n) = F(n-1) + F(n-2)

we only need the previous two values to compute the next one.

This reduces space from: O(n)  → DP Array ,  to: O(1)  → Constant Space

---------------------------------------------------------
*/