package Sorting;

import java.util.Arrays;

public class ALT_MergeSort {
    public static void main(String[] args) {
        int[] arr = {5,4,3,2,1};
        System.out.println(Arrays.toString(sort(arr)));
    }
    static int[] sort(int[] arr){

//        base case = when array is null or have just a single ele
        if(arr == null || arr.length < 2)
            return arr;

//        now we do partition
        int mid = arr.length/2;

        int[] left = sort(Arrays.copyOfRange(arr , 0 , mid));
        int[] right = sort(Arrays.copyOfRange(arr , mid , arr.length));

        return merge(left,right);
    }
    static int[] merge(int[] first , int[] last){

//        create the ans array
        int[] ans = new int[first.length + last.length];
//        to access the index of ans array
        int k = 0;

//        to access the index of the divided arrays
        int i = 0;
        int j = 0;

//        checking the eles:

        while(i < first.length && j < last.length){

            if (first[i] < last[j]){
                ans[k] = first[i];
                i++;
            }
            else{
                ans[k] = last[j];
                j++;
            }
            k++;
        }
//        for the left eles:
        while(i != first.length){
            ans[k] = first[i];
            i++;
            k++;
        }
        while(j != last.length){
            ans[k] = last[j];
            j++;
            k++;
        }
        return ans;
    }
}
