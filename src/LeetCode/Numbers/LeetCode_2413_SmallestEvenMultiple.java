package LeetCode.Numbers;

//Given a positive integer n, return the smallest positive integer that is a multiple of both 2 and n.

public class LeetCode_2413_SmallestEvenMultiple {
    public static void main(String[] args) {

        int n = 5;

        System.out.println(smallestEvenMultiple(n));
    }

    /*
        Bit Manipulation Approach :

        If n is odd:
            Smallest even multiple = 2 × n

        If n is even:
            Smallest even multiple = n

        (n & 1) checks whether n is odd.

        Left shifting by:
            0 -> keeps the number unchanged.
            1 -> multiplies the number by 2.
    */
    static int smallestEvenMultiple(int n) {

        /*
            (n & 1)

            = 0 if n is even
            = 1 if n is odd

            Therefore:

            n << 0 = n
            n << 1 = 2 × n
        */
        return n << (n & 1);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(1)

Reason:

Only one bitwise AND operation and one
left shift operation are performed.

---------------------------------------------------------

Space Complexity: O(1)

Reason:

No extra variables or data structures are used.

---------------------------------------------------------

Key Observation:

A number's smallest even multiple is:

1. The number itself if it is even.
2. Twice the number if it is odd.

Using bit manipulation:

(n & 1) determines whether the number is odd, and << efficiently performs multiplication by 2 when needed.

---------------------------------------------------------
*/