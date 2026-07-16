package LeetCode.Numbers;

import java.util.Arrays;

public class LeetCode_3867_SumOfGCD_OfFormedPairs {
    public static void main(String[] args) {

        int[] nums = {2, 6, 4, 8};

        System.out.println(gcdSum(nums));
    }

    /*
        Greedy + Sorting Approach :

        Step 1: Build a prefix GCD array where each element stores the GCD of the current number and the maximum element seen so far.

        Step 2: Sort the prefix GCD array.

        Step 3: Pair the smallest value with the largest, the second smallest with the second largest, and so on.

        Compute the GCD of every pair and return the total sum.
    */
    static long gcdSum(int[] nums) {

        int n = nums.length;

        // Stores the prefix GCD values.
        int[] prefixGcd = new int[n];

        // Maximum value seen so far.
        int maxSoFar = 0;

        /*
            Build the prefix GCD array.

            prefixGcd[i] = GCD(nums[i], maximum element seen so far)
         */
        for (int i = 0; i < n; i++) {

            maxSoFar = Math.max(maxSoFar, nums[i]);

            prefixGcd[i] = gcd(nums[i], maxSoFar);
        }

        // Sort the prefix GCD array.
        Arrays.sort(prefixGcd);

        // Stores the final answer.
        long sum = 0;

        int left = 0;
        int right = n - 1;

//            Pair the smallest value with the largest until all pairs are processed.
        while (left < right) {

            sum += gcd(prefixGcd[left], prefixGcd[right]);

            left++;
            right--;
        }

        return sum;
    }

//        Euclidean Algorithm : Computes the Greatest Common Divisor (GCD) of two integers.
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

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n log n)

Reason:

1. Building the prefix GCD array: O(n)

2. Sorting the prefix GCD array: O(n log n)

3. Pairing elements: O(n)

4. Each GCD computation takes: O(log M)

Overall, sorting dominates.

Overall: O(n log n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: An additional prefixGcd array of size n is used.

Overall: O(n)

---------------------------------------------------------

Key Observation:

Instead of pairing the original numbers, the problem first transforms them into a prefix GCD array.

After sorting, pairing the smallest value with the largest provides the required pairing order, and the Euclidean Algorithm efficiently computes the GCD for every pair.

---------------------------------------------------------
*/