package LeetCode.Greedy;

import java.util.Arrays;

public class LeetCode_1846_MaxEleAfterDecreasingAndRearranging {
    public static void main(String[] args) {

        int[] arr = {2, 2, 1, 2, 1};

        System.out.println(maximumElementAfterDecrementingAndRearranging(arr));
    }

    /*
        Greedy Approach :  Conditions:
                              1. First element must be 1.
                              2. Absolute difference between adjacent elements cannot be greater than 1.

        To maximize the largest element:
            1. Sort the array.
            2. Force the first element to become 1.
            3. For every next element:  current <= previous + 1

        If an element is larger than (previous + 1), decrease it to (previous + 1).
        Since we are only allowed to decrease elements, this greedy strategy gives the maximum possible final element.
     */

    static int maximumElementAfterDecrementingAndRearranging(int[] arr) {

        // Sort elements in ascending order.
        Arrays.sort(arr);

        // First element must always be 1.
        arr[0] = 1;

        // Adjust remaining elements.
        for (int i = 1; i < arr.length; i++) {

            /*
                Current element cannot exceed previous element + 1.

                Example: [1, 2, 7] becomes [1, 2, 3]
             */
            arr[i] = Math.min(arr[i], arr[i - 1] + 1);
        }

        // The last element will be the maximum possible value.
        return arr[arr.length - 1];
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = arr.length

---------------------------------------------------------

Time Complexity: O(n log n)

Reason:

1. Sorting the array takes O(n log n).
2. Single traversal to adjust elements takes O(n).

Overall: O(n log n)

---------------------------------------------------------

Space Complexity: O(1)

Reason: Ignoring the space used internally by the sorting algorithm, we only modify the input array in-place.

No additional data structures are used.

Overall: O(1)

---------------------------------------------------------

Key Observation:

To maximize the largest element, every element should be as large as possible while satisfying: arr[i] <= arr[i - 1] + 1

Sorting ensures smaller values are processed first, allowing us to greedily construct the maximum valid sequence.

---------------------------------------------------------
*/