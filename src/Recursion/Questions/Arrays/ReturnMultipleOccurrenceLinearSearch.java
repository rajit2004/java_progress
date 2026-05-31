package Recursion.Questions.Arrays;

import java.util.ArrayList;

public class ReturnMultipleOccurrenceLinearSearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,4,4};
        System.out.println(find(arr,4));
    }

//    this helper fn finds all the indexes where we find the target.
    static ArrayList<Integer> helper(int[] arr , int target , int index){

//        base condition :
        if (index == arr.length)
            return new ArrayList<>();

//        we have to store the index wherever we find the target:
        ArrayList<Integer> ans = helper(arr , target , index + 1);

        if(arr[index] == target) {
            ans.add(0,index);
        }
        return ans;
    }
    static ArrayList<Integer> find(int[] arr , int target){
        if(arr == null || arr.length == 0)
            return new ArrayList<>();

        return helper(arr , target , 0);
    }
}
