package LeetCode.Arrays;

import java.util.Arrays;
import java.util.HashMap;

public class LeetCode_1331_RankTransformation {
    public static void main(String[] args) {

        int[] arr = {40, 10, 20, 30};

        System.out.println(Arrays.toString(arrayRankTransform(arr)));
    }

    /*
        Sorting + HashMap Approach :

        Copy and sort the original array.

        Assign ranks only to unique elements.

        Store:

            number -> rank

        in a HashMap.

        Finally, replace every element in the original
        array with its corresponding rank.
     */

    static int[] arrayRankTransform(int[] arr) {

        // Create a copy of the original array.
        int[] sorted = arr.clone();

        // Sort the copied array.
        Arrays.sort(sorted);

        // Maps each unique number to its rank.
        HashMap<Integer, Integer> map = new HashMap<>();

        int rank = 1;

        // Assign ranks to unique numbers.
        for (int num : sorted) {

            if (!map.containsKey(num)) {

                map.put(num, rank);

                rank++;
            }
        }

        // Replace every element with its assigned rank.
        for (int i = 0; i < arr.length; i++) {

            arr[i] = map.get(arr[i]);
        }

        return arr;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = arr.length

---------------------------------------------------------

Time Complexity: O(n log n)

Reason:

1. Copying the array:
   O(n)

2. Sorting the copied array:
   O(n log n)

3. Assigning ranks:
   O(n)

4. Replacing original values:
   O(n)

Overall:

O(n log n)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

Additional data structures used:

1. Sorted copy of the array.
2. HashMap storing ranks.

Overall:

O(n)

---------------------------------------------------------

Key Observation:

Sorting places equal values together,
allowing us to assign the same rank to
duplicate elements.

The HashMap provides O(1) lookup, making
it easy to replace every original element
with its corresponding rank.

---------------------------------------------------------
*/