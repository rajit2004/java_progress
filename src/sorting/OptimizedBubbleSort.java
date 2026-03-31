package sorting;

import java.util.Arrays;

public class OptimizedBubbleSort {
    public static void main(String[] args) {
        System.out.println("case 1 : ");
        int[] array = {1,2,3,4,5};
        System.out.println(Arrays.toString(bubble(array)));
        System.out.println("Case 2 : ");
        int[] arr2 = {5,3,7,1,0,23};
        System.out.println(Arrays.toString(bubble(arr2)));
    }

    static void swap(int[] arr , int first , int second){
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    static int[] bubble(int[] arr){
        boolean swaped = false;

        for (int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j]>arr[j+1]){
                    swap(arr,j,j+1);
                    swaped=true;
                }
            }
            if(!swaped) {
                System.out.println("optimized solution");
                break;
            }
        }
        return arr;
    }
}


