package searching_sorting;

import java.util.Arrays;
import java.util.Scanner;

public class SearchIn2dArray {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] arr = {{1,2,3},{4,5,6,7,8},{9,11,53,12,87,20,43},{69},{35,61,88,97}};
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
