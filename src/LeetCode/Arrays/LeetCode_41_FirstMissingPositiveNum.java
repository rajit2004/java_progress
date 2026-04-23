package LeetCode;

// Given an unsorted integer array nums. Return the smallest positive integer that is not present in nums.

public class LeetCode_41_FirstMissingPositiveNum {
    public static void main(String[] args) {
        int[] nums = {1,2,0};
        System.out.println(missing(nums));
    }

    static void swap(int[] arr , int index1 , int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    static int missing(int[] arr){
//         cyclic sort :
        int i = 0;
        while(i<arr.length){
            int correct = arr[i]-1;
            if(arr[i] > 0 && arr[i]<arr.length && arr[i] != arr[correct])           // arr[i] > 0 && arr[i]<arr.length  this condition makes sure we do not pass -ve values or 0 or values greater than the size of the array itself
                swap(arr,i,correct);
            else
                i++;
        }
//        missing smallest +ve number :
        for(int j = 0 ; j < arr.length ; j++){
            if(arr[j] != j+1)
                return j+1;             // case 1 if n is in the array
        }
        return arr.length + 1;          // case 2 if n is missing from the array
    }
}
