package LeetCode.Numbers;

/*
You are given an integer n.

Return true if its binary representation contains
exactly one pair of consecutive set bits, otherwise false.
*/

public class LeetCode_3950_ExactlyOneConsecutiveSetBitsPair {
    public static void main(String[] args) {
        System.out.println(consecutiveSetBits(6));
    }

    /*
        Approach: A pair of consecutive set bits is: 11 in binary.

        We examine the last two bits at every step. If the last two bits are both 1:
            binary = 11
            decimal = 3

        then we have found one consecutive set-bit pair.

        After checking, shift the number right by one bit and continue checking the next overlapping pair.
     */

    static boolean consecutiveSetBits(int n) {

        // Counts how many consecutive set-bit pairs exist.
        int count = 0;

        while (n > 0) {

            /*
                n & 3 extracts the last two bits.

                Example:

                n = 6
                binary = 110

                110
              & 011
              -----
                010

                Not equal to 3.

                After shifting:

                11
              & 11
              ----
                11 (= 3)

                One consecutive set-bit pair found.
             */
            if ((n & 3) == 3)
                count++;

            // Move one bit to the right to check the next overlapping pair.
            n >>= 1;
        }

        // Valid only if exactly one pair exists.
        return count == 1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(log n)

Reason: The number is repeatedly shifted right by one bit.

The number of bits in n is: log₂(n)

Therefore: Time Complexity = O(log n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few integer variables are used. No extra data structures are created.

---------------------------------------------------------

Key Observation:

(n & 3) : extracts the last two bits.

Since: 11₂ = 3

checking: (n & 3) == 3 => tells us whether the current two-bit window contains consecutive set bits.

By shifting one bit at a time, we examine all overlapping pairs in the binary representation.

---------------------------------------------------------
*/