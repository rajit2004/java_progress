package LeetCode.PrefixSum;

public class LeetCode_3739_CountSubarraysWithMajorityELe_II {
    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3};
        int target = 2;

        System.out.println(countMajoritySubarrays(nums, target)); // 6
    }

    /*
        Prefix Sum + Fenwick Tree (Binary Indexed Tree) Approach : Convert the problem into a prefix sum problem.

        For each element:
            if nums[i] == target -> +1
            else                 -> -1

        A subarray has target as majority if: count(target) > subarrayLength / 2
        This condition becomes: prefix[j] > prefix[i]

        So for every current prefix sum, we need to count how many previous prefix sums are strictly smaller.

        Fenwick Tree efficiently maintains frequencies of previous prefix sums and answers these queries in O(log n).
     */

    static class Fenwick {
        int[] bit;

        Fenwick(int n) {
            bit = new int[n + 1];
        }

        // Add val at index idx.
        void update(int idx, int val) {
            while (idx < bit.length) {
                bit[idx] += val;
                idx += idx & -idx;
            }
        }

        // Returns sum from index 1 to idx.
        int query(int idx) {
            int sum = 0;

            while (idx > 0) {
                sum += bit[idx];
                idx -= idx & -idx;
            }

            return sum;
        }
    }

    static long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        /*
            Prefix sums can range from: -n to +n
            Shift them by (n + 1) to make all indices positive.
         */
        Fenwick ft = new Fenwick(2 * n + 3);

        // Represents prefix sum = 0 after shifting.
        int prefix = n + 1;

        // Add initial prefix sum.
        ft.update(prefix, 1);

        long ans = 0;

        for (int num : nums) {

            // Update prefix sum.
            prefix += (num == target) ? 1 : -1;

//                Count how many previous prefix sums are strictly smaller than current prefix.
            ans += ft.query(prefix - 1);

            // Store current prefix sum.
            ft.update(prefix, 1);
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

Time Complexity: O(n log n)

Reason: For each element:

1. One Fenwick Tree query -> O(log n)
2. One Fenwick Tree update -> O(log n)

Overall: n * O(log n) = O(n log n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: Fenwick Tree stores frequencies of all possible prefix sums.

Prefix sums range from: -n to +n
Hence: O(2n + 3) = O(n)

---------------------------------------------------------

Key Observation:

A subarray has target as majority if: count(target) > subarrayLength / 2

By mapping:
    target     -> +1
    non-target -> -1

the condition transforms into: currentPrefix > previousPrefix

Therefore, for every prefix sum we only need to count how many previous prefix sums are strictly smaller.

Fenwick Tree performs these frequency queries efficiently in O(log n).

---------------------------------------------------------
*/