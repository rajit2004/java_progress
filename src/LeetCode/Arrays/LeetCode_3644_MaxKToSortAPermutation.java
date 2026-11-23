package LeetCode.Arrays;

public class LeetCode_3644_MaxKToSortAPermutation {

    public static void main(String[] args) {
        int[] nums1 = {0, 1, 2, 3};
        int[] nums2 = {0, 2, 3, 1};
        int[] nums3 = {0, 3, 2, 1};

        System.out.println(sortPermutationAnd(nums1));
        System.out.println(sortPermutationAnd(nums2));
        System.out.println(sortPermutationAnd(nums3));
    }

    /* Approach 1: Bitwise AND of all out-of-place elements
       A value at an out-of-place position i must be reduced to i via
       nums[i] &= K. Every bit that survives in any out-of-place nums[i] must
       also be present in K, so AND-ing all out-of-place values gives the
       largest bitmask K that keeps every already-matching bit consistent.
       If every element is already in its own position, no K is needed and
       the answer is 0 (detected by `res` staying at Integer.MAX_VALUE).
       Example: nums = [0,1,2,3] -> all in place -> 0
                nums = [0,2,3,1] -> out-of-place values 2,3,1 -> 2&3&1 = 0
                nums = [0,3,2,1] -> out-of-place values 3,1   -> 3&1 = 1
    */
    static int sortPermutationAnd(int[] nums) {
        int n = nums.length;
        int res = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (nums[i] == i) continue;
            res &= nums[i];
        }

        return res == Integer.MAX_VALUE ? 0 : res;
    }

    /* Approach 2: Brute force over candidate K
       Try every K from (2^bits - 1) down to 0, where `bits` is enough to
       cover the largest target (n-1). For each K, the permutation is
       sortable iff nums[i] & K == i for every position i. Return the first
       K that succeeds, or 0 if none does.
       Example: nums = [0,1,2,3] -> K = 3: all nums[i] & 3 == i -> 3? no,
                nums[2] = 2, 2 & 3 = 2 = i ✓, nums[3] = 3, 3 & 3 = 3 = i ✓,
                but nums[0] = 0 & 3 = 0 = i ✓, nums[1] = 1 & 3 = 1 = i ✓ so
                K = 3 works. Since we scan down and stop at first valid K, we
                return 3 for the already-sorted case (the AND approach returns
                0 because it skips all in-place positions).
    */
    static int sortPermutationBrute(int[] nums) {
        int n = nums.length;
        int bits = 32 - Integer.numberOfLeadingZeros(Math.max(1, n - 1));
        int upperK = (1 << bits) - 1;

        for (int K = upperK; K >= 0; K--) {
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                if ((nums[i] & K) != i) {
                    ok = false;
                    break;
                }
            }
            if (ok) return K;
        }
        return 0;
    }
}

/* Complexity Analysis
   Let n = nums.length.

   Approach 1: Bitwise AND of all out-of-place elements
     Time : O(n)   - one left-to-right pass, one AND per element.
     Space: O(1)   - single accumulator plus the loop index.
     Key Observation: any bit that survives in an out-of-place value also has
     to survive in K, so the AND of all such values is the largest bitmask
     that keeps every already-in-place bit untouched; if no position is out
     of place, the answer collapses to 0.

   Approach 2: Brute force over candidate K
     Time : O(2^bits * n) - up to (1 << bits) candidate values of K, each
            verified in O(n) against every position.
     Space: O(1)          - only the loop variables.
     Key Observation: the permutation is sortable by nums[i] &= K iff every
     position already evaluates to its target under that K; scanning K from
     largest to smallest and returning the first hit is a direct
     restatement of the problem definition and is only feasible because n
     (and hence `bits`) is small.
*/

