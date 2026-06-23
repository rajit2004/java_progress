package LeetCode.DynamicProgramming;

public class LeetCode_3699_NumOfZigZagArrays_I {
    public static void main(String[] args) {
        System.out.println(zigZagArrays(3, 4, 5));
    }

    /*
        Dynamic Programming Approach A ZigZag array alternates between:

        smaller -> larger -> smaller -> ... OR larger -> smaller -> larger -> ...

        DP States:
            up[i] = number of zigzag arrays ending with value i where the last movement was upward.
            down[i] = number of zigzag arrays ending with value i where the last movement was downward.

        Since we need sums over ranges repeatedly, prefix sums are used to optimize transitions.
     */

    static int zigZagArrays(int n, int l, int r) {

        int MOD = 1_000_000_007;

        // Total distinct values available.
        int m = r - l + 1;

        /*
            Base Case for length = 2
                up[i]: Number of smaller values before i.
                down[i]: Number of larger values before i.
         */
        long[] up = new long[m];
        long[] down = new long[m];

        for (int i = 0; i < m; i++) {

            // Previous element must be smaller.
            up[i] = i;

            // Previous element must be larger.
            down[i] = m - i - 1;
        }

//            Build DP for lengths: 3 -> n
        for (int len = 3; len <= n; len++) {

//     Prefix sums allow us to answer: sum of DP values in a range in O(1) time instead of O(m).
            long[] prefUp = new long[m + 1];
            long[] prefDown = new long[m + 1];

            for (int i = 0; i < m; i++) {
                prefUp[i + 1] =
                        (prefUp[i] + up[i]) % MOD;

                prefDown[i + 1] =
                        (prefDown[i] + down[i]) % MOD;
            }

            long[] newUp = new long[m];
            long[] newDown = new long[m];

            for (int i = 0; i < m; i++) {

//  To end with an upward move at i: Previous value must be smaller than i and the previous move must be downward.
                newUp[i] = prefDown[i];

//  To end with a downward move at i: Previous value must be greater than i and the previous move must be upward.
                newDown[i] =
                        (prefUp[m] - prefUp[i + 1] + MOD) % MOD;
            }

            // Move to the next array length.
            up = newUp;
            down = newDown;
        }

//            Special handling for arrays of length 2. Any two distinct values form a zigzag.
        if (n == 2) {
            long ans = (long) m * (m - 1);
            return (int) (ans % MOD);
        }

//            Final answer: Sum all arrays ending with either an upward or downward movement.
        long ans = 0;

        for (int i = 0; i < m; i++) {
            ans = (ans + up[i] + down[i]) % MOD;
        }

        return (int) ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = required array length
m = number of possible values
    = (r - l + 1)

---------------------------------------------------------

Time Complexity: O(n * m)

Reason:

For every length from:

3 -> n

we perform:

1. Prefix sum construction: O(m)
2. DP transition: O(m)

Thus:

O((n - 2) * m)

≈ O(n * m)

---------------------------------------------------------

Space Complexity: O(m)

Reason:

We store:

up[m]
down[m]
prefUp[m + 1]
prefDown[m + 1]

All arrays are proportional to m.

Therefore: O(m)

---------------------------------------------------------

Key Observation: A naive DP transition would require: For every value i, check all smaller/larger values.

This would lead to: O(n * m²)

Using prefix sums reduces these range sum queries to O(1), improving the overall complexity to: O(n * m)

---------------------------------------------------------
*/