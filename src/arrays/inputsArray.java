package arrays;
import java.util.Arrays;
public class inputsArray {
    public static void main(String[] args) {
        String[] arr = new String[5];
        System.out.println(Arrays.toString(arr));   //empty so will have the default value "null"
        arr[0] = "Apple";       // this is how we assign values when we have declared and initialized the array already
        arr[1] = "Banana";
        arr[2] = "Mango";
        arr[3] = "Orange";
        arr[4] = "Grapes";
        System.out.println(Arrays.toString(arr));


//        other ways to assign the values

        int[] arr2 = new int[] {1,2,3,4,5}; // when we have to assign later we use new keyword
        int[] arr3 = {1,2,3,4,5,65,6,7,8};  //direct assignment
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(arr3));
        int[] arr4 = new int[5];
        arr4[0] = 1;
        arr4[1] = 45;
        arr4[2] = 234;
        arr4[3] = 5445;
        arr4[4] = 12323;
        System.out.println(Arrays.toString(arr4));

    }
}
