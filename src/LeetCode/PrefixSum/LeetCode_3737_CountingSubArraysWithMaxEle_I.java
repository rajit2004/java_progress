package LeetCode.PrefixSum;

public class LeetCode_3737_CountingSubArraysWithMaxEle_I {
    public static void main(String[] args) {
        int[] nums = {1,2,2,3};
        int target = 2;

        System.out.println(countMajoritySubarrays(nums, target));
    }

    /*
        Brute Force Approach : We generate every possible subarray.

        For each subarray:
            1. Count how many times 'target' appears.
            2. Find the length of the subarray.
            3. If: count(target) > length / 2 : then target is the majority element in that subarray.

        Count all such valid subarrays.
     */

    static int countMajoritySubarrays(int[] nums, int target) {

        int n = nums.length;

        // Stores the number of valid subarrays.
        int ans = 0;

//            Choose every possible starting index.
        for (int i = 0; i < n; i++) {

            // Number of target occurrences in the current subarray.
            int cntTarget = 0;

//                Extend the subarray from i to j.
            for (int j = i; j < n; j++) {

                // Update target count if found.
                if (nums[j] == target) {
                    cntTarget++;
                }

                // Current subarray length.
                int len = j - i + 1;

//                    Check if target appears more than half the length of the current subarray.
                if (cntTarget > len / 2) {
                    ans++;
                }
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n²)

Reason:
1. Outer loop chooses the starting index.
2. Inner loop extends the subarray.

The nested loops generate all possible subarrays.
Total iterations: n + (n-1) + (n-2) + ... + 1 = n(n + 1) / 2 = O(n²)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few integer variables are used: ans and cntTarget and len
No extra arrays or data structures are created.

---------------------------------------------------------

Key Observation:

A number is a majority element in a subarray if: frequency(target) > subarrayLength / 2
By maintaining a running count of target while expanding the subarray, we avoid recounting frequencies from scratch.

---------------------------------------------------------
*/