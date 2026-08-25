package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3718_SmallestMissingMultipleOfK {
    public static void main(String[] args) {

        int[] nums = {2, 3, 6, 10, 12};
        int k = 2;

        System.out.println(new Solution().missingMultiple(nums, k));
        System.out.println(new Solution2().missingMultiple(nums, k));
        System.out.println(new Solution3().missingMultiple(nums, k));
        System.out.println(new Solution4().missingMultiple(nums, k));
    }
}

/*
    Approach 1: HashSet : Store all elements in a HashSet for O(1) average lookup.
    Starting from k, keep checking multiples of k until a multiple is not present in the set.
 */
class Solution {

    public int missingMultiple(int[] nums, int k) {

        // Store all numbers for constant-time lookup.
        Set<Integer> seen = new HashSet<>();

        for (int num : nums)
            seen.add(num);

        // Start checking from the first positive multiple of k.
        int cur = k;

        // Keep moving to the next multiple while it exists.
        while (seen.contains(cur))
            cur += k;

        return cur;
    }
}

/*
---------------------------------------------------------
Approach 2: Boolean Array

    Since the required values are bounded, a boolean array can be used to track which numbers are present.
    present[x] == true means x exists in nums.
    Then check k, 2k, 3k, ... until a missing multiple is found.
 */
class Solution2 {

    public int missingMultiple(int[] nums, int k) {

        // Track whether each value up to 100 is present.
        boolean[] present = new boolean[101];

        for (int num : nums) {

            // Only values within the supported range need to be stored.
            if (num <= 100)
                present[num] = true;
        }

        // Check every positive multiple of k.
        for (int multiple = k; ; multiple += k) {

            // Return the first missing multiple.
            if (multiple > 100 || !present[multiple])
                return multiple;

        }
    }
}

/*
---------------------------------------------------------
Approach 3: Brute Force

    For every multiple of k, scan the entire nums array to determine whether that multiple exists.
    This approach does not use any additional data structure.
 */
class Solution3 {

    public int missingMultiple(int[] nums, int k) {

        // Start with the first positive multiple of k.
        for (int multiple = k; ; multiple += k) {

            boolean found = false;

            // Search for the current multiple in nums.
            for (int num : nums) {

                if (num == multiple) {
                    found = true;
                    break;
                }
            }

            // Return the first multiple that does not exist.
            if (!found)
                return multiple;
        }
    }
}

/*
---------------------------------------------------------
Approach 4: Boolean Array Using Multiples of K

    Instead of storing the actual values, store the multiplier of k.

    For example, if k = 3:

        3  -> index 1
        6  -> index 2
        9  -> index 3

    present[num / k] tells us whether that multiple of k exists.
 */
class Solution4 {

    public int missingMultiple(int[] nums, int k) {

        // present[i] represents whether i * k exists.
        boolean[] present = new boolean[101];

        for (int num : nums) {

            // Only multiples of k are relevant.
            if (num % k == 0)

                // Convert the number into its multiplier.
                present[num / k] = true;

        }

        // Find the first missing positive multiple.
        for (int i = 1; i <= 100; i++) {

            if (!present[i])
                return i * k;

        }

        // If all first 100 multiples exist.
        return 101 * k;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: HashSet

Time Complexity: O(n + m)

Space Complexity: O(n)

Where:
    n = nums.length
    m = number of multiples checked


---------------------------------------------------------

Approach 2: Boolean Array

Time Complexity: O(n + m)

Space Complexity: O(1)

The boolean array has a fixed size of 101.


---------------------------------------------------------

Approach 3: Brute Force

Time Complexity: O(n * m)

Space Complexity: O(1)

For every multiple of k, the complete nums array may need to be scanned.


---------------------------------------------------------

Approach 4: Boolean Array of Multipliers

Time Complexity: O(n + 100)

Space Complexity: O(1)

The array size is fixed at 101.


---------------------------------------------------------
Key Observation:

Only positive multiples of k matter.

Starting from: k, 2k, 3k, ... , the first value that does not exist in nums is the required answer.

The four approaches differ only in how they check whether a multiple exists:

    1. HashSet
    2. Boolean array
    3. Linear search
    4. Boolean array using the multiplier

---------------------------------------------------------
*/