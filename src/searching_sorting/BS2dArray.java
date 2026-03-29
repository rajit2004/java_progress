package searching_sorting;

//also called staircase search

import java.util.Arrays;

public class BS2dArray {
    public static void main(String[] args) {
        int target = 24;
        int[][] array = {{1, 4, 7, 11, 15, 20},
                {2, 5, 8, 12, 19, 25},
                {3, 6, 9, 16, 22, 27},
                {10, 13, 14, 17, 24, 30}};
        System.out.println(Arrays.toString(search(array,target)));
    }
    static int[] search(int[][] matrix , int target){
        int row = 0;
        int col = matrix[0].length-1;

        while (row < matrix.length && col >= 0){
            if(matrix[row][col] == target){
                return new int[] {row,col};
            }
            if(matrix[row][col] > target)
                col--;
            else
                row++;
        }
        return new int[]{-1,-1};        // not found
    }
}
