package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3727_MaxAlternatingSumOfSqs {
    public static void main(String[] args) {
        System.out.println(maxAlternatingSum(new int[]{1, 2, 3}));       // 12
        System.out.println(maxAlternatingSum(new int[]{1, 2, 3, 4}));    // 20
    }


    /*
        Approach: Square, sort, subtract small half from large half

        After squaring, we want to arrange the values into an alternating
        sum  a1 - a2 + a3 - a4 + ...  that is maximized.

        To maximize, put the LARGEST values in the positive positions and
        the SMALLEST values in the negative positions. Since the number of
        positive slots is ceil(n/2) and negative slots is floor(n/2), the
        answer is:

            sum of largest ceil(n/2)  -  sum of smallest floor(n/2)

        Sort the squared array ascending, subtract the first n/2 elements,
        and add the rest.

        Time:  O(n log n)  — dominated by the sort
        Space: O(1)        — sort in place, mutate nums
    */
    static long maxAlternatingSum(int[] nums) {
        int n = nums.length;

        // Square each element in place
        for (int i = 0; i < n; i++) {
            nums[i] *= nums[i];
        }

        // Sort so we can pick the smallest half to subtract
        Arrays.sort(nums);

        long ans = 0;

        // Subtract the smaller half
        for (int i = 0; i < n / 2; i++) {
            ans -= nums[i];
        }

        // Add the larger half (ceil(n/2) elements)
        for (int i = n / 2; i < n; i++) {
            ans += nums[i];
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Square, sort, subtract small half from large half

Time Complexity: O(n log n)

- Squaring: O(n)
- Sorting: O(n log n)
- Sum loops: O(n)

Space Complexity: O(1)

- In-place mutation of nums; only constant extra variables.

Key Observation:
An alternating sum a1 - a2 + a3 - a4 + ... is maximized by putting the
largest values in positive slots and the smallest in negative slots.
After squaring (which makes all values non-negative and order-preserving
in magnitude), this reduces to: (sum of largest ceil(n/2)) - (sum of
smallest floor(n/2)).

---------------------------------------------------------
*/