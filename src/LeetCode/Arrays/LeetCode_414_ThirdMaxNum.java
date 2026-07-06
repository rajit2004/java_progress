package LeetCode.Arrays;

public class LeetCode_414_ThirdMaxNum {
    public static void main(String[] args) {

        int[] nums = {2, 2, 3, 1};

        System.out.println(thirdMax(nums));
    }

    /*
        Greedy Approach :

        Maintain the three largest distinct numbers seen so far.

        While traversing the array:

            1. Ignore duplicate values.
            2. Update first, second and third maximums whenever needed.

        At the end:

            - If the third maximum exists, return it.
            - Otherwise, return the largest element.
     */

    static int thirdMax(int[] nums) {

        // Stores the first, second and third distinct maximum values.
        Long first = null;
        Long second = null;
        Long third = null;

        for (int num : nums) {

            // Ignore duplicate values.
            if ((first != null && num == first) ||
                    (second != null && num == second) ||
                    (third != null && num == third)) {
                continue;
            }

            /*
                Current number becomes the largest.

                Shift previous first and second
                maximums one position down.
             */
            if (first == null || num > first) {

                third = second;
                second = first;
                first = (long) num;
            }

            /*
                Current number becomes the
                second largest distinct value.
             */
            else if (second == null || num > second) {

                third = second;
                second = (long) num;
            }

            /*
                Current number becomes the
                third largest distinct value.
             */
            else if (third == null || num > third) {

                third = (long) num;
            }
        }

        // If fewer than three distinct values exist,
        // return the maximum element.
        if (third == null) {
            return first.intValue();
        }

        return third.intValue();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason:

Traverse the array only once.

Each iteration performs a constant number
of comparisons and assignments.

Overall:

O(n)

---------------------------------------------------------

Space Complexity: O(1)

Reason:

Only three variables are maintained:

first
second
third

No extra data structures are used.

Overall:

O(1)

---------------------------------------------------------

Key Observation:

There is no need to sort the array.

By maintaining the three largest distinct
values while traversing the array once,
we can directly obtain the answer in linear time.

Duplicates are ignored to ensure only
distinct maximum values are considered.

---------------------------------------------------------
*/