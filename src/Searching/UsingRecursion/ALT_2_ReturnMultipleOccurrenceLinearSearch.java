package Searching.UsingRecursion;

import java.util.ArrayList;

public class ALT_2_ReturnMultipleOccurrenceLinearSearch {
    public static void main(String[] args) {
        int[] arr = {1,2,34,5,6,7,5,3,2,9,2,1,9,2};
        System.out.println(find(arr,2));
    }
    static ArrayList<Integer> helper (int[] arr , int target , int index , ArrayList<Integer> ans){

//        base condition :
        if(index == arr.length-1)
            return ans;

        if(arr[index] == target)
            ans.add(index);

        return helper(arr , target , index+1 , ans);
    }
    static ArrayList<Integer> find(int[] arr , int target){

//        base condition :
        if(arr == null || arr.length == 0)
            return new ArrayList<>();

        return helper(arr , target , 0 , new ArrayList<>());
    }
}
