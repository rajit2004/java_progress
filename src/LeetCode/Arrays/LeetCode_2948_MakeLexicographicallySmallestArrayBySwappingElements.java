package LeetCode.Arrays;

import java.util.*;

public class LeetCode_2948_MakeLexicographicallySmallestArrayBySwappingElements {
    public static void main(String[] args) {

        int[] nums = {1, 5, 3, 9, 8};
        int limit = 2;

        LeetCode_2948_MakeLexicographicallySmallestArrayBySwappingElements sol =
                new LeetCode_2948_MakeLexicographicallySmallestArrayBySwappingElements();

        System.out.println(
                Arrays.toString(sol.lexicographicallySmallestArray(nums, limit))
        );
    }

    /*
        Greedy + Sorting + Grouping Approach: Two elements can be swapped when their values differ by at most 'limit'.

        After sorting the array, whenever the difference between two consecutive values is greater than limit, they cannot belong to the same swappable group.

        Therefore:
            1. Sort a copy of nums.
            2. Divide the sorted values into groups.
            3. Elements belonging to the same group can be rearranged freely.
            4. For every original element, take the smallest
               remaining value from its group.

        This produces the lexicographically smallest array.
     */
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {

        // Create a sorted copy so that the original array can still be used to construct the final answer.
        int[] numsSorted = new int[nums.length];

        for (int i = 0; i < nums.length; i++)
            numsSorted[i] = nums[i];

        // Sort values to identify connected groups.
        Arrays.sort(numsSorted);

        int currGroup = 0;

        /*
            Map each value to its group.

            Example:
                sorted = [1, 3, 5, 8, 9]
                limit = 2
                [1, 3, 5] -> group 0
                [8, 9]    -> group 1
         */
        HashMap<Integer, Integer> numToGroup = new HashMap<>();
        numToGroup.put(numsSorted[0], currGroup);

        /*
            For every group, maintain its values in sorted order.

            LinkedList is used as a queue so that the smallest available value can be removed from the front.
         */
        HashMap<Integer, LinkedList<Integer>> groupToList = new HashMap<>();

        groupToList.put(currGroup,new LinkedList<>(Arrays.asList(numsSorted[0])));

        // Build the groups from the sorted array.
        for (int i = 1; i < nums.length; i++) {

//      If the gap between consecutive sorted values is greater than limit, they cannot be swapped with each other, so start a new group.
            if (Math.abs(numsSorted[i] - numsSorted[i - 1]) > limit)
                currGroup++;

            // Assign the current value to its group.
            numToGroup.put(numsSorted[i], currGroup);

            // Create the group if it does not exist yet.
            if (!groupToList.containsKey(currGroup))
                groupToList.put(currGroup, new LinkedList<>());

            // Add the value in sorted order.
            groupToList.get(currGroup).add(numsSorted[i]);
        }

        /*
            Reconstruct the answer.

            For each original element:
                1. Find its group.
                2. Take the smallest unused value from that group.

            Since we process nums from left to right, putting the smallest available value at each position gives the lexicographically smallest possible array.
         */
        for (int i = 0; i < nums.length; i++) {

            int num = nums[i];

            // Find the group containing this value.
            int group = numToGroup.get(num);

            // Take the smallest remaining value from that group.
            nums[i] = groupToList.get(group).removeFirst();
        }

        return nums;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n log n)

1. Copying the array: O(n)
2. Sorting: O(n log n)
3. Creating groups: O(n)
4. Reconstructing the answer: O(n)

Overall: O(n log n)

---------------------------------------------------------

Space Complexity: O(n)

We maintain:
    1. Sorted copy of nums
    2. numToGroup HashMap
    3. groupToList HashMap

Overall: O(n)

---------------------------------------------------------

Key Observation:

After sorting, if: numsSorted[i] - numsSorted[i - 1] <= limit then the two values belong to the same swappable group.
If the difference is greater than limit, a new group must begin.
Every element inside one group can effectively be rearranged among the positions occupied by that group.
Therefore, for every original position, we simply place the smallest unused value from its corresponding group.

---------------------------------------------------------
*/
