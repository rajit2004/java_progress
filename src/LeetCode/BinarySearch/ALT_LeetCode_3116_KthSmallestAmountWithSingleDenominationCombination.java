package LeetCode.BinarySearch;

import java.util.Arrays;

public class ALT_LeetCode_3116_KthSmallestAmountWithSingleDenominationCombination {
    public static void main(String[] args) {

        int[] coins = {3, 6, 9};
        int k = 7;

        System.out.println(findKthSmallest(coins, k));
    }

    /*
        Binary Search + Inclusion-Exclusion Approach : We need to find the kth smallest positive amount that is divisible by at least one of the given coins.

        For a fixed value x, count how many valid amounts are <= x using Inclusion-Exclusion.
        Binary search is then used to find the smallest value whose count is at least k.
        For every subset of coins, its LCM represents numbers divisible by all coins in that subset.
     */
    static long findKthSmallest(int[] coins, int k) {

        // Sort coins so the smallest coin can be used to establish the upper bound.
        Arrays.sort(coins);

        int n = coins.length;
        int m = 1 << n;

        // The answer cannot be smaller than k.
        long l = k;

        // The kth multiple of the smallest coin provides an upper bound for the answer.
        long r = (long) coins[0] * k + 1;

        // Stores the number of coins selected in each subset.
        int[] bitCount = new int[m];

        // Stores the LCM of the coins represented by each subset.
        long[] lcm = new long[m];

//            Calculate the LCM and number of selected coins for every possible subset.
        for (int mask = 1; mask < m; mask++) {

            long curLcm = 1;

            for (int i = 0; i < n; i++) {

                // Check whether the current coin is included in this subset.
                if (((mask >> i) & 1) == 1) {

                    // Calculate GCD of the current LCM and the selected coin.
                    long g = gcd(curLcm, coins[i]);

                    long tmp = curLcm / g;

//                        Check for overflow before calculating the new LCM.
                    if (tmp <= r / coins[i]) {
                        curLcm = tmp * coins[i];
                    } else {

                        // This LCM is too large to contribute within the current binary-search range.
                        curLcm = r + 1;
                        break;
                    }

                    // Count the number of selected coins.
                    bitCount[mask]++;
                }
            }

            lcm[mask] = curLcm;
        }


//            Binary search for the smallest value x such that at least k valid amounts are less than or equal to x.

        while (l < r) {
            long x = l + (r - l) / 2;
            if (count(x, m, lcm, bitCount) >= k)
                r = x;
            else
                l = x + 1;
        }

        return l;
    }

    /*
        Count how many positive integers <= x are divisible by at least one of the given coins.

        Inclusion-Exclusion:
            Odd number of selected coins: Add multiples of their LCM.
            Even number of selected coins: Subtract multiples of their LCM.
     */
    private static long count(
            long x,
            int m,
            long[] lcm,
            int[] bitCount
    ) {

        long res = 0;

        for (int mask = 1; mask < m; mask++) {

            // This subset cannot contribute if its LCM is greater than x.
            if (lcm[mask] > x)
                continue;

            // Count multiples of the subset's LCM.
            long multiples = x / lcm[mask];

            // Apply the Inclusion-Exclusion sign.
            if ((bitCount[mask] & 1) == 1)
                res += multiples;
            else
                res -= multiples;
        }

        return res;
    }

    /*
        Euclidean Algorithm for GCD.

        Repeatedly replace: (a, b) -> (b, a % b) until b becomes zero.
     */
    private static long gcd(long a, long b) {

        while (b != 0) {

            long t = b;
            b = a % b;
            a = t;
        }

        return a;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of coins
K = k

---------------------------------------------------------

Time Complexity: O(n * 2^n + 2^n * log(K))

Reason:

1. There are 2^n possible subsets.
2. For every subset, we check all n coins: O(n * 2^n)
3. Binary search performs O(log K) iterations.
4. Every binary-search iteration checks all 2^n subsets: O(2^n * log K)

Overall: O(n * 2^n + 2^n * log K)

---------------------------------------------------------

Space Complexity: O(2^n)

Reason:

Two arrays are created for every subset:

1. bitCount
2. lcm

Therefore: O(2^n)

---------------------------------------------------------

Key Observation:

For a fixed value x, we need to know how many positive integers <= x are divisible by at least one of the given coins.
The LCM of a subset gives the numbers divisible by every coin in that subset.
Inclusion-Exclusion combines these subset counts without double-counting common multiples.
Once this count can be calculated, binary search finds the smallest value whose count reaches k.

---------------------------------------------------------
*/