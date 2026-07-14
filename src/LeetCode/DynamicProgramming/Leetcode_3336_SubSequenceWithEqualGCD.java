package LeetCode.DynamicProgramming;

public class Leetcode_3336_SubSequenceWithEqualGCD {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 4};

        System.out.println(subsequencePairCount(nums));
    }

    static final int MOD = 1_000_000_007;

    /*
        Dynamic Programming Approach :

        We build two subsequences simultaneously.

        DP State:

            dp[gcd1][gcd2]

        represents the number of ways to process
        the current elements such that:

            gcd of first subsequence = gcd1
            gcd of second subsequence = gcd2

        For every number, we have three choices:

            1. Ignore it.
            2. Put it in the first subsequence.
            3. Put it in the second subsequence.

        At the end, count all states where:

            gcd1 == gcd2
     */

    static int subsequencePairCount(int[] nums) {

        // Find the maximum value in the array.
        int maxValue = 0;

        for (int num : nums) {
            maxValue = Math.max(maxValue, num);
        }

        /*
            dp[gcd1][gcd2]

            Stores the number of ways to obtain
            the pair of GCDs.
         */
        int[][] dp = new int[maxValue + 1][maxValue + 1];

        // Initially both subsequences are empty.
        dp[0][0] = 1;

        // Process every number.
        for (int num : nums) {

            int[][] newDp =
                    new int[maxValue + 1][maxValue + 1];

            for (int gcd1 = 0; gcd1 <= maxValue; gcd1++) {

                // New GCD if current number is added
                // to the first subsequence.
                int nextGcd1 = gcd(gcd1, num);

                for (int gcd2 = 0; gcd2 <= maxValue; gcd2++) {

                    int ways = dp[gcd1][gcd2];

                    // Ignore unreachable states.
                    if (ways == 0)
                        continue;

                    // New GCD if added to the second subsequence.
                    int nextGcd2 = gcd(gcd2, num);

                    // Option 1 : Ignore current number.
                    newDp[gcd1][gcd2] =
                            (newDp[gcd1][gcd2] + ways) % MOD;

                    // Option 2 : Add to first subsequence.
                    newDp[nextGcd1][gcd2] =
                            (newDp[nextGcd1][gcd2] + ways) % MOD;

                    // Option 3 : Add to second subsequence.
                    newDp[gcd1][nextGcd2] =
                            (newDp[gcd1][nextGcd2] + ways) % MOD;
                }
            }

            dp = newDp;
        }

        int answer = 0;

        // Count all states where both subsequences
        // have the same non-zero GCD.
        for (int gcd = 1; gcd <= maxValue; gcd++) {

            answer = (answer + dp[gcd][gcd]) % MOD;
        }

        return answer;
    }

    /*
        Euclidean Algorithm

        Computes the Greatest Common Divisor (GCD)
        of two numbers.
     */
    static int gcd(int a, int b) {

        while (b != 0) {

            int temp = a % b;

            a = b;
            b = temp;
        }

        return a;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = nums.length

m = maximum element in nums

---------------------------------------------------------

Time Complexity: O(n × m²)

Reason:

1. There are (m + 1) × (m + 1)
   DP states.

2. Every number processes all states.

3. Each state performs only constant-time
   transitions.

Overall:

O(n × m²)

---------------------------------------------------------

Space Complexity: O(m²)

Reason:

Two DP tables are maintained:

dp[][]

newDp[][]

Each has size:

(m + 1) × (m + 1)

Overall:

O(m²)

---------------------------------------------------------

Key Observation:

Instead of generating every possible pair
of subsequences, Dynamic Programming keeps
track of only their current GCD values.

Each number has exactly three choices:

1. Ignore it.
2. Add it to the first subsequence.
3. Add it to the second subsequence.

This efficiently counts all valid pairs
whose final GCDs are equal.

---------------------------------------------------------
*/