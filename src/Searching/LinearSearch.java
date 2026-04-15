package Searching;

import java.util.Scanner;
import java.util.Arrays;

public class LinearSearch {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the size of array : ");
        int n = in.nextInt();
        String[] array = new String[n];

        for (int i = 0; i< array.length ; i++){
            System.out.print("Enter the element at index " + i + " : ");
            array[i] = in.next();
        }
        System.out.println("Entered array : " + Arrays.toString(array));

        System.out.print("Enter the target word : ");
        String x = in.next();
        System.out.println(linearsearch(array , x));
    }

    static String linearsearch(String[] arr, String target) {
        if (arr.length == 0)
            return "Empty!";

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) {
                return "Found at index " + i;
            }
        }
        return "Not found!";
    }
}
