package LeetCode.PrefixSum;

import java.util.HashSet;
import java.util.Set;

public class LeetCode_2996_SmallestMissingIntGreaterThanSequentialPrefixSum {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 2, 5};

        System.out.println(missingInteger(nums));
    }

    /*
        Prefix Sum + HashSet Approach :

        Step 1: Find the longest sequential prefix. A sequential prefix follows: nums[i] = nums[i - 1] + 1
        Step 2: Calculate the sum of this sequential prefix.
        Step 3: Store all array elements in a HashSet for O(1) average-time lookup.
        Step 4: Starting from the sequential prefix sum, keep increasing the value until we find an integer that does not exist in nums.
     */
    static int missingInteger(int[] nums) {

        int n = nums.length;

        // Stores the sum of the sequential prefix.
        int sequentialSum = nums[0];

        // Find the longest sequential prefix.
        for (int i = 1; i < n; i++) {

            if (nums[i] == nums[i - 1] + 1) {

                sequentialSum += nums[i];

            } else {

                break;
            }
        }

        // Store all numbers for fast lookup.
        Set<Integer> numbers = new HashSet<>();

        for (int num : nums) {
            numbers.add(num);
        }

//            Find the smallest integer greater than or equal to the sequential prefix sum that does not exist in the array.
        while (numbers.contains(sequentialSum)) {

            sequentialSum++;
        }

        return sequentialSum;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Find the sequential prefix: O(n)

2. Insert all elements into the HashSet: O(n)

3. Search for the missing integer: O(n) in the worst case.

HashSet lookup takes O(1) average time.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: The HashSet stores all elements of the array.

Overall: O(n)

---------------------------------------------------------

Key Observation:

The sequential prefix only needs to be scanned once.
After calculating its sum, a HashSet allows us to quickly check whether that sum or any subsequent value exists in the array.

Therefore, we avoid repeatedly scanning the entire array for every candidate value.

---------------------------------------------------------
*/