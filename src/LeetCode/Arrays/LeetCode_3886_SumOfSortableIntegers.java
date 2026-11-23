package LeetCode.Arrays;

import java.util.*;

public class LeetCode_3886_SumOfSortableIntegers {

    public static void main(String[] args) {
        System.out.println(sortableIntegersKMP(new int[]{1, 2, 3, 4}));
        System.out.println(sortableIntegersRotate(new int[]{2, 1, 4, 3}));
        System.out.println(sortableIntegersOptimal(new int[]{3, 4, 1, 2}));
    }

    /* Approach 1: KMP over a doubled block
       Enumerate every divisor d of n, split nums into blocks of size d.
       A block can be rearranged into sorted order iff its sorted version is a
       substring of (block + block) -- which KMP answers in O(d).
       Example: nums = [2,1,4,3], d = 2 -> [2,1] -> [1,2], [4,3] -> [3,4] -> valid.
    */
    static int sortableIntegersKMP(int[] nums) {
        int n = nums.length;

        List<Integer> divisors = new ArrayList<>();
        for (int x = 1; x * x <= n; x++) {
            if (n % x == 0) {
                divisors.add(x);
                if (n / x != x) divisors.add(n / x);
            }
        }

        int[] target = nums.clone();
        Arrays.sort(target);

        int ans = 0;

        for (int d : divisors) {
            boolean allGood = true;

            for (int left = 0; left < n; left += d) {
                int right = left + d - 1;
                int[] block  = Arrays.copyOfRange(nums,   left, right + 1);
                int[] sorted = Arrays.copyOfRange(target, left, right + 1);

                if (!kmpContains(doubled(block), sorted)) {
                    allGood = false;
                    break;
                }
            }

            if (allGood) ans += d;
        }

        return ans;
    }

    // Does `pattern` occur inside `text`? Standard KMP, O(|text| + |pattern|).
    static boolean kmpContains(int[] text, int[] pattern) {
        int n = text.length, m = pattern.length;

        int[] pi = new int[m];
        int j = 0;
        for (int i = 1; i < m; i++) {
            while (j > 0 && pattern[i] != pattern[j]) j = pi[j - 1];
            if (pattern[i] == pattern[j]) j++;
            pi[i] = j;
        }

        j = 0;
        for (int i = 0; i < n; i++) {
            while (j > 0 && text[i] != pattern[j]) j = pi[j - 1];
            if (text[i] == pattern[j]) j++;
            if (j == m) return true;
        }
        return false;
    }

    static int[] doubled(int[] a) {
        int[] res = new int[a.length * 2];
        System.arraycopy(a, 0, res, 0, a.length);
        System.arraycopy(a, 0, res, a.length, a.length);
        return res;
    }

    /* Approach 2: Descent-then-rotate
       Same block decomposition, but the rotation check is pure O(d) with no KMP:
       find the single index i where block[i] > block[i+1], rotate the block at
       i+1 and compare with the sorted block. No descent -> already sorted.
       Example: [3,4,1,2], d = 2 -> [3,4] and [1,2] both sorted,
                but 4 > 1 so the blocks are out of order -> d = 2 invalid.
    */
    static int sortableIntegersRotate(int[] nums) {
        int n = nums.length;

        List<Integer> divisors = new ArrayList<>();
        for (int x = 1; x * x <= n; x++) {
            if (n % x == 0) {
                divisors.add(x);
                if (n / x != x) divisors.add(n / x);
            }
        }

        int[] target = nums.clone();
        Arrays.sort(target);

        int ans = 0;

        for (int d : divisors) {
            boolean allGood = true;

            for (int left = 0; left < n; left += d) {
                int right = left + d - 1;
                int[] block  = Arrays.copyOfRange(nums,   left, right + 1);
                int[] sorted = Arrays.copyOfRange(target, left, right + 1);

                if (!canRotateToSorted(block, sorted)) {
                    allGood = false;
                    break;
                }
            }

            if (allGood) ans += d;
        }

        return ans;
    }

    // True iff some cyclic rotation of `block` equals `sorted`.
    static boolean canRotateToSorted(int[] block, int[] sorted) {
        int L = block.length;

        for (int i = 0; i < L - 1; i++) {
            if (block[i] > block[i + 1]) {
                int[] rotated = new int[L];
                System.arraycopy(block, i + 1, rotated, 0, L - i - 1);
                System.arraycopy(block, 0, rotated, L - i - 1, i + 1);
                return Arrays.equals(rotated, sorted);
            }
        }
        return Arrays.equals(block, sorted);
    }

    /* Approach 3: Single-pass per divisor (optimal)
       No sub-arrays at all. For each candidate block size k, scan nums once and
       count cyclic descents inside every block, tracking each block's min/max.
       k is valid iff
         (a) every block has at most one cyclic descent (so it is a rotation of
             its sorted version), and
         (b) max(previous block) <= min(current block) (blocks are in order).
       Example: [2,1,4,3], k = 2 -> block1 descents {2>1} = 1, block2 {4>3} = 1,
                max(block1) = 2 <= min(block2) = 3 -> valid.
    */
    static int sortableIntegersOptimal(int[] nums) {
        int n = nums.length, ans = 0;

        for (int k = 1; k * k <= n; k++) {
            if (n % k != 0) continue;

            if (isValidBlockSize(nums, k)) ans += k;

            int other = n / k;
            if (other != k && isValidBlockSize(nums, other)) ans += other;
        }

        return ans;
    }

    static boolean isValidBlockSize(int[] nums, int k) {
        int n = nums.length, prevMax = Integer.MIN_VALUE;

        for (int i = 0; i < n; i += k) {
            int descents = 0;
            int mini = nums[i], maxi = nums[i];

            for (int j = i; j < i + k - 1; j++) {
                if (nums[j] > nums[j + 1]) descents++;
                mini = Math.min(mini, nums[j + 1]);
                maxi = Math.max(maxi, nums[j + 1]);
            }

            // wrap-around descent: last -> first inside the block
            if (nums[i + k - 1] > nums[i]) descents++;

            if (descents > 1 || prevMax > mini) return false;
            prevMax = maxi;
        }

        return true;
    }
}

/* Complexity Analysis
   Let n = nums.length and tau(n) = number of divisors of n (<= ~100 for n <= 1e5).

   Approach 1: KMP over doubled block
     Time : O(n * tau(n))  - O(sqrt n) divisor enumeration, then n/d blocks of
            O(d) KMP work per divisor d -> O(n) per divisor.
     Space: O(n)           - doubled block, KMP pi array, sorted copy.
     Key Observation: sorted(block) is a rotation of block iff it is a substring
     of block+block, so rotation is a substring search.

   Approach 2: Descent-then-rotate
     Time : O(n * tau(n))  - same decomposition, rotation check is O(d) with a
            smaller constant than KMP.
     Space: O(n)           - sub-arrays + rotated buffer.
     Key Observation: a sequence is a rotation of its sorted version iff it has
     at most one descent. Rotate at that descent and compare directly.

   Approach 3: Single-pass per divisor (recommended)
     Time : O(n * tau(n))  - one O(n) scan per divisor, no sub-array allocation.
     Space: O(1)           - only descent counter + min/max + prevMax.
     Key Observation: validity factors into (a) each k-block has at most one
     cyclic descent, and (b) max(previous block) <= min(current block). Both
     can be checked in the same pass.
*/
