package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode_3020_MaxNumOfEleInSubset {

    public static void main(String[] args) {

        int[] nums = {5, 4, 1, 2, 2};

        System.out.println(maximumLength(nums));
    }

    /*
        Greedy + Sorting Approach :

        Sort the array and store distinct elements along with their frequencies.
        For every distinct number x: x -> x² -> x⁴ -> x⁸ ...

        Try to build the longest possible chain.

        Rules:
            1. Every element except the middle element must appear at least twice.
            2. The subset length must always be odd.
            3. If an element appears only once, it becomes the middle element and the chain stops.

        Special Case: Number 1 always squares to itself, so the answer is simply the largest odd frequency of 1.
     */

    static int maximumLength(int[] nums) {

        Arrays.sort(nums);

        // Stores distinct values.
        List<Long> values = new ArrayList<>();

        // Stores frequency of corresponding values.
        List<Integer> freq = new ArrayList<>();

        // Build frequency list.
        for (int num : nums) {

            if (values.isEmpty() || values.get(values.size() - 1) != num) {
                values.add((long) num);
                freq.add(1);
            } else {
                freq.set(freq.size() - 1,
                        freq.get(freq.size() - 1) + 1);
            }
        }

        int ans = 1;

        // Try starting a chain from every distinct value.
        for (int i = 0; i < values.size(); i++) {

            long x = values.get(i);

            // Special handling for 1.
            if (x == 1) {

                int cnt = freq.get(i);

                // Length must always be odd.
                if (cnt % 2 == 0)
                    cnt--;

                ans = Math.max(ans, cnt);
                continue;
            }

            int len = 0;
            long curr = x;

            // Build chain: x -> x² -> x⁴ -> ...
            while (true) {

                int idx = binarySearch(values, curr);

                // Current value does not exist.
                if (idx == -1)
                    break;

//                    If current number appears at least twice, it can contribute two elements to the chain.
                if (freq.get(idx) >= 2) {
                    len += 2;
                }

//                    If it appears once, it can only act as the middle element.
                else if (freq.get(idx) == 1) {
                    len += 1;
                    break;
                }

                else {
                    break;
                }

                // Move to next square.
                curr = curr * curr;

//                    Prevent overflow and unnecessary work.
                if (curr > 1_000_000_000L) {

                    // Last pair cannot be completed.
                    len--;
                    break;
                }
            }

//                If chain ended before finding a valid middle, remove one element from the last pair to keep the subset length odd.
            if (len > 0 && len % 2 == 0) {
                len--;
            }

            ans = Math.max(ans, len);
        }

        return ans;
    }

//        Binary Search on distinct values list. Returns: index of target if present -1 otherwise

    static int binarySearch(List<Long> values, long target) {

        int left = 0;
        int right = values.size() - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (values.get(mid) == target)
                return mid;

            else if (values.get(mid) < target)
                left = mid + 1;

            else
                right = mid - 1;
        }

        return -1;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:
n = nums.length
m = number of distinct values

---------------------------------------------------------

Time Complexity: O(n log n)

Reason:

1. Sorting the array: O(n log n)

2. Building frequency lists: O(n)

3. For each distinct value, perform binary searches along the squaring chain.

   Since values grow exponentially: x -> x² -> x⁴ -> ... the chain length is very small (at most ~5). Therefore total complexity remains dominated by sorting.

Overall: O(n log n)

---------------------------------------------------------

Space Complexity: O(m)

Reason: Two lists are maintained:

1. values  -> distinct numbers
2. freq    -> frequencies

In the worst case: m = n

Hence: O(n)

---------------------------------------------------------

Key Observation:

A valid subset must always have odd length.

All elements except the middle element require a matching pair.

Therefore:

frequency >= 2 -> contributes 2 elements
frequency == 1 -> contributes only the middle element

Special handling is required for number 1 because: 1² = 1 which creates an infinite chain.

---------------------------------------------------------
*/
