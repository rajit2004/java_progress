package LeetCode.Numbers;

/*
You are given two integers n and k.

The cost of a binary string s is defined as the sum of all indices i (0-based)
such that s[i] == '1'.

A binary string is valid if:
1. It does not contain consecutive '1's.
2. Its cost is <= k.

Return all valid binary strings of length n.
*/

import java.util.ArrayList;
import java.util.List;

public class LeetCode_3955_ValidBinaryStringsWithCostLimit {
    public static void main(String[] args) {
        System.out.println(generateValidStrings(3,1));
    }

    /*
        Brute Force Approach:

        A binary string of length n can have: 2^n possible combinations.

        Generate every possible binary string using bit masking and check:

        1. Does it contain consecutive 1s?
        2. Is its cost <= k?

        If both conditions are satisfied, add it to the answer.
     */

    static List<String> generateValidStrings(int n, int k) {
        List<String> ans = new ArrayList<>();

        // Total binary strings of length n.
        int total = 1 << n;

        // Generate every possible binary string.
        for (int mask = 0; mask < total; mask++) {

            StringBuilder sb = new StringBuilder();

            int cost = 0;
            boolean valid = true;

            /*
                Build the binary representation corresponding to the current mask.
             */
            for (int i = 0; i < n; i++) {

                // Extract the current bit.
                int bit = (mask >> (n - 1 - i)) & 1;

                sb.append(bit);

                if (bit == 1) {

                    // Add the index to the cost.
                    cost += i;

                    /*
                        Check for consecutive 1s.

                        Example:

                        0110

                        At index 2:
                        Current bit = 1
                        Previous bit = 1

                        => Invalid string
                     */
                    if (i > 0 && sb.charAt(i - 1) == '1') {
                        valid = false;
                        break;
                    }
                }
            }

            // Add only valid strings whose cost is within the limit.
            if (valid && cost <= k) {
                ans.add(sb.toString());
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n * 2^n)

Reason:

1. There are 2^n possible binary strings.
2. For each string, we process n positions.

Therefore:

O(2^n * n)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

- StringBuilder stores at most n characters.
- Other variables take constant space.

Ignoring the output list:

Auxiliary Space = O(n)

---------------------------------------------------------

Output Space:

In the worst case, many valid strings may exist.

Output Size = O(n * 2^n)

---------------------------------------------------------

Key Observation:

Every binary string of length n can be represented by a number from: 0 to (2^n - 1)

Using bit masking: (mask >> position) & 1 allows us to generate all possible binary strings without using recursion.

---------------------------------------------------------
*/