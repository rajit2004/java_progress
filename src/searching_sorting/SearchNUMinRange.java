package searching_sorting;

import java.util.Arrays;
import java.util.Scanner;

public class SearchNUMinRange{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int [] arr = {-12, 45, -7, 89, -23, 56, -91, 34, -18, 67, 2, -78, 39, -50, 6, 99, -27, 41, -73, 15};
        System.out.println(Arrays.toString(arr));

        System.out.print("Enter the target element : ");
        int target = in.nextInt();

        System.out.print("Enter the range to search within (st end) : ");
        int index1 = in.nextInt();
        int index2 = in.nextInt();

        System.out.println(find(arr , target , index1 , index2));
    }
    static String find(int[] arr , int target , int i1 , int i2){

        if(i1<0 || i2>=arr.length || i1>i2)
            return "invalid range!";

        for(int i = i1 ; i < i2 ; i++ ){
            if(arr[i] == target)
                return "Found at index :" + i;
        }
        return "Not found!";
    }
}