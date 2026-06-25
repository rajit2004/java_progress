package LeetCode.DynamicProgramming;

import java.util.Arrays;

public class LeetCode_3700_NumOfZigZagArrays_II {
    public static void main(String[] args) {
        System.out.println(zigZagArrays(3, 4, 5));
    }

   /*
    Matrix Exponentiation + Dynamic Programming Approach :

    A ZigZag array alternates between:

    smaller -> larger -> smaller -> ...
    OR
    larger -> smaller -> larger -> ...

    We model these transitions using matrices.

    m1[i][j] = 1 if j > i (next element is larger)
    m2[i][j] = 1 if j < i (next element is smaller)

    Matrix exponentiation allows us to efficiently compute transitions for very large n.
 */

    static int zigZagArrays(int n, int l, int r) {

        // Number of distinct values available.
        int len = r - l + 1;

        // Transition matrix for increasing moves.
        long[][] m1 = new long[len][len];

        // Transition matrix for decreasing moves.
        long[][] m2 = new long[len][len];

        // Build transition matrices.
        for (int i = 0; i < len; i++) {

            // current < next
            for (int j = i + 1; j < len; j++) {
                m1[i][j] = 1;
            }

            // current > next
            for (int j = 0; j < i; j++) {
                m2[i][j] = 1;
            }
        }

        // Combine two consecutive transitions.
        long[][] m = pro(m1, m2);

        // Every value can be chosen as the first element.
        long[] arr = new long[len];
        Arrays.fill(arr, 1);

        // First element already chosen.
        n--;

        // Process transitions in pairs.
        int count = n / 2;

        // Fast Matrix Exponentiation.
        while (count > 0) {

            if (count % 2 == 1)
                arr = pro(arr, m);

            m = pro(m);

            count /= 2;
        }

        // If one transition remains, apply increasing transition.
        if (n % 2 == 1)
            arr = pro(arr, m1);

        // Sum all possible ending states.
        long res = 0;

        for (long num : arr) {
            res += num;
        }

        // ZigZag can start with either increase or decrease.
        return (int) (res * 2 % mod);
    }

    static int mod = 1_000_000_007;

//        Matrix Squaring : Computes a × a
    static long[][] pro(long[][] a) {

        long[][] res = new long[a.length][a[0].length];

        for (int i = 0; i < res.length; i++) {

            for (int k = 0; k < res.length; k++) {

                // Skip unnecessary computations.
                if (a[i][k] == 0)
                    continue;

                for (int j = 0; j < res.length; j++) {
                    res[i][j] =
                            (res[i][j] + a[i][k] * a[k][j]) % mod;
                }
            }
        }

        return res;
    }

//        Matrix Multiplication : Computes a × b
    static long[][] pro(long[][] a, long[][] b) {

        long[][] res = new long[a.length][a[0].length];

        for (int i = 0; i < res.length; i++) {

            for (int k = 0; k < res.length; k++) {

                if (a[i][k] == 0)
                    continue;

                for (int j = 0; j < res.length; j++) {
                    res[i][j] =
                            (res[i][j] + a[i][k] * b[k][j]) % mod;
                }
            }
        }

        return res;
    }

//        Vector × Matrix Multiplication : Computes currentState × transitionMatrix
    static long[] pro(long[] a, long[][] b) {

        long[] res = new long[a.length];

        for (int j = 0; j < res.length; j++) {

            if (a[j] == 0)
                continue;

            for (int i = 0; i < res.length; i++) {
                res[i] =
                        (res[i] + a[j] * b[j][i]) % mod;
            }
        }

        return res;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:
m = number of possible values = (r - l + 1)
n = required array length

---------------------------------------------------------

Time Complexity: O(m³ log n)

Reason: Matrix multiplication takes O(m³).

Fast exponentiation performs O(log n) matrix multiplications.

Overall : O(m³ log n)

---------------------------------------------------------

Space Complexity: O(m²)

Reason:

Transition matrices store m × m values.

Additional vector requires O(m) space.

Overall : O(m²)

---------------------------------------------------------

Key Observation:

Instead of performing DP transitions one level at a time:

O(n × m²)

we represent transitions as matrices and use Fast Matrix Exponentiation to reduce the number of transitions from O(n) to O(log n).

---------------------------------------------------------
*/