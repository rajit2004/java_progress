package searching_sorting;

import java.util.Arrays;

public class MaxIn2dArray {
    public static void main(String[] args) {
        int[][] nums = {{10, -3, 25}, {7, 14}, {0, -9, 8, 21}, {5}, {32, -11, 6, 19, 4}, {17, 2}};

        System.out.println("Array : " + Arrays.deepToString(nums));
        System.out.println(max(nums));
        System.out.println(min(nums));

    }
    static String max(int[][] arr){
        int max = arr[0][0];
        for(int[] row : arr){
            for(int ele : row){
                if(ele > max)
                    max = ele;
            }
        }
        return "Max element : " + max;
    }
    static String min(int[][] arr){
        int min = arr[0][0];
        for(int[] row : arr){
            for(int ele : row){
                if(ele < min)
                    min = ele;
            }
        }
        return "Min element : " + min;
    }
}
