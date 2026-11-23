package LeetCode.Arrays;

import java.util.*;

public class LeetCode_1385_FindDistanceValueBtwTwoArrays {

    public static void main(String[] args) {
        int[] arr1 = {4, 5, 8};
        int[] arr2 = {10, 9, 1, 8};
        int d = 2;
        System.out.println(findTheDistanceValueBrute(arr1, arr2, d));
        System.out.println(findTheDistanceValueBinarySearch(arr1, arr2, d));
    }

    /* Approach 1: Brute force -- compare every pair
       For each x in arr1, check every y in arr2. x contributes 1 to the
       answer iff |x - y| > d for every y. Accumulate the count.
       Example: arr1 = [4,5,8], arr2 = [10,9,1,8], d = 2
                4 and 5 are far from every arr2 value -> count 2.
                8 has |8-8| = 0 <= d -> not counted -> answer 2.
    */
    static int findTheDistanceValueBrute(int[] arr1, int[] arr2, int d) {
        int count = 0;

        for (int x : arr1) {
            boolean ok = true;
            for (int y : arr2) {
                if (Math.abs(x - y) <= d) {
                    ok = false;
                    break;
                }
            }
            if (ok) count++;
        }

        return count;
    }

    /* Approach 2: Sort arr2 + binary search for the nearest value
       Sort arr2 once. For each x in arr1, use lower_bound to find the first
       index p with arr2[p] >= x. The only candidates that can violate
       |x - y| > d are arr2[p] (just >= x) and arr2[p - 1] (just < x); if both
       satisfy |x - y| > d, then every arr2 element does, because all others
       are farther away. Count x if both checks pass.
       Example: arr1 = [4,5,8], arr2 sorted = [1,8,9,10], d = 2
                x = 4 -> p = 1 (arr2[1] = 8), candidates 8 and 1, both > 2 -> count
                x = 5 -> p = 1, candidates 8 and 1, both > 2 -> count
                x = 8 -> p = 1 (arr2[1] = 8), |8 - 8| = 0 -> skip
                answer 2.
    */
    static int findTheDistanceValueBinarySearch(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr2);
        int count = 0;

        for (int x : arr1) {
            int p = lowerBound(arr2, x);
            boolean ok = true;

            if (p < arr2.length) {
                ok &= arr2[p] - x > d;
            }
            if (p - 1 >= 0) {
                ok &= x - arr2[p - 1] > d;
            }

            if (ok) count++;
        }

        return count;
    }

    // First index i with arr[i] >= target, or arr.length if none.
    static int lowerBound(int[] arr, int target) {
        int low = 0, high = arr.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] < target) low = mid + 1;
            else                   high = mid;
        }

        return low;
    }
}

/* Complexity Analysis
   Let n = arr1.length, m = arr2.length.

   Approach 1: Brute force -- compare every pair
     Time : O(n * m)  - each x scans all of arr2, breaking on the first
            violation.
     Space: O(1)      - only the counter and a flag.
     Key Observation: x contributes iff no y satisfies |x - y| <= d; a single
     violation disqualifies x, so the inner loop can break early.

   Approach 2: Sort arr2 + binary search for the nearest value (recommended)
     Time : O(m log m + n log m)  - one sort of arr2, then O(log m) per x.
     Space: O(1)                  - sort in place, no auxiliary arrays (beyond
            the sorted copy of arr2).
     Key Observation: after sorting arr2, only arr2[lower_bound(x)] and
     arr2[lower_bound(x) - 1] can be the closest values to x, so it suffices
     to test these two neighbours.
*/

