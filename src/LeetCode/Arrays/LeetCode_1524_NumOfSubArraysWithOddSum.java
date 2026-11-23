package LeetCode.Arrays;

public class LeetCode_1524_NumOfSubArraysWithOddSum {

    public static void main(String[] args) {
        int[] arr = {1, 3, 5};
        System.out.println(numOfSubarraysBrute(arr));
        System.out.println(numOfSubarraysDP(arr));
        System.out.println(numOfSubarraysPrefixParity(arr));
    }

    /* Approach 1: Brute force -- enumerate every subarray
       Try every start index, extend end forward accumulating the running sum,
       and count whenever that sum is odd.
       Example: [1,3,5] -> odd-sum subarrays [1], [1,3,5], [3], [5] -> 4.
    */
    static int numOfSubarraysBrute(int[] arr) {
        final int MOD = 1_000_000_007;
        int n = arr.length;
        int count = 0;

        for (int start = 0; start < n; start++) {
            int sum = 0;
            for (int end = start; end < n; end++) {
                sum += arr[end];
                if (sum % 2 != 0) count++;
            }
        }

        return count % MOD;
    }

    /* Approach 2: DP on subarray parity
       dp[parity][slot] = number of subarrays ending at the previous/current
       position whose sum has the given parity (0 even, 1 odd). For each arr[i]:
         - subarrays whose sum parity equals arr[i]&1: start a new one ([i]) or
           extend every EVEN-parity subarray ending at i-1.
         - subarrays whose sum parity is the opposite: extend every OPPOSITE-
           parity subarray ending at i-1.
       Add dp[1][current slot] to the answer each step.
       Example: [1,3,5] -> counts per position = [1, 1, 2] -> answer 4.
    */
    static int numOfSubarraysDP(int[] arr) {
        final int MOD = 1_000_000_007;
        int[][] dp = new int[2][2];   // dp[parity][slot]
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            int slot   = i & 1;         // alternates 0,1,0,1,...
            int parity = arr[i] & 1;    // 1 if arr[i] is odd, 0 if even

            dp[parity][slot]       = (1 + dp[0][1 - slot]) % MOD;
            dp[1 - parity][slot]   = dp[1][1 - slot] % MOD;

            count = (count + dp[1][slot]) % MOD;
        }

        return count;
    }

    /* Approach 3: Prefix-sum parity counting (optimal)
       A subarray sum is odd iff its two prefix sums have opposite parity.
       Walk the array keeping prefixSum and the counts of even / odd prefix
       sums seen so far (evenCount starts at 1 for the empty prefix, sum 0).
       When the current prefix sum is even, every odd prefix count contributes
       an odd-sum subarray; when odd, every even prefix count does.
       Example: [1,3,5] -> running prefix parities o,e,o -> contributes 1+1+2 = 4.
    */
    static int numOfSubarraysPrefixParity(int[] arr) {
        final int MOD = 1_000_000_007;
        int count = 0, prefixSum = 0;
        int oddCount = 0, evenCount = 1;   // empty prefix has even sum 0

        for (int num : arr) {
            prefixSum += num;
            if (prefixSum % 2 == 0) {
                count += oddCount;
                evenCount++;
            } else {
                count += evenCount;
                oddCount++;
            }
            count %= MOD;
        }

        return count;
    }
}

/* Complexity Analysis
   Let n = arr.length.

   Approach 1: Brute force
     Time : O(n^2)  - every start/end pair is enumerated once.
     Space: O(1)    - running sum and counter only.
     Key Observation: directly check the sum parity of every subarray.

   Approach 2: DP on subarray parity
     Time : O(n)    - single pass, constant work per element.
     Space: O(1)    - 2x2 dp table plus a counter.
     Key Observation: only the parity (not the value) of the running sum
     matters, so two counters per ending position are enough to extend every
     prior subarray.

   Approach 3: Prefix-sum parity counting (recommended)
     Time : O(n)    - single pass.
     Space: O(1)    - two parity counters.
     Key Observation: subarray sum is odd iff its two prefix sums have opposite
     parity, so the answer is sum over i of (opposite-parity prefix count
     seen before i).
*/