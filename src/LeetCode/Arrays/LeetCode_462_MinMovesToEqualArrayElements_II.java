package LeetCode.Arrays;

import java.util.*;

public class LeetCode_462_MinMovesToEqualArrayElements_II {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(minMoves2SortAverage(nums));
        System.out.println(minMoves2SortUpperMedian(nums));
        System.out.println(minMoves2QuickSelect(nums));
    }

    /* Approach 1: Sort + average of two middle values
       The sum of absolute deviations is minimised at any median. For even n,
       every value between the two middle elements gives the same minimum, so
       taking their average (integer division) is also optimal. Sort, compute
       that median, then sum |nums[i] - median|.
       Example: [1,2,3] -> median 2 -> 1 + 0 + 1 = 2.
                [1,2,3,4] -> median (2+3)/2 = 2 -> 1+0+1+2 = 4.
    */
    static int minMoves2SortAverage(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;
        int median = (n % 2 == 0)
                ? (nums[n / 2] + nums[n / 2 - 1]) / 2
                : nums[n / 2];

        int moves = 0;
        for (int x : nums) moves += Math.abs(median - x);
        return moves;
    }

    /* Approach 2: Sort + upper median (optimal simple)
       Any median minimises the sum of absolute deviations. For even n, the
       upper middle element nums[n/2] itself is a median (and lies inside the
       optimal interval), so we do not need the average — just pick it and
       sum |nums[i] - median|.
       Example: [1,2,3] -> median 2 -> 2.
                [1,2,3,4] -> median 3 -> 2+1+0+1 = 4.
    */
    static int minMoves2SortUpperMedian(int[] nums) {
        Arrays.sort(nums);
        int median = nums[nums.length / 2];

        long moves = 0;
        for (int x : nums) moves += Math.abs(x - median);
        return (int) moves;
    }

    /* Approach 3: Quickselect + sum absolute deviations (optimal fastest)
       Sorting is O(n log n); we only need the k-th smallest (k = n/2) which is
       an upper median and is optimal. Use randomised 3-way quickselect for
       expected O(n) selection, then sum |nums[i] - median|.
       Example: [1,2,3] -> k=1 -> median 2 -> 2.
                [1,2,3,4] -> k=2 -> median 3 -> 4.
    */
    static int minMoves2QuickSelect(int[] nums) {
        int median = quickselect(nums, nums.length / 2);
        int moves = 0;
        for (int x : nums) moves += Math.abs(x - median);
        return moves;
    }

    static int quickselect(int[] a, int k) {
        int lo = 0, hi = a.length - 1;

        while (true) {
            int pivot = a[lo + new Random().nextInt(hi - lo + 1)];
            int lt = lo, i = lo, gt = hi;

            while (i <= gt) {
                if (a[i] < pivot)      swap(a, lt++, i++);
                else if (a[i] > pivot) swap(a, i, gt--);
                else                   i++;
            }

            if (k < lt)      hi = lt - 1;
            else if (k > gt) lo = gt + 1;
            else             return pivot;
        }
    }

    static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}

/* Complexity Analysis
   Let n = nums.length.

   Approach 1: Sort + average of two middle values
     Time : O(n log n)  - dominated by the sort.
     Space: O(1)        - sort in place, only scalars used.
     Key Observation: for even n, any value between the two middle elements
     minimises the sum of absolute deviations; the truncated average lies in
     that interval, so it is safe.

   Approach 2: Sort + upper median (recommended simple)
     Time : O(n log n)  - sort dominates.
     Space: O(1)        - sort in place.
     Key Observation: nums[n/2] is always a valid minimiser for both odd and
     even n, so a single index lookup replaces the average calculation.

   Approach 3: Quickselect + sum absolute deviations (recommended fastest)
     Time : O(n) expected - randomised 3-way quickselect selects the k-th
            smallest in expected linear time.
     Space: O(1)         - in-place partitioning.
     Key Observation: we only need the k-th smallest (k = n/2), not a full
     sort, so selection beats sorting asymptotically.
*/

