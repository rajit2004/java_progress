package LeetCode.BitManipulation;

public class LeetCode_3513_NumOfUniqueXORTriplets_I_ALT {
    public static void main(String[] args) {

        int[] nums = {1, 2};

        System.out.println(uniqueXorTriplets(nums));
    }

    /*
        Bit Manipulation + Mathematical Observation :

        Let: n = nums.length
        The answer depends only on the length of the array.
        Find the position of the highest set bit in n using Integer.numberOfLeadingZeros().
        This directly computes the required power of two.
        The subtraction: 3 / (n + 1) automatically handles the special case when n <= 2 without using an if-statement.
     */
    static int uniqueXorTriplets(int[] nums) {

        // Number of elements in the array.
        int n = nums.length;

        /*
            Integer.numberOfLeadingZeros(n) returns the number of leading zero bits.

            Therefore, 32 - Integer.numberOfLeadingZeros(n) gives the position (1-based) of the highest set bit.

            Examples:

            n = 5
            Highest set bit = 3
            Answer = 1 << 3 = 8

            -----------------------

            For n <= 2, (3 / (n + 1)) becomes 1, reducing the exponent by one.
            For n >= 3, (3 / (n + 1)) becomes 0, leaving the exponent unchanged.
         */
        return 1 << (32 - Integer.numberOfLeadingZeros(n) - 3 / (n + 1));
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(1)

Reason: Only a constant number of arithmetic and bitwise operations are performed.

Overall: O(1)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few integer variables are used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

The answer depends only on the array length.

Integer.numberOfLeadingZeros() directly locates the highest set bit, allowing the required power of two to be computed in constant time.

The arithmetic expression 3 / (n + 1) eliminates the need for a separate conditional statement for small values of n.

---------------------------------------------------------
*/