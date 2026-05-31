package Recursion.Questions.Arrays;

// see if the given array is sorted or not

public class SortArray {
    public static void main(String[] args) {
        int[] arr = {1,2,4,8,9,12};
//        System.out.println(isSorted(arr));
        System.out.println(sorted(arr));
    }

//    iterative approach :
    static boolean isSorted(int[] arr){
        int i = 0;
        while(i < arr.length - 1){
            if(arr[i] > arr[i + 1])
                return false;               // found an ele for which i is not smaller than i+1 so we instantly say its unsorted
            i++;
        }
        return true;
    }

//    recursive approach :
    static boolean sorted(int[] arr){
        if (arr == null || arr.length == 0)
            return true;            // empty array is sorted

//        call the helper fn to recursively check the array:
        return helper(arr,0);
    }

    static boolean helper(int[] arr, int index){

//        base condition : when we reach last element -> array was sorted
        if(index == arr.length -1)
            return true;

//        when array is not sorted -> nTH ele is greater than n+1TH ele
        if(arr[index] > arr[index+1])
            return false;

//        recursive call : iTh was sorted so we check further :
        return helper(arr , index + 1);
    }
}
