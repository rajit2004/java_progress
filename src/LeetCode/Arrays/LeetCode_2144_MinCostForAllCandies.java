package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_2144_MinCostForAllCandies {
    public static void main(String[] args) {
        int[] cost = {6,5,7,9,2,2};
        System.out.println(minimumCost(cost));
    }
    static int minimumCost(int[] cost){
        Arrays.sort(cost);      // array is sorted

        int min = 0;
        int count = 0;

        for (int i = cost.length - 1; i >= 0; i--) {            // traverse from the largest candy
            count++;                                            // takes 2

//            we buy 2 candies and get the third one for free :

            if (count % 3 == 0)
                continue;       // free

            min += cost[i];         // cost of whichever candy bought
        }

        return min;
    }
}
