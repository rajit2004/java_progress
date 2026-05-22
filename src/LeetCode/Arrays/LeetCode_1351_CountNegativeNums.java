package LeetCode.Arrays;

public class LeetCode_1351_CountNegativeNums {
    public static void main(String[] args) {
        int[][] grid = {{4,3,2,-1},{3,2,1,-1},{1,1,-1,-2},{-1,-1,-2,-3}};
        System.out.println(countNegatives(grid));
    }
    static int countNegatives(int[][] grid){
        int count = 0;
        for(int[] lines : grid){
            for(int ele : lines){
                if(ele<0)
                    count++;
            }
        }
        return count;
    }
}
