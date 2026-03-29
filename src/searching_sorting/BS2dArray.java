package searching_sorting;

//also called staircase search

import java.util.Arrays;

public class BS2dArray {
    public static void main(String[] args) {
        int target = 37;
        int[][] array = {{10,20,30,40},{15,25,35,45},{28,29,37,49},{33,34,38,50}};
        System.out.println(Arrays.toString(search(array,target)));
    }
    static int[] search(int[][] matrix , int target){
        int row = 0;
        int col = matrix.length-1;

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
