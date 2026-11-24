package LeetCode.Enum;

public class LeetCode_3411_MaxSubArrayWithEqualProducts {

    public static void main(String[] args) {
        System.out.println(maxLengthSlidingWindow(new int[]{1, 2, 3, 4}));
        System.out.println(maxLengthBrute(new int[]{1, 2, 3, 4}));
        System.out.println(maxLengthBruteNoBreak(new int[]{1, 2, 3, 4}));
    }

    /* Approach 1: Sliding window over pairwise-coprime elements (optimal)
       The identity prod = gcd * lcm holds for any subarray whose elements
       are pairwise coprime (then gcd = 1 and lcm = prod). Maintain a window
       of pairwise-coprime elements:
         - while gcd(runningProduct, nums[end]) > 1, nums[end] shares a factor
           with something already in the window, so shrink from the left;
         - multiply nums[end] into the product;
         - update the answer with the current window length.
       Every 2-element window already satisfies the identity, which is why
       res starts at 2 and the pairwise-coprime window only needs to track
       longer candidates.
       Example: [1,2,3,4] -> window [1,2,3] has prod 6 = gcd 1 * lcm 6
                -> answer 3.
    */
    static int maxLengthSlidingWindow(int[] nums) {
        int n = nums.length;
        int res = 2;
        int prodVal = 1;
        int start = 0, end = 0;

        while (end < n) {
            while (gcd(prodVal, nums[end]) > 1) {
                prodVal /= nums[start];
                start++;
            }
            prodVal *= nums[end];
            res = Math.max(res, end - start + 1);
            end++;
        }

        return res;
    }

    /* Approach 2: Brute force with early break
       For each start i, extend j while tracking running prod, gcd and lcm.
       Once prod > gcd * lcm the window can never become equal again — any
       later extension keeps prod strictly greater (pairwise coprimality is
       the only way back to equality, and adding elements cannot restore it
       for length >= 3) — so break early. When prod == gcd * lcm, update the
       answer.
       Example: [1,2,3,4] -> windows [1,2,3] match -> answer 3.
    */
    static int maxLengthBrute(int[] nums) {
        int res = 0;

        for (int i = 0; i < nums.length; i++) {
            long prodVal = nums[i];
            long gcdVal  = nums[i];
            long lcmVal  = nums[i];

            for (int j = i + 1; j < nums.length; j++) {
                prodVal *= nums[j];
                gcdVal = gcd(gcdVal, nums[j]);
                lcmVal = lcm(lcmVal, nums[j]);

                if (prodVal > gcdVal * lcmVal) break;
                if (prodVal == gcdVal * lcmVal) res = Math.max(res, j - i + 1);
            }
        }

        return res;
    }

    /* Approach 3: Brute force without break
       Same triple (prod, gcd, lcm) update per window, but no early exit.
       Simplest to reason about — just checks every (i, j) — and only useful
       when the input is small.
       Example: [1,2,3,4] -> 3 (same as Approaches 1 and 2).
    */
    static int maxLengthBruteNoBreak(int[] nums) {
        int n = nums.length;
        int maxL = 0;

        for (int i = 0; i < n; i++) {
            long currGCD = nums[i];
            long currLCM = nums[i];
            long currPro = nums[i];

            for (int j = i + 1; j < n; j++) {
                currPro *= nums[j];
                currGCD = gcd(currGCD, nums[j]);
                currLCM = lcm(currLCM, nums[j]);

                if (currPro == currLCM * currGCD) {
                    maxL = Math.max(maxL, j - i + 1);
                }
            }
        }

        return maxL;
    }

    static long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}

/* Complexity Analysis
   Let n = nums.length, C = max(nums[i]) (value bound).

   Approach 1: Sliding window over pairwise-coprime elements (recommended)
     Time : O(n * log C)  - each element is pushed once and popped at most
            once; each gcd call is O(log C).
     Space: O(1)          - running product, start, end, res only.
     Key Observation: pairwise coprime implies gcd = 1 and lcm = prod, so the
     window invariant "gcd(runningProduct, nums[end]) == 1" is exactly the
     coprimality condition; any 2-element window already satisfies the
     identity, so the window only needs to detect longer valid runs.

   Approach 2: Brute force with early break
     Time : O(n^2 * log C) worst case, but the break usually fires after a
            few steps because pairwise-coprime windows are short.
     Space: O(1)           - just the running prod/gcd/lcm triple.
     Key Observation: once prod > gcd * lcm, no further extension can restore
     equality (pairwise coprimality is impossible once a common factor is
     present for length >= 3), so breaking is safe.

   Approach 3: Brute force without break
     Time : O(n^2 * log C)  - every (i, j) window is checked.
     Space: O(1)            - running triple only.
     Key Observation: simplest correct version; useful as an oracle to
     validate the two optimised versions, but too slow for large n.
*/

