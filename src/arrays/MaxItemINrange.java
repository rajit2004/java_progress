package arrays;

import java.util.Scanner;
import java.util.Arrays;

public class MaxItemINrange {
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
        System.out.print("Enter the range of check (st end) : ");
        int x1 = in.nextInt();
        int x2 = in.nextInt();
        maxRange(array , x1 , x2);
    }
    static void maxRange(int[] arr , int index1 , int index2){
        int max_item = arr[index1];
        int index = index1;
        for(int i=index1 ; i<= index2 ; i++) {
            if (max_item < arr[i]) {
                max_item = arr[i];
                index++;
            }
        }
        System.out.println("Maximum item in array : " + max_item + " at index : " + index);
    }

}
