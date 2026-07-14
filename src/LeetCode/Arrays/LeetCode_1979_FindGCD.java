package LeetCode.Arrays;

public class LeetCode_1979_FindGCD {
    public static void main(String[] args) {

        int[] nums = {2, 5, 6, 9, 10};

        System.out.println(findGCD(nums));
    }

    /*
        Math + Euclidean Algorithm Approach :

        Find:

            1. Smallest element in the array.
            2. Largest element in the array.

        The required answer is simply the GCD
        of these two numbers.

        Compute the GCD using the
        Euclidean Algorithm.
     */

    static int findGCD(int[] nums) {

        // Stores the minimum element.
        int minimum = Integer.MAX_VALUE;

        // Stores the maximum element.
        int maximum = Integer.MIN_VALUE;

        // Find the minimum and maximum values.
        for (int num : nums) {

            minimum = Math.min(minimum, num);
            maximum = Math.max(maximum, num);
        }

        /*
            Compute the GCD of the minimum
            and maximum values.
         */
        int a = minimum;
        int b = maximum;

        while (b != 0) {

            int temp = b;

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

n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Traverse the array once to find the
   minimum and maximum values:
   O(n)

2. Euclidean Algorithm computes the GCD in:
   O(log(maximum))

Overall:

O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Only a few integer variables are used.

No extra data structures are required.

Overall:

O(1)

---------------------------------------------------------

Key Observation:

The GCD of the smallest and largest elements
is exactly the answer required by the problem.

Therefore, there is no need to compute the
GCD of every pair or of the entire array.

---------------------------------------------------------
*/