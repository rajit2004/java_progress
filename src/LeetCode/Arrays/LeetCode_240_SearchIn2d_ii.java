package LeetCode.Arrays;

public class LeetCode_240_SearchIn2d_ii {
    public static void main(String[] args) {
        int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        int target = 5;
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
