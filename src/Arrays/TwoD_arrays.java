package Arrays;
import java.util.Arrays;
import java.util.Scanner;
public class TwoD_arrays {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        declaration , initialization , assignment :
//        int[][] arr = {{1,2} , {3,4,5}, {6,7,8,9,10}};    // this declares the no of elements in rows n cols but when assigning values
//        using for each value is overwritten

//        to avoid this we can do this instead :

        int[][] arr = new int[3][];     //initializes a 2d array of 3 rows  (3 elements)
        arr[0] = new int[2];            // initializes a 1-d on 0th index   (1st element)
        arr[1] = new int[3];            // initializes a 1-d on 1st index   (2nd element)
        arr[2] = new int[5];            // initializes a 1-d on 2nd index   (3rd element)


//        inputs :
        for(int row = 0 ; row < arr.length ;row++){
            for(int col = 0 ; col < arr[row].length ; col++){
                System.out.print("Enter element at (" + row + "," + col + ") : ");
                arr[row][col] = in.nextInt();
            }
        }
//        System.out.println(Arrays.deepToString(arr));
//        outputs:
        for(int[] row : arr){                       // iterates over each array in multi-d array
            for(int col : row){                     // same as 1-d array . iterates over all elements of a 1-d array
                System.out.print(col + " ");
            }
            System.out.println();
        }
        for(int[] row : arr){
            System.out.println(Arrays.toString(row));
        }
    }
}
