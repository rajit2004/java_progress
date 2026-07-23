package LeetCode.BitManipulation;

public class LeetCode_3513_NumOfUniqueXORTriplets_I {
    public static void main(String[] args) {

        int[] nums = {1, 2};

        System.out.println(uniqueXorTriplets(nums));
    }

    /*
        Bit Manipulation + Mathematical Observation :

        Let: n = nums.length

        The number of unique XOR triplets depends only on the length of the array.

        First, compute the smallest number of the form: 2^k - 1 , that is greater than or equal to n.

        Then, return: 2^k , except when n <= 2, in which case the answer is n itself.

        The expression at the end handles both cases without using an explicit if-statement.
     */
    static int uniqueXorTriplets(int[] nums) {

        int n = nums.length;

        // Copy n so its bits can be expanded.
        int m = n;

        /*
            Propagate the highest set bit to all lower positions.

            Example:  n = 5  (101) becomes m = 7  (111)
         */
        m |= m >> 1;
        m |= m >> 2;
        m |= m >> 4;
        m |= m >> 8;
        m |= m >> 16;

        /*
            (m + 1) becomes the next power of two.

            Examples:

            n = 5
            m = 7
            m + 1 = 8
        ----------------------
        For n <= 2, (3 / (n + 1)) evaluates to 1, so the value is divided by 2, producing the required answer.
        For n >= 3, (3 / (n + 1)) evaluates to 0, so no shift occurs.
         */
        return (m + 1) >> (3 / (n + 1));
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(1)

Reason: Only a fixed number of bitwise operations are performed.

Overall: O(1)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few integer variables are used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The answer depends only on the size of the array, not on its elements.

By expanding the highest set bit and adding 1, we obtain the required power of two.

A small arithmetic trick in the final right-shift handles the special case when n <= 2 without requiring an explicit conditional statement.

---------------------------------------------------------
*/