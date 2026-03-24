package arrays;
import java.util.Scanner;
import java.util.Arrays;
public class SwappingElementsArray {
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

        System.out.print("Enter the index you want to swap elements with :");
        int x1= in.nextInt();
        int x2 = in.nextInt();

        swap(array , x1 ,x2);

    }
    static void swap(int[] arr , int index1 , int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

        System.out.println("Swapped array : " + Arrays.toString(arr));
    }
}
