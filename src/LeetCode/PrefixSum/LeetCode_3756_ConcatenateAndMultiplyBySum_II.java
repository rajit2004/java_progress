package LeetCode.PrefixSum;

public class LeetCode_3756_ConcatenateAndMultiplyBySum_II {
    public static void main(String[] args) {

        String s = "105203";

        int[][] queries = {
                {0, 5},
                {1, 4},
                {2, 5}
        };

        int[] ans = sumAndMultiply(s, queries);

        for (int num : ans) {
            System.out.print(num + " ");
        }
    }

    /*
        Prefix Sum Approach :

        Precompute four prefix arrays:

            1. digitSum      -> Sum of digits.
            2. nonZeroCount  -> Number of non-zero digits.
            3. prefixNum     -> Number formed by concatenating
                               only non-zero digits.
            4. pow10         -> Powers of 10 (mod MOD).

        For every query:

            1. Find digit sum using prefix sums.
            2. Find number of non-zero digits.
            3. Extract the concatenated non-zero number.
            4. Return:

                   concatenatedNumber × digitSum

               (mod MOD)
     */

    static int[] sumAndMultiply(String s, int[][] queries) {

        int n = s.length();

        int MOD = 1_000_000_007;

        // Prefix concatenated number (ignoring zeros).
        long[] prefixNum = new long[n + 1];

        // Prefix sum of digits.
        int[] digitSum = new int[n + 1];

        // Prefix count of non-zero digits.
        int[] nonZeroCount = new int[n + 1];

        // Powers of 10 modulo MOD.
        long[] pow10 = new long[n + 1];

        pow10[0] = 1;

        // Build all prefix arrays.
        for (int i = 0; i < n; i++) {

            int digit = s.charAt(i) - '0';

            digitSum[i + 1] = digitSum[i] + digit;

            nonZeroCount[i + 1] = nonZeroCount[i];

            if (digit != 0) {

                nonZeroCount[i + 1]++;

                /*
                    Append the digit to the
                    concatenated non-zero number.
                 */
                prefixNum[i + 1] =
                        (prefixNum[i] * 10 + digit) % MOD;
            }

            else {

                // Ignore zero digits.
                prefixNum[i + 1] = prefixNum[i];
            }

            // Precompute powers of 10.
            pow10[i + 1] =
                    (pow10[i] * 10) % MOD;
        }

        int[] ans = new int[queries.length];

        // Process every query independently.
        for (int i = 0; i < queries.length; i++) {

            int left = queries[i][0];
            int right = queries[i][1];

            // Sum of digits inside the range.
            int sum =
                    digitSum[right + 1] - digitSum[left];

            // Number of non-zero digits inside the range.
            int count =
                    nonZeroCount[right + 1] - nonZeroCount[left];

            /*
                Extract the concatenated non-zero
                number for the current range.

                Similar to extracting a substring
                using prefix hashes.
             */
            long number =
                    (prefixNum[right + 1]
                            - (prefixNum[left] * pow10[count]) % MOD
                            + MOD) % MOD;

            ans[i] = (int) ((number * sum) % MOD);
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = length of string
q = number of queries

---------------------------------------------------------

Time Complexity: O(n + q)

Reason:

1. Building all prefix arrays:
   O(n)

2. Every query is answered in O(1).

Overall:

O(n + q)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

Four prefix arrays are maintained:

1. prefixNum
2. digitSum
3. nonZeroCount
4. pow10

Each has size n + 1.

Overall:

O(n)

---------------------------------------------------------

Key Observation:

Without preprocessing, every query would
require scanning the substring,

resulting in O(n × q).

Using prefix sums and prefix concatenation,
each query is answered in constant time.

The concatenated non-zero number is extracted
using the same idea as prefix hashing by
removing the contribution of the prefix.

---------------------------------------------------------
*/