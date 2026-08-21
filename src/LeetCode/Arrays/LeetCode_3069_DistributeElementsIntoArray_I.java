package LeetCode.Arrays;

import java.util.ArrayList;
import java.util.Arrays;

public class LeetCode_3069_DistributeElementsIntoArray_I {
    public static void main(String[] args) {

        int[] nums = {2, 1, 3, 3};
        System.out.println(Arrays.toString(resultArray(nums)));
    }

    /*
        Greedy + ArrayList Approach : We need to distribute the elements into two arrays according to the following rule:

        If the last element of array1 is greater than the last element of array2, add the current element to array1.
        Otherwise, add it to array2.

        The first element always goes to array1 and the second element always goes to array2.

        Finally, concatenate array1 and array2 into the original nums array.
     */
    static int[] resultArray(int[] nums) {

        // Store elements assigned to the first array.
        ArrayList<Integer> a1 = new ArrayList<>();

        // Store elements assigned to the second array.
        ArrayList<Integer> a2 = new ArrayList<>();

        // First element goes to the first array.
        a1.add(nums[0]);

        // Second element goes to the second array.
        a2.add(nums[1]);

        /*
            Process the remaining elements.
            Compare the last elements of both arrays and place the current element accordingly.
         */
        for (int i = 2; i < nums.length; i++) {

            // Add to array1 if its last element is larger.
            if (a1.get(a1.size() - 1) > a2.get(a2.size() - 1))
                a1.add(nums[i]);
            else
                // Otherwise, add to array2.
                a2.add(nums[i]);
        }

//            Copy array1 back into the beginning of the original nums array.
        for (int i = 0; i < a1.size(); i++)
            nums[i] = a1.get(i);

//            Append array2 after all elements of array1 to form the final result.
        for (int i = 0; i < a2.size(); i++)
            nums[i + a1.size()] = a2.get(i);

        return nums;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = nums.length

---------------------------------------------------------

Time Complexity: O(n)

Reason: The input array is traversed once to distribute the elements. After that, array1 and array2 are copied back into nums, which together takes O(n).

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

Two ArrayLists are used to store all elements before placing them back into nums.
Together they contain exactly n elements.

Overall: O(n)

---------------------------------------------------------

Key Observation:

Only the last element of each array is required to decide where the next element should go.
Therefore, for every nums[i]: if last(a1) > last(a2) add to a1 else add to a2
After processing all elements, concatenate a1 followed by a2.

---------------------------------------------------------
*/