package Searching;

public class LinearSearchInMatrix {
    public static void main(String[] args) {
        int[][] arr = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int target = 51;
        System.out.println(find(arr , target));
    }
    static String find(int[][] matrix , int target){

        if(matrix.length==0)
            return "Matrix is empty";
        for(int[] rows : matrix){
            for(int ele : rows){
                if(target == ele)
                    return "found";
            }
        }
        return "does not exists";
    }
}
