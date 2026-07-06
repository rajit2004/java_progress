package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_506_RelativeRank {
    public static void main(String[] args) {

        int[] score = {5, 4, 3, 2, 1};

        System.out.println(Arrays.toString(findRelativeRanks(score)));
    }

    /*
        Sorting Approach :

        Store every athlete's:

            1. Score
            2. Original Index

        Sort the athletes in descending order of score.

        Assign:

            1st -> Gold Medal
            2nd -> Silver Medal
            3rd -> Bronze Medal

        Remaining athletes receive their ranking number.

        Finally, place each rank back at its original index.
     */

    static String[] findRelativeRanks(int[] score) {

        int n = score.length;

        // Stores the final ranks.
        String[] ans = new String[n];

        /*
            pair[i][0] -> score
            pair[i][1] -> original index
         */
        int[][] pair = new int[n][2];

        // Store scores along with their original indices.
        for (int i = 0; i < n; i++) {
            pair[i][0] = score[i];
            pair[i][1] = i;
        }

        // Sort athletes by score in descending order.
        Arrays.sort(pair, (a, b) -> b[0] - a[0]);

        // Assign ranks according to sorted order.
        for (int i = 0; i < n; i++) {

            int originalIndex = pair[i][1];

            if (i == 0) {
                ans[originalIndex] = "Gold Medal";
            }

            else if (i == 1) {
                ans[originalIndex] = "Silver Medal";
            }

            else if (i == 2) {
                ans[originalIndex] = "Bronze Medal";
            }

            // Remaining athletes receive their rank number.
            else {
                ans[originalIndex] = String.valueOf(i + 1);
            }
        }

        return ans;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = score.length

---------------------------------------------------------

Time Complexity: O(n log n)

Reason:

1. Creating the score-index pairs:
   O(n)

2. Sorting the pairs:
   O(n log n)

3. Assigning the ranks:
   O(n)

Overall:

O(n log n)

---------------------------------------------------------

Space Complexity: O(n)

Reason:

Additional data structures used:

1. pair[][] array
2. answer[] array

Both require O(n) space.

Overall:

O(n)

---------------------------------------------------------

Key Observation:

Sorting changes the original order of athletes.

Therefore, store each athlete's original index
before sorting.

After assigning ranks in sorted order,
place each rank back into its original position.

---------------------------------------------------------
*/