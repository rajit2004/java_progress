package searching;

import java.util.Arrays;
import java.util.Scanner;

public class SearchIn2dArray {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] arr = {{3, -1, 7, 2, -9, 14},
                {-5, 8, 0, -2, 11, -13, 6},
                {15, -3, 4, 9, -7, 16, -10, 1},
                {-8, 17, -4},
                {18, -6, 5, 12, -11, 19, -14, 20, 21},
                {13, -15, 22, -16, 23},
                {-18, 24, -19, 25, -20, 26}};
        System.out.println("Array : " + Arrays.deepToString(arr));
        System.out.print("Enter elements to search : ");
        int target = in.nextInt();

        System.out.println(find(arr , target));

    }
    static String find(int[][] arr , int target){
        for(int[] row : arr){
            for(int ele : row){
                if(ele == target)
                    return "Found";
            }
        }
        return "Not found";
    }
}
