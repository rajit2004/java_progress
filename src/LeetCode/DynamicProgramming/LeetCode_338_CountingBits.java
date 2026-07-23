package LeetCode.DynamicProgramming;

public class LeetCode_338_CountingBits {
    public static void main(String[] args) {

        int n = 5;

        int[] answer = countBits(n);

        for (int bit : answer) {
            System.out.print(bit + " ");
        }
    }

    /*
        Dynamic Programming Approach :

        Let: bits[i] = Number of set bits in i

        Observation: Dividing a number by 2 removes its least significant bit.

        Therefore, bits[i] = bits[i / 2] + (i % 2)

        where: bits[i / 2] gives the number of set bits after removing the last bit and (i % 2) tells whether the removed bit was 0 or 1.
     */
    static int[] countBits(int n) {

        // bits[i] stores the number of set bits in i.
        int[] bits = new int[n + 1];

        // bits[0] is already 0.

        // Compute the answer for every number from 1 to n.
        for (int i = 1; i <= n; i++) {

//                Remove the last bit by dividing by 2. Add 1 if the removed bit was 1, otherwise add 0.
            bits[i] = bits[i / 2] + (i % 2);
        }

        return bits;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = input number

---------------------------------------------------------

Time Complexity: O(n)

Reason: Each number from 1 to n is processed exactly once.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: An array of size (n + 1) is used to store the number of set bits for every integer.

Overall: O(n)

---------------------------------------------------------

Key Observation:

The number of set bits in a number can be derived from the result of half of that number.

Since: bits[i] = bits[i / 2] + (i % 2) every answer is built using a previously computed value, making Dynamic Programming possible.

---------------------------------------------------------
*/