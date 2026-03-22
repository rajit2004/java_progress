package arrays;
import java.util.Arrays;
import java.util.Scanner;
public class input_using_for {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[] arr = new int[5];
        for (int i=0 ; i <arr.length ;i++ ){
            System.out.print("Enter the element : ");
            arr[i] = input.nextInt();
        }
        System.out.print("Array : ");
        System.out.println(Arrays.toString(arr));

        for (int j : arr) {     // enhanced for loop to iterate and access elements
            System.out.print(j + " ");
        }
    }
}


// enhanced for = for (data_type reference_var : '[in]' array){ print reference_var)