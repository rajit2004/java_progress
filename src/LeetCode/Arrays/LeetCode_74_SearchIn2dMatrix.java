package LeetCode.Arrays;

/*
You are given an m x n integer matrix matrix with the following two properties:
Each row is sorted in non-decreasing order.
The first integer of each row is greater than the last integer of the previous row.
Given an integer target, return true if target is in matrix or false otherwise.
*/

public class LeetCode_74_SearchIn2dMatrix {
    public static void main(String[] args) {
        int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        int target = 16;
        System.out.println(searchMatrix(matrix , target));
    }
    static boolean searchMatrix(int[][] matrix, int target) {
        for (int[] ints : matrix) {
            for (int element : ints) {
                if (element == target)
                    return true;
            }
        }
        return false;
    }
}
