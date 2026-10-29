package LeetCode.Arrays;

import java.util.*;

public class LeetCode_2089_FindTargetIndicesAfterSortingArray {
    public static void main(String[] args) {
        System.out.println(targetIndices(new int[]{1, 2, 5, 2, 3}, 2));   // [1, 2]
        System.out.println(targetIndices(new int[]{1, 2, 5, 2, 3}, 3));   // [3]
    }


    /*
        Approach 1: Counting (no sort, O(n))

        After sorting, all elements equal to target occupy a contiguous block
        starting at position `cnt1` (number of elements < target) and ending
        at position `cnt1 + cnt2 - 1` (where cnt2 = count of elements == target).

        So we don't need to actually sort — just count and return the range.

        Time:  O(n)
        Space: O(1) auxiliary (output not counted)
    */
    static List<Integer> targetIndices(int[] nums, int target) {
        int cnt1 = 0;   // number of elements < target
        int cnt2 = 0;   // number of elements == target

        for (int num : nums) {
            if (num < target)      cnt1++;
            else if (num == target) cnt2++;
        }

        List<Integer> res = new ArrayList<>();
        for (int i = cnt1; i < cnt1 + cnt2; i++) {
            res.add(i);
        }
        return res;
    }


    /*
        Approach 2: Sort then scan

        Sort the array, then collect all indices where nums[i] == target.

        Time:  O(n log n)
        Space: O(1) auxiliary beyond the sort
    */
    static List<Integer> targetIndicesSort(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] == target) res.add(i);
        }
        return res;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach 1: Counting

Time Complexity: O(n)
- Single pass to count elements < target and == target.
- Filling the result range is O(k), where k is the number of matches.

Space Complexity: O(1) auxiliary
- Only two counters; the result list is required output.

Approach 2: Sort then scan

Time Complexity: O(n log n)
- Dominated by the sort.
- Scan afterward is O(n).

Space Complexity: O(1) auxiliary
- Sort is in-place (Java uses O(log n) stack for DualPivotQuicksort, but
  asymptotically O(log n) at worst; typically considered O(1) for simple arrays).

Key Observation:
The sorted positions of `target` form a contiguous block starting right
after all elements strictly less than it. So counting elements < target
gives the start index, and counting elements == target gives the block
length — no actual sorting needed. This drops the complexity from
O(n log n) to O(n).

---------------------------------------------------------
*/