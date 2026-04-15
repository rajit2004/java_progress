// this kind of problem is known as two pointers method .
//here we use two pointers st and end

package Arrays;

import java.util.Scanner;
import java.util.Arrays;

public class ArrayReversal {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of elements in the array : ");
        int n = in.nextInt();

        int[] array = new int[n];

        for(int i = 0 ; i < array.length ; i++){
            System.out.print("Enter the element at " + i + " index : ");
            array[i] = in.nextInt();
        }
        System.out.println("Entered array : " + Arrays.toString(array));

        reverse(array);

    }
    static void reverse(int[] arr){
        int st = 0;
        int end = arr.length - 1;

        while (st<end){
            int temp = arr[st];
            arr[st] = arr[end];
            arr[end] = temp;

            st++;
            end--;
        }
        System.out.println("Array after reversal : " + Arrays.toString(arr));
    }
}
