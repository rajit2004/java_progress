package Searching;

import java.util.Arrays;

public class LinearSearchMatrixReturnIndex {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int target = 2;
        System.out.println(Arrays.toString(find(matrix , target)));
    }
    static int[] find(int[][] matrix , int target){
        if(matrix.length==0)
            return new int[]{};
        for (int i = 0; i < matrix.length; i++) {
            for(int j = 0 ; j< matrix[i].length ; j++){
                if(target == matrix[i][j])
                    return new int[]{i,j};
            }
        }
        return new int[]{-1,-1};
    }
}
