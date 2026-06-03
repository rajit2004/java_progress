package LeetCode.Arrays;

import java.util.Arrays;

public class LeetCode_1051_HeightChecker {
    public static void main(String[] args) {
        int[] arr = {1,1,4,2,1,3};
        System.out.println(heightChecker(arr));
    }
    static int heightChecker(int[] heights){
        int[] sorted = Arrays.copyOfRange(heights, 0 , heights.length);
        Arrays.sort(sorted);
        int count = 0;

        for(int i = 0 ; i < heights.length ; i++){
            if(heights[i] != sorted[i])
                count++;
        }
        return count;
    }
}

/*
time complexities :
    Copy Array  -> O(n)
    Sort Array  -> O(n log n)
    Compare     -> O(n)
space complexity
    O(n)
*/
