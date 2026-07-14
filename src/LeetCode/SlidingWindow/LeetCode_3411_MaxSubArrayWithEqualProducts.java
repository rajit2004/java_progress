package LeetCode.SlidingWindow;

public class LeetCode_3411_MaxSubArrayWithEqualProducts {
    public static void main(String[] args) {

        int[] nums = {1, 2, 1, 2, 1, 1};

        System.out.println(maxLength(nums));
    }

    /*
        Brute Force Approach :

        Generate every possible subarray.

        For each subarray:

            1. Compute its Product.
            2. Compute its GCD.
            3. Compute its LCM.

        If:

            Product == GCD × LCM

        update the maximum subarray length.
    */
    static int maxLength(int[] nums) {

        int n = nums.length;

        // Stores the maximum valid subarray length.
        int maxLength = 0;

        // Choose every possible starting index.
        for (int i = 0; i < n; i++) {

            int currentGCD = nums[i];
            int currentLCM = nums[i];
            int currentProduct = nums[i];

            /*
                Extend the subarray one element at a time.
             */
            for (int j = i + 1; j < n; j++) {

                currentProduct *= nums[j];

                currentGCD = gcd(currentGCD, nums[j]);

                currentLCM = lcm(currentLCM, nums[j]);

                // Check whether the condition holds.
                if (currentProduct == currentLCM * currentGCD) {

                    maxLength = Math.max(maxLength,
                            j - i + 1);
                }
            }
        }

        return maxLength;
    }

    /*
        Euclidean Algorithm

        Computes the Greatest Common Divisor (GCD)
        of two integers.
    */
    static int gcd(int a, int b) {

        if (b == 0)
            return a;

        return gcd(b, a % b);
    }

    /*
        Computes the Least Common Multiple (LCM)
        using the relation:

        LCM(a, b) = (a × b) / GCD(a, b)
    */
    static int lcm(int a, int b) {

        return (a / gcd(a, b)) * b;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = nums.length

---------------------------------------------------------

Time Complexity: O(n² × log M)

Reason:

1. The nested loops generate all possible subarrays:
   O(n²)

2. For every new element added to the subarray,
   GCD and LCM are updated.

3. Each GCD computation takes:

   O(log M)

where M is the maximum element.

Overall:

O(n² × log M)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Only a few integer variables are maintained.

No additional data structures are used.

---------------------------------------------------------

Key Observation:

Instead of recomputing the Product, GCD,
and LCM for every subarray from scratch,
they are updated incrementally while the
subarray expands.

This reduces redundant work and keeps the
solution efficient for the brute-force approach.

---------------------------------------------------------
*/