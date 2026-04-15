package Arrays;
import java.util.Arrays;
public class modification {
    public static void main(String[] args) {
        String[] arr = {"apple" , "banana" , "pear" , "grapes" , "orange"};
        System.out.print("Array : ");
        System.out.println(Arrays.toString(arr));
        System.out.print("Modifying element at index 0 : ");
        arr[0] = "pineapple";
        System.out.println(Arrays.toString(arr));
    }
}
