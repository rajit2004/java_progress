package LeetCode.Arrays;

public class LeetCode_2091_RemovingMinimumAndMaxFromArray {
    public static void main(String[] args) {

        int[] nums = {2, 10, 7, 5, 4, 1, 8, 6};

        LeetCode_2091_RemovingMinimumAndMaxFromArray solution =
                new LeetCode_2091_RemovingMinimumAndMaxFromArray();

        System.out.println(
                solution.minimumDeletions(nums)
        );
    }

    /*
        Greedy Approach: We need to remove both the minimum and maximum elements from the array.

        There are only three possible strategies:
            1. Remove both from the left.
            2. Remove both from the right.
            3. Remove the minimum from the left and the maximum from the right (or vice versa).

        We find the positions of minimum and maximum first, then calculate the cost of all three possibilities.

        Example: nums = [2, 10, 7, 5, 4, 1, 8, 6]
            min = 1  -> index 5
            max = 10 -> index 1

            l = 1
            r = 5

            Option 1: Remove from left until index r , Cost = r + 1
            Option 2: Remove from right starting from index l , Cost = n - l
            Option 3: Remove left part through l and right part starting from r , Cost = l + 1 + n - r

        Take the minimum of these three costs.
     */
    public int minimumDeletions(int[] nums) {

        int n = nums.length;
        int minidx = 0;
        int maxidx = 0;

        // Find the indices of the minimum and maximum elements.
        for (int i = 0; i < n; i++) {

            if (nums[i] < nums[minidx])
                minidx = i;
            if (nums[i] > nums[maxidx])
                maxidx = i;
        }

        /*
            l = index of the element that occurs first.
            r = index of the element that occurs later.

            This simplifies the three deletion cases.
         */
        int l = Math.min(minidx, maxidx);
        int r = Math.max(minidx, maxidx);

        /*
            Option 1: Remove everything from the left up to the later element.
            Number of deletions = r + 1
         */
        int removeFromLeft = r + 1;

        /*
            Option 2: Remove everything from the right starting from the earlier element.
            Number of deletions = n - l
         */
        int removeFromRight = n - l;

        /*
            Option 3: Remove the earlier element from the left and the later element from the right.
            Left deletions  = l + 1
            Right deletions = n - r
         */
        int removeBothSides = l + 1 + n - r;

        // Choose the strategy requiring the fewest deletions.
        return Math.min(Math.min(removeFromLeft, removeFromRight),removeBothSides);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

We scan the array once to find the minimum and maximum elements.
After that, all calculations take O(1).

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Only a few integer variables are used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

Only the positions of the minimum and maximum elements matter.

If: l = earlier index and r = later index
then there are only three meaningful ways to remove them:
    1. Both from the left: r + 1
    2. Both from the right: n - l
    3. One from each side: l + 1 + n - r

Take the minimum of these three values.

---------------------------------------------------------
*/
