package arrays;

import java.util.Scanner;
import java.util.Arrays;

public class MaxItem {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of elements in the array : ");
        int n = in.nextInt();
        int[] array = new int[n];

        for(int  i = 0 ; i<n ; i++){
            System.out.print("Enter the element at " + i + " index : ");
            array[i] = in.nextInt();
        }
        System.out.println("Entered Array : " + Arrays.toString(array));

        max(array);
    }
    static void max(int[] arr){
        int max_item = arr[0];
        for(int i=1 ; i< arr.length ; i++) {
            if (max_item < arr[i])
                max_item = arr[i];
        }
        System.out.println("Maximum item in array : " + max_item);
    }
}
