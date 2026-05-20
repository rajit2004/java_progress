package LeetCode.BitManipulation;

/*
Given a positive integer n, write a function that returns the number of set bits in its binary representation
(also known as the Hamming weight).
*/

public class LeetCode_191_NumOf1bits {
    public static void main(String[] args) {
        int n = 11;
        System.out.println(hammingWeight(n));
    }
    static int hammingWeight(int n) {
        int count = 0;

        while (n != 0) {
            // Check if last bit is 1
            if ((n & 1) == 1) {
                count++;
            }

            // Unsigned right shift
            n = n >>> 1;
        }

        return count;
    }
}
