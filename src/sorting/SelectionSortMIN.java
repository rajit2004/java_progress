package sorting;
import java.util.Arrays;
public class SelectionSortMIN {
    public static void main(String[] args) {
        int[] array = {12, -5, 0, 23, -17, 8, -1, 34, -9, 2, 19, -12, 7, -3, 25, -20, 4, -7, 15, -2};
        System.out.println(Arrays.toString(sort(array)));
    }
    static void swap(int[] arr , int first , int second){
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
    static int getMinIndex(int[] arr , int start , int last){
        int min = start;
        for (int i = start; i <= last ; i++) {
            if(arr[i]<arr[min])
                min = i;
        }
        return min;
    }
    static int[] sort(int[] arr){
        for(int i = 0 ; i<arr.length;i++){
            int start = i ;
            int end = arr.length-1;
            int minIndex = getMinIndex(arr , start , end );
            swap(arr , minIndex , start);
        }
        return arr;
    }
}
