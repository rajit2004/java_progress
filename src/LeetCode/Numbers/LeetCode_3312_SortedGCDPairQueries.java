package LeetCode.Numbers;

public class LeetCode_3312_SortedGCDPairQueries {
    public static void main(String[] args) {

        int[] nums = {2, 3, 4};

        long[] queries = {0, 2, 3};

        int[] ans = gcdValues(nums, queries);

        for (int num : ans) {
            System.out.print(num + " ");
        }
    }

    /*
        Number Theory + Inclusion-Exclusion + Binary Search Approach :

        Step 1: Count the frequency of every number.

        Step 2: For every possible GCD from largest to smallest, count how many pairs have exactly that GCD. This is done using Inclusion-Exclusion.

        Step 3: Build prefix sums over the GCD counts. prefix[i] = number of pairs having GCD ≤ i

        Step 4: For every query, use Binary Search to find the smallest GCD whose prefix count exceeds the query index.
    */
    static int[] gcdValues(int[] nums, long[] queries) {

        // Find the maximum value in the array.
        int max = 0;

        for (int num : nums) {
            max = Math.max(max, num);
        }

        // freq[x] stores the frequency of value x.
        int[] freq = new int[max + 1];

        for (int num : nums) {
            freq[num]++;
        }

//            gcdCount[i] stores the number of pairs whose GCD is exactly i.

        long[] gcdCount = new long[max + 1];

        /*
            Compute the number of pairs having exact GCD = i.

            Process from largest to smallest using Inclusion-Exclusion.
         */
        for (int i = max; i >= 1; i--) {

            long count = 0;

            // Count numbers divisible by i.
            for (int j = i; j <= max; j += i) {

                count += freq[j];

                // Remove pairs already counted for larger GCD values.
                gcdCount[i] -= gcdCount[j];
            }

            // Total pairs divisible by i.
            gcdCount[i] += count * (count - 1) / 2;
        }

        /*
            Convert exact counts into prefix sums.

            gcdCount[i] now represents the number of pairs having GCD ≤ i.
         */
        for (int i = 1; i <= max; i++) {

            gcdCount[i] += gcdCount[i - 1];
        }

        int[] ans = new int[queries.length];

        // Answer every query independently.
        for (int i = 0; i < queries.length; i++) {

            long query = queries[i];

            int left = 1;
            int right = max;

//                Binary Search for the smallest GCD whose prefix count exceeds the query.

            while (left < right) {

                int mid = left + (right - left) / 2;

                if (gcdCount[mid] > query) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            ans[i] = left;
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = nums.length

m = maximum value in nums

q = queries.length

---------------------------------------------------------

Time Complexity: O(m log m + q log m)

Reason:

1. Building the frequency array: O(n)

2. Computing exact GCD counts using Inclusion-Exclusion: O(m log m)

3. Building prefix sums: O(m)

4. Binary Search for each query: O(log m)

Overall: O(m log m + q log m)

---------------------------------------------------------

Space Complexity: O(m)

Reason:

Two arrays of size (max + 1) are maintained:

1. freq[]
2. gcdCount[]

Overall: O(m)

---------------------------------------------------------

Key Observation:

Instead of computing the GCD of every pair,
count pairs for every possible GCD using
multiples and Inclusion-Exclusion.

After converting these counts into prefix sums,
each query becomes a simple Binary Search,
reducing the overall complexity dramatically.

---------------------------------------------------------
*/