package LeetCode.DynamicProgramming;

import java.util.*;

public class LeetCode_3041_MaximizeConsecutiveElementsInArrayAfterModification {

    public static void main(String[] args) {
        int[] nums = {2, 1, 5, 1, 1};
        System.out.println(maxSelectedElementsBrute(nums));
        System.out.println(maxSelectedElementsHashMap(nums));
    }

    /* Approach 1: Sort + O(n^2) DP
       Sort nums. For each element i keep two chain-ending states:
         dp[i][0] = longest consecutive chain ending at value nums[i], with
                    element i kept as-is.
         dp[i][1] = longest consecutive chain ending at value nums[i]+1, with
                    element i bumped.
       For each i, scan all j < i and extend from the best j whose chain-end
       value is exactly one less than the current start:
         dp[i][0] extends dp[j][0] if nums[j]     == nums[i] - 1
         dp[i][0] extends dp[j][1] if nums[j] + 1 == nums[i] - 1
         dp[i][1] extends dp[j][0] if nums[j]     == nums[i]
         dp[i][1] extends dp[j][1] if nums[j] + 1 == nums[i]
       Answer is max over all dp[i][0] / dp[i][1]. O(n^2) time, fine for small
       inputs.
       Example: [2,1,5,1,1] -> sorted [1,1,1,2,5] -> best chain {1,2,3} = 3.
    */
    static int maxSelectedElementsBrute(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);

        int[][] dp = new int[n][2];
        int ans = 0;

        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;  // chain of one element {nums[i]}
            dp[i][1] = 1;  // chain of one element {nums[i]+1}

            for (int j = 0; j < i; j++) {
                // extend to dp[i][0] -- chain ends at nums[i], i kept
                if (nums[j] == nums[i] - 1) {
                    dp[i][0] = Math.max(dp[i][0], dp[j][0] + 1);
                }
                if (nums[j] + 1 == nums[i] - 1) {
                    dp[i][0] = Math.max(dp[i][0], dp[j][1] + 1);
                }
                // extend to dp[i][1] -- chain ends at nums[i]+1, i bumped
                if (nums[j] == nums[i]) {
                    dp[i][1] = Math.max(dp[i][1], dp[j][0] + 1);
                }
                if (nums[j] + 1 == nums[i]) {
                    dp[i][1] = Math.max(dp[i][1], dp[j][1] + 1);
                }
            }

            ans = Math.max(ans, Math.max(dp[i][0], dp[i][1]));
        }

        return ans;
    }

    /* Approach 2: Sort + DP with HashMap (optimal)
       Same idea, but collapse the O(n^2) inner scan into O(1) map lookups.
       Let dp[v] = longest consecutive chain ending at value v using elements
       processed so far. For each element x (in sorted order):
         dp[x+1] = max(dp[x+1], dp[x] + 1)     // bump x to x+1
         dp[x]   = max(dp[x],   dp[x-1] + 1)   // keep x as x
       Both updates must use the OLD dp[x] / OLD dp[x-1] so the current element
       is never counted twice (dp[x+1] first with old dp[x], then dp[x] with
       old dp[x-1]).
       Example: [2,1,5,1,1] -> sorted [1,1,1,2,5] -> answer 3.
    */
    static int maxSelectedElementsHashMap(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Integer> dp = new HashMap<>();
        int ans = 0;

        for (int x : nums) {
            // use OLD dp[x] to bump x -> x+1
            dp.put(x + 1, Math.max(dp.getOrDefault(x + 1, 0), dp.getOrDefault(x, 0) + 1));
            // use OLD dp[x-1] to keep x as x
            dp.put(x, Math.max(dp.getOrDefault(x, 0), dp.getOrDefault(x - 1, 0) + 1));

            ans = Math.max(ans, Math.max(dp.get(x), dp.get(x + 1)));
        }

        return ans;
    }
}

/* Complexity Analysis
   Let n = nums.length.

   Approach 1: Sort + O(n^2) DP
     Time : O(n^2)  - O(n log n) sort + O(n^2) nested scan.
     Space: O(n)    - dp table of n x 2 ints.
     Key Observation: two chain-ending states per element (kept or bumped);
     extending from any earlier j whose chain-end value equals the current
     start - 1 covers every valid transition.

   Approach 2: Sort + DP with HashMap (recommended)
     Time : O(n log n)  - one sort + O(n) HashMap operations.
     Space: O(n)        - dp map holds at most 2n distinct values.
     Key Observation: dp[v] captures the best chain ending at v; per element
     x we get both choices (keep x, or bump x->x+1) with a single O(1) update
     each, reading the old dp[x] / dp[x-1] so x is never counted twice.
*/

