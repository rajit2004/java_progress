package searching;

import java.util.Arrays;
import java.util.Scanner;

public class Enhanced_SearchIn2dArray {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] arr = {{1,2,3},{4,5,6,7,8},{9,11,53,12,87,20,43},{69},{35,61,88,97}};
        System.out.println("Array : " + Arrays.deepToString(arr));
        System.out.print("Enter elements to search : ");
        int target = in.nextInt();

        System.out.println(Arrays.toString(find(arr , target)));

    }
    static int[] find(int[][] arr , int target){
        for(int row = 0 ; row<arr.length ; row++){
            for(int col = 0 ; col<arr[row].length ; col++){
                if(arr[row][col] == target)
                    return new int[] {row , col};
            }
        }
        return new int[] {-1,-1};       // index -1 = not found
    }
}