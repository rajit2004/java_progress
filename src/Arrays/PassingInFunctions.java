package Arrays;
import java.util.Arrays;
public class PassingInFunctions {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        System.out.println(Arrays.toString(nums));
        change(nums);
    }
    static void change(int[] arr){
        arr[0] = 12;
        arr[4] = 33;
        System.out.println(Arrays.toString(arr));
    }
}
