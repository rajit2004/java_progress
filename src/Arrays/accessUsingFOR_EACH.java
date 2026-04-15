package Arrays;
import java.util.Scanner;
import java.util.Arrays;
public class accessUsingFOR_EACH {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of elements : ");
        int n = in.nextInt();
        String[] array = new String[n];

        for (int i = 0; i < array.length; i++) {
            System.out.print("Enter the element : ");
            array[i] = in.next();

        }

        for(String index : array) {
            System.out.print(index + " ");
        }
        System.out.println();

        System.out.println(Arrays.toString(array));
    }
}
