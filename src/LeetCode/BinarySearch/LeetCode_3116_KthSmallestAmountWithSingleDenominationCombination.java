package LeetCode.BinarySearch;

import java.util.*;

public class LeetCode_3116_KthSmallestAmountWithSingleDenominationCombination {
    public static void main(String[] args) {

        int[] coins = {3, 6, 9};
        int k = 7;

        System.out.println(findKthSmallest(coins, k));
    }

    /*
        Binary Search + Inclusion-Exclusion Approach : We need to find the kth smallest positive amount that can be formed using at least one denomination.

        For a given value x, we can count how many valid amounts are <= x.
        Binary search is then used to find the smallest x whose count is at least k.
        Inclusion-Exclusion is used to avoid counting multiples shared by multiple denominations.
        Before processing, redundant coins are removed.

        Example: coins = [2, 4, 8]

        Since every multiple of 4 is already a multiple of 2, and every multiple of 8 is also a multiple of 2, only 2 is needed.
        This reduces the number of subsets that Inclusion-Exclusion needs to process.
     */
    static long findKthSmallest(int[] coins, int k) {

        // Sort coins so that redundant larger denominations can be detected.
        Arrays.sort(coins);

        List<Integer> usefulCoins = new ArrayList<>();

        /*
            Remove any coin that is divisible by an already selected smaller coin.
            Such a coin does not introduce any new possible amounts.
         */
        for (int coin : coins) {

            boolean useful = true;

            for (int selected : usefulCoins) {
                if (coin % selected == 0) {
                    useful = false;
                    break;
                }
            }

            if (useful)
                usefulCoins.add(coin);
        }

        // Convert the useful coins back to an array.
        coins = usefulCoins.stream().mapToInt(Integer::intValue).toArray();

        int n = coins.length;

        // Total number of subsets of the useful coins.
        int totalMasks = 1 << n;

        // Number of selected coins in each subset.
        int[] bitCount = new int[totalMasks];

        // LCM of the coins represented by each subset.
        long[] lcm = new long[totalMasks];

        /*
            The kth smallest value cannot be smaller than k.
            Using the smallest coin, the kth multiple is at most coins[0] * k.
         */
        long left = k;
        long right = (long) coins[0] * k + 1;

//            Precompute the number of selected coins for every subset mask.
        for (int mask = 1; mask < totalMasks; mask++)
            bitCount[mask] = bitCount[mask >> 1]+ (mask & 1);

        // LCM of an empty set is 1.
        lcm[0] = 1;

        /*
            Calculate the LCM for every subset.
            If the LCM becomes larger than the binary-search range, store right + 1 because such a subset cannot contribute to the count.
         */
        for (int mask = 1; mask < totalMasks; mask++) {

            // Remove the lowest set bit.
            int previousMask = mask & (mask - 1);

            // Find the coin represented by that bit.
            int index = Integer.numberOfTrailingZeros(mask);

            long gcd = gcd(lcm[previousMask],coins[index]);
            long value = lcm[previousMask] / gcd;

//                Check for overflow before multiplying by the current coin.
            if (value <= right / coins[index])
                lcm[mask] = value * coins[index];
            else
                lcm[mask] = right + 1;

        }

//            Binary search for the smallest value x such that at least k valid amounts are <= x.
        while (left < right) {

            long mid = left + (right - left) / 2;

            if (count(mid, totalMasks, lcm, bitCount) >= k)
                right = mid;
            else
                left = mid + 1;
        }

        return left;
    }

    /*
        Counts how many positive integers <= x are divisible by at least one useful coin.

        Inclusion-Exclusion:
            Odd number of selected coins: Add the multiples of their LCM.
            Even number of selected coins: Subtract the multiples of their LCM.
     */
    private static long count(
            long x,
            int totalMasks,
            long[] lcm,
            int[] bitCount
    ) {

        long result = 0;

        for (int mask = 1; mask < totalMasks; mask++) {

            // This subset cannot contribute if its LCM is greater than x.
            if (lcm[mask] > x)
                continue;

            long multiples = x / lcm[mask];

            // Inclusion-Exclusion sign.
            if ((bitCount[mask] & 1) == 1)
                result += multiples;
            else
                result -= multiples;
        }

        return result;
    }

    /*
        Euclidean Algorithm for GCD.

        Repeatedly replace: (a, b) -> (b, a % b) until b becomes zero.
     */
    private static long gcd(long a, long b) {

        while (b != 0) {

            long temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of useful coins
K = k

---------------------------------------------------------

Time Complexity: O(n * 2^n + log(K) * 2^n)

Reason:

1. Sorting the coins: O(c log c)

2. Removing redundant coins: O(n²)

3. Building subset information: O(2^n)

4. Calculating every subset LCM: O(2^n)

5. Binary search performs O(log K) iterations.

6. Each binary-search iteration checks every subset using Inclusion-Exclusion: O(2^n)

Overall: O(n * 2^n + 2^n log K)

---------------------------------------------------------

Space Complexity: O(2^n)

Reason:

Two arrays are maintained for every subset:

1. bitCount
2. lcm

Therefore: O(2^n)

---------------------------------------------------------

Key Observation:

The problem can be converted into: "How many positive integers <= x are divisible by at least one coin?"
Once this count can be calculated, binary search can find the kth smallest valid amount.
Inclusion-Exclusion calculates the count without double-counting numbers divisible by multiple coins.
The LCM of each subset determines the numbers common to all coins in that subset.

---------------------------------------------------------
*/