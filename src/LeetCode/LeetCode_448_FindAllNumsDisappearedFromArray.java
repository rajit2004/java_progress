package LeetCode;

/*
Given an array nums of n integers where nums[i] is in the range [1, n],
 return an array of all the integers in the range [1, n] that do not appear in nums.
*/



public class LeetCode_448_FindAllNumsDisappearedFromArray {
    public static void main(String[] args) {
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println();
    }
    static void swap(int[] arr , int index1 , int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
    static int[] missingNum(int[] arr){

//        step 1 = cycle sort
        int i = 0;
        while(i < arr.length){
            int correct = arr[i]-1;
            if(arr[i] != arr[correct])
                swap(arr,i,correct);
            else
                i++;
        }
        return arr;
    }
}
